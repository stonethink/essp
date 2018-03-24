package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import client.framework.view.common.DefaultComp;
import validator.Validator;
import validator.ValidatorResult;
import java.awt.event.ActionListener;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class VWJCheckBox extends JCheckBox implements IVWComponent {
    Object dtoBean;
    Validator validator;
    ValidatorResult validatorResult = null;

    BorderLayout borderLayout1 = new BorderLayout();
    private boolean keyType;
    private boolean pbSelect_Save = false;

    private boolean bReshap = false;
    private int offset = 0;

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public VWJCheckBox() {
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
     *　タイプ　：　初期化<BR>
     *　処理名　：　初期値設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    private void jbInit() throws Exception {
        this.setBackground(Color.white);
//        this.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setBorder(UIManager.getBorder("CheckBox.border"));

        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                this_keyPressed(e);
            }
        });
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                this_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                this_focusLost(e);
            }
        });
        this.setLayout(borderLayout1);
    }

    /**
     *<BR>
     *　タイプ　：　初期化<BR>
     *　処理名　：　ユーザ初期値設定処理<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    private void initBeanUser() throws Exception {

        //++****************************
        //	文字情報設定
        //--****************************
        setFont(DefaultComp.DEFUALT_BOLD_FONT);
    }

    public void _setBackgroundColor(
            ) {
        if (isEnabled() == true) {
            setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
        } else {
            setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
            this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }
    }

    void this_focusGained(FocusEvent e) {
        setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
    }

    void this_focusLost(FocusEvent e) {
        setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
    }

    /**
     *<BR>
     *　タイプ　：　イベント<BR>
     *　処理名　：　キー押下時の処理<BR>
     *　備　考　：　Enterキー押下でフィールド移動<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2002/05/30　宝来　幸弘　　新規作成<BR>
     *   00.01   2004/11/30  Yery        Enterイベントの処理を削除
     *<BR>
     */
    void this_keyPressed(KeyEvent e) {
        //ここのENTERイベントで問題を発生するので、削除するはずです。comFORM.setEnterOrder()を参照ください
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}

    }

    public void setKeyType(boolean keyType) {
        this.keyType = keyType;
    }

    public boolean isKeyType() {
        return keyType;
    }

    /**
     *<BR>
     *　タイプ　：　ユーザー関数<BR>
     *　処理名　：　キー項目変更チェックメソッド<BR>
     *　備　考　：　<BR>
     *<BR>
     *　変更履歴<BR>
     *<BR>
     *　　Version　　日　付　　　更新者　　　　　コメント<BR>
     *　－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－<BR>
     *　　00.00　　2003/03/24　宝来　幸弘　　新規作成<BR>
     *<BR>
     */
    public boolean checkModified(
        ) {
        boolean bState;

        bState = isSelected();
        if (pbSelect_Save != bState) {
            return true;
        } else {
            return false;
        }
    }

    public void clearModified(
        ) {
        pbSelect_Save = isSelected();
    }

    public void setDtoClass(Class dtoClass) {
        try {
            if (dtoClass != null) {
                this.dtoBean = dtoClass.newInstance();
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    public Class getDtoClass() {
        if (dtoBean == null) {
            return null;
        }
        return dtoBean.getClass();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setUICValue(Object value) {
//        if (value != null) {
//            setText(value.toString());
//        } else {
//            setText("");
//        }

        if (value != null) {
            String sValue = value.toString();
            if (new Boolean(sValue).booleanValue() == true) {
                this.setSelected(true);
                return;
            }
        }

        this.setSelected(false);
    }

    public Object getUICValue() {
        if (this.isSelected() == true) {
            return "true";
        } else {
            return "false";
        }

//        String sValue = getText();
//        return sValue;
    }

    public boolean validateValue() {
        return true;
    }


    public ValidatorResult getValidatorResult() {
        return validatorResult;
    }

    public boolean isBReshap() {
        return bReshap;
    }

    public int getOffset() {
        return offset;
    }

    public void setErrorField(boolean flag) {
        if (this.isEnabled() == true) {
            if (flag) {
                setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
                setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
            } else {
                setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
                setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
            }
        } else {
            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }
    }

    public void setEnabled(
            boolean prm_bValue
            ) {
        super.setEnabled(prm_bValue);
        _setBackgroundColor();
    }

    public IVWComponent duplicate() {
        VWJCheckBox comp = new VWJCheckBox();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        //comp.validatorResult=(ValidatorResult)this.validatorResult.duplicate();
        comp.setFont(this.getFont());
//        comp.setText(this.getText());

        ActionListener[] actionListeners = this.getActionListeners();
        for( int i = 0 ; i < actionListeners.length; i++ ){
            comp.addActionListener(actionListeners[i]);
        }
        return comp;
    }

}
