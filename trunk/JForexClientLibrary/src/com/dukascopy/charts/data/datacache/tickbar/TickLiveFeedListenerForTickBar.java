/*    */ package com.dukascopy.charts.data.datacache.tickbar;
/*    */ 
/*    */ import com.dukascopy.api.Instrument;
/*    */ import com.dukascopy.charts.data.datacache.TickData;
/*    */ import com.dukascopy.charts.data.datacache.priceaggregation.AbstractPriceAggregationLiveFeedListener;
/*    */ import java.util.List;
/*    */ 
/*    */ public class TickLiveFeedListenerForTickBar extends AbstractPriceAggregationLiveFeedListener<TickBarData, TickData, ITickBarLiveFeedListener, ITickBarCreator>
/*    */ {
/*    */   public TickLiveFeedListenerForTickBar(ITickBarCreator creator)
/*    */   {
/* 14 */     super(creator);
/*    */   }
/*    */ 
/*    */   public TickLiveFeedListenerForTickBar(ITickBarCreator creator, long lastPossibleTime)
/*    */   {
/* 21 */     super(creator, lastPossibleTime);
/*    */   }
/*    */ 
/*    */   public void newTick(Instrument instrument, long time, double ask, double bid, double askVol, double bidVol)
/*    */   {
/* 33 */     this.collectedDatas.add(new TickData(time, ask, bid, askVol, bidVol));
/*    */   }
/*    */ }

/* Location:           G:\javawork\JForexClientLibrary\libs\greed-common-173.jar
 * Qualified Name:     com.dukascopy.charts.data.datacache.tickbar.TickLiveFeedListenerForTickBar
 * JD-Core Version:    0.6.0
 */