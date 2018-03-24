package client.framework.view.vwcomp;

import java.net.URL;
import java.util.Map;
import javax.swing.JApplet;

import client.framework.common.Global;
import com.wits.util.Parameter;
import netscape.javascript.JSObject;
import org.apache.log4j.Category;
import validator.Validator;
import c2s.dto.DtoUtil;
import com.wits.util.ProcessVariant;
import com.wits.util.comDate;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Locale; 

public abstract class VWJApplet extends JApplet implements IVWJavaScriptCaller {
    protected static Category log = Category.getInstance("client");
    protected static final String KEY_CONTROLLER_URL = "controllerUrl";
    protected static final String KEY_APP_ROOT = "serverURL";
    protected boolean isStandalone = false;
    private Map standaloneMap = new java.util.HashMap();
    private IVWJavaScriptListener jsListener = null;

    public VWJApplet() {
    }

    /**
     * ��ͨ�ĳ�ʼ��������Ŀǰֻ����ControllUrl��������ȡ
     */
    public final void init() {
        System.out.println("Welcome enter ESSP!");
        log.info("ProcessVariant.clear()");
		ProcessVariant.clear();

        Validator.setResourceInstance(this.getClass());

        /**
         * ����ȫ�ֲ���
         */
        Global.applet = this;
        Global.userId = getParameter("userId");
        Global.userName = getParameter("userName");
        Global.userType = getParameter("userType");

        Global.todayDateStr = getParameter("todayDate");
        Global.timeZoneID = getParameter("timeZoneID");
        //����Applet�ͻ���ʱ����Server��ʱ��ͬ��
        if(Global.timeZoneID == null || "".equals(Global.timeZoneID.trim())){
            Global.timeZoneID = Global.DEFAULT_TIMEZONE;
        }
        TimeZone.setDefault(TimeZone.getTimeZone(Global.timeZoneID));
        Global.selDateFromCalendar = Calendar.getInstance();
        if (!nvl(getParameter("todayDatePattern")).trim().equals("")) {
            Global.todayDatePattern = getParameter("todayDatePattern");
            Global.todayDate = comDate.toDate(Global.todayDateStr,Global.todayDatePattern);
        }
        //����Applet�ͻ��������Server��һ��
        String locale = getParameter("locale");
        System.out.println("Get Loacle: " + locale);
        if (!nvl(locale).trim().equals("")) {
            Global.locale = new Locale(locale);
        }

        URL docBase = null;

        try {
            //log.info("try to getDocumentBase ");
            docBase = this.getDocumentBase();

            //log.info("docBase is "+docBase);
        } catch (Exception ex) {
            log.info(ex);
        }

        if (docBase != null) {
            String protocol = docBase.getProtocol();

            //log.info("Protocol is "+Protocol);

            /**
             * ����Applet�Ƿ�������ҳ�ϱ���������
             */
            if ((protocol != null) && protocol.equalsIgnoreCase("http")) {
                String docRoot = "";
                String url = docBase.toString().trim();

                //log.info("url is "+url);
                // address is "http://hostname:port/docRoot/..."
                //first 7 char is "http://"
                //index1 is  "http://hostname:port/"

                /**
                 * docBase��ǰ�߸��ַ��� "http://",��������"hostname:port"
                 * ���docBase��ֵ���� "http://hostname:port/" ���Ǹ�document Root���� "http://hostname:port/"
                 * ���docBase��ֵ���� "http://hostname:port/xxxx/.../" ���Ǹ�document Root���� "http://hostname:port/xxxx/"
                 */
                if ((url.length() > 7) && (url.substring(7).indexOf("/") >= 0)) {
                    //log.info("index 7:"+url.substring(7));
                    int index1 = 7 + url.substring(7).indexOf("/") + 1;

                    //log.info("index 7 + url.substring(7).indexOf(\"/\")) is "+index1);
                    if ((index1 > 7) && (url.length() > index1)) {
                        int index2 = url.substring(index1).indexOf("/");

                        //log.info("index2="+index2);
                        if (index2 >= 0) {
                            docRoot = url.substring(0, index1 + index2);
                        } else {
                            docRoot = url.substring(0, index1);
                        }
                    } else {
                        docRoot = url;
                    }
                }

                //log.info("docRoot is "+docRoot);
                if (docRoot.trim().equals("") == false) {
                    if (docRoot.endsWith("/") == false) {
                        docRoot = docRoot + "/";
                    }

                    Global.appRoot = docRoot.substring(0, docRoot.length() - 1);

                    //log.info("docRoot is "+docRoot);

                    /**
                     * controllerUrl��ֵ�� document Root+controllerUrl
                     */
                    String controllerUrl = this.getParameter(KEY_CONTROLLER_URL);
                    log.info("PARAM " + KEY_CONTROLLER_URL + " is '"
                             + controllerUrl + "'");

                    if (controllerUrl != null) {
                        if (controllerUrl.trim().startsWith("/")) {
                            controllerUrl = controllerUrl.trim().substring(1);
                        }

                        if (controllerUrl.trim().toLowerCase().startsWith("http") == false) {
                            Global.defaultControllerURL = docRoot
                                + controllerUrl;
                        } else {
                            Global.defaultControllerURL = controllerUrl;
                        }

                        log.info("DefaultControllerURL is '"
                                 + Global.defaultControllerURL + "'");
                    } else {
                        log.info("PARAM " + KEY_CONTROLLER_URL + " is null");
                    }
                }
                setStandalone(false);
            } else {
                //setStandalone(true);
            }
        } else {
            // this applet is invoked by local , no need to set url
            setStandalone(true);
        }

        initUI();
    }

    /**
     * ��Applet����ʵ�ֵĳ�ʼ������
     */
    public abstract void initUI();

    /**
     * ȡ��ҳ�洫��Ĳ���
     * @param key String
     * @param def String
     * @return String
     */
    public String getParameter(String key,
                               String def) {
        return isStandalone ? System.getProperty(key, def)
            : ((getParameter(key) != null) ? getParameter(key)
               : def);
    }

    /**
     * ����encodeParam��������ַ���
     * @param paramString String
     * @throws Exception
     * @return Map
     */
    protected Map decodeParam(String paramString) throws Exception {
        return DtoUtil.decodeParam(paramString);
    }

    /**
     * ��Map���͵Ĳ���������ַ���
     * @param params Map
     * @throws Exception
     * @return String
     */
    protected String encodeParam(Map params) throws Exception {
        return DtoUtil.encodeParam(params);
    }

    /**
     * ȡ�ø����ھ��
     * @return Frame
     */
    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }

    /**
     * ʵ��Java Applet�е���JavaScript����
     * @param functionName String JavaScript������
     * @param args Object[]       ���ò���
     */
    protected void callJs(String functionName,
                          Object[] args) {
        JSObject win = JSObject.getWindow(this);
        JSObject doc = (JSObject) win.getMember("document");
        JSObject loc = (JSObject) doc.getMember("location");

        String s = (String) loc.getMember("href"); // document.location.href
        win.call(functionName, args); // Call f() in HTML page
    }

    public void setParameter(String name,
                             String value) {
        if (isStandalone) {
            standaloneMap.put(name, value);
        }
    }

    public String getParameter(String name) {
        if (isStandalone) {
            return (String) standaloneMap.get(name);
        } else {
            String value = "";
            try {
                value = super.getParameter(name);
            } catch (Exception e) {
                // TODO �Զ����� catch ��
                e.printStackTrace();
            }
            return value;
        }
    }

    protected void setStandalone(boolean isStandalone) {
        this.isStandalone = isStandalone;
    }

    /**
     * ������ performJS() ��Java������ã�Ȼ����JavaScript��ɲ�����ص�calledByJS()
     * @param listener IVWJavaScriptListener
     * @param functionName String
     * @param args Object[]
     */
    public void performJS(IVWJavaScriptListener listener, String functionName, Object[] args) {
        jsListener = listener;
        callJs(functionName, args);
    }

    /**
     * This method is called by JavaScript
     * @param paramString a String like "a=1&b=2&c=3..."
     */
    public void calledByJS(String paramString) {
        if (paramString != null && !paramString.trim().equals("")) {
            Parameter param = new Parameter();
            String[] args = paramString.split("&");
            for (int i = 0; i < args.length; i++) {
                String argString = args[i];
                if (argString.indexOf("=") > 0) {
                    String[] argStr = argString.split("=");
                    String argsName = argStr[0];
                    String argsValue = null;
                    if (argStr.length > 1) {
                        argsValue = argStr[1];
                    }
                    param.put(argsName, argsValue);
                }
            }
            if (jsListener != null) {
                jsListener.actionPerformed(param);
            }

        }
    }

    public String nvl(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    public static void main(String[] args) {
        //calledByJS("a=1&b=2&c=3");
        System.out.println("OK");

        String[] argStr = "newData=".split("=");
        String argsName = argStr[0];
        String argsValue = argStr[1];

    }
}
