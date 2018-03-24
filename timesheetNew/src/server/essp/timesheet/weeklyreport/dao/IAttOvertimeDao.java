/*
 * Created on 2008-1-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.weeklyreport.dao;

import java.util.Date;
import java.util.List;

public interface IAttOvertimeDao {

   public List loadByCondition(String empId,Date otDate);
   
}
