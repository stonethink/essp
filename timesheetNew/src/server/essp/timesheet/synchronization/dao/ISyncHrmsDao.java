package server.essp.timesheet.synchronization.dao;

public interface ISyncHrmsDao {
	
	public void add(String companyCode, String projectId, String employeeId, String op);

}
