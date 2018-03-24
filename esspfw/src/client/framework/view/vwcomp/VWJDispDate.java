package client.framework.view.vwcomp;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.framework.view.common.DefaultComp;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class VWJDispDate extends VWJDate {
    BorderLayout borderLayout1 = new BorderLayout();

    public VWJDispDate() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            initBeanUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *<BR>
     *�@�^�C�v�@�F�@������<BR>
     *�@�������@�F�@�����l�ݒ菈��<BR>
     *�@���@�l�@�F�@<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     */
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setBorder(UIManager.getBorder("TextField.border"));
    }


    /**
     *<BR>
     *�@�^�C�v�@�F�@������<BR>
     *�@�������@�F�@���[�U�����l�ݒ菈��<BR>
     *�@���@�l�@�F�@<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     */
    private void initBeanUser() throws Exception {

        //++****************************
        //	��Ԑݒ�
        //--****************************
        setEnabled(false);

        //++****************************
        //	�������ݒ�
        //--****************************
        setFont(DefaultComp.DISP_DATE_FONT);

        //++****************************
        //	�F�ݒ�
        //--****************************
        setBackground(DefaultComp.DISP_DATE_BACKGROUND_COLOR);
        this.setDisabledTextColor(DefaultComp.DISP_DATA_INACT_FOREGROUND_COLOR);
    }

    public void setErrorField(boolean flag) {
    }

    public void setEnabled ( boolean isEnabled ){
        super.setEnabled(false);
    }

    public IVWComponent duplicate() {
        VWJDispDate comp = new VWJDispDate();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        comp.setDataType(this.getDataType());
        comp.setFont(this.getFont());
        comp.setText(this.getText());
        return comp;
    }
}
