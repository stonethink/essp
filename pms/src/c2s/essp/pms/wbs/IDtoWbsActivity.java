package c2s.essp.pms.wbs;

import c2s.dto.IDto;

public interface IDtoWbsActivity extends IDto{
    public boolean isWbs();
    public boolean isActivity();
    public Boolean isMilestone();
    public void setWbs(boolean iswbs);
    public void setActivity(boolean isactivity);
    public boolean isReadonly();
}
