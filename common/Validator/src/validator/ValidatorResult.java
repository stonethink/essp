package validator;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class ValidatorResult implements Serializable {
  public static final String ANONYMOUS = "ANONYMOUS";
  private HashMap msgMap = new HashMap();
  public boolean isValid() {
    if(msgMap.size()>0) {
      return false;
    }
    return true;
  }

  public boolean isValid(String field) {
    if(getMsg(field)!=null) {
      return false;
    }
    return true;
  }

  public String[] getMsg() {
    String firstKey=ANONYMOUS;
    if(msgMap.keySet().size()>0) {
      firstKey=(String)msgMap.keySet().iterator().next();
    }
    return getMsg(firstKey);
  }

  public String[] getAllMsg() {
    List allMsgList=new ArrayList();
    for(Iterator it=msgMap.keySet().iterator();it.hasNext();) {
      String key=(String)it.next();
      List msgList=(List)msgMap.get(key);
      if(msgList!=null) {
        allMsgList.addAll(msgList);
      }
    }
    String[] allMsg=null;
    if(allMsgList.size()>0) {
      allMsg=new String[allMsgList.size()];
      for (int i = 0; i < allMsgList.size(); i++) {
        allMsg[i]=(String)allMsgList.get(i);
      }
    }
    return allMsg;
  }

  public String[] getMsg(String field) {
    String[] msg = null;
    List msgList = (List) msgMap.get(field);
    if (msgList != null && msgList.size() > 0) {
      msg = new String[msgList.size()];
      for (int i = 0; i < msgList.size(); i++) {
        msg[i] = (String) msgList.get(i);
      }
    }
    return msg;
  }

  public void setMsg(String field, String[] msg) {
    List msgList = (List) msgMap.get(field);
    if (msgList == null) {
      msgList = new ArrayList();
      msgMap.put(field, msgList);
    }
    else {
      msgList.clear();
    }

    if (msg != null) {
      for (int i = 0; i < msg.length; i++) {
        msgList.add(msg[i]);
      }
    }
  }

  public void setMsg(String[] msg) {
    setMsg(ANONYMOUS, msg);
  }

  public void addMsg(String field, String msg) {
    List msgList = (List) msgMap.get(field);
    if (msgList == null) {
      msgList = new ArrayList();
      msgMap.put(field, msgList);
    }
    msgList.add(msg);
  }

  public void addMsg(String msg) {
    String firstKey=ANONYMOUS;
    if(msgMap.keySet().size()>0) {
      firstKey=(String)msgMap.keySet().iterator().next();
    }
    addMsg(firstKey, msg);
  }

  public String[] getErrorFields() {
      String[] fields=null;
      if(msgMap.size()>0) {
          fields=new String[msgMap.size()];
      }
      int i=0;
      for(Iterator it=msgMap.keySet().iterator();it.hasNext();) {
          fields[i]=(String)it.next();
          i++;
      }
      return fields;
  }

  public ValidatorResult duplicate() {
    ValidatorResult result=new ValidatorResult();
    result.msgMap=(HashMap)(this.msgMap.clone());
    return result;
  }

  /**
   * Çå³ývalidate½á¹û
   */
  public void clear() {
      msgMap.clear();
  }
}
