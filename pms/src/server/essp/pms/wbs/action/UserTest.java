package server.essp.pms.wbs.action;

import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;

public class UserTest {
    public UserTest() {
    }

    public static void test() {
        DtoUser user=new DtoUser();
        user.setUserID("davidliao");
        ThreadVariant thread=ThreadVariant.getInstance();
        thread.set(DtoUser.SESSION_USER,user);

    }
}
