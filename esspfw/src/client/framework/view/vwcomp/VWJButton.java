package client.framework.view.vwcomp;

import client.framework.view.jmscomp.JMsButton;
import validator.ValidatorResult;
import validator.Validator;
import java.awt.Dimension;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJButton extends JMsButton implements IVWComponent{
    public VWJButton() {
        this("");
    }

    public VWJButton(String text) {
        super(text);
    }

    public void setUICValue(Object value){
        this.setText(value==null?"":value.toString());
    }

    public Object getUICValue(){
        return this.getText();
    }

    public void setDtoClass(Class dtoClass){}

    public void setValidator(Validator validator){}

    public boolean validateValue(){return true;}

    public ValidatorResult getValidatorResult(){return null;}

    public void setErrorField(boolean flag){}


    /**
     * ¸´ÖÆ¿Ø¼þ
     * @return Object
     */
    public IVWComponent duplicate(){
        VWJButton button = new VWJButton();
        button.setText(this.getText());
        button.setIcon(this.getIcon());
        button.setVerticalAlignment(this.getVerticalAlignment());
        button.setPreferredSize(new Dimension(20,20));
        for(int i=0; i < this.getActionListeners().length; i++ ){
            button.addActionListener(this.getActionListeners()[i]);
        }

        return button;
    }

    public boolean isBReshap(){return false;};

    public void setBReshap(boolean bReshap){}

    public int getOffset(){return 0;}

    public void setOffset(int offset){}


}
