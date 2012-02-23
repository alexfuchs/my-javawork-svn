/*     */ package com.dukascopy.api.connector;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ public abstract interface IConst
/*     */ {
/*     */   public static final int PERIOD_M1 = 1;
/*     */   public static final int PERIOD_M5 = 5;
/*     */   public static final int PERIOD_M10 = 10;
/*     */   public static final int PERIOD_M15 = 15;
/*     */   public static final int PERIOD_M30 = 30;
/*     */   public static final int PERIOD_H1 = 60;
/*     */   public static final int PERIOD_H4 = 240;
/*     */   public static final int PERIOD_D1 = 1440;
/*     */   public static final int PERIOD_W1 = 10080;
/*     */   public static final int PERIOD_MN1 = 43200;
/*     */   public static final int OP_BUY = 0;
/*     */   public static final int OP_SELL = 1;
/*     */   public static final int OP_BUYLIMIT = 2;
/*     */   public static final int OP_SELLLIMIT = 3;
/*     */   public static final int OP_BUYSTOP = 4;
/*     */   public static final int OP_SELLSTOP = 5;
/*     */   public static final int OP_BUYLIMIT_BIDOFFER = 6;
/*     */   public static final int OP_SELLLIMIT_BIDOFFER = 7;
/*     */   public static final int PRICE_CLOSE = 0;
/*     */   public static final int PRICE_OPEN = 1;
/*     */   public static final int PRICE_HIGH = 2;
/*     */   public static final int PRICE_LOW = 3;
/*     */   public static final int PRICE_MEDIAN = 4;
/*     */   public static final int PRICE_TYPICAL = 5;
/*     */   public static final int PRICE_WEIGHTED = 6;
/*     */   public static final int MODE_OPEN = 0;
/*     */   public static final int MODE_CLOSE = 3;
/*     */   public static final int MODE_VOLUME = 4;
/*     */   public static final int MODE_LOW = 1;
/*     */   public static final int MODE_HIGH = 2;
/*     */   public static final int MODE_TIME = 5;
/*     */   public static final short MODE_BID = 9;
/*     */   public static final short MODE_ASK = 10;
/*     */   public static final short MODE_POINT = 11;
/*     */   public static final int MODE_DIGITS = 12;
/*     */   public static final short MODE_SPREAD = 13;
/*     */   public static final int MODE_STOPLEVEL = 14;
/*     */   public static final short MODE_LOTSIZE = 15;
/*     */   public static final int MODE_TICKVALUE = 16;
/*     */   public static final short MODE_TICKSIZE = 17;
/*     */   public static final int MODE_SWAPLONG = 18;
/*     */   public static final int MODE_SWAPSHORT = 19;
/*     */   public static final int MODE_STARTING = 20;
/*     */   public static final int MODE_EXPIRATION = 21;
/*     */   public static final int MODE_TRADEALLOWED = 22;
/* 125 */   public static final BigInteger MODE_MINLOT = new BigInteger(String.valueOf(23));
/*     */   public static final int MODE_MINLOT_SHORT = 23;
/*     */   public static final int MODE_LOTSTEP = 24;
/*     */   public static final int MODE_MAXLOT = 25;
/*     */   public static final int MODE_SWAPTYPE = 26;
/*     */   public static final int MODE_PROFITCALCMODE = 27;
/*     */   public static final int MODE_MARGINCALCMODE = 28;
/*     */   public static final int MODE_MARGININIT = 29;
/*     */   public static final int MODE_MARGINMAINTENANCE = 30;
/*     */   public static final int MODE_MARGINHEDGED = 31;
/*     */   public static final int MODE_MARGINREQUIRED = 32;
/*     */   public static final int MODE_FREEZELEVEL = 33;
/*     */   public static final int MODE_ASCEND = 0;
/*     */   public static final int MODE_DESCEND = 1;
/*     */   public static final int OBJPROP_TIME1 = 0;
/*     */   public static final int OBJPROP_PRICE1 = 1;
/*     */   public static final int OBJPROP_TIME2 = 2;
/*     */   public static final int OBJPROP_PRICE2 = 3;
/*     */   public static final int OBJPROP_TIME3 = 4;
/*     */   public static final int OBJPROP_PRICE3 = 5;
/*     */   public static final int OBJPROP_COLOR = 6;
/*     */   public static final int OBJPROP_STYLE = 7;
/*     */   public static final int OBJPROP_WIDTH = 8;
/*     */   public static final int OBJPROP_BACK = 9;
/*     */   public static final int OBJPROP_RAY = 10;
/*     */   public static final int OBJPROP_ELLIPSE = 11;
/*     */   public static final int OBJPROP_SCALE = 12;
/*     */   public static final int OBJPROP_ANGLE = 13;
/*     */   public static final int OBJPROP_ARROWCODE = 14;
/*     */   public static final int OBJPROP_TIMEFRAMES = 15;
/*     */   public static final int OBJPROP_DEVIATION = 16;
/*     */   public static final int OBJPROP_FONTSIZE = 100;
/*     */   public static final int OBJPROP_CORNER = 101;
/*     */   public static final int OBJPROP_XDISTANCE = 102;
/*     */   public static final int OBJPROP_YDISTANCE = 103;
/*     */   public static final int OBJPROP_FIBOLEVELS = 200;
/*     */   public static final int OBJPROP_LEVELCOLOR = 201;
/*     */   public static final int OBJPROP_LEVELSTYLE = 202;
/*     */   public static final int OBJPROP_LEVELWIDTH = 203;
/*     */   public static final int MODE_MAIN = 0;
/*     */   public static final int MODE_SIGNAL = 1;
/*     */   public static final int MODE_PLUSDI = 1;
/*     */   public static final int MODE_MINUSDI = 2;
/*     */   public static final int MODE_UPPER = 1;
/*     */   public static final int MODE_LOWER = 2;
/*     */   public static final int MODE_SMA = 0;
/*     */   public static final int MODE_EMA = 1;
/*     */   public static final int MODE_SMMA = 2;
/*     */   public static final int MODE_LWMA = 3;
/*     */   public static final int MB_OK = 0;
/*     */   public static final int MB_OKCANCEL = 1;
/*     */   public static final int MB_ABORTRETRYIGNORE = 2;
/*     */   public static final int MB_YESNOCANCEL = 3;
/*     */   public static final int MB_YESNO = 4;
/*     */   public static final int MB_RETRYCANCEL = 5;
/*     */   public static final int MB_CANCELTRYCONTINUE = 6;
/*     */   public static final int IDOK = 1;
/*     */   public static final int IDCANCEL = 2;
/*     */   public static final int IDABORT = 3;
/*     */   public static final int IDRETRY = 4;
/*     */   public static final int IDIGNORE = 5;
/*     */   public static final int IDYES = 6;
/*     */   public static final int IDNO = 7;
/*     */   public static final int IDTRYAGAIN = 10;
/*     */   public static final int IDCONTINUE = 11;
/*     */   public static final int ERR_NO_ERROR = 0;
/*     */   public static final int ERR_NO_RESULT = 1;
/*     */   public static final int ERR_COMMON_ERROR = 2;
/*     */   public static final int ERR_INVALID_TRADE_PARAMETERS = 3;
/*     */   public static final int ERR_SERVER_BUSY = 4;
/*     */   public static final int ERR_OLD_VERSION = 5;
/*     */   public static final int ERR_NO_CONNECTION = 6;
/*     */   public static final int ERR_NOT_ENOUGH_RIGHTS = 7;
/*     */   public static final int ERR_TOO_FREQUENT_REQUESTS = 8;
/*     */   public static final int ERR_MALFUNCTIONAL_TRADE = 9;
/*     */   public static final int ERR_ACCOUNT_DISABLED = 64;
/*     */   public static final int ERR_INVALID_ACCOUNT = 65;
/*     */   public static final int ERR_TRADE_TIMEOUT = 128;
/*     */   public static final int ERR_INVALID_PRICE = 129;
/*     */   public static final int ERR_INVALID_STOPS = 130;
/*     */   public static final int ERR_INVALID_TRADE_VOLUME = 131;
/*     */   public static final int ERR_MARKET_CLOSED = 132;
/*     */   public static final int ERR_TRADE_DISABLED = 133;
/*     */   public static final int ERR_NOT_ENOUGH_MONEY = 134;
/*     */   public static final int ERR_PRICE_CHANGED = 135;
/*     */   public static final int ERR_OFF_QUOTES = 136;
/*     */   public static final int ERR_BROKER_BUSY = 137;
/*     */   public static final int ERR_REQUOTE = 138;
/*     */   public static final int ERR_ORDER_LOCKED = 139;
/*     */   public static final int ERR_LONG_POSITIONS_ONLY_ALLOWED = 140;
/*     */   public static final int ERR_TOO_MANY_REQUESTS = 141;
/*     */   public static final int ERR_TRADE_MODIFY_DENIED = 145;
/*     */   public static final int ERR_TRADE_CONTEXT_BUSY = 146;
/*     */   public static final int ERR_TRADE_EXPIRATION_DENIED = 147;
/*     */   public static final int ERR_TRADE_TOO_MANY_ORDERS = 148;
/*     */   public static final int OBJ_VLINE = 0;
/*     */   public static final int OBJ_HLINE = 1;
/*     */   public static final int OBJ_TREND = 2;
/*     */   public static final int OBJ_TRENDBYANGLE = 3;
/*     */   public static final int OBJ_REGRESSION = 4;
/*     */   public static final int OBJ_CHANNEL = 5;
/*     */   public static final int OBJ_STDDEVCHANNEL = 6;
/*     */   public static final int OBJ_GANNLINE = 7;
/*     */   public static final int OBJ_GANNFAN = 8;
/*     */   public static final int OBJ_GANNGRID = 9;
/*     */   public static final int OBJ_FIBO = 10;
/*     */   public static final int OBJ_FIBOTIMES = 11;
/*     */   public static final int OBJ_FIBOFAN = 12;
/*     */   public static final int OBJ_FIBOARC = 13;
/*     */   public static final int OBJ_EXPANSION = 14;
/*     */   public static final int OBJ_FIBOCHANNEL = 15;
/*     */   public static final int OBJ_RECTANGLE = 16;
/*     */   public static final int OBJ_TRIANGLE = 17;
/*     */   public static final int OBJ_ELLIPSE = 18;
/*     */   public static final int OBJ_PITCHFORK = 19;
/*     */   public static final int OBJ_CYCLES = 20;
/*     */   public static final int OBJ_TEXT = 21;
/*     */   public static final int OBJ_ARROW = 22;
/*     */   public static final int OBJ_LABEL = 23;
/*     */   public static final int MODE_KIJUNSEN = 0;
/*     */   public static final int MODE_TENKANSEN = 0;
/*     */   public static final int MODE_SENKOUSPANA = 0;
/*     */   public static final int MODE_SENKOUSPANB = 0;
/*     */   public static final int DRAW_LINE = 0;
/*     */   public static final int DRAW_SECTION = 1;
/*     */   public static final int DRAW_HISTOGRAM = 2;
/*     */   public static final int DRAW_ARROW = 3;
/*     */   public static final int DRAW_ZIGZAG = 4;
/*     */   public static final int DRAW_NONE = 12;
/*     */   public static final int STYLE_SOLID = 0;
/*     */   public static final int STYLE_DASH = 1;
/*     */   public static final int STYLE_DOT = 2;
/*     */   public static final int STYLE_DASHDOT = 3;
/*     */   public static final int STYLE_DASHDOTDOT = 4;
/*     */   public static final int LONG_VALUE = 4;
/*     */   public static final int CHAR_VALUE = 1;
/*     */   public static final int SHORT_VALUE = 2;
/*     */   public static final int FLOAT_VALUE = 4;
/*     */   public static final int DOUBLE_VALUE = 8;
/*     */   public static final int ERR_NO_MQLERROR = 0;
/*     */   public static final int MODE_CHINKOUSPAN = 0;
/*     */   public static final int FILE_READ = 1;
/*     */   public static final int FILE_WRITE = 2;
/*     */   public static final int FILE_BIN = 4;
/*     */   public static final int FILE_CSV = 8;
/*     */   public static final int SELECT_BY_POS = 0;
/*     */   public static final int SELECT_BY_TICKET = 1;
/*     */   public static final int MODE_TRADES = 0;
/*     */   public static final int MODE_HISTORY = 1;
/*     */   public static final int TIME_DATE = 1;
/*     */   public static final int TIME_MINUTES = 2;
/*     */   public static final int TIME_SECONDS = 4;
/*     */   public static final int SEEK_SET = 0;
/*     */   public static final int SEEK_CUR = 1;
/*     */   public static final int SEEK_END = 2;
/*     */   public static final int EMPTY = -1;
/*     */   public static final int EMPTY_VALUE = 2147483647;
/* 535 */   public static final Color CLR_NONE = Color.white;
/*     */   public static final int WHOLE_ARRAY = 0;
/*     */   public static final int SYMBOL_THUMBSUP = 67;
/*     */   public static final int SYMBOL_THUMBSDOWN = 68;
/*     */   public static final int SYMBOL_ARROWUP = 241;
/*     */   public static final int SYMBOL_ARROWDOWN = 242;
/*     */   public static final int SYMBOL_STOPSIGN = 251;
/*     */   public static final int SYMBOL_CHECKSIGN = 252;
/*     */   public static final int SYMBOL_LEFTPRICE = 5;
/*     */   public static final int SYMBOL_RIGHTPRICE = 6;
/*     */   public static final int REASON_REMOVE = 1;
/*     */   public static final int REASON_RECOMPILE = 2;
/*     */   public static final int REASON_CHARTCHANGE = 3;
/*     */   public static final int REASON_CHARTCLOSE = 4;
/*     */   public static final int REASON_PARAMETERS = 5;
/*     */   public static final int REASON_ACCOUNT = 6;
/*     */ }

/* Location:           G:\javawork\JForexClientLibrary\libs\DDS2-Connector-1.1.49.jar
 * Qualified Name:     com.dukascopy.api.connector.IConst
 * JD-Core Version:    0.6.0
 */