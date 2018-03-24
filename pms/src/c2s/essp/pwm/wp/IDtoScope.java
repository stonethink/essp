package c2s.essp.pwm.wp;

public interface IDtoScope {
    Long getAcntRid();
    String getAcntCode();
    String getAcntName();

    Long getActivityRid();
    String getActivityCode();
    String getActivityName();

    Long getWpRid();
    String getWpCode();
    String getWpName();

    String getScopeInfo();
    String getDescription();
}
