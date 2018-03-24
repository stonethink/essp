package com.wits.db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

import org.apache.log4j.Category;

/**
 * <p>
 *comment: This class is the parent class of the database access interface DataBaseAcc,
 *         It provides the basic API for database connection.
 *</p>
 *<p>
 *DATE:2002/12/11
 *</p>
 **/
public class DbConnection {
    private static HashMap htDS = new HashMap();
    static Category log = Category.getInstance( "server" );

  //private static DataSourcePara mDataSourcePara = null;
  //private static JdbcPara mJdbcPara = null;
  public static Connection getConnect() {
    return getConnect("");
  }

  public static Connection getConnect(String key) {
    Connection mConn = null;

    Object oPara = DbConfig.getPara(key);

    if (oPara instanceof DataSourcePara) {
      mConn = DbConnection.connectDataSource( (DataSourcePara) oPara);
    }
    else {
      mConn = DbConnection.connectJdbc( (JdbcPara) oPara);
    }

    return mConn;

  }

  //DataSourceConnection
  public static Connection connectDataSource(DataSourcePara mDataSourcePara) {
    DataSource ds = null;
    String dsKey = null;
    Connection mConn = null;

    ds = (DataSource) htDS.get(mDataSourcePara.DbKey);
    if (ds == null) {
      //mDataSourcePara = DbConfig.getDataSourcePara(key);

      Hashtable ht = new Hashtable();
      ht.put(Context.INITIAL_CONTEXT_FACTORY, mDataSourcePara.InitContext);
      ht.put(Context.PROVIDER_URL, mDataSourcePara.ProviderUrl);
      try {
        Context ctx = new InitialContext(ht);
        ds = (DataSource) ctx.lookup(mDataSourcePara.DataSource);
        htDS.put(mDataSourcePara.DbKey, ds);
      }
      catch (Exception e) {
        log.debug( "DataSourceConnection Error:" + e.getMessage());
        log.error( "Exception occur", e );
      }

    }

    try {
      log.debug("In DbConnection UserName=" + mDataSourcePara.UserName +
                ";length=" + mDataSourcePara.UserName.length());

      if (mDataSourcePara.UserName == null || mDataSourcePara.UserName == "") {
        mConn = ds.getConnection();
      }
      else {
        mConn = ds.getConnection(mDataSourcePara.UserName,
                                 mDataSourcePara.Password);
      }
      return (mConn);
    }
    catch (SQLException e) {
      log.debug( "DataSourceConnection Error:" + e.getMessage());
      log.error( "Exception occur", e );
    }
    return (mConn);
  }

  //JdbcConnection
  public static Connection connectJdbc(JdbcPara mJdbcPara) {
    Connection mConn = null;

//    log.debug("In connectJdbc UserName=" + mJdbcPara.UserName +
//             ";password=" + mJdbcPara.Password);
   try {
      Class.forName(mJdbcPara.DbDriver);
    }
    catch (ClassNotFoundException e) {
      log.debug(
                "Can't find Class name for Database Driver: " +
                mJdbcPara.DbDriver);
      log.error( "Exception occur", e );
      //throw new SQLException("Can't find Class name for Database Driver: " + mJdbcPara.DbDriver);
    }
    try {
      mConn = DriverManager.getConnection(mJdbcPara.DbUrl, mJdbcPara.UserName,
                                          mJdbcPara.Password);
      return (mConn);
    }
    catch (SQLException e) {
      log.debug( "GetConnection error: DbUrl=" + mJdbcPara.DbUrl
                + ";UserName=" + mJdbcPara.UserName + ";Password=" +
                mJdbcPara.Password);
      log.error( "Exception occur", e );
      //throw new SQLException("Can't find Class name for Database Driver: " + mJdbcPara.DbDriver);
    }

    return (mConn);
  }

}
