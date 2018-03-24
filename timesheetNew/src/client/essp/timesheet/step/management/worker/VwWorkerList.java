package client.essp.timesheet.step.management.worker;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoBase;
import c2s.essp.common.user.DtoUserBase;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class VwWorkerList extends VWTableWorkArea {

	protected List wkrList = new ArrayList();

	public VwWorkerList() {
		Object[][] configs = {
				{ "rsid.timesheet.resourceIds", "userLoginId", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE },
				{ "rsid.timesheet.resources", "userName", VMColumnConfig.UNEDITABLE,
						new VWJText(), Boolean.FALSE } };
		try {
			super.jbInit(configs, DtoBase.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		getTable().setSortable(true);

		// ÍÏ·ÅÊÂ¼þ
		(new WorkersDragSource(getTable())).create();
	}

	public void setParameter(Parameter param) {
		wkrList = new ArrayList();
		String resourceIds = (String) param.get("ResourceIds");
		String resouceNames = (String) param.get("Resources");
		if(resourceIds == null || resouceNames == null) {
			return;
		}
		String []ids=resourceIds.split(",");
		String []names=resouceNames.split(",");
		
		for (int i = 0; i < names.length; i++) {
			DtoUserBase res = new DtoUserBase();
			res.setUserName(names[i]);
			res.setUserLoginId(ids[i]);
			wkrList.add(res);
		}
	}

	public void resetUI() {
		this.getTable().setRows(this.wkrList);
	}

}
