package server.framework.controller;

/**
 * @author Stone 20050304
 *
 */

import java.util.*;
import java.io.InputStream;

public class CTActionLoad {

  private static CTActionLoad me = null;

  //Action_XX
  private static HashMap hmSqlCfg = new HashMap();

  //Sql语句
  private static HashMap hmSqlStm = new HashMap();

  private CTActionLoad() {
    _Config("action");
  }

  public static CTActionLoad getInstance() {
    if (me == null) {
      me = new CTActionLoad();
    }
    return me;
  }

  //Load Action_XX
  public void loadActionConfig(String system_name) {
    String name = "";
    if (system_name == null || system_name.length() == 0) {
      name = "action";
    }
    else {
      name = "action_" + system_name;
    }

    _Config(name);
  }

  public String getValue(String action_id) {
    if (action_id == null) {
      return null;
    }

    if (!hmSqlStm.containsKey( (String) action_id)) {
      if (action_id.length() > 3) {
        String system_name = action_id.substring(1, 3);
//        System.out.println("action_id:"+action_id+"-----system:"+system_name);
        return getValue(system_name, action_id);
      }
      else {
        return null;
      }
    }

    String  action_value = (String)hmSqlStm.get(action_id);
    return action_value;
  }

  public String getValue(String system_name, String action_id) {
    String name = "";

    if (system_name == null || system_name.length() == 0) {
      name = "action";
    }
    else {
      name = "action_" + system_name;
    }

    if (!hmSqlCfg.containsKey( (String) name)) {
      loadActionConfig(system_name);
    }

    String  action_value = (String)hmSqlStm.get(action_id);
    return action_value;
  }

  private void _Config(String configFileName) {
    try {
      //为了标记是否载入Action_XX.properties
      hmSqlCfg.put(configFileName, configFileName);
//      System.out.println("load action config:" + configFileName+".properties");
//      InputStream in = this.getClass().getClassLoader().getResourceAsStream(configFileName+".properties");
//      in = ClassLoader.getSystemResourceAsStream(configFileName+".properties");
//      System.out.println("---------------------------------");
//      byte[] cache = new byte[1024] ;
//      int totalCount = 0;
//      while( in.read(cache) != -1){
//          String s = new String(cache);
//          System.out.println(s);
//          totalCount += (s.length());
//      }
//      System.out.println("-----------size:"+totalCount+"---------------------");
      ResourceBundle resources = ResourceBundle.getBundle(configFileName,
          Locale.getDefault());
      Enumeration enumVar = resources.getKeys();
      while (enumVar.hasMoreElements()) {
        String key = (String) enumVar.nextElement();
        String value = resources.getString(key).trim();
        //hmSqlCfg.put(key, value);
        //将Action_XX.properties的记录载入hmSqlStm
//        System.out.println("key:"+key+"/////value:"+value);
        hmSqlStm.put(key, value);
      }
    }
    catch (MissingResourceException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
