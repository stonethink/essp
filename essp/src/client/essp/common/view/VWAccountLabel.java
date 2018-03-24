package client.essp.common.view;

import client.framework.view.vwcomp.*;
import c2s.essp.common.account.IAccountModel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.FontMetrics;

public class VWAccountLabel extends VWJLabel {
    private IAccountModel model;

    public VWAccountLabel() {
        this(null);
    }

    public VWAccountLabel(IAccountModel model) {
        super();
        Dimension dim = new Dimension(150, 20);
        this.setSize(dim);
        this.setPreferredSize(dim);

        setModel(model);

    }

    public IAccountModel getModel() {
        return this.model;
    }

    public String getAccountInf() {
        if (model == null) {
            model = new DefaultModel();
        }
        return "" + model.getAccountCode() + "--" +
            model.getAccountName();
    }

    public void setModel(IAccountModel model) {
        this.model = model;
        if (this.model == null) {
            this.model = new DefaultModel();
        }
        setText(getAccountInf());
        //������ʾ��������Ϣ�Ժ󣬸����ַ��ĳ������ı����С������Ӧ��ͬ���ȵ�����
        //��������ܳ���250,�������ֲ�����ʾ��ʱ��ToolTip����æ
        //Modified by:Robin.zhang

        FontMetrics fm = this.getFontMetrics(this.getFont());
        int tempWidth = fm.stringWidth(getAccountInf());
        if (tempWidth + 10 < 150) {
            this.setSize(tempWidth + 10, 20);
            this.setPreferredSize(new Dimension(tempWidth + 10, 20));
        }else{
            this.setSize(150, 20);
            this.setPreferredSize(new Dimension(150, 20));
        }
        this.setToolTipText("Account:"+getAccountInf());

    }

    public void setAccountName(String accountName) {
        model.setAccountName(accountName);
        setText(getAccountInf());
    }

    public void setAccountCode(String accountCode) {
        model.setAccountCode(accountCode);
        setText(getAccountInf());
    }

    public String getAccountCode() {
        return model.getAccountCode();
    }

    public String getAccountName() {
        return model.getAccountName();
    }

    public String getText() {
        return getAccountInf();
    }

    public static IAccountModel createtDefaultModel(String accountCode,
        String accountName) {
        return new DefaultModel(accountCode, accountName);
    }

    static class DefaultModel implements IAccountModel {
        private String accountCode = "";
        private String accountName = "";

        public DefaultModel() {}

        public DefaultModel(String accountCode, String accountName) {
            this.accountCode = accountCode;
            this.accountName = accountName;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountCode(String code) {
            accountCode = code;
        }

        public void setAccountName(String name) {
            accountName = name;
        }
    }
}
