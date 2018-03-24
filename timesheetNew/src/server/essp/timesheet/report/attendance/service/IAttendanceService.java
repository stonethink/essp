/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.service;

import java.util.List;

import c2s.essp.timesheet.report.DtoAttendanceQuery;

public interface IAttendanceService {
       
       public List queryAttendance(DtoAttendanceQuery dto) throws Exception;
           
       public void setExcelDto(boolean flag);
}
