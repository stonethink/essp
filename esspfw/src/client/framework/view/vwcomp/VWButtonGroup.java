package client.framework.view.vwcomp;

import client.framework.view.jmscomp.JMsButtonGroup;
import validator.ValidatorResult;
import validator.Validator;

public class VWButtonGroup extends JMsButtonGroup implements IVWComponent{


    public VWButtonGroup(){
        super();
    }

    public void setUICValue(Object value){

    }

    public Object getUICValue(){
        return null;
    }

    public IVWComponent duplicate(){
        VWButtonGroup vWButtonGroup = new VWButtonGroup();
        return vWButtonGroup;
    }
    public void setDtoClass(Class dtoClass){}

    public void setBReshap(boolean bReshap){}

    public boolean isBReshap(){return false;}

    public void setEnabled(boolean enabled){}

    public boolean isEnabled(){ return false;}

    public int getHorizontalAlignment(){return 0;}

    public int getOffset(){return 0;}

    public void setOffset(int offset){}

    public void setErrorField(boolean flag){}

    public void setToolTipText(String text){}

    public ValidatorResult getValidatorResult(){return null;}

    public boolean validateValue(){return true;}

    public void setValidator(Validator validator){}

    public String getName(){return null;};
}
