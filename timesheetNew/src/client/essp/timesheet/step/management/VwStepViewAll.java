package client.essp.timesheet.step.management;

public class VwStepViewAll extends VwStepManagement {

	/**
	 * ��ȡVwActivityList
	 * @return VwActivityList
	 */
	protected VwActivityList getVwActivityList() {
		return new VwActivityList(true);
	}
	
	

}
