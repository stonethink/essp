package client.essp.pwm.workbench.period;

import java.awt.event.*;
import com.wits.util.TestPanel;
import java.util.Calendar;
import java.util.Iterator;
import java.util.*;
import com.wits.util.comDate;

public class VwPeriodWeekDefTest extends VwPeriodWeekDef {
    public VwPeriodWeekDefTest() {
        this.numWeekIntv.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showResult();
                }
            }
        });
    }

    private void showResult() {
        Iterator it = this.getPeriodList(getC(2005,9,26).getTime(), getC(2005,10,26).getTime());
        for (; it.hasNext(); ) {
            Calendar item = (Calendar) it.next();
            if( item != null ){
                System.out.println(comDate.dateToString(item.getTime(), "yyyy-MM-dd"));
            }
        }
    }

    private Calendar getC(int y, int m, int d){
        Calendar c = Calendar.getInstance();
        c.set(y,m,d,0,0,0);
        return c;
    }

    public static void main(String args[]) {
        TestPanel.show(new VwPeriodWeekDefTest());
    }

}
