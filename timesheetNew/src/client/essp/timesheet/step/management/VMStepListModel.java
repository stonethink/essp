package client.essp.timesheet.step.management;

import c2s.essp.timesheet.step.management.DtoStep;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;

public class VMStepListModel extends VMTable2 {

	public VMStepListModel(Object[][] configs) {
		super(configs);
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		VMColumnConfig columnConfig = (VMColumnConfig) this.getColumnConfigs()
				.get(columnIndex);
		String dataName = columnConfig.getDataName();
		if ("resourceName".equals(dataName)) {
			Object oldValue = this.getValueAt(rowIndex, columnIndex);
			if (oldValue != null && value != null) {
				if (oldValue.toString().equals(value.toString())) {
					return;
				}
			}
			DtoStep dataBean = (DtoStep) this.getRow(rowIndex);
			dataBean.setResourceId("");
		}
		// if ("completeFlag".equals(dataName)) {
		// DtoStep dataBean = (DtoStep) this.getRow(rowIndex);
		// if (new Boolean(value.toString())) {
		// dataBean.setActualFinish(new Date());
		// } else {
		// dataBean.setActualFinish(null);
		// }
		// }
		// if ("actualFinish".equals(dataName)) {
		// DtoStep dataBean = (DtoStep) this.getRow(rowIndex);
		// if (value == null) {
		// dataBean.setCompleteFlag(false);
		// } else {
		// dataBean.setCompleteFlag(true);
		// }
		// }
		super.setValueAt(value, rowIndex, columnIndex);
		this.fireTableDataChanged();
	}

}
