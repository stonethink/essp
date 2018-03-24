package client.essp.common.code.choose;

import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Color;
import c2s.essp.common.code.DtoCodeValueChoose;

public class CodeNodeViewManager extends NodeViewManager{
    static final Color CodeTypeBack = new Color(0,200,0);

    public Color getOddBackground() {
        if( getDtoCodeChoose() != null &&getDtoCodeChoose().isCodeValue() == false ){
            return CodeTypeBack;
        }else{
            return super.getOddBackground();
        }
    }

    public Color getEvenBackground() {
        if( getDtoCodeChoose() != null &&getDtoCodeChoose().isCodeValue() == false ){
            return CodeTypeBack;
        }else{
            return super.getEvenBackground();
        }
    }

    public DtoCodeValueChoose getDtoCodeChoose(){
        if( getNode() != null ){
            return (DtoCodeValueChoose)getNode().getDataBean();
        }else{
            return null;
        }

    }
}
