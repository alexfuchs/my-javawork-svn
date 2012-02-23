/*     */ package com.dukascopy.charts.view.drawingstrategies.renko;
/*     */ 
/*     */ import com.dukascopy.charts.chartbuilder.ChartState;
/*     */ import com.dukascopy.charts.chartbuilder.MouseControllerMetaDrawingsState;
/*     */ import com.dukascopy.charts.chartbuilder.SubIndicatorGroup;
/*     */ import com.dukascopy.charts.data.AbstractDataSequenceProvider;
/*     */ import com.dukascopy.charts.data.datacache.renko.RenkoData;
/*     */ import com.dukascopy.charts.drawings.DrawingsLabelHelperContainer;
/*     */ import com.dukascopy.charts.mappers.time.GeometryCalculator;
/*     */ import com.dukascopy.charts.mappers.time.ITimeToXMapper;
/*     */ import com.dukascopy.charts.mappers.value.IValueToYMapper;
/*     */ import com.dukascopy.charts.mappers.value.SubValueToYMapper;
/*     */ import com.dukascopy.charts.math.dataprovider.priceaggregation.renko.RenkoDataSequence;
/*     */ import com.dukascopy.charts.orders.OrdersDrawingManager;
/*     */ import com.dukascopy.charts.utils.PathHelper;
/*     */ import com.dukascopy.charts.utils.formatter.FormattersManager;
/*     */ import com.dukascopy.charts.view.displayabledatapart.IDrawingStrategy;
/*     */ import com.dukascopy.charts.view.displayabledatapart.IOrdersDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.AbstractDrawingStrategyFactory;
/*     */ import com.dukascopy.charts.view.drawingstrategies.DefaultPriceAggregationIndicatorDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.DefaultPriceAggregationSubIndicatorsDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.candle.MainChartPanelMetaDrawingsDrawingsStrategyCandle;
/*     */ import com.dukascopy.charts.view.drawingstrategies.candle.OrdersCandleDrawingLogic;
/*     */ import com.dukascopy.charts.view.drawingstrategies.common.CommonAxisXPanelGridDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.main.MainAxisYIndicatorValueLabelDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.main.MainChartPeriodSeparatorsDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.sub.SubAxisYIndicatorValueLabelDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.sub.SubAxisYPanelGridDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.sub.SubAxisYPanelMovableLabelDrawingStrategy;
/*     */ import com.dukascopy.charts.view.drawingstrategies.sub.SubChartPeriodSeparatorsDrawingStrategy;
/*     */ 
/*     */ public class RenkoDrawingStrategyFactory extends AbstractDrawingStrategyFactory<RenkoDataSequence, RenkoData>
/*     */ {
/*     */   public RenkoDrawingStrategyFactory(ChartState chartState, MouseControllerMetaDrawingsState mouseControllerMetaDrawingsState, FormattersManager formattersManager, GeometryCalculator geometryCalculator, ITimeToXMapper timeToXMapper, IValueToYMapper valueToYMapper, SubValueToYMapper subValueToYMapper, OrdersDrawingManager ordersDrawingManager, AbstractDataSequenceProvider<RenkoDataSequence, RenkoData> dataSequenceProvider, PathHelper pathHelper, DrawingsLabelHelperContainer drawingsLabelHelperContainer)
/*     */   {
/*  54 */     super(chartState, mouseControllerMetaDrawingsState, formattersManager, geometryCalculator, timeToXMapper, valueToYMapper, subValueToYMapper, ordersDrawingManager, dataSequenceProvider, pathHelper, drawingsLabelHelperContainer);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createCommonAxisXPanelGridDrawingStrategy()
/*     */   {
/*  71 */     return new CommonAxisXPanelGridDrawingStrategy(this.chartState, this.timeToXMapper, this.dataSequenceProvider, this.formattersManager.getDateFormatter());
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createIndicatorsDrawingStrategy()
/*     */   {
/*  81 */     return new DefaultPriceAggregationIndicatorDrawingStrategy(this.dataSequenceProvider, this.geometryCalculator, this.valueToYMapper, this.timeToXMapper, this.pathHelper, getLabelHelperForMain(), this.formattersManager, this.chartState);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createMainAxisYIndicatorValueLabelDrawingStrategy()
/*     */   {
/*  95 */     return new MainAxisYIndicatorValueLabelDrawingStrategy(this.dataSequenceProvider, this.valueToYMapper, this.timeToXMapper, this.chartState);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createMainAxisYPanelMovableLabelDrawingStrategy()
/*     */   {
/* 105 */     return new MainAxisYPanelMovableLabelDrawingStrategyRenko(this.chartState, this.formattersManager.getValueFormatter(), this.valueToYMapper, this.dataSequenceProvider);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createMainChartPanelMetaDrawingsDrawingStrategy()
/*     */   {
/* 115 */     return new MainChartPanelMetaDrawingsDrawingsStrategyCandle(this.formattersManager.getValueFormatter(), this.chartState, this.mouseControllerMetaDrawingsState, this.geometryCalculator, this.valueToYMapper);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createMainChartPeriodSeparatorsDrawingStrategy()
/*     */   {
/* 126 */     return new MainChartPeriodSeparatorsDrawingStrategy(this.chartState, this.dataSequenceProvider, this.timeToXMapper);
/*     */   }
/*     */ 
/*     */   public IOrdersDrawingStrategy createOrdersDrawingStrategy()
/*     */   {
/* 135 */     return new OrdersCandleDrawingLogic(this.ordersDrawingManager, this.dataSequenceProvider, this.timeToXMapper, this.valueToYMapper, this.geometryCalculator, this.chartState);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createRawDrawingStrategy()
/*     */   {
/* 147 */     return new RenkoRawDataStrategy(this.chartState, new RenkoAsBrickVisualisationDrawingStrategy(this.formattersManager.getDateFormatter(), this.chartState, this.dataSequenceProvider, this.geometryCalculator, this.timeToXMapper, this.valueToYMapper, this.pathHelper));
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createSubAxisYIndicatorValueLabelDrawingStrategy(SubIndicatorGroup subIndicatorGroup)
/*     */   {
/* 163 */     return new SubAxisYIndicatorValueLabelDrawingStrategy(subIndicatorGroup, this.chartState, this.dataSequenceProvider, this.subValueToYMapper, this.timeToXMapper);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createSubAxisYPanelGridDrawingStrategy(SubIndicatorGroup subIndicatorGroup)
/*     */   {
/* 174 */     return new SubAxisYPanelGridDrawingStrategy(subIndicatorGroup, this.subValueToYMapper, this.chartState);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createSubAxisYPanelMovableLabelDrawingStrategy(SubIndicatorGroup subIndicatorGroup)
/*     */   {
/* 183 */     return new SubAxisYPanelMovableLabelDrawingStrategy(subIndicatorGroup, this.chartState, this.dataSequenceProvider, this.subValueToYMapper);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createSubChartPeriodSeparatorsDrawingStrategy()
/*     */   {
/* 193 */     return new SubChartPeriodSeparatorsDrawingStrategy(this.chartState, this.dataSequenceProvider, this.timeToXMapper);
/*     */   }
/*     */ 
/*     */   public IDrawingStrategy createSubIndicatorsDrawingStrategy(SubIndicatorGroup subIndicatorGroup)
/*     */   {
/* 202 */     return new DefaultPriceAggregationSubIndicatorsDrawingStrategy(subIndicatorGroup, this.geometryCalculator, this.dataSequenceProvider, this.subValueToYMapper, this.timeToXMapper, this.pathHelper, getLabelHelper(subIndicatorGroup), this.formattersManager, this.chartState);
/*     */   }
/*     */ }

/* Location:           G:\javawork\JForexClientLibrary\libs\DDS2-Charts-5.48.jar
 * Qualified Name:     com.dukascopy.charts.view.drawingstrategies.renko.RenkoDrawingStrategyFactory
 * JD-Core Version:    0.6.0
 */