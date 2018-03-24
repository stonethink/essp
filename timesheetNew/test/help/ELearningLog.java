package help;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import c2s.essp.common.user.DtoUser;

public class ELearningLog {
	private String fileName = "c:\\eLearingLog.xml";
	private final static Namespace NS_EMP = Namespace.getNamespace("employee");
	private final static String ATT_COUNT = "count";
	private final static String ATT_PERSON = "person";
	public ELearningLog(String file) {
		fileName = file;
	}
	
	public void log(HttpServletRequest request) {
		try {
			this.log(this.getUser(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void log(DtoUser user) throws JDOMException, IOException {
		if(user == null) {
			return;
		}
		String loginId = user.getUserLoginId();
		String name = user.getUserName();
		String orgId = user.getOrgId();
		SAXBuilder builder = new SAXBuilder();
		File f = new File(fileName);
		Document doc = null;
		if(f.exists() == false) {
			Element root = new Element("root", "root");
			root.setAttribute(ATT_COUNT, "0");
			root.setAttribute(ATT_PERSON, "0");
			doc = new Document(root);
		} else {
			doc = builder.build(fileName); // ≤È’“Œƒµµ
		}
		Element root = doc.getRootElement();
		Element emp = root.getChild(loginId, NS_EMP);
		if(emp == null) {
			emp = new Element(loginId, NS_EMP);
			emp.setAttribute("name", name);
			emp.setAttribute(ATT_COUNT, "1");
			emp.setAttribute("dept", orgId);
			emp.setAttribute("date", (new Date()).toString());
			root.addContent(emp);
			addPerson(root);
		} else {
			addCount(emp);
		}
		addCount(root);
		
		
		
		
		FileOutputStream fout = new FileOutputStream(fileName);
		XMLOutputter out = new XMLOutputter();
		out.output(doc, fout);
		System.out.println("complete");
	}
	
	private void addPerson(Element element) {
		this.addCount(element, ATT_PERSON);
	}
	
	private void addCount(Element element) {
		this.addCount(element, ATT_COUNT);
	}
	
	private void addCount(Element element, String countAtt) {
		Attribute att = element.getAttribute(countAtt);
		String countStr = att.getValue();
		int count = 0;
		if(countStr != null && "".equals(countStr) == false) {
			try {
				count = Integer.valueOf(countStr);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		count ++;
		att.setValue(String.valueOf(count));
	}
	
	private DtoUser getUser(HttpServletRequest request) {
		return (DtoUser) request.getSession().getAttribute(DtoUser.SESSION_USER);
	}

}
