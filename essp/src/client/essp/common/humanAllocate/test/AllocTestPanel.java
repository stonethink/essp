package client.essp.common.humanAllocate.test;

import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import c2s.dto.DtoBase;
import client.framework.view.vwcomp.VWUtil;
import javax.swing.JOptionPane;
import c2s.essp.common.humanAllocate.Human;
import client.essp.common.humanAllocate.HrAllocate;
import com.wits.util.TestPanel;
import javax.servlet.http.HttpSession;
import junitpack.HttpServletRequestMock;
import c2s.essp.common.user.DtoUser;
import javax.servlet.http.HttpServletRequest;
import client.framework.common.Global;

public class AllocTestPanel extends VWWorkArea {
     VWJHrAllocateButton btnPm = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);
//     VWJHrAllocateButton btnManger = new VWJHrAllocateButton( HrAllocate.ALLOC_SINGLE);
     VWJButton resultButton = new VWJButton();
     Dto dto = new Dto();
     static{
         Global.todayDateStr = "2005-12-03";
         Global.todayDatePattern = "yyyy-MM-dd";
         Global.userId = "stone.shi";
         DtoUser user = new DtoUser();
         user.setUserID(Global.userId);
         user.setUserLoginId(Global.userId);
         HttpServletRequest request = new HttpServletRequestMock();
         HttpSession session = request.getSession();
         session.setAttribute(DtoUser.SESSION_USER, user);
    }
    public AllocTestPanel(){
        System.out.println( "AllocTestApplet.initUI()" );
        this.setLayout(null);

        VWJLabel lbl = new VWJLabel();
        lbl.setText( "pm" );
        lbl.setBounds(50, 90, 50, 30);
        btnPm.setBounds(110,90,300,30);
        btnPm.setEnabled(false);
        VWJLabel lbl2 = new VWJLabel();
        lbl2.setText( "manager" );
        lbl2.setBounds(50, 150, 50, 30);
//        btnManger.setBounds(110,150,300,30);

        resultButton.setBounds( 50, 200, 100, 30 );
        resultButton.setText(" show result ");
        resultButton.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e){
                actionPerformedR();
            }
        });

        this.add(lbl);
        this.add(btnPm);
//        this.add(btnManger);
        this.add( resultButton );

        btnPm.setName("pm");
//        btnManger.setName("manager");

        dto.setPm("stone.shi");
        dto.setManager(new Human("000038"));

        VWUtil.bindDto2UI( dto, this );
    }

    public  void actionPerformedR(){
        System.out.println( "show result:" );

        VWUtil.convertUI2Dto( dto, this );
        String msg = "dto: \r\n \tpm: " + dto.getPm().getClass().getName() + " -- " + dto.getPm() ;
        msg += "\r\n \tmanager: " + dto.getManger()==null?"null":dto.getManger().getClass().getName();
        msg += "\r\n \t\tid: " + dto.getManger()==null?"null":dto.getManger().getIds();
        msg += "\r\n \t\tname: " + dto.getManger()==null?"null":dto.getManger().getNames();
        JOptionPane.showMessageDialog(this, msg );
    }

    public static void main(String[] args){
        AllocTestPanel panel = new AllocTestPanel();
        TestPanel.show(panel);
    }
    public class Dto extends DtoBase{
        String pm;
        Human manager;
        public void setPm(String pm){
            this.pm = pm;
        }

        public String getPm(){
            return this.pm;
        }

        public void setManager(Human manager){
            this.manager = manager;
        }

        public Human getManger(){
            return this.manager;
        }
    }


}
