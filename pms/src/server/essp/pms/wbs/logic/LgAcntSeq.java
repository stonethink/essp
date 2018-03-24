package server.essp.pms.wbs.logic;

import server.framework.hibernate.HBComAccess;
import net.sf.hibernate.cfg.Configuration;
import server.framework.common.BusinessException;
import net.sf.hibernate.mapping.Table;
import server.framework.hibernate.HibernateUtil;
import net.sf.hibernate.mapping.PersistentClass;
import db.essp.pms.AccountSeq;
import db.essp.pms.AccountSeqPK;
import java.util.Date;
import db.essp.pms.Activity;
import db.essp.pms.Wbs;

public class LgAcntSeq {
    public static Long getSeq(Long acntRid,Class type) {
        String tablename=getTableName(type);
        return getSeq(acntRid,tablename);
    }

    public static Long getSeq(Long acntRid,String type2) {
        if(acntRid==null) {
            throw new BusinessException("LgAcntSeq","Account Rid is null");
        }
        if(type2==null) {
            throw new BusinessException("LgAcntSeq","Table name is null");
        }
        String type=type2.toUpperCase();
        Long result = null;
        HBComAccess dbAccessor = null;
        try {
            dbAccessor = new HBComAccess();
            dbAccessor.newTx();
            AccountSeq seq = null;
            AccountSeqPK pk = new AccountSeqPK(acntRid, type);
            seq = (AccountSeq) dbAccessor.get(AccountSeq.class, pk);
            if (seq == null) {
                seq = new AccountSeq(pk);
                seq.setLastRid(new Long(1));
                seq.setSeq(new Long(10000));
                seq.setStep(new Long(10));
                seq.setRst("N");
                seq.setRut(new Date());
                seq.setRct(new Date());
                dbAccessor.save(seq);
                result = seq.getLastRid();
            } else {
                long lLastRid = seq.getLastRid().longValue() + 1;
//                long lCode = seq.getSeq().longValue() + seq.getStep().longValue();
                seq.setLastRid(new Long(lLastRid));
//                seq.setSeq(new Long(lCode));
                dbAccessor.update(seq);
                result = new Long(lLastRid);
            }
            dbAccessor.commit();
        } catch (net.sf.hibernate.ObjectNotFoundException e) {
            try {
                dbAccessor.rollback();
                AccountSeq seq = null;
                AccountSeqPK pk = new AccountSeqPK(acntRid, type);
                seq = new AccountSeq(pk);
                seq.setLastRid(new Long(1));
                seq.setSeq(new Long(10000));
                seq.setStep(new Long(10));
                seq.setRst("N");
                seq.setRut(new Date());
                seq.setRct(new Date());
                dbAccessor.save(seq);
                result = seq.getLastRid();
                dbAccessor.commit();
            } catch (Exception ex) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (dbAccessor != null) {
                    dbAccessor.rollback();
                }
            } catch (Exception e) {
            }
        } finally {
            try {
                if (dbAccessor != null) {
                    dbAccessor.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static Long getActivitySeq(Long acntRid) {
        Long result = null;
        String type = getTableName(Activity.class);
        result=getSeq(acntRid,type);
        return result;
    }

    public static String getActivityCode(Long acntRid) {
        String result = null;
        HBComAccess dbAccessor = null;
        try {
            dbAccessor = new HBComAccess();
            String type = getTableName(Activity.class);
            AccountSeq seq = null;
            AccountSeqPK pk = new AccountSeqPK(acntRid, type);
            seq = (AccountSeq) dbAccessor.load(AccountSeq.class, pk);
            if (seq == null) {
                seq = new AccountSeq(pk);
                seq.setLastRid(new Long(1));
                seq.setSeq(new Long(10000));
                seq.setStep(new Long(10));
                seq.setRst("N");
                seq.setRut(new Date());
                seq.setRct(new Date());
                dbAccessor.save(seq);
                result = "A10000";
            } else {
                long lCode = seq.getSeq().longValue() + seq.getStep().longValue();
                seq.setSeq(new Long(lCode));
                dbAccessor.update(seq);
                result = "A" + lCode;
            }
            dbAccessor.commit();
            dbAccessor.close();
        } catch (net.sf.hibernate.ObjectNotFoundException e) {
            try {
                dbAccessor.rollback();
                AccountSeq seq = null;
                String type = getTableName(Activity.class);
                AccountSeqPK pk = new AccountSeqPK(acntRid, type);
                seq = new AccountSeq(pk);
                seq.setLastRid(new Long(1));
                seq.setSeq(new Long(10000));
                seq.setStep(new Long(10));
                seq.setRst("N");
                seq.setRut(new Date());
                seq.setRct(new Date());
                dbAccessor.save(seq);
                result = "A10000";
                dbAccessor.commit();
            } catch (Exception ex) {
            }

        } catch (Exception ex) {
            try {
                if (dbAccessor != null) {
                    dbAccessor.rollback();
                }
            } catch (Exception e) {
            }
        } finally {
            try {
                if (dbAccessor != null) {
                    dbAccessor.close();
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static Long getWbsSeq(Long acntRid) {
        Long result = null;
        String type = getTableName(Wbs.class);
        result=getSeq(acntRid,type);
        return result;
    }

    public static void setRootRid(Long accountRid,Class type, Long rootRid) {
        String tablename = getTableName(type);
        setRootRid(accountRid,tablename,rootRid);
    }

    public static void setRootRid(Long accountRid,String type, Long rootRid) {
        String tableName = type;
        HBComAccess dbAccessor = null;
        if (tableName != null) {
            try {
                dbAccessor = new HBComAccess();
                dbAccessor.newTx();
                AccountSeq seq = null;
                AccountSeqPK pk = new AccountSeqPK(accountRid, tableName);
                seq = (AccountSeq) dbAccessor.load(AccountSeq.class,
                    pk);
                seq.setRootRid(rootRid);
                dbAccessor.update(seq);
                dbAccessor.commit();
            } catch (net.sf.hibernate.ObjectNotFoundException e) {
                try {
                    AccountSeq seq = null;
                    AccountSeqPK pk = new AccountSeqPK(accountRid, tableName);
                    seq = new AccountSeq(pk);
                    seq.setLastRid(new Long(0));
                    seq.setRootRid(rootRid);
                    dbAccessor.save(seq);
                    dbAccessor.commit();
                } catch (Exception ex) {
                    throw new BusinessException(ex);
                }
            } catch (Exception ex) {
                throw new BusinessException(ex);
            } finally {
                try {
                    dbAccessor.close();
                } catch (Exception ex) {

                }
            }
        } else {
            throw new BusinessException("E_ACNTSEQ",
                                        "Table Type is null");
        }
    }

    public static Long getRootRid(Long accountRid,Class type) {
        String tablename = getTableName(type);
        return getRootRid(accountRid,tablename);
    }

    public static Long getRootRid(Long accountRid,String type) {
        Long result = null;
        String tableName = type;
        HBComAccess dbAccessor = null;
        if (tableName != null) {
            try {
                dbAccessor = new HBComAccess();
                dbAccessor.newTx();
                AccountSeq seq = null;
                AccountSeqPK pk = new AccountSeqPK(accountRid, tableName);
                seq = (AccountSeq) dbAccessor.load(AccountSeq.class,
                    pk);
                result = seq.getRootRid();
            } catch (net.sf.hibernate.ObjectNotFoundException e) {
                /**
                 * 没有找到此笔记录，则新建一笔
                 */
                try {
                    dbAccessor.rollback();
                    dbAccessor.followTx();
                    AccountSeqPK pk = new AccountSeqPK(accountRid, tableName);
                    AccountSeq seq = new AccountSeq(pk);
                    seq.setLastRid(new Long(0));
                    dbAccessor.save(seq);
                    dbAccessor.commit();
                } catch (Exception e2) {
                    try {
                        if (dbAccessor != null) {
                            dbAccessor.rollback();
                        }
                    } catch (Exception ex) {

                    }
                }
                return null;
            } catch (Exception ex) {
                throw new BusinessException(ex);
            } finally {
                try {
                    if (dbAccessor != null) {
                        dbAccessor.close();
                    }
                } catch (Exception ex) {

                }

            }
        } else {
            throw new BusinessException("E_ACNTSEQ",
                                        "Table Type is null");
        }
        return result;
    }


    public static void setWbsRootRid(Long accountRid, Long rootRid) {
        String tableName = getTableName(Wbs.class);
        setRootRid(accountRid,tableName,rootRid);
    }

    public static Long getWbsRootRid(Long accountRid) {
        Long result = null;
        String tableName = getTableName(Wbs.class);
        result=getRootRid(accountRid,tableName);
        return result;
    }

    private static String getTableName(String persistentClassName) {
        String tableName = null;
        try {
            Class persistentClass = Class.forName(persistentClassName);
            tableName = getTableName(persistentClass);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return tableName;
    }

    private static String getTableName(Class persistentClass) {
        String tableName = null;
        try {
            Configuration cfg = HibernateUtil.getInstance().getConfig();
            PersistentClass obj = cfg.getClassMapping(persistentClass);
            Table table = obj.getTable();
            tableName = table.getName();
            tableName=tableName.toUpperCase();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return tableName;
    }
}
