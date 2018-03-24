package server.framework.taglib.util;

import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Title:SelectContentBase </p>
 *
 * <p>Description: 所有Select中的内容均需继承此类，并设置标签中的Type属性为此类的相应子类</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin.zhang
 * @version 1.0
 */

public abstract class SelectContentBase {

    public SelectContentBase() {
//        this.orgIds = "UNIT00000328";

    }

    public void initData() {

        try {
            getData();
        } catch (Exception e) {
            System.out.println("数据读取失败");
        }

    }

    public String orgIds = "";
    
    private ServletRequest request;
    private HttpSession session;

    public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public abstract void getData() throws Exception;

    public abstract ArrayList getList();

    public abstract Object getDefualt();

    public void setOrgIds(String orgIds) {
        this.orgIds = orgIds;
    }

    public String getOrgIds() {
        return this.orgIds;
    }

    public Object elementAt(int i) {
        return getList().get(i);
    }

    public Object get(String key) {
        for (int i = 0; i < size(); i++) {
            SelectContentInterface d = (SelectContentInterface) getList().get(i);
            if (key.equals(d.getValue())) {
                return d;
            }
        }
        return getDefualt();
    }

    public Object getById(int key) {
        for (int i = 0; i < size(); i++) {
            SelectContentInterface d = (SelectContentInterface) getList().get(i);
            if (key == d.getId()) {
                return d;
            }
        }
        return getDefualt();
    }

    public int size() {
        return getList().size();
    }

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}
}
