package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

import validator.Validator;
import validator.ValidatorResult;
import client.framework.view.common.comFORM;
import client.image.ComImage;
/**
 * 加入了图标的日历选择控件
 * @author wenhaizheng
 *
 */
public class VWJDatePanel extends JPanel implements IVWComponent{
	private boolean bReshap = false;
    private int offset = 0;
	private VWJDate dateComp = new VWJDate();
	private VWJButton imgBtn = new VWJButton();
	
	public VWJDatePanel() {
	    jbInit();
		addUICEvent();
	}
	public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }
	/**
	 * 初始控件
	 */
	private void jbInit() {
		this.setLayout(new BorderLayout());
		dateComp.setText("");
		imgBtn.setText("");
		imgBtn.setIcon(new ImageIcon(ComImage.getImage("cal.png")));
		
		this.add(dateComp, BorderLayout.CENTER);
		this.add(imgBtn, BorderLayout.EAST);
	}
	/**
	 * 获取日期控件
	 * @return
	 */
	public VWJDate getDateComp() {
		return dateComp;
	}

	/**
	 * 添加事件
	 */
	private void addUICEvent(){
		imgBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processClick();
			}
		});
	}
	public void setCanSelect(boolean isCanSelect) {
		dateComp.setCanSelect(isCanSelect);
	}
	
	
	/**
	 * 点击按钮时间
	 */
	private void processClick() {
		if(dateComp.isEnabled() && dateComp.isCanSelect()){
			java.awt.Frame owner = this.getParentWindow();
	        VWJCalendar calendar = new VWJCalendar(owner, "", true);
	        String returnDate = calendar.showDialog(dateComp);
	        dateComp.setValueText(returnDate);
	        comFORM.setFocus(dateComp);
		}
	}
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
	/**
	 * 获取值
	 * @return
	 */
	public Object getUICValue(){
		return dateComp.getUICValue();
	}
	
	/**
	 * 设置值
	 * @param value
	 */
	public void setUICValue(Object value) {
		dateComp.setUICValue(value);
	}
	public void setDataType( String dataType) {
		dateComp.setDataType(dataType);
	}
	/**
	 * 设置是否可用
	 * @param disable
	 */
	public void setEnabled(boolean prm_bValue) {
		dateComp.setEnabled(prm_bValue);
		imgBtn.setEnabled(prm_bValue);
	}

	public boolean isBReshap() {
        return bReshap;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }
	public IVWComponent duplicate() {
		return null;
	}
	public int getHorizontalAlignment() {
		return SwingConstants.CENTER;
	}
	public int getOffset() {
		return offset;
	}
	public ValidatorResult getValidatorResult() {
		return this.dateComp.getValidatorResult();
	}
	public void setDtoClass(Class dtoClass) {
		this.dateComp.setDtoClass(dtoClass);
	}
	public Class getDtoClass() {
        return this.dateComp.getDtoClass();
    }
	public void setErrorField(boolean flag) {
		this.dateComp.setErrorField(flag);
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setValidator(Validator validator) {
		this.dateComp.setValidator(validator);
	}
	public boolean validateValue() {
		return this.dateComp.validateValue();
	}
	public void setEditable(boolean b) {
		dateComp.setEditable(b);
		imgBtn.setVisible(false);
	}
}
