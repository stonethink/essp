package client.essp.common.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * <p>Title: </p>
 * <p>Description: ����һ��VWJPanel����ʱ������������Ŀؼ���������ԣ�
 *   1�� ����ʱ׼�����ݣ� �����ؼ���ֵ��
 *    ��
 *   2�� ��������ʱ��׼�����ݣ���ʹ�ö���ʱ��׼�����ݡ�
 *       initialize()����������ʹ��ʱΪ����׼�����ݣ������ؼ���ֵ
 *   ��һ��VWJPanel����Ҫ���ڿ�Ƭ��ʱ���������ݿ����Լ��㶨�������� 2 ������
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJPanel extends JPanel {
    VWButtonPanel btnPanel = new VWButtonPanel();
//    String title = "";

    public static final int NO_CARD = 0;
    public static final int CARD = 1;

    public VWJPanel() {
        this.setLayout(new BorderLayout());
    }

    //׼�����ݣ�����ʼ������
    public void initialize() {

    }

    public VWButtonPanel getButtonPanel() {
        return btnPanel;
    }

    public void addPanel( VWJPanel panel ){
        this.addPanel( panel, false );
    }

    /**
     *
     * @param panel VWJPanel
     * @param bScroll boolean  ��panel��ʾ����ʱ���Ƿ���ֹ�����
     */
    public void addPanel( VWJPanel panel , boolean bScroll ){
        if( bScroll == true ){
            this.add(VWScrollPanel.addScroll(panel), BorderLayout.CENTER );
        }else{
            this.add( panel, BorderLayout.CENTER );
        }
    }

//
//    public String getTitle(){
//        return this.title;
//    }
//
//    public void setTitle( String title ){
//        this.title = title;
//    }

}
