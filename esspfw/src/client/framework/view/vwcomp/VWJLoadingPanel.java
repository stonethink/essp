package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VWJLoadingPanel extends JPanel implements IVWLoading, IVWLoadingStatus {
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JProgressBar jProgressBar1 = new JProgressBar();
    private LodingPicture lodingPicture = new LodingPicture();
    private LodingProcess lodingProcess = new LodingProcess();
    private JPanel contentPanel = null;
    private Component currComp = null;
    private IVWLoading lodingIf = null;

    public VWJLoadingPanel() {
        try {
            lodingIf = this;
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public VWJLoadingPanel(IVWLoading lodingIf) {
        try {
            this.lodingIf = lodingIf;
            jbInit();
            contentPanel = (JPanel) lodingIf;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        this.add(lodingPicture, BorderLayout.CENTER);
        currComp = lodingPicture;
        //start();
    }

    public final void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
        this.remove(currComp);
        this.add(contentPanel, BorderLayout.CENTER);
        this.updateUI();
    }

    public void loading(IVWLoadingStatus lodingStatus) {
        /**
         * 在这里加入Loding代码
         */
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 900000; j++) {
                jProgressBar1.setValue(i);
            }
        }

    }

    public final void start() {
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);
        jProgressBar1.setOrientation(0);
        //System.out.println("Loading");
        lodingProcess.start();
    }

    /***************************************************************************/
    private class LodingProcess extends Thread {
        public void run() {
            VWJLoadingPanel.this.remove(currComp);
            VWJLoadingPanel.this.add(lodingPicture, BorderLayout.CENTER);
            VWJLoadingPanel.this.updateUI();
            currComp = lodingPicture;
            try {
                lodingIf.loading(VWJLoadingPanel.this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (contentPanel != null) {
                VWJLoadingPanel.this.remove(currComp);
                VWJLoadingPanel.this.add(contentPanel, BorderLayout.CENTER);
                VWJLoadingPanel.this.updateUI();
                currComp = contentPanel;
            }
        }
    }


    private class LodingPicture extends JPanel {
        public LodingPicture() {
            this.setLayout(new GridBagLayout());
            this.add(jProgressBar1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 100, 5));
        }
    }


    public void setMaximum(int max) {
        jProgressBar1.setMaximum(max);
    }

    public void setMinimum(int min) {
        jProgressBar1.setMinimum(min);
    }

    public void setStatus(int status) {
        jProgressBar1.setValue(status);
        //jProgressBar1.updateUI();
    }

    public int getStatus() {
        return jProgressBar1.getOrientation();
    }

    public static void main(String[] args) {
        VWJLoadingPanel panel = new VWJLoadingPanel(new TestContent());
        Dimension dim = new Dimension(320, 200);
        panel.setSize(dim);
        panel.setPreferredSize(dim);
        //panel.setContentPanel(new TestContent());
        TestPanel.show(panel);
        panel.start();
    }
}


class TestContent extends JPanel implements IVWLoading {
    javax.swing.JTextField text = new javax.swing.JTextField("Test text");
    public TestContent() {
        text.setSize(100, 20);
        this.add(text);
    }

    public void loading(IVWLoadingStatus lodingStatus) {
        System.out.println("Loading");
        /**
         * 在这里加入Loding代码
         */
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 900000; j++) {
                lodingStatus.setStatus(i);
            }
        }
        System.out.println("Loaded");
    }
}
