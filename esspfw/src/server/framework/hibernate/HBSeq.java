package server.framework.hibernate;


import org.apache.log4j.*;

import essp.tables.*;
import server.framework.common.BusinessException;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.mapping.PersistentClass;
import net.sf.hibernate.mapping.Table;
import net.sf.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import net.sf.hibernate.engine.SessionImplementor;
import java.sql.SQLException;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.id.Configurable;
import net.sf.hibernate.type.Type;
import java.util.Properties;
import net.sf.hibernate.dialect.Dialect;
import net.sf.hibernate.MappingException;
import net.sf.hibernate.id.PersistentIdentifierGenerator;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author Stone
 * @version 1.0
 */
public class HBSeq implements IdentifierGenerator ,Configurable{
  static Category log = Category.getInstance("server");

  public synchronized static long getSeqByHBClassName(String className) {
    if (className == null) {
      return 0;
    }
    String type=getTableName(className);

    long lSeqno = getSEQ(type);
    return lSeqno;
  }

  public synchronized static long getSEQ(String type2) {
  	String type=type2;
    HBComAccess hbAccess = new HBComAccess();
    EsspHbseq esspHbseq = new EsspHbseq();

    long lSeqno = 0;

    if (type == null || type.length() == 0) {
      type = "__DEFAULT__";
    }

    esspHbseq.setSeqType(type.toUpperCase());
    esspHbseq.setSeqNo(new Long(lSeqno));

    try {
      hbAccess.newTx();
      esspHbseq = (EsspHbseq) hbAccess.load(EsspHbseq.class, type.toUpperCase());
      lSeqno = esspHbseq.getSeqNo().longValue() + 1;
      esspHbseq.setSeqNo(new Long(lSeqno));
      hbAccess.update(esspHbseq);
      hbAccess.endTxCommit();
      return lSeqno;
    } catch (Exception e) {
      try {
        lSeqno = 1;
        esspHbseq.setSeqNo(new Long(lSeqno));
        hbAccess.save(esspHbseq);
        hbAccess.endTxCommit();
        return lSeqno;
      } catch (Exception ex) {
          ex.printStackTrace();
          log.error("Insert ESSP_SEQ error,SEQ_TYPE=" + type);
      }
    }

    return lSeqno;
  }

  private static String getTableName(String persistentClassName) {
    String tableName=null;
    try {
        Class persistentClass=Class.forName(persistentClassName);
        tableName=getTableName(persistentClass);
    } catch (Exception ex) {
        throw new BusinessException(ex);
    }
    return tableName;
  }

  private static String getTableName(Class persistentClass) {
    String tableName=null;
    try {
        Configuration cfg = HibernateUtil.getInstance().getConfig();
        PersistentClass obj=cfg.getClassMapping(persistentClass);
        Table table=obj.getTable();
        tableName=table.getName();
    } catch (Exception ex) {
        throw new BusinessException(ex);
    }
    return tableName;
  }
  //--------------------------实现Hibernate自定义ID生成接口--------------------------
  private String table;
    public Serializable generate(SessionImplementor sessionImplementor,
                                 Object object) throws SQLException,
            HibernateException {
        return getSEQ(table);
    }

    public void configure(Type type, Properties properties, Dialect dialect) throws
            MappingException {
        //     取出table参数
       table = properties.getProperty("table");
       if (table == null) {
           table = properties.getProperty(PersistentIdentifierGenerator.TABLE);
       }
    }
}
