package client.essp.timesheet.calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import c2s.essp.common.user.DtoUser;
import junitpack.HttpServletRequestMock;

public class TimesheetCalendarTest {
    public static void main(String[] args) {
        DtoUser user = new DtoUser();
        user.setUserID("wh0607015");
        user.setUserLoginId("WH0607015");
        HttpServletRequest request = new HttpServletRequestMock();
        HttpSession session = request.getSession();
        session.setAttribute(DtoUser.SESSION_USER, user);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        TimesheetCalendarPanel multiCalendars = new TimesheetCalendarPanel();

        JOptionPane.showMessageDialog(null, multiCalendars);


    }
}
