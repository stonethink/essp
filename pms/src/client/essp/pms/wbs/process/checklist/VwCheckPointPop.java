package client.essp.pms.wbs.process.checklist;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;

/**
 * <p>Title: check point pop</p>
 *
 * <p>Description: check point µ¯³öÏêÏ¸½çÃæ</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwCheckPointPop extends VWGeneralWorkArea
    implements IVWPopupEditorEvent{
    private DtoQaCheckPoint dtoQaCheckPoint;
    private Boolean isReadOnly = Boolean.TRUE;
    public VwCheckPointPop() {
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    /**
     * jbInit
     */
    private void jbInit() {
        this.setLayout(null);

        titleLabel.setText("Edit Check Point");
        titleLabel.setBounds(new Rectangle(146, 18, 227, 31));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        seqLabel.setText("Seq");
        seqLabel.setBounds(new Rectangle(41, 62, 40, 31));
        seq.setName("seq");
        seq.setBounds(new Rectangle(97, 66, 84, 26));
        seq.setMaxInputDecimalDigit(0);

        nameLabel.setText("Name");
        nameLabel.setBounds(new Rectangle(40, 97, 40, 31));
        name.setName("name");
        name.setBounds(new Rectangle(97, 100, 335, 45));

        methodLabel.setText("Method");
        methodLabel.setBounds(new Rectangle(40, 148, 40, 31));
        method.setName("method");
        method.setBounds(new Rectangle(97, 154, 335, 80));

        remarkLabel.setText("Remark");
        remarkLabel.setBounds(new Rectangle(40, 232, 40, 31));
        remark.setName("remark");
        remark.setBounds(new Rectangle(97, 243, 335, 45));

        this.add(titleLabel);
        this.add(seqLabel);
        this.add(seq);
        this.add(name);
        this.add(method);
        this.add(remark);
        this.add(methodLabel);
        this.add(nameLabel);
        this.add(remarkLabel);
    }
    /**
     * addUICEvent
     */
    private void addUICEvent() {
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.dtoQaCheckPoint = (DtoQaCheckPoint) param.get(DtoKey.DTO);
        isReadOnly = (Boolean) param.get("isReadOnly");
    }

    protected void resetUI() {
        VWUtil.bindDto2UI(dtoQaCheckPoint, this);
        setEnable(!isReadOnly.booleanValue());
    }

    private void setEnable(boolean enable) {
        seq.setEnabled(enable);
        name.setEnabled(enable);
        method.setEnabled(enable);
        remark.setEnabled(enable);
    }

    public boolean onClickOK(ActionEvent e) {
       VWUtil.convertUI2Dto(dtoQaCheckPoint, this);
       return true;
   }

   public boolean onClickCancel(ActionEvent e) {
       return true;
   }


    VWJLabel seqLabel = new VWJLabel();
    VWJReal seq = new VWJReal();
    VWJLabel nameLabel = new VWJLabel();
    VWJLabel methodLabel = new VWJLabel();
    VWJLabel remarkLabel = new VWJLabel();
    VWJTextArea name = new VWJTextArea();
    VWJTextArea method = new VWJTextArea();
    VWJTextArea remark = new VWJTextArea();
    VWJLabel titleLabel = new VWJLabel();
}
