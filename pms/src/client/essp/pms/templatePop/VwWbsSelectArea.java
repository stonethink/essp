package client.essp.pms.templatePop;

import client.essp.common.view.VWGeneralWorkArea;

/**
 * <p>Title: </p>
 *
 * <p>Description:���쵯����ѡ�������Ĳ��֣���2����Ƭ���ֱ���osp��template </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsSelectArea extends VWGeneralWorkArea {
    VwWbsTemplateArea temArea;
    VwWbsOspTree ospArea;
    public VwWbsSelectArea() {
        temArea=new VwWbsTemplateArea();
        ospArea=new VwWbsOspTree();
        this.addTab("Template",temArea);
        this.addTab("Osp",ospArea);
        ospArea.setParameter(null);
    }
    public void refreshWorkArea() {
        this.getSelectedWorkArea().refreshWorkArea();
    }
}
