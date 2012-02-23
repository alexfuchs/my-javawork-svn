/*
 * Copyright 2009 Dukascopy® (Suisse) SA. All rights reserved.
 * DUKASCOPY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.dukascopy.indicators;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.dukascopy.api.Filter;
import com.dukascopy.api.IBar;
import com.dukascopy.api.Period;
import com.dukascopy.api.indicators.BooleanOptInputDescription;
import com.dukascopy.api.indicators.IDrawingIndicator;
import com.dukascopy.api.indicators.IIndicator;
import com.dukascopy.api.indicators.IIndicatorContext;
import com.dukascopy.api.indicators.IIndicatorDrawingSupport;
import com.dukascopy.api.indicators.IndicatorInfo;
import com.dukascopy.api.indicators.IndicatorResult;
import com.dukascopy.api.indicators.InputParameterInfo;
import com.dukascopy.api.indicators.IntegerListDescription;
import com.dukascopy.api.indicators.OptInputParameterInfo;
import com.dukascopy.api.indicators.OutputParameterInfo;
import com.dukascopy.api.indicators.OutputParameterInfo.DrawingStyle;
import com.dukascopy.api.indicators.OutputParameterInfo.Type;

/**
 * Created by: S.Vishnyakov
 * Date: Oct 21, 2009
 * Time: 1:59:16 PM
 */
public class PivotIndicator implements IIndicator, IDrawingIndicator {
	
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT 0"));
	}

    private IndicatorInfo indicatorInfo;
    private InputParameterInfo[] inputParameterInfos;
    private OutputParameterInfo[] outputParameterInfos;
    private OptInputParameterInfo[] optInputParameterInfos;
    
    private IBar[][] inputs = new IBar[2][];
    
    private double[][] outputs = new double[8][];
    private PivotLevel[] finishedCalculationOutputs, drawingOutputs;
    private InputParameterInfo dailyInput;
    private DecimalFormat decimalFormat;
    
    private final GeneralPath generalPath = new GeneralPath(); 
    private List<Point> tmpHandlesPoints = new ArrayList<Point>();
    
    private boolean showHistoricalLevels = false;
    
    private IIndicatorContext context = null;
    
    private int maxDistanceBetweenTwoSeparators;
    private int lastCalculatedOutputSize = Integer.MIN_VALUE;
    
    
    private class PivotLevel {
    	private long time;
    	private double[] values = new double[7];
    	
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public double[] getValues() {
			return values;
		}
    }
    
    
    public void onStart(IIndicatorContext context) {
    	this.context = context;
    	
        indicatorInfo = new IndicatorInfo("PIVOT", "Pivot", "Overlap Studies", true, false, true, 2, 2, 8);
        indicatorInfo.setSparseIndicator(true);
        indicatorInfo.setRecalculateAll(true);
        
        dailyInput = new InputParameterInfo("Input data", InputParameterInfo.Type.BAR);
        dailyInput.setPeriod(Period.DAILY_SUNDAY_IN_MONDAY);
        dailyInput.setFilter(Filter.WEEKENDS);
        inputParameterInfos = new InputParameterInfo[] {
        	new InputParameterInfo("Main Input data", InputParameterInfo.Type.BAR),
            dailyInput
        };
        
        int[] periodValues = new int[10];
        String[] periodNames = new String[10];
        periodValues[0] = 0;
        periodNames[0] = "1 Min";
        periodValues[1] = 1;
        periodNames[1] = "5 Mins";
        periodValues[2] = 2;
        periodNames[2] = "10 Mins";
        periodValues[3] = 3;
        periodNames[3] = "15 Mins";
        periodValues[4] = 4;
        periodNames[4] = "30 Mins";
        periodValues[5] = 5;
        periodNames[5] = "Hourly";
        periodValues[6] = 6;
        periodNames[6] = "4 Hours";
        periodValues[7] = 7;
        periodNames[7] = "Daily";
        periodValues[8] = 8;
        periodNames[8] = "Weekly";
        periodValues[9] = 9;
        periodNames[9] = "Monthly";

        optInputParameterInfos = new OptInputParameterInfo[] {
              new OptInputParameterInfo("Period", OptInputParameterInfo.Type.OTHER, new IntegerListDescription(7 , periodValues, periodNames)),
              new OptInputParameterInfo("Show historical levels", OptInputParameterInfo.Type.OTHER, new BooleanOptInputDescription(showHistoricalLevels))
        };

        outputParameterInfos = new OutputParameterInfo[] {
        	createOutputParameterInfo("Central Point (P)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Resistance (R1)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Support (S1)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Resistance (R2)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Support (S2)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Resistance (R3)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        	createOutputParameterInfo("Support (S3)", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LEVEL_LINE),
        	createOutputParameterInfo("Separators", OutputParameterInfo.Type.DOUBLE, OutputParameterInfo.DrawingStyle.LINE),
        };
        
        decimalFormat = new DecimalFormat("0.00000");
    }
    
    private OutputParameterInfo createOutputParameterInfo(String name, Type type, DrawingStyle drawingStyle) {
    	return new OutputParameterInfo(name, type, drawingStyle, false){{
			setDrawnByIndicator(true);
		}};
    }

    public IndicatorResult calculate(int startIndex, int endIndex) {
    	dailyInput.setFilter(context.getFeedDescriptor().getFilter());

    	resetOutputs(outputs);
    	
    	if (startIndex > endIndex) {
    		return new IndicatorResult(0, 0);
    	}
    	
    	IndicatorResult result = new IndicatorResult(startIndex, endIndex - startIndex + 1);
    	
    	int leftIndexForIndicatorPeriod = getLeftBarIndexForIndicatorPeriod(startIndex) - 1;
    	int rightIndexForIndicatorPeriod = getRightBarIndexForIndicatorPeriod(endIndex);
    	
    	if (leftIndexForIndicatorPeriod < 0) {
    		leftIndexForIndicatorPeriod = 0;
    	}
    	if (rightIndexForIndicatorPeriod < 0) {
    		rightIndexForIndicatorPeriod = inputs[1].length - 1;
    	}
    	
    	if (leftIndexForIndicatorPeriod < 0 || rightIndexForIndicatorPeriod < 0) {
    		/*
    		 * Not enough data for calculations
    		 */
    		return result;
    	}
    	else {
    		IBar previousBar = null;
    		
    		PivotLevel[] innerOutputs = new PivotLevel[rightIndexForIndicatorPeriod - leftIndexForIndicatorPeriod];
    		
    		for (int i = leftIndexForIndicatorPeriod; i <= rightIndexForIndicatorPeriod; i++) {
    			IBar currentBar = inputs[1][i];
    			
    			if (previousBar != null) {
    				int chartPeriodBarIndex = i - leftIndexForIndicatorPeriod - 1;
    				
    				if (innerOutputs[chartPeriodBarIndex] == null) {
    					innerOutputs[chartPeriodBarIndex] = new PivotLevel();
    				}
    				
    				calculateAndSetupPivotValue(innerOutputs, outputs, chartPeriodBarIndex, previousBar, currentBar);
    			}
    			
    			
    			previousBar = currentBar;
    		}
    		
    		lastCalculatedOutputSize = endIndex - startIndex + 1;
    		
    		synchronized(this) {
    			finishedCalculationOutputs = innerOutputs;
    		}
    		
    		return result;
    	}
    }
    
    private void resetOutputs(double[][] outputs) {
    	if (outputs != null) {
    		for (double[] otpts : outputs) {
    			if (otpts != null) {
    				for (int i = 0; i < otpts.length; i++) {
    					otpts[i] = Double.NaN;
    				}
    			}
    		}
    	}
    }
    
    private void calculateAndSetupPivotValue(
    		PivotLevel[] innerOutputs,
    		double[][] outerOutputs,
    		int chartPeriodBarIndex,
    		IBar previousBar,
    		IBar currentBar
    ) {
    	if (previousBar != null) {
        	// P
        	double p = (previousBar.getClose() + previousBar.getHigh() + previousBar.getLow())/3;
        	
        	innerOutputs[chartPeriodBarIndex].getValues()[0] = p;
        	// R1
        	innerOutputs[chartPeriodBarIndex].getValues()[1] = 2 * p - previousBar.getLow();
        	// S1
        	innerOutputs[chartPeriodBarIndex].getValues()[2] = 2 * p - previousBar.getHigh();
        	// R2
        	innerOutputs[chartPeriodBarIndex].getValues()[3] = p + previousBar.getHigh() - previousBar.getLow();
        	// S2
        	innerOutputs[chartPeriodBarIndex].getValues()[4] = p - previousBar.getHigh() + previousBar.getLow();
        	// R3
        	innerOutputs[chartPeriodBarIndex].getValues()[5] = previousBar.getHigh() + 2 * (p - previousBar.getLow());
        	// S3
        	innerOutputs[chartPeriodBarIndex].getValues()[6] = previousBar.getLow() - 2 * (previousBar.getHigh() - p);
        	
        	innerOutputs[chartPeriodBarIndex].setTime(currentBar.getTime());
        	
        	for (int i = 0; i < indicatorInfo.getNumberOfOutputs(); i++) {
        		if (
        				i > -1 && i < outerOutputs.length &&
        				chartPeriodBarIndex > -1 && chartPeriodBarIndex < outerOutputs[i].length
        		) {
        			if (i < indicatorInfo.getNumberOfOutputs() - 1) {
        				outerOutputs[i][chartPeriodBarIndex] = innerOutputs[chartPeriodBarIndex].getValues()[i];
        			}
        			else {
        				outerOutputs[i][chartPeriodBarIndex] = 0;
        			}
        		}
        	}
        	
    	}
    }
    
    private int getRightBarIndexForIndicatorPeriod(int index) {
    	int result = -1;
    	
    	if (index >= 0 && index < inputs[0].length) {
    		IBar bar = inputs[0][index];
    		if (bar != null) {
    			long time = bar.getTime();
    			result = getTimeOrAfterTimeIndex(time, inputs[1]);
    		}
    	}
    	
        return result;
    }
  
	private int getLeftBarIndexForIndicatorPeriod(int index) {
    	int result = -1;
    	
    	if (index >= 0 && index < inputs[0].length) {
    		IBar bar = inputs[0][index];
    		if (bar != null) {
    			long time = bar.getTime();
    			result = getTimeOrBeforeTimeIndex(time, inputs[1]);
    		}
    	}
    	
        return result;
    }
    
    private int getTimeOrAfterTimeIndex(long time, IBar[] target) {
    	if (target == null) {
    		return -1;
    	}

	    int first = 0;
	    int upto = target.length;
	    
	    while (first < upto) {
	        int mid = (first + upto) / 2;
	        int next = mid + 1;
	        
	        IBar data = target[mid];
	        IBar nextBar = next >= 0 && next < target.length ? target[next] : null;
	        
	        if (data.getTime() == time) {
	        	return mid;
	        }
	        else if (nextBar != null && data.getTime() < time && time <= nextBar.getTime()) {
	        	return next;
	        }
        	else if (time < data.getTime()) {
	            upto = mid;
	        } 
	        else if (time > data.getTime()) {
	            first = mid + 1;
	        } 
	    }

		return -1;
	}

	private int getTimeOrBeforeTimeIndex(long time, IBar[] target) {
    	if (target == null) {
    		return -1;
    	}

	    int first = 0;
	    int upto = target.length;
	    
	    while (first < upto) {
	        int mid = (first + upto) / 2;
	        int previous = mid - 1;
	        
	        IBar data = target[mid];
	        IBar previousBar = previous >= 0 && previous < target.length ? target[previous] : null;
	        
	        if (data.getTime() == time) {
	        	return mid;
	        }
	        else if (previousBar != null && previousBar.getTime() <= time && time < data.getTime()) {
	        	return previous;
	        }
        	else if (time < data.getTime()) {
	            upto = mid;
	        } 
	        else if (time > data.getTime()) {
	            first = mid + 1;
	        } 
	    }

		return -1;
	}

    public IndicatorInfo getIndicatorInfo() {
        return indicatorInfo;
    }

    public InputParameterInfo getInputParameterInfo(int index) {
        if (index <= inputParameterInfos.length) {
            return inputParameterInfos[index];
        }
        return null;
    }

    public int getLookback() {
        return 0;
    }

    public int getLookforward() {
        return 0;
    }

    public OptInputParameterInfo getOptInputParameterInfo(int index) {
        if (index <= optInputParameterInfos.length) {
            return optInputParameterInfos[index];
        }
        return null;
    }

    public OutputParameterInfo getOutputParameterInfo(int index) {
        if (index <= outputParameterInfos.length) {
            return outputParameterInfos[index];
        }
        return null;
    }

    public void setInputParameter(int index, Object array) {
        inputs[index] = (IBar[]) array;
    }

    public void setOptInputParameter(int index, Object value) {
    	if (index == 0) {
    		int period = ((Integer) value).intValue();
    		switch (period) {
    		case 0 : dailyInput.setPeriod(Period.ONE_MIN);
    		break;
    		case 1 : dailyInput.setPeriod(Period.FIVE_MINS);
    		break;
    		case 2 : dailyInput.setPeriod(Period.TEN_MINS);
    		break;
    		case 3 : dailyInput.setPeriod(Period.FIFTEEN_MINS);
    		break;
    		case 4 : dailyInput.setPeriod(Period.THIRTY_MINS);
    		break;
    		case 5 : dailyInput.setPeriod(Period.ONE_HOUR);
    		break;
    		case 6 : dailyInput.setPeriod(Period.FOUR_HOURS);
    		break;
    		case 7 : dailyInput.setPeriod(Period.DAILY_SUNDAY_IN_MONDAY);
    		break;
    		case 8 : dailyInput.setPeriod(Period.WEEKLY);
    		break;
    		case 9 : dailyInput.setPeriod(Period.MONTHLY);
    		break;
    		default: dailyInput.setPeriod(Period.DAILY_SUNDAY_IN_MONDAY);
    		}
    	}
    	else if (index == 1) {
    		showHistoricalLevels = Boolean.valueOf(String.valueOf(value)).booleanValue();
    	}
    }

    public void setOutputParameter(int index, Object array) {
        outputs[index] = (double[]) array;
    }

	@Override
	public Point drawOutput(
			Graphics g,
			int outputIdx,
			Object values,
			Color color,
			Stroke stroke,
			IIndicatorDrawingSupport indicatorDrawingSupport,
			List<Shape> shapes,
			Map<Color, List<Point>> handles
	) {
		doDrawOutput(g, outputIdx, values, color, stroke, indicatorDrawingSupport, shapes, handles);
		return null;
	}
	
	private void doDrawOutput(
			Graphics g,
			int outputIdx,
			Object values,
			Color color,
			Stroke stroke,
			IIndicatorDrawingSupport indicatorDrawingSupport,
			List<Shape> shapes,
			Map<Color, List<Point>> handles
	) {
		synchronized(this) {
			drawingOutputs = finishedCalculationOutputs;
		}
		
		if (drawingOutputs == null) {
			return;
		}

		tmpHandlesPoints.clear();
		
		if (values != null) {
			double[] output = (double[]) values;
			
			if (output.length != lastCalculatedOutputSize) {
				/*
				 * This means that data was changed, but not calculated yet - can not draw yet
				 */
				return;
			}
			
			Graphics2D g2 = (Graphics2D) g;
			generalPath.reset();
			
			
			g2.setColor(color);
			g2.setStroke(stroke);
			
			maxDistanceBetweenTwoSeparators = calculateMaxDistanceBetweenTwoSeparators(drawingOutputs, indicatorDrawingSupport);
			
			int fontSize = calculateFontSize(maxDistanceBetweenTwoSeparators, (int)indicatorDrawingSupport.getCandleWidthInPixels());
			boolean drawValues = canDrawValues(fontSize);
			
			if (outputIdx == 7) {
				if (drawValues) {
					drawSeparators(
							drawingOutputs,
							indicatorDrawingSupport, 
							generalPath,
							maxDistanceBetweenTwoSeparators
					);
				}
			}
			else {
				drawPivotLevels(
						g2,
						outputIdx,
						drawingOutputs,
						indicatorDrawingSupport, 
						generalPath,
						fontSize,
						drawValues,
						maxDistanceBetweenTwoSeparators
				);
			}
			
			g2.draw(generalPath);
			
			shapes.add((Shape) generalPath.clone()); // cloning path, so when checking for intersection each indicator has its own path
			handles.put(color, new ArrayList<Point>(tmpHandlesPoints));
		}
		
	}

	
	private void drawSeparators(
			PivotLevel[] innerOutputs,
			IIndicatorDrawingSupport indicatorDrawingSupport,
			GeneralPath generalPath,
			int maxDistanceBetweenTwoSeparators
	) {
		if (innerOutputs == null) {
			return;
		}
		
		int maxWidth = indicatorDrawingSupport.getChartWidth() + maxDistanceBetweenTwoSeparators;
		int maxHeight = indicatorDrawingSupport.getChartHeight();
		
		Integer lastSeparatorX = null;
		
		for (int i = innerOutputs.length - 1; i >= 0; i --) {
			int x = indicatorDrawingSupport.getXForTime(innerOutputs[i].getTime());
			
			if (lastSeparatorX == null) {
				lastSeparatorX = new Integer(x);
			}
			
			if (x < 0) {
				/*
				 * Drawing is from right to left
				 * Stop drawing if we are out of screen
				 */
				break;
			}
			
			drawSeparator(
					generalPath,
					x,
					maxWidth,
					maxHeight
			);
			
			if (!showHistoricalLevels) {
				/*
				 * Don't draw separators further if user don't want them
				 */
				break;
			}
		}
		
		drawSeparator(
				generalPath,
				(lastSeparatorX == null ? 0 : lastSeparatorX.intValue()) + maxDistanceBetweenTwoSeparators,
				maxWidth,
				maxHeight
		);

	}

	private int calculateMaxDistanceBetweenTwoSeparators(
			PivotLevel[] innerOutputs,
			IIndicatorDrawingSupport indicatorDrawingSupport
	) {
		int maxDistance = Integer.MIN_VALUE;
		
		if (innerOutputs == null) {
			return maxDistance;
		}
		
		int previousX = -1;
		
		for (int i = 0; i < innerOutputs.length; i++) {
			int x = indicatorDrawingSupport.getXForTime(innerOutputs[i].getTime());
			if (i > 0) {
				if (x != previousX) {
					int distance = Math.abs(x - previousX);
					
					if (maxDistance < distance) {
						maxDistance = distance;
					}
				}
			}
			previousX = x;
		}
		return maxDistance;
	}

	private void drawPivotLevels(
			Graphics2D g2,
			int outputIdx,
			PivotLevel[] innerOutputs,
			IIndicatorDrawingSupport indicatorDrawingSupport,
			GeneralPath generalPath,
			int fontSize,
			boolean drawValues,
			int maxDistanceBetweenTwoSeparators
	) {
		g2.setFont(new Font(g2.getFont().getName(), g2.getFont().getStyle(), fontSize));
		
		int maxX = indicatorDrawingSupport.getChartWidth() + maxDistanceBetweenTwoSeparators; //JFOREX-2432
		int minX = -maxDistanceBetweenTwoSeparators;
		
		Integer previousX = null;

		for (int i = innerOutputs.length - 1; i >= 0; i --) {
			double d = innerOutputs[i].getValues()[outputIdx];
			
			int x = indicatorDrawingSupport.getXForTime(innerOutputs[i].getTime());
			int y = (int)indicatorDrawingSupport.getYForValue(d);
			
			if (previousX == null) {
				previousX = new Integer(x + maxDistanceBetweenTwoSeparators);
			}
			
			if (
					(minX <= previousX.intValue() && previousX.intValue() <= maxX) ||
					(minX <= x && x <= maxX)
			) {
				generalPath.moveTo(previousX.intValue(), y);
				generalPath.lineTo(x, y);
				
				if (drawValues) {
					String valueStr = decimalFormat.format(d);
					String lineCode = getLineCodeText(outputIdx);
					String result = lineCode + ": " + valueStr;
					
					int lineCodeX = x + 1;
					int distance = Math.abs(x - previousX.intValue());
					int newFontSize = calculateFontSize(distance, (int)indicatorDrawingSupport.getCandleWidthInPixels());
					boolean canDrawValues = this.canDrawValues(newFontSize);
					
					if (canDrawValues) {
						if (newFontSize != fontSize) {
							fontSize = newFontSize;
							g2.setFont(new Font(g2.getFont().getName(), g2.getFont().getStyle(), fontSize));
						}
						g2.drawString(result, lineCodeX, y - 2);
					}
				}
				
				if (!showHistoricalLevels) {
					break;
				}
			}
			else if (
					x > maxX ||
					previousX.intValue() > maxX
			) {
				/*
				 * Means that the last actual period is out of screen, so don't draw anything in case if showHistoricalLevels == false
				 */
				if (!showHistoricalLevels) {
					break;
				}
			}
			
			previousX = Integer.valueOf(x);
		}
	}
	
	private boolean canDrawValues(int fontSize) {
		final int MIN_FONT_SIZE = 4;
		
		if (fontSize <= MIN_FONT_SIZE) {
			return false;
		}
		return true;
	}

	private int calculateFontSize(
			int spaceBetweenTwoSeparators,
			int candleWidthInPixels
	) {
		
		final int MAX_FONT_SIZE = 12;
		final int DIVISION_COEF = 7;
		
		spaceBetweenTwoSeparators /= DIVISION_COEF;
		spaceBetweenTwoSeparators = spaceBetweenTwoSeparators < 0 ? candleWidthInPixels : spaceBetweenTwoSeparators;
		
		return spaceBetweenTwoSeparators > MAX_FONT_SIZE ? MAX_FONT_SIZE : spaceBetweenTwoSeparators;
	}

	private void drawSeparator(
			GeneralPath generalPath,
			int x,
			int maxWidth,
			int maxHeight
	) {
		if (0 <= x && x <= maxWidth) {
			generalPath.moveTo(x, 0);
			generalPath.lineTo(x, maxHeight);
			
			tmpHandlesPoints.add(new Point(x, 5));
			tmpHandlesPoints.add(new Point(x, maxHeight/2));
			tmpHandlesPoints.add(new Point(x, maxHeight - 5));
		}
	}

	private String getLineCodeText(int outputIdx) {
		String lineCode = "";
		switch (outputIdx) {
			case 0 : lineCode = "P"; break;
			case 1 : lineCode = "R1"; break;
			case 2 : lineCode = "S1"; break;
			case 3 : lineCode = "R2"; break;
			case 4 : lineCode = "S2"; break;
			case 5 : lineCode = "R3"; break;
			case 6 : lineCode = "S3"; break;
			default: throw new IllegalArgumentException("Illegal outputIdx - " + outputIdx);
		}
		return lineCode;
	}
	
}