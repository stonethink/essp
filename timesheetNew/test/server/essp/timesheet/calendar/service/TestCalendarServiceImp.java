package server.essp.timesheet.calendar.service;

import java.util.Calendar;
import java.util.Date;

import com.wits.util.comDate;
import junit.framework.TestCase;
import server.essp.timesheet.calendar.dao.ICalendarP3Dao;
import server.essp.timesheet.preference.dao.IPreferenceP3DbDao;
import static org.easymock.EasyMock.*;
import c2s.essp.common.calendar.WorkCalendar;

public class TestCalendarServiceImp extends TestCase {
    private CalendarServiceImp calendarServiceImp = null;
    private ICalendarP3Dao icalendarP3DaoMock;
    private IPreferenceP3DbDao ipreferenceP3DbDaomock;

    public TestCalendarServiceImp(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        calendarServiceImp = new CalendarServiceImp();
        icalendarP3DaoMock = createMock(ICalendarP3Dao.class);
        ipreferenceP3DbDaomock = createMock(IPreferenceP3DbDao.class);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    public void testGetWeekStrat(){
        expect(ipreferenceP3DbDaomock.getWeekStartDayNum()).andReturn(1);
        replay(ipreferenceP3DbDaomock);

        calendarServiceImp.setPreferenceP3DbDao(ipreferenceP3DbDaomock);
        int weekStart = calendarServiceImp.getWeekStrat();
        this.assertEquals("weekStart is not sunday", weekStart, 1);
    }
    public void testGetWorkDayString() throws Exception {
        expect(icalendarP3DaoMock.getWorkHours((com.primavera.integration.client.bo.object.Calendar)anyObject(),
                                               (Date)anyObject()))
                    .andReturn(Double.valueOf(0)).anyTimes();
        replay(icalendarP3DaoMock);
        String expectResult = "2007-01-01~2007-12-31+00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        int tempYear = 2007;

        calendarServiceImp.setCalendarP3Dao(icalendarP3DaoMock);
        String workDayString = calendarServiceImp.getWorkDayString(tempYear,
                (com.primavera.integration.client.bo.object.Calendar)anyObject());
        this.assertNotNull("result is null", workDayString);
        this.assertEquals("result is wrong", workDayString, expectResult);
    }

}

