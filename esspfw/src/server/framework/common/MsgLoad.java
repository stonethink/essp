package server.framework.common;

/**
 * @author Stone 20050304
 *
 */

import java.util.*;

public class MsgLoad {

  private static MsgLoad me = null;

  //Action_XX
  private static HashMap hmSqlCfg = new HashMap();

  //Sql语句
  private static HashMap hmSqlStm = new HashMap();

  private MsgLoad() {
    _Config("msg");
  }

  public static MsgLoad getInstance() {
    if (me == null) {
      me = new MsgLoad();
    }
    return me;
  }

  //Load Action_XX
  public void loadActionConfig(String system_name) {
    String name = "";
    if (system_name == null || system_name.length() == 0) {
      name = "msg";
    }
    else {
      name = "msg_" + system_name;
    }

    _Config(name);
  }

  public String getMsg(String msg_id) {
    if (msg_id == null) {
      return null;
    }

    if (!hmSqlStm.containsKey( (String) msg_id)) {
      if (msg_id.length() > 3) {
        String system_name = msg_id.substring(1, 3);
        return getMsg(system_name, msg_id);
      }
      else {
        return null;
      }
    }

    String  msg_value = (String)hmSqlStm.get(msg_id);
    return msg_value;
  }

  public String getMsg(String system_name, String msg_id) {
    String name = "";

    if (system_name == null || system_name.length() == 0) {
      name = "msg";
    }
    else {
      name = "msg_" + system_name;
    }

    if (!hmSqlCfg.containsKey( (String) name)) {
      loadActionConfig(system_name);
    }

    String  msg_value = (String)hmSqlStm.get(msg_id);
    return msg_value;
  }

  private void _Config(String configFileName) {
    try {
      //为了标记是否载入Action_XX.properties
      hmSqlCfg.put(configFileName, configFileName);

      ResourceBundle resources = ResourceBundle.getBundle(configFileName,
          Locale.CHINA);
      Enumeration enumVar = resources.getKeys();
      while (enumVar.hasMoreElements()) {
        String key = (String) enumVar.nextElement();
        String value = resources.getString(key).trim();
        //hmSqlCfg.put(key, value);
        //将Action_XX.properties的记录载入hmSqlStm
        hmSqlStm.put(key, value);
      }
    }
    catch (MissingResourceException e) {
      e.printStackTrace();
    }
    catch (NullPointerException e) {
      e.printStackTrace();
    }
  }
}
