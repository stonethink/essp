package essp.tables;

public class Wkitem {
    public long                 RID               = 0;
    public String               PROJECT_ID        = "";
    public String               WP_ID             = "";
    public String               WKITEM_OWNER      = "";
    public String               WKITEM_PLACE      = "";
    public String               WKITEM_BELONGT0   = "";
    public String               WKITEM_NAME       = "";
    public java.util.Date       WKITEM_DATE;
    public java.util.Date       WKITEM_STARTTIME;
    public java.util.Date       WKITEM_FINISHTIME;
    public java.math.BigDecimal WKITEM_WKHOURS    = new java.math.BigDecimal(8)
                                                    .setScale(1);
    public String               WKITEM_ISDLRPT    = "";
    public int                  WKITEM_COPYFROM   = 0;
    public String               RST               = "";
    public java.util.Date       RCT;
    public java.util.Date       RUT;
}
