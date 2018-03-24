package c2s.essp.common.user;

public class DtoCustomer extends DtoUser {
//    private String password;
    public DtoCustomer(){
        this.setUserType(DtoUser.USER_TYPE_CUST);
    }
}
