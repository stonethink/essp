package itf.workflow;

public interface IWorkflowAppProc {
//    public static String WK_STATUS_
    boolean proActivity( int wkActivityID , String activityType  );
    boolean viewActivity( int wkActivityID );
}
