package client.essp.timesheet.admin.preference;

import java.awt.Dimension;

import client.essp.common.view.VWGeneralWorkArea;
import com.wits.util.Parameter;

/**
 * <p>Title: VwPreferenceFrame</p>
 *
 * <p>Description: VwPreference 卡片包装</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwPreferenceFrame extends VWGeneralWorkArea {
    private VwPreference vwPreference = new VwPreference();
    private VwPreferenceSite vwPreferenceSite = new VwPreferenceSite();

    public VwPreferenceFrame() {
        vwPreference.setPreferredSize(new Dimension(200, 590));
        vwPreferenceSite.setPreferredSize(new Dimension(200, 590));
        this.addTab("rsid.timesheet.preferenceGlobal", vwPreference, true);
        this.addTab("rsid.timesheet.preferenceSite", vwPreferenceSite, true);
    }

    /**
     * 设置参数，激活刷新
     */
    public void setParameter(Parameter param) {
        vwPreference.setParameter(param);
        vwPreferenceSite.setParameter(param);
    }

    /**
     * 刷新事件
     */
    public void refreshWorkArea() {
    	this.getSelectedWorkArea().refreshWorkArea();
    }
}
