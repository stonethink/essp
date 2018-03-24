/*
 * Created on 2006-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.finance.webservice;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.axis.client.Call;

public class FinanceService {
    private List closeCallList;
    private List updatCallList;
    private List addCallList;

    public void invokeAdd(Map map) throws RemoteException{
        for(int i = 0;i < addCallList.size() ;i ++){
            Call call = (Call) addCallList.get(i);
            call.invoke(new Object[]{map});
        }
    }
    public void invokeUpdate(Map map) throws RemoteException{
        for(int i = 0;i < updatCallList.size() ;i ++){
            Call call = (Call) updatCallList.get(i);
            call.invoke(new Object[]{map});
        }
    }
    public void invokeClose(String acntId) throws RemoteException{
        for(int i = 0;i < closeCallList.size() ;i ++){
            Call call = (Call) closeCallList.get(i);
            call.invoke(new Object[]{acntId});
        }
    }
 
    public List getAddCallList() {
        return addCallList;
    }
    public void setAddCallList(List addCallList) {
        this.addCallList = addCallList;
    }
    public List getCloseCallList() {
        return closeCallList;
    }
    public void setCloseCallList(List closeCallList) {
        this.closeCallList = closeCallList;
    }
    public List getUpdatCallList() {
        return updatCallList;
    }
    public void setUpdatCallList(List updatCallList) {
        this.updatCallList = updatCallList;
    }

}
