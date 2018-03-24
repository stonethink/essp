package client.net;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;

public class NetConnectorDebugImp extends AbstractConnector  implements NetConnector {
    public NetConnectorDebugImp() {

    }

    public int accessData(InputInfo input,ReturnInfo output) {
        TransactionData data=new TransactionData();
        data.setInputInfo(input);
        data.setReturnInfo(output);
        server.framework.controller.BusinessController controller
            = new server.framework.controller.BusinessController();
        controller.run(data);
        return 0;
    }

    public int accessData(TransactionData data) {
        server.framework.controller.BusinessController controller
            = new server.framework.controller.BusinessController();
        controller.run(data);
        return 0;
    }

//    public int accessData(c2s.vds.VDView_Data vd) {
//        server.framework.controller.Controller controller
//            = new server.framework.controller.Controller();
//        controller.run(vd);
//        return 0;
//    }
}
