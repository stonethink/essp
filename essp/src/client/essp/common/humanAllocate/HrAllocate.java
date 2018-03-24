package client.essp.common.humanAllocate;

import client.framework.common.Global;
import client.framework.view.vwcomp.IVWJavaScriptCaller;
import client.framework.view.vwcomp.IVWJavaScriptListener;
import com.wits.util.Parameter;
import org.apache.log4j.Category;
import com.wits.util.StringUtil;
public class HrAllocate {
    protected static Category log = Category.getInstance("client");
    public static final int ALLOC_SINGLE = 0;
    public static final int ALLOC_MULTIPLE = 1;

    public static final String allocSingleUserFunName = "allocSingleInAD";
    public static final String allocMultiUserFunName = "allocMultipleInAD";

    //分配前已分配的人
    private String oldLoginIds;

    //分配后选中的人
    private String newLoginIds;

    private Long acntRid;
    private String title;

//    private IAllocateResult allocateResult;

    public HrAllocate() {
        this("");
    }

    public HrAllocate(String oldUserIds){
        this.oldLoginIds = oldUserIds;
        this.newLoginIds = oldLoginIds;
    }


    public void allocMultiple(){
        alloc( this.allocMultiUserFunName );
    }

    public void allocSingle(){
        alloc( this.allocSingleUserFunName );
    }

    private void alloc(String functionName){
        if (Global.applet == null
            || !(Global.applet instanceof IVWJavaScriptCaller)) {
            return;
        }

        IVWJavaScriptListener jsListener = new IVWJavaScriptListener(){
            public void actionPerformed(Parameter param){
                String newUserIds = StringUtil.nvl( (String)param.get( "newUserIds" ) );
                String newUserNames = StringUtil.nvl( (String)param.get( "newUserNames" ) );
                newLoginIds = newUserIds;
            }
        };

        String sAcntRid = StringUtil.nvl(acntRid);
        String sTitle = StringUtil.nvl(title);
        Object[] functionArgs = new Object[] {
                                ""
                                ,sAcntRid
                                , sTitle
                                ,this.getOldUserIds()
                                ,"false"};

        try {
            ((IVWJavaScriptCaller) Global.applet).performJS(jsListener,
                    functionName, functionArgs );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getOldUserIds(){
        return oldLoginIds == null ? "" : oldLoginIds;
    }


    public String getNewUserIds(){
        return this.newLoginIds;
    }

    public void setAcntRid(Long acntRid){
        this.acntRid = acntRid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Long getAcntRid(){
        return this.acntRid;
    }

    public String getTitle(){
        return this.title;
    }
}
