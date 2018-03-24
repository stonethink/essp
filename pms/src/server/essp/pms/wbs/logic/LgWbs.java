package server.essp.pms.wbs.logic;

import server.framework.hibernate.HBComAccess;
import server.framework.common.BusinessException;
import c2s.dto.ITreeNode;
import c2s.dto.DtoUtil;
import db.essp.pms.Wbs;
import java.util.List;
import c2s.essp.pms.wbs.DtoWbs;
import db.essp.pms.Account;
import db.essp.pms.Activity;
import c2s.essp.pms.wbs.DtoActivity;
import server.essp.framework.logic.AbstractESSPLogic;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import db.essp.pms.WbsPK;
import com.wits.util.ThreadVariant;
import c2s.dto.IDto;
import c2s.essp.common.user.DtoUser;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import java.util.Vector;
import server.essp.pms.activity.logic.LgActivity;
import java.util.Date;
import com.wits.util.comDate;
import net.sf.hibernate.*;
import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import db.essp.pms.ActivityPK;
import c2s.essp.common.account.IDtoAccount;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import java.util.ArrayList;

/**
 * Error Message define:
 * E_WBS001 : User object is null!!!
 * E_WBS002 : account rid is error [ %1 ] !!!
 * E_WBS003 : There are more than one root on Wbs tree[accountRid=%1] !!!
 * E_WBS004 : Wbs is null
 * E_WBS005 : dataBean is null!!!
 * E_WBS006 : Account is null!!!
 * E_WBS007 : wbs rid is null!!!
 * E_WBS008 : activity rid is null!!!
 * E_WBS009 : PMS_WBS table not mapped to Wbs class
 * E_WBS010 : Root of WBS Tree not exist!!!
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class LgWbs extends AbstractESSPLogic {
    public final static int TYPE_START_DATE = 1;
    public final static int TYPE_FINISH_DATE = 2;
    private String userId;

    private IDtoAccount account = null;

    public LgWbs() {
        super();
        DtoUser user = getUser();
        if (user == null) {
            throw new BusinessException("E_WBS001", "User object is null!!!");
        }
        userId = user.getUserLoginId();
    }

    public LgWbs(HBComAccess hbAccess) {
        super(hbAccess);
    }

    /**
     * Construct a wbs tree by Account(or account id)
     * @param account Account
     * @return ITreeNode
     */
    public ITreeNode getWbsTree(Account account) {
        Long lAccountRid = account.getRid();
        return getWbsTree(lAccountRid);
    }

    public ITreeNode getWbsTree(String accountRid) {
        try {
            Long lAccountRid = new Long(accountRid);
            return getWbsTree(lAccountRid);
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                throw (BusinessException) e;
            } else {
                throw new BusinessException("E_WBS002",
                                            "account rid is error:[" +
                                            accountRid +
                                            "]!!!", e);
            }
        }
    }


    public ITreeNode getWbsTree(long accountRid) {
        Long lAccountRid = new Long(accountRid);
        return getWbsTree(lAccountRid);
    }

    public void setWbsRootRid(Long accountRid, Long rootRid) {
        LgAcntSeq.setWbsRootRid(accountRid, rootRid);
    }

    public Long getWbsRootRid(Long accountRid) {
        return LgAcntSeq.getWbsRootRid(accountRid);
    }

    public DtoWbsActivity getWbsRid(DtoWbsActivity dataBean) {
        Long rid = LgAcntSeq.getWbsSeq(dataBean.getAcntRid());
        dataBean.setWbsRid(rid);
        return dataBean;
    }

    public Long getAccountRid() {
        if (this.getAccount() != null) {
            return this.getAccount().getRid();
        } else {
            return new Long( -1);
        }

    }

    public String getAccountId() {
        if (this.getAccount() != null) {
            return this.getAccount().getId();
        } else {
            return "";
        }
    }

    public void setAccount(IDtoAccount account) {
        this.account = account;
    }

    public IDtoAccount getAccount() {
        if (this.account != null) {
            return this.account;
        } else {
            return super.getAccount();
        }
    }

    public String getAccountCode() {
        if (this.getAccount() != null) {
            return this.getAccount().getId();
        } else {
            return "";
        }
    }

    public String getAccountName() {
        if (this.getAccount() != null) {
            return this.getAccount().getName();
        } else {
            return "";
        }
    }

    /**
     * 根据Account信息产生第一笔WBS
     * @return DtoWbsActivity
     */
    protected DtoWbsActivity createFirstWbs() {
        DtoWbsActivity dataBean = new DtoWbsActivity();
        dataBean.setAcntRid(getAccountRid());
        dataBean.setWbs(true);
        dataBean.setName(getAccountName());
        dataBean.setCode(getAccountId());
        dataBean.setAutoCode(getAccountId());
        dataBean.setAcntCode(getAccountCode());
        dataBean.setAnticipatedStart(getAccount().getAnticipatedStart());
        dataBean.setAnticipatedFinish(getAccount().getAnticipatedFinish());
        dataBean.setActualStart(getAccount().getActualStart());
        dataBean.setActualFinish(getAccount().getActualFinish());
        dataBean.setPlannedStart(getAccount().getPlannedStart());
        dataBean.setPlannedFinish(getAccount().getPlannedFinish());
        dataBean.setBrief(getAccount().getBrief());
        dataBean.setHasActivity(false);
        dataBean.setStart(Boolean.FALSE);
        dataBean.setFinish(Boolean.FALSE);
        dataBean.setCompleteMethod(DtoWbsActivity.WBS_COMPLETE_BY_ACTIVITY);
        dataBean.setManager(getAccount().getManager());
        dataBean.setWeight(new Double(1));
        dataBean.setCompleteRate(new Double(0));
        //dataBean.setEctMethod();
        return dataBean;
    }

    public ITreeNode getWbsTree(Long accountRid) {
        IAccountUtil util = AccountFactory.create();
        IDtoAccount accountInstance = util.getAccountByRID(accountRid);
        this.setAccount(accountInstance);
        this.modifyWbsDate(accountRid);
        this.modifyActivityDate(accountRid);
        ITreeNode root = null;

        try {
            Long rootRid = getWbsRootRid(accountRid);
            if (rootRid == null) {
                /**
                 * 如果没有Root则依据Account资料新增一笔后再显示
                 */
                log.debug("ER1 : Root of WBS Tree not exist!!!");
                DtoWbsActivity dataBean = createFirstWbs();
                add(dataBean);
                setWbsRootRid(accountRid, dataBean.getWbsRid());
                if (this.userId != null && this.getAccount() != null &&
                    this.userId.equals(this.getAccount().getManager())) {
                    dataBean.setReadonly(false);
                } else {
                    dataBean.setReadonly(true);
                }
                return this.createTreeNode(dataBean);
            } else {
                WbsPK wbsPK = new WbsPK(accountRid, rootRid);
                Wbs wbs = null;
                try {
                    wbs = (Wbs)this.getDbAccessor().load(Wbs.class, wbsPK);
                    if (wbs == null || wbs.getPk() == null) {
                        /**
                         * 如果没有Root则依据Account资料新增一笔后再显示
                         */
                        log.debug("ER2 : Root of WBS Tree not exist!!!");
                        DtoWbsActivity dataBean = createFirstWbs();
                        add(dataBean);
                        setWbsRootRid(accountRid, dataBean.getWbsRid());
                        if (this.userId != null && this.getAccount() != null &&
                            this.userId.equals(this.getAccount().getManager())) {
                            dataBean.setReadonly(false);
                        } else {
                            dataBean.setReadonly(true);
                        }

                        return this.createTreeNode(dataBean);
                    }
                    root = createNode(wbs);
                } catch (net.sf.hibernate.ObjectNotFoundException e) {
                    /**
                     * 如果没有Root则依据Account资料新增一笔后再显示
                     */
                    log.debug("ER2 : Root of WBS Tree not exist!!!");
                    DtoWbsActivity dataBean = createFirstWbs();
                    add(dataBean);
                    setWbsRootRid(accountRid, dataBean.getWbsRid());
                    if (this.userId != null && this.getAccount() != null &&
                        this.userId.equals(this.getAccount().getManager())) {
                        dataBean.setReadonly(false);
                    } else {
                        dataBean.setReadonly(true);
                    }

                    return this.createTreeNode(dataBean);
                }
            }
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }
        return root;
    }

    public Wbs getWbs(Long accountRid) {
        Wbs wbs = null;
        try {
            Long rootRid = getWbsRootRid(accountRid);
            if (rootRid != null) {
                WbsPK wbsPK = new WbsPK(accountRid, rootRid);
                wbs = (Wbs)this.getDbAccessor().load(Wbs.class, wbsPK);
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }

        }
        return wbs;
    }

    //////////////////////////////// private method section////////////////////
    /**
     * create a root node on WBS Tree
     * @param wbs Wbs
     * @return ITreeNode
     */
    private ITreeNode createNode(Wbs wbs) {
        ITreeNode root = null;
        if (wbs == null) {
            throw new BusinessException("E_WBS004", "Wbs is null");
        }

        try {
            DtoWbs dtoBean = createDTO(wbs);
            if (this.userId != null && this.getAccount() != null &&
                this.userId.equals(this.getAccount().getManager())) {
                dtoBean.setReadonly(false);
            } else if (dtoBean.getManager() != null && this.userId != null &&
                       this.userId.equals(dtoBean.getManager())) {
                dtoBean.setReadonly(false);
            } else {
                dtoBean.setReadonly(true);
            }
            root = createTreeNode(dtoBean);

            //root.setActivityTree(true);
            List childs = wbs.getChilds();
            if (childs != null && childs.size() > 0) {
                boolean hasEmptyChild = false;
                for (int i = 0; i < childs.size(); i++) {
                    Wbs wbsChild = (Wbs) childs.get(i);
                    if (wbsChild != null) {
                        createWbsNode(root, wbsChild, dtoBean.isReadonly());
                    } else {
                        hasEmptyChild = true;
                    }
                }

                if (hasEmptyChild) {
                    for (int i = childs.size() - 1; i >= 0; i--) {
                        Wbs wbsChild = (Wbs) childs.get(i);
                        if (wbsChild == null) {
                            wbs.getChilds().remove(i);
                        }
                    }
                    this.getDbAccessor().update(wbs);
                }
            }
            createActivityNode(root, wbs, dtoBean.isReadonly());

            List activities = wbs.getActivities();
            if (activities != null && activities.size() > 0) {
                dtoBean.setHasActivity(true);
            } else {
                dtoBean.setHasActivity(false);
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return root;
    }

    protected ITreeNode createTreeNode(IDto dataBean) {
        ITreeNode node = new DtoWbsTreeNode(dataBean);
        return node;
    }

    /**
     * create other sub wbs node
     * @param parent ITreeNode
     * @param wbs Wbs
     */
    private void createWbsNode(ITreeNode parent, Wbs wbs, boolean readonly) {
        try {
            DtoWbs dtoBean = createDTO(wbs);

            if (this.userId != null &&
                this.getAccount() != null &&
                this.userId.equals(this.getAccount().getManager())) {
                dtoBean.setReadonly(false);
            } else if (dtoBean.getManager() != null &&
                       this.userId != null &&
                       this.userId.equals(dtoBean.getManager())) {
                dtoBean.setReadonly(false);
            } else {
                dtoBean.setReadonly(readonly);
            }

            ITreeNode node = createTreeNode(dtoBean);
            List childs = wbs.getChilds();
            if (childs != null && childs.size() > 0) {
                boolean hasEmptyChild = false;

                for (int i = 0; i < childs.size(); i++) {
                    Wbs wbsChild = (Wbs) childs.get(i);
                    if (wbsChild != null) {
                        createWbsNode(node, wbsChild, dtoBean.isReadonly());
                    } else {
                        hasEmptyChild = true;
                    }
                }

                if (hasEmptyChild) {
                    for (int i = childs.size() - 1; i >= 0; i--) {
                        Wbs wbsChild = (Wbs) childs.get(i);
                        if (wbsChild == null) {
                            wbs.getChilds().remove(i);
                        }
                    }
                    this.getDbAccessor().update(wbs);
                }
            }
            if (parent != null) {
                parent.addChild(node, IDto.OP_NOCHANGE);
            }
            createActivityNode(node, wbs, dtoBean.isReadonly());

            List activities = wbs.getActivities();
            if (activities != null && activities.size() > 0) {
                dtoBean.setHasActivity(true);
            } else {
                dtoBean.setHasActivity(false);
            }

        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * create activity leaf node
     * @param parent ITreeNode
     * @param wbs Wbs
     */
    private void createActivityNode(ITreeNode parent, Wbs wbs, boolean readonly) {
        try {
            List activities = wbs.getActivities();
            if (activities != null && activities.size() > 0) {
                boolean hasEmptyActivity = false;

                for (int i = 0; i < activities.size(); i++) {
                    Activity activity = (Activity) activities.get(i);
                    if (activity != null) {
                        DtoActivity dtoBean = createDTO(activity);
                        if (this.userId != null &&
                            this.getAccount() != null &&
                            this.userId.equals(this.getAccount().getManager())) {
                            dtoBean.setReadonly(false);
                        } else if (dtoBean.getManager() != null &&
                                   this.userId != null &&
                                   this.userId.equals(dtoBean.getManager())) {
                            dtoBean.setReadonly(false);
                        } else {
                            dtoBean.setReadonly(readonly);
                        }

                        ITreeNode node = createTreeNode(dtoBean);
                        parent.addChild(node, IDto.OP_NOCHANGE);
                    } else {
                        hasEmptyActivity = true;
                    }
                }

                if (hasEmptyActivity) {
                    for (int i = activities.size() - 1; i >= 0; i--) {
                        Activity activity = (Activity) activities.get(i);
                        if (activity == null) {
                            activities.remove(i);
                        }
                    }
                    try {
                        this.getDbAccessor().update(wbs);
                    } catch (Exception ex) {
                        throw new BusinessException(ex);
                    }
                }
            }
        } catch (BusinessException ex1) {
            throw ex1;
        }
    }

    /**
     * Translate a Wbs Hibernate object into a DtoWbs dto object
     * @param wbs Wbs
     * @return DtoWbs
     */
    protected DtoWbs createDTO(Wbs wbs) {
        DtoWbs dataBean = new DtoWbs();
        dataBean.setOp(DtoWbs.OP_NOCHANGE);
        try {
            DtoUtil.copyProperties(dataBean, wbs);
            dataBean.setAcntRid(wbs.getPk().getAcntRid());
            dataBean.setWbsRid(wbs.getPk().getWbsRid());
            dataBean.setAcntCode(getAccountCode());
            dataBean.setAcntName(getAccountName());

            if (wbs.getParent() != null) {
                dataBean.setParentRid(wbs.getParent().getPk().getWbsRid());
            }

//            if (wbs.getMilestoneData() != null) {
//                String isMilestone = wbs.getMilestoneData().getIsMilestone();
//                if (isMilestone != null && isMilestone.equals("1")) {
//                    dataBean.setMilestone(true);
//                }
//            }

//            try {
//                /**
//                 * 工期计算
//                 */
//                double durationPlan = LgWbsComplete.caculateDurationPlan(
//                    dataBean);
//                double durationActual = LgWbsComplete.caculateDurationActual(
//                    dataBean);
//                if (durationPlan < 0) {
//                    durationPlan = 0;
//                }
//                if (durationActual < 0) {
//                    durationActual = 0;
//                }
//                dataBean.setDurationPlan(new Long((long) durationPlan));
//                dataBean.setDurationActual(new Long((long) durationActual));
//                dataBean.setDurationRemain(new Long((long) (durationPlan -
//                    durationActual)));
//                double completeRate = durationActual * 100 / durationPlan;
//                if (durationPlan != 0) {
//                    dataBean.setDurationComplete(new Double(completeRate));
//                } else {
//                    dataBean.setDurationComplete(new Double(0));
//                }
//            } catch (Exception ex1) {
//            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return dataBean;
    }

    /**
     * Translate a Activity Hibernate object into a DtoActivity dto object
     * @param activity Activity
     * @return DtoActivity
     */
    protected DtoActivity createDTO(Activity activity) {
        DtoActivity dtoBean = new DtoActivity();
        dtoBean.setOp(DtoActivity.OP_NOCHANGE);
        try {
            DtoUtil.copyProperties(dtoBean, activity);
            dtoBean.setAcntRid(activity.getPk().getAcntRid());
            dtoBean.setActivityRid(activity.getPk().getActivityId());
            dtoBean.setAcntCode(getAccountCode());
            dtoBean.setAcntName(getAccountName());
            Wbs wbs = activity.getWbs();
            if (activity.getWbs() != null) {
                dtoBean.setWbsRid(wbs.getPk().getWbsRid());
            }
            dtoBean.setAutoCode(dtoBean.getCode());

            try {
                /**
                 * 工期计算
                 */
                double durationPlan = LgWbsComplete.caculateDurationPlan(
                    dtoBean);
                double durationActual = LgWbsComplete.caculateDurationActual(
                    dtoBean);
                if (durationPlan < 0) {
                    durationPlan = 0;
                }
                if (durationActual < 0) {
                    durationActual = 0;
                }
                dtoBean.setDurationPlan(new Long((long) durationPlan));
                dtoBean.setDurationActual(new Long((long) durationActual));
                dtoBean.setDurationRemain(new Long((long) (durationPlan -
                    durationActual)));
                if (durationPlan != 0) {
                    dtoBean.setDurationComplete(new Double(durationActual * 100 /
                        durationPlan));
                } else {
                    dtoBean.setDurationComplete(new Double(0));
                }
            } catch (Exception ex1) {
            }
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return dtoBean;
    }

    //新增一条WBS，并更新其父节点的关系
    public void add(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long parentRid = dataBean.getParentRid();
        Wbs wbs = new Wbs();
        try {
            DtoUtil.copyProperties(wbs, dataBean);
            Long rid = dataBean.getWbsRid();
            if (rid == null) { //modify by lipengxu use dataBean's rid
                rid = LgAcntSeq.getWbsSeq(acntRid);
                dataBean.setWbsRid(rid);
            }
            WbsPK pk = new WbsPK(acntRid, dataBean.getWbsRid());
            wbs.setPk(pk);
            if (parentRid != null) {
                WbsPK parentPk = new WbsPK(acntRid, parentRid);
                Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
                this.getDbAccessor().save(wbs);
                parent.getChilds().add(wbs);
                this.getDbAccessor().update(parent);

                modifyWbsDate(wbs);
            } else {
                /*
                 * set this Wbs as root
                 */
                setWbsRootRid(acntRid, rid);
                this.getDbAccessor().save(wbs);
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }

    public Long addData(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long parentRid = dataBean.getParentRid();
        Wbs wbs = new Wbs();
        try {
            DtoUtil.copyProperties(wbs, dataBean);
            Long rid = dataBean.getWbsRid();
            if (rid == null || dataBean.isInsert()) {
                rid = LgAcntSeq.getWbsSeq(acntRid);
                dataBean.setWbsRid(rid);
            }
            WbsPK pk = new WbsPK(acntRid, dataBean.getWbsRid());
            wbs.setPk(pk);
            if (parentRid != null) {
                WbsPK parentPk = new WbsPK(acntRid, parentRid);
                Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
                this.getDbAccessor().save(wbs);
                if (parent.getChilds() == null) {
                    List list = new ArrayList();
                    list.add(wbs);
                    parent.setChilds(list);
                } else {
                    parent.getChilds().add(wbs);
                }
                this.getDbAccessor().update(parent);

                modifyWbsDate(wbs);
            } else {
                /*
                 * set this Wbs as root
                 */
                setWbsRootRid(acntRid, rid);
                this.getDbAccessor().save(wbs);
            }
            return wbs.getPk().getWbsRid();
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }


    public void add(ITreeNode node) {
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long parentRid = dataBean.getParentRid();
        Wbs wbs = new Wbs();
        try {
            DtoUtil.copyProperties(wbs, dataBean);
            Long rid = dataBean.getWbsRid();
            if (rid == null || dataBean.isInsert()) {
                rid = LgAcntSeq.getWbsSeq(acntRid);
                dataBean.setWbsRid(rid);
                System.out.println("rid:" + rid + ",parentRid:" +
                                   dataBean.getParentRid());
                if (!node.isLeaf()) {
                    List list = node.children();
                    for (int i = 0; i < list.size(); i++) {
                        ITreeNode tempNode = (ITreeNode) list.get(i);
//                        node.deleteChild(tempNode);
                        DtoWbsActivity dtoTemp = (DtoWbsActivity) tempNode.
                                                 getDataBean();
                        dtoTemp.setParentRid(rid);
                        if (dtoTemp.isActivity()) {
                            //为了下面的Activity也能保存
                            dtoTemp.setWbsRid(rid);
                        }
                        tempNode.setDataBean(dtoTemp);
//                        node.addChild(tempNode);
                    }
                }
            }
            WbsPK pk = new WbsPK(acntRid, dataBean.getWbsRid());
            wbs.setPk(pk);
            if (parentRid != null) {
                WbsPK parentPk = new WbsPK(acntRid, parentRid);
                Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
                this.getDbAccessor().save(wbs);
                if (parent.getChilds() != null) {
                    parent.getChilds().add(wbs);
                } else {
                    List list = new ArrayList();
                    list.add(wbs);
                    parent.setChilds(list);
                }
                this.getDbAccessor().update(parent);
                modifyWbsDate(wbs);
            } else {
                /*
                 * set this Wbs as root
                 */
                setWbsRootRid(acntRid, rid);
                this.getDbAccessor().save(wbs);
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }

    public void add(ITreeNode node, boolean isAutoGetAllSeq) {
        if (!isAutoGetAllSeq) {
//            System.out.println("不是所有情况都自动取WBSID，只有在没有的情况下才会去取.");
        }
        DtoWbsActivity dataBean = (DtoWbsActivity) node.getDataBean();
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long parentRid = dataBean.getParentRid();
        Wbs wbs = new Wbs();
        try {
            DtoUtil.copyProperties(wbs, dataBean);
            Long rid = dataBean.getWbsRid();
            if (rid == null || dataBean.isInsert()) {
                if (rid == null) {
                    rid = LgAcntSeq.getWbsSeq(acntRid);
                    dataBean.setWbsRid(rid);
                }
                System.out.println("rid:" + rid + ",parentRid:" +
                                   dataBean.getParentRid());
                if (!node.isLeaf()) {
                    List list = node.children();
                    for (int i = 0; i < list.size(); i++) {
                        ITreeNode tempNode = (ITreeNode) list.get(i);
//                        node.deleteChild(tempNode);
                        DtoWbsActivity dtoTemp = (DtoWbsActivity) tempNode.
                                                 getDataBean();
                        dtoTemp.setParentRid(rid);
                        if (dtoTemp.isActivity()) {
                            //为了下面的Activity也能保存
                            dtoTemp.setWbsRid(rid);
                        }
                        tempNode.setDataBean(dtoTemp);
//                        node.addChild(tempNode);
                    }
                }
            }
            WbsPK pk = new WbsPK(acntRid, dataBean.getWbsRid());
            wbs.setPk(pk);
            if (parentRid != null) {
                WbsPK parentPk = new WbsPK(acntRid, parentRid);
                Wbs parent = (Wbs)this.getDbAccessor().load(Wbs.class, parentPk);
                this.getDbAccessor().save(wbs);
                if (parent.getChilds() != null) {
                    parent.getChilds().add(wbs);
                } else {
                    List list = new ArrayList();
                    list.add(wbs);
                    parent.setChilds(list);
                }
                this.getDbAccessor().update(parent);
                modifyWbsDate(wbs);
            } else {
                /*
                 * set this Wbs as root
                 */
                setWbsRootRid(acntRid, rid);
                this.getDbAccessor().save(wbs);
            }

        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }

    }


    public void update(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        Long acntRid = dataBean.getAcntRid();
        Long wbsRid = dataBean.getWbsRid();
        if (acntRid == null) {
            throw new BusinessException("E_WBS006", "account rid is null!!!");
        }
        if (wbsRid == null) {
            throw new BusinessException("E_WBS006", "wbs rid is null!!!");
        }

        WbsPK pk = new WbsPK();
        pk.setAcntRid(acntRid);
        pk.setWbsRid(wbsRid);
        try {
            Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
            DtoUtil.copyProperties(wbs, dataBean);
            this.getDbAccessor().update(wbs);
            this.modifyWbsDate(wbs);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }
    }

    public void delete(Wbs wbs) {
        try {
            Wbs parent = wbs.getParent();
            if (parent != null) {
                parent.getChilds().remove(wbs);
            }

            /**
             * 删除PMS_PBS_ASSIGNMENT
             */

            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS_ASSIGNMENT where acnt_rid=" +
                wbs.getPk().getAcntRid().longValue() + " and JOIN_TYPE=" +
                DtoPbsAssign.JOINTYPEWBS.longValue() + " and JOIN_RID=" +
                wbs.getPk().getWbsRid().longValue());

            /**
             * 删除CheckPoint
             */

            this.getDbAccessor().executeUpdate(
                "delete from PMS_WBS_CHECKPOINT where acnt_rid=" +
                wbs.getPk().getAcntRid().longValue() + " and wbs_rid=" +
                wbs.getPk().getWbsRid().longValue());

            /**
             *
             */

            /**
             * 删除相关内容：Activity & childWbs
             */
            List activities = wbs.getActivities();
            if (activities != null) {
                for (int i = activities.size() - 1; i >= 0; i--) {
                    Activity activity = (Activity) activities.get(i);
                    delete(activity);
                }
            }

            List childs = wbs.getChilds();
            if (childs != null) {
                for (int i = childs.size() - 1; i >= 0; i--) {
                    Wbs child = (Wbs) childs.get(i);
                    if (child != null) {
                        delete(child);
                    }
                }
            }
            this.getDbAccessor().delete(wbs);
            this.getDbAccessor().update(parent);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }
    }

    public void delete(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            /**
             * 删除PMS_PBS_ASSIGNMENT
             */
            this.getDbAccessor().executeUpdate(
                "delete from PMS_PBS_ASSIGNMENT where acnt_rid=" +
                activity.getPk().getAcntRid().longValue() + " and JOIN_TYPE=" +
                DtoPbsAssign.JOINTYPEACT.longValue() + " and JOIN_RID=" +
                activity.getPk().getActivityId().longValue());

            /**
             * 删除PMS_ACTIVITY_RELATION(包括前置和后置)
             */
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_RELATION where acnt_rid=" +
                activity.getPk().getAcntRid().longValue() +
                " and (ACTIVITY_ID=" +
                activity.getPk().getActivityId().longValue() +
                " or POST_ACTIVITY_ID=" +
                activity.getPk().getActivityId().longValue() + ")");

            /**
             * 删除PMS_ACTIVITY_WORKERS
             */
            this.getDbAccessor().executeUpdate(
                "delete from PMS_ACTIVITY_WORKERS where acnt_rid=" +
                activity.getPk().getAcntRid().longValue() +
                " and ACTIVITY_RID=" +
                activity.getPk().getActivityId().longValue());

            Wbs wbs = activity.getWbs();
            wbs.getActivities().remove(activity);
            this.getDbAccessor().update(wbs);
            this.getDbAccessor().delete(activity);
        } catch (Exception ex) {
            if (ex instanceof BusinessException) {
                throw (BusinessException) ex;
            } else {
                throw new BusinessException(ex);
            }
        }
    }

    public void delete(DtoWbsActivity dataBean) {
        if (dataBean == null) {
            throw new BusinessException("E_WBS005", "dataBean is null!!!");
        }

        if (dataBean.isWbs()) {
            Long acntRid = dataBean.getAcntRid();
            Long wbsRid = dataBean.getWbsRid();
            if (acntRid == null) {
                throw new BusinessException("E_WBS006",
                                            "account rid is null!!!");
            }
            if (wbsRid == null) {
                throw new BusinessException("E_WBS007", "wbs rid is null!!!");
            }
            WbsPK pk = new WbsPK();
            pk.setAcntRid(acntRid);
            pk.setWbsRid(wbsRid);
            try {
                Wbs wbs = (Wbs)this.getDbAccessor().load(Wbs.class, pk);
                delete(wbs);
            } catch (Exception ex) {
                if (ex instanceof BusinessException) {
                    throw (BusinessException) ex;
                } else {
                    throw new BusinessException(ex);
                }
            }
        } else if (dataBean.isActivity()) {
            Long acntRid = dataBean.getAcntRid();
            Long wbsRid = dataBean.getWbsRid();
            Long activityRid = dataBean.getActivityRid();
            if (acntRid == null) {
                throw new BusinessException("E_WBS006",
                                            "account rid is null!!!");
            }
            if (wbsRid == null) {
                throw new BusinessException("E_WBS007", "wbs rid is null!!!");
            }
            if (activityRid == null) {
                throw new BusinessException("E_WBS008",
                                            "activity rid is null!!!");
            }

            ActivityPK activityPK = new ActivityPK();
            activityPK.setAcntRid(acntRid);
            activityPK.setActivityId(dataBean.getActivityRid());
            try {
                Activity activity = (Activity)this.getDbAccessor().load(
                    Activity.class,
                    activityPK);
                delete(activity);
            } catch (Exception ex) {
                if (ex instanceof BusinessException) {
                    throw (BusinessException) ex;
                } else {
                    throw new BusinessException(ex);
                }
            }
        }
    }

    public List getManagers(Long acntRid) {
        LgAccountLaborRes accountLaborRes = new LgAccountLaborRes();
        Vector vec = accountLaborRes.listComboLaborRes(acntRid);
        return vec;
    }

    private void saveOrUpdateWbs(DtoWbsActivity dataBean) {
        if (dataBean.isInsert()) {
            this.add(dataBean);
        } else {
            this.update(dataBean);
        }
    }

    private void saveOrUpdateActivity(DtoWbsActivity dataBean) {
        LgActivity activityLogic = new LgActivity();
        if (dataBean.isInsert()) {
            activityLogic.add(dataBean);
        } else {
            activityLogic.update(dataBean);
        }

    }

    private void saveOrUpdate(DtoWbsActivity dto) {
        if (dto == null) {
            return;
        }

        if (dto.isWbs()) {
            saveOrUpdateWbs(dto);
        } else {
            /**
             * 工期计算
             */
            double durationPlan = LgWbsComplete.caculateDurationPlan(dto);
            double durationActual = LgWbsComplete.caculateDurationActual(dto);
            dto.setDurationPlan(new Long((long) durationPlan));
            dto.setDurationActual(new Long((long) durationActual));
            dto.setDurationRemain(new Long((long) (durationPlan -
                durationActual)));
            if (durationPlan == 0) {
                dto.setDurationComplete(new Double(0));
            } else {
                dto.setDurationComplete(new Double(durationActual * 100 /
                    durationPlan));
            }
            saveOrUpdateActivity(dto);
        }
    }

    public void updateList(List nodeList) {
        if (nodeList == null || nodeList.size() == 0) {
            return;
        }

        for (int i = 0; i < nodeList.size(); i++) {
            ITreeNode node = (ITreeNode) nodeList.get(i);
            DtoWbsActivity dto = (DtoWbsActivity) node.getDataBean();
            saveOrUpdate(dto);
        }
    }

    public void saveWbsTree(ITreeNode wbsTree) {
        if (wbsTree != null) {
            DtoWbsActivity dto = (DtoWbsActivity) wbsTree.getDataBean();
            saveOrUpdate(dto);
            List childs = wbsTree.children();
            if (childs != null && childs.size() > 0) {
                for (int i = 0; i < childs.size(); i++) {
                    ITreeNode child = (ITreeNode) childs.get(i);
                    saveWbsTree(child);
                }
            }
        }
    }

    public void modifyWbsDate(Long accountRid) {
        if (accountRid == null) {
            return;
        }
        try {
            Query q = this.getDbAccessor().getSession().createQuery(
                "from Wbs w where w.childs.size=0 and w.pk.acntRid=" +
                accountRid.longValue());
            List list = q.list();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Wbs wbs = (Wbs) list.get(i);
                    this.modifyWbsDate(wbs);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void modifyActivityDate(Long accountRid) {
        if (accountRid == null) {
            return;
        }
        try {
            Query q = this.getDbAccessor().getSession().createQuery(
                "from Activity w where w.pk.acntRid=" + accountRid.longValue());
            List list = q.list();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Activity activity = (Activity) list.get(i);
                    this.modifyActivityDate(activity);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void modifyWbsDate(Wbs wbs) {
        upModifyDate(wbs, "plannedStart", TYPE_START_DATE);
        upModifyDate(wbs, "plannedFinish", TYPE_FINISH_DATE);
    }

    public void modifyActivityDate(Activity activity) {
        upModifyDate(activity, "plannedStart", TYPE_START_DATE);
        upModifyDate(activity, "plannedFinish", TYPE_FINISH_DATE);
    }

    private void upModifyDate(Object node, String fieldName, int DATE_TYPE) {
        Wbs parent = null;
        if (node instanceof Wbs) {
            parent = ((Wbs) node).getParent();
        } else if (node instanceof Activity) {
            parent = ((Activity) node).getWbs();
        }
        if (node == null || parent == null) {
            return;
        }

        Date currentValue = null;
        Date dateValue = null;

        List allChilds = new java.util.ArrayList();
        allChilds.addAll(parent.getChilds());
        allChilds.addAll(parent.getActivities());
//        System.out.println("*************************************************");
//        if (DATE_TYPE == TYPE_START_DATE) {
//            System.out.println("从以下日期中取出最小日期:");
//        } else {
//            System.out.println("从以下日期中取出最大日期:");
//        }

        for (int i = 0; i < allChilds.size(); i++) {
            try {
                Object child = allChilds.get(i);
                dateValue = (Date) DtoUtil.getProperty(child, fieldName);
//                if (dateValue != null) {
//                    System.out.println("[" + comDate.dateToString(dateValue) +
//                                       "]");
//                } else {
//                    System.out.println("[]");
//                }
                if (currentValue == null) {
                    currentValue = dateValue;
                } else if (dateValue != null) {
                    if (DATE_TYPE == TYPE_START_DATE) {
                        if (!currentValue.before(dateValue)) {
                            currentValue = dateValue;
                        }
                    } else if (DATE_TYPE == TYPE_FINISH_DATE) {
                        if (!currentValue.after(dateValue)) {
                            currentValue = dateValue;
                        }
                    }
                }
            } catch (Exception ex) {
            }
        }

//        if (DATE_TYPE == TYPE_START_DATE) {
//            System.out.println("取得最小日期:" + comDate.dateToString(currentValue));
//        } else {
//            System.out.println("取得最大日期:" + comDate.dateToString(currentValue));
//        }

        try {
            DtoUtil.setProperty(parent, fieldName, currentValue);
            this.getDbAccessor().update(parent);
        } catch (Exception ex1) {
        }

        upModifyDate(parent, fieldName, DATE_TYPE);
    }

    /**
     * 保存WbsActivity的数据
     * @param root ITreeNode
     */
    public void saveBLPlan(ITreeNode root, Long acntrid) {
        DtoWbsActivity dataBean = (DtoWbsActivity) root.getDataBean();
        if (dataBean == null) {
            return;
        }
        dataBean.setOp(IDto.OP_INSERT);
        if (dataBean.isInsert()) {

            dataBean.setAcntRid(acntrid);
            dataBean.setManager("");
            dataBean.setReadonly(false);
            dataBean.setActualFinish(null);
            dataBean.setActualStart(null);
            dataBean.setAnticipatedFinish(null);
            dataBean.setAnticipatedStart(null);
            root.setDataBean(dataBean);
            if (dataBean.isWbs()) {
                LgWbs lgWbs = new LgWbs();
                if (dataBean.isInsert()) {
                    lgWbs.add(root);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
            }
            if (dataBean.isActivity()) {
                LgActivity lgActivity = new LgActivity();
                if (dataBean.isInsert()) {
                    lgActivity.add(dataBean);
                    dataBean.setOp(IDto.OP_NOCHANGE);
                }
            }

        } //插入或更新操作结束

        if (!root.isLeaf()) {
            List childs = root.children();
            for (int i = 0; i < childs.size(); i++) {
                saveBLPlan((ITreeNode) childs.get(i), acntrid);
            }
        } else {
            return;
        }

    }
}
