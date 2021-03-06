package client.framework.view.vwcomp;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.framework.view.common.DefaultComp;

/**
 * <p>^Cg: JMsComp </p>
 * <p>à¾: Javax.Swingp³ÌIWiR|[lgQ</p>
 * <p>ì : milestone Copyright (c) 2002</p>
 * <p>ïÐ¼: }CXg[®ïÐ</p>
 * @author ¢üÍ
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
     *@^Cv@F@ú»<BR>
     *@¼@F@úlÝè<BR>
     *@õ@l@F@<BR>
     *<BR>
     *@ÏXð<BR>
     *<BR>
     *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
     *@|||||||||||||||||||||||||||||||||||<BR>
     *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
     *<BR>
     */
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setBorder(UIManager.getBorder("TextField.border"));
    }


    /**
     *<BR>
     *@^Cv@F@ú»<BR>
     *@¼@F@[UúlÝè<BR>
     *@õ@l@F@<BR>
     *<BR>
     *@ÏXð<BR>
     *<BR>
     *@@Version@@ú@t@@@XVÒ@@@@@Rg<BR>
     *@|||||||||||||||||||||||||||||||||||<BR>
     *@@00.00@@2002/05/30@ó@KO@@VKì¬<BR>
     *<BR>
     */
    private void initBeanUser() throws Exception {

        //++****************************
        //	óÔÝè
        //--****************************
        setEnabled(false);

        //++****************************
        //	¶îñÝè
        //--****************************
        setFont(DefaultComp.DISP_DATE_FONT);

        //++****************************
        //	FÝè
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
