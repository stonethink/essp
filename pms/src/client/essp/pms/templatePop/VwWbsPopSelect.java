package client.essp.pms.templatePop;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;

import client.essp.common.view.VWPopToolBar;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: 显示template树和用来选择的ComboBox</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author wuyi
 * @version 1.0
 */
public class VwWbsPopSelect extends VWPopToolBar {

    private VwWbsSelectArea selectArea;

//    private static VwWbsPopSelect instance;
    public static VwWbsPopSelect createInstance(Container parent) {
        return new VwWbsPopSelect(parent);
    }

//    public static VwWbsPopSelect getInstance(){
//        public return instance;
//    }

    private  VwWbsPopSelect(Point p, Container parent) {
       super(p,parent,"Template/OSP Tree",250,500);
        try {
            selectArea = new VwWbsSelectArea();
            toolbar.add(selectArea, BorderLayout.CENTER);
            showPopSelect();
        } catch (Exception ex) {
            log.error(ex);
        }
    }
     private  VwWbsPopSelect( Container parent) {
         this(null,parent);
     }
    public void setParameter(Parameter param) {
       selectArea.setParameter(param);
       selectArea.refreshWorkArea();
   }

}
