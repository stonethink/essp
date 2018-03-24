package client.essp.ebs;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import c2s.essp.ebs.IDtoEbsAcnt;
import client.framework.view.vwcomp.NodeViewManager;
import client.image.ComImage;

public class EbsNodeViewManager extends NodeViewManager{
    public IDtoEbsAcnt getDataBean(){
        if( getNode() != null ){
            return (IDtoEbsAcnt)getNode().getDataBean();
        }else{
            return null;
        }
    }

//    public Color getForeground(){
//        if( getDataBean() == null ){
//            return super.getForeground();
//        }else if( getDataBean().isAcnt() ){
//            return Color.red;
//        }else{
//            return super.getForeground();
//        }
//    }

    public Icon getIcon(){
        if( getDataBean() == null ){
            return super.getIcon();
        }else if( getDataBean().isEbs() ){
            return new ImageIcon(ComImage.getImage("group.gif"));
        }else if( getDataBean().isAcnt() ){
            return new ImageIcon(ComImage.getImage("account.gif"));
        } else {
            return super.getIcon();
        }
    }

}
