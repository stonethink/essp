package server.framework.hibernate;

import org.apache.log4j.Category;
import java.util.Date;
import c2s.dto.DtoUtil;
import com.wits.util.ThreadVariant;
import server.framework.common.BusinessException;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 *
 * <p>
 * Company: Wistron ITS Wuhan SDC
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class HBComAccess extends HBJdbcAccess {
    static Category log = Category.getInstance("server");

//    private Object userObject;

    /**
     * This method search a exist HBComAccess Object. If it not exist , a new
     * HBComAccess Object will be created.
     *
     * @return HBComAccess
     */
    public static HBComAccess getInstance() {
        ThreadVariant thread = ThreadVariant.getInstance();
        String key = HBComAccess.class.getName();
        Object obj = thread.get(key);
        HBComAccess dbAccessor = null;
        if (obj != null) {
            dbAccessor = (HBComAccess) obj;
        } else {
            dbAccessor = new HBComAccess();
            thread.set(key, dbAccessor);
        }
        return dbAccessor;
    }

    /**
     * This method always create a new HBComAccess Object, and update it into
     * ThreadVariant
     *
     * @return HBComAccess
     */
    public static HBComAccess newInstance() {
        ThreadVariant thread = ThreadVariant.getInstance();
        String key = HBComAccess.class.getName();
        HBComAccess dbAccessor = null;
        dbAccessor = new HBComAccess();
        thread.set(key, dbAccessor);
        return dbAccessor;
    }

//    public void setUserObject() {
//        //this.userObject = null;
//    }
//
//    public Object getUserObject() {
//        return this.userObject;
//    }

    public HBComAccess() {
        super();
    }

    public HBComAccess(boolean isNewSession) {
        super();
        this.isNewSession = isNewSession;
    }

    public void save(Object object) throws RuntimeException {
        try {
            try {
                if (DtoUtil.hasProperty(object, "rid")) {
                    Long lRid = (Long) DtoUtil.getProperty(object, "rid");
                    if (lRid == null || lRid.intValue() == 0) {
                        assignedRid(object);
                    }
                }
                DtoUtil.setProperty(object, "rst", "N");
                DtoUtil.setProperty(object, "rct", new Date());
                DtoUtil.setProperty(object, "rut", new Date());
                DtoUtil.setProperty(object, "rcu", getLoginId());
                DtoUtil.setProperty(object, "ruu", getLoginId());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            super.save(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }

    }

    public void update(Object object) throws RuntimeException {
        try {
            try {
                DtoUtil.setProperty(object, "rut", new Date());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            super.update(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    /**
     * 将最后更新用户记录到相应的栏位中
     * @param object Object
     * @throws RuntimeException
     */
    public void updateU(Object object) throws RuntimeException {
        try {
            try {
                DtoUtil.setProperty(object, "rut", new Date());
                DtoUtil.setProperty(object, "ruu", getLoginId());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            super.update(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    public void saveOrUpdate(Object object) throws RuntimeException {
        try {
            try {
                if (DtoUtil.hasProperty(object, "rid")) {
                    Long lRid = (Long) DtoUtil.getProperty(object, "rid");
                    if (lRid == null || lRid.intValue() == 0) {
                        assignedRid(object);
                        DtoUtil.setProperty(object, "rst", "N");
                        DtoUtil.setProperty(object, "rct", new Date());
                    }
                }
                DtoUtil.setProperty(object, "rut", new Date());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
            super.saveOrUpdate(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    public void saveOrUpdateU(Object object) throws RuntimeException {
        try {
            try {

                if (DtoUtil.hasProperty(object, "rid")) {
                    Long lRid = (Long) DtoUtil.getProperty(object, "rid");
                    if (lRid == null || lRid.intValue() == 0) {
                        assignedRid(object);
                        DtoUtil.setProperty(object, "rst", "N");
                        DtoUtil.setProperty(object, "rct", new Date());
                        DtoUtil.setProperty(object, "rcu", getLoginId());
                    }
                }

                DtoUtil.setProperty(object, "rut", new Date());
                DtoUtil.setProperty(object, "ruu", getLoginId());
            } catch (Exception e) {
                log.debug(e.getMessage());
            }

            super.saveOrUpdate(object);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }


    public Object load(Class cls, Long lRid) throws RuntimeException {
        try {
            return super.load(cls, lRid);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    public Object load(Class cls, long lRid) throws RuntimeException {
        try {
            return super.load(cls, new Long(lRid));
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }


    public static Long assignedRid(Object object) throws RuntimeException {
        try {
            Long lRid = new Long(HBSeq.getSeqByHBClassName(object.getClass()
                    .getName()));
            DtoUtil.setProperty(object, "rid", lRid);
            return lRid;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    public static Long assignedRid(Object object, String tableName) throws
            RuntimeException {
        try {
            Long lRid = new Long(HBSeq.getSeqByHBClassName(tableName));
            DtoUtil.setProperty(object, "rid", lRid);
            return lRid;
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new BusinessException("error.system.db", e);
        }
    }

    private static String getLoginId() {
        String loginId = "";
        try {
            ThreadVariant thread = ThreadVariant.getInstance();
            loginId = (String) thread.get("LOGIN_ID");
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return loginId;
    }

}
