package c2s.essp.attendance;

import c2s.essp.common.user.*;
import c2s.workflow.IUser;
import c2s.dto.DtoUtil;
import java.util.ArrayList;

public class WfUser extends DtoUser implements IUser {
    public WfUser (){
    }
    public WfUser (DtoUser user){
        DtoUtil.copyBeanToBean(this,user);
    }
}
