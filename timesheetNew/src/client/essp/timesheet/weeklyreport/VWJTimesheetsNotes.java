package client.essp.timesheet.weeklyreport;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.image.ComImage;

/**
 *
 * <p>Title: VWJTimesheetsNotes</p>
 *
 * <p>Description: 显示Notes界面的按钮</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class VWJTimesheetsNotes extends VWJButton {

    public final static ImageIcon image
            = new ImageIcon(ComImage.getImage("notesBtn.gif"));
    public final static ImageIcon imageActive
            = new ImageIcon(ComImage.getImage("ActNotesBtn.gif"));

    private VWJTimesheetsNotesArea notesArea;
    private Long tsRid;
    /**
     * 构造方法
     */
    public VWJTimesheetsNotes() {
        super();
//       this.setText("rsid.timesheet.notes");
        setActive(false);
        this.setEnabled(false);
        this.setText("");
        this.setToolTipText("Notes");
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showNotesArea();
            }
        });

    }

    /**
     * 设置Button外观，以显示Notes是否有内容
     * @param active boolean
     */
    public void setActive(boolean active) {
        if(active) {
            this.setIcon(imageActive);
        } else {
            this.setIcon(image);
        }
    }

    /**
     * 显示Notes界面
     * @param e MouseEvent
     */
    protected void showNotesArea() {
    	if(notesArea == null) {
    		return;
    	}
        notesArea.setPreferredSize(new Dimension(410, 290));
        java.awt.Frame owner = this.getParentWindow();
        VWJPopupEditor poputEditor = new VWJPopupEditor(owner, "Notes",
                notesArea);
        Component[] Components = poputEditor.getButtonPanel().getComponents();
        VWJButton okbtn = (VWJButton) Components[0];
        VWJButton cancelBtn = (VWJButton)Components[1];
        okbtn.setText("rsid.common.add");
        okbtn.setHorizontalAlignment(SwingConstants.CENTER);
        okbtn.setVerticalAlignment(SwingConstants.CENTER);
        cancelBtn.setText("rsid.common.cancel");
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setVerticalAlignment(SwingConstants.CENTER);
        poputEditor.showConfirm();
        if (notesArea.getNotes() != null && !"".equals(notesArea.getNotes())) {
            setActive(true);
        } else {
            setActive(false);
        }
    }

    /**
     * 获取父窗口
     * @return Frame
     */
    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }


    public void setTsRid(Long tsRid) {
        this.tsRid = tsRid;
        if(tsRid == null) {
            this.setEnabled(false);
            return;
        }
        this.setEnabled(true);
        notesArea = new VWJTimesheetsNotesArea(tsRid);
        if(notesArea.getNotes() != null && !"".equals(notesArea.getNotes())) {
            setActive(true);
        } else {
            setActive(false);
        }
    }

    public Long getTsRid() {
        return tsRid;
    }
}
