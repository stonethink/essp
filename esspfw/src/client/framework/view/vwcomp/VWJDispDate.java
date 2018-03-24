package client.framework.view.vwcomp;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import client.framework.view.common.DefaultComp;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
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
        this.setLayout(borderLayout1);
        this.setBorder(UIManager.getBorder("TextField.border"));
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
        //	状態設定
        //--****************************
        setEnabled(false);

        //++****************************
        //	文字情報設定
        //--****************************
        setFont(DefaultComp.DISP_DATE_FONT);

        //++****************************
        //	色設定
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
