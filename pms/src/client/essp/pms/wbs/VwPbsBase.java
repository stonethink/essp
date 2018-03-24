package client.essp.pms.wbs;

import client.essp.common.view.VWWorkArea;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwPbsBase extends VWWorkArea {
    public VwPbsBase() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VwPbsBase vwpbs = new VwPbsBase();
    }

    public void jbInit() throws Exception {
    }
}
