package itf.workflow;
import c2s.essp.common.workflow.IWkInstance;
import c2s.essp.common.workflow.IActivity;
import java.util.Date;
import java.util.List;
import c2s.essp.common.workflow.IWkDefine;
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
public interface IWorkflowEngine {

    IWkInstance createWKInstance(String sInstanceName,String sSubEmpLoginID , IWkDefine iwkDefine );
    IWkInstance createWKInstance(IWkInstance iwInstance , IWkDefine iwkDefine );
    IWkInstance proceWKInstance( Long lInstanceID,String sWKStatus , IWkDefine iwkDefine );
    IWkInstance proceWKInstance( IWkInstance iwInstance , IWkDefine iwkDefine );
    void closeWKInstance(Long lInstanceID );
    void closeWKInstance(Long lInstanceID,Date dWKFinishDate );

    IActivity startActivity( String sCurrentEmpLoginID , IWkDefine iwkDefine);
    IActivity startActivity( IActivity iActivity , IWkDefine iwkDefine );
    IActivity proceAcitivity( Long lActivityID, String sCurrentEmpLoginID , String sStatus , IWkDefine iwkDefine );
    IActivity proceAcitivity( IActivity iActivity , IWkDefine iwkDefine );

    IWkInstance loadWKInstance(Long lInstanceID);
    List loadWKInstanceList(String sSubEmpLoginID);
    IActivity loadActivity(Long lInstanceID);
    List loadActivityList(Long lWKInstanceID);

}
