package server.essp.attendance;

import java.util.*;

public class VbWorkProcess {
    private List requestProcessList;
    private List workingProcessList;
    public List getRequestProcessList() {
        return requestProcessList;
    }

    public List getWorkingProcessList() {
        return workingProcessList;
    }

    public void setRequestProcessList(List requestProcessList) {
        this.requestProcessList = requestProcessList;
    }

    public void setWorkingProcessList(List workingProcessList) {
        this.workingProcessList = workingProcessList;
    }

    public int getRequestProcessSize(){
        if(requestProcessList == null)
            return 0;
        else
            return requestProcessList.size();
    }

    public int getWorkingProcessSize(){
        if(workingProcessList == null)
            return 0;
        else
            return workingProcessList.size();
    }
}
