import java.rmi.*;
import java.util.*;

import org.apache.axis.client.*;
import org.springframework.context.*;
import org.springframework.context.support.*;

public class TestClient {

	private List closeCallList;
	private List updatCallList;
        private List addCallList;

	public void invoke() throws RemoteException{
		for(int i = 0;i < closeCallList.size() ;i ++){
			Call call = (Call) closeCallList.get(i);
			call.invoke(new Object[]{"002645W"});
		}
	}


	public static void main(String[] args) throws RemoteException{
		System.out.print(System.getProperty("javax.xml.parsers.SAXParserFactory"));
		ApplicationContext app = new ClassPathXmlApplicationContext("sysCfg/applicationContext-essp.xml");
		TestClient test = (TestClient) app.getBean("test");
		test.invoke();
	}

    public List getAddCallList() {
        return addCallList;
    }

    public List getCloseCallList() {
        return closeCallList;
    }

    public List getUpdatCallList() {
        return updatCallList;
    }

    public void setUpdatCallList(List updatCallList) {
        this.updatCallList = updatCallList;
    }

    public void setCloseCallList(List closeCallList) {
        this.closeCallList = closeCallList;
    }

    public void setAddCallList(List addCallList) {
        this.addCallList = addCallList;
    }
}
