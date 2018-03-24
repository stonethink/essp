package server.essp.syncproject;

import itf.webservices.IAccountWService;

import java.util.List;
import java.util.Map;

public class SyncService {
	private List closeCallList;
    private List updatCallList;
    private List addCallList;
    private IAccountWService logic;
    
    public void invokeAdd(Map map) {
    	int size = addCallList.size();
    	for(int i = 0;i<size;i++){
    		logic = (IAccountWService)addCallList.get(i);
    		logic.addAccount(map);
    	}
    }
    public void invokeUpdate(Map map) {
    	int size = updatCallList.size();
    	IAccountWService logic = null;
    	for(int i = 0;i<size;i++){
    		logic = (IAccountWService)updatCallList.get(i);
    		logic.updateAccount(map);
    	}
    }
    public void invokeClose(String acntId){
    	int size = closeCallList.size();
    	IAccountWService logic = null;
    	for(int i = 0;i<size;i++){
    		logic = (IAccountWService)closeCallList.get(i);
//    		logic.closeAccount(acntId);
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
	public void setLogic(IAccountWService logic) {
		this.logic = logic;
	}
}
