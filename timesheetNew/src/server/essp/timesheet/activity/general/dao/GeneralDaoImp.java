package server.essp.timesheet.activity.general.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import c2s.dto.DtoUtil;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Resource;
import c2s.essp.timesheet.activity.DtoActivityGeneral;
import java.util.Map;
import java.util.HashMap;
import server.framework.common.BusinessException;
import com.primavera.common.value.EndDate;
import com.primavera.common.value.BeginDate;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.network.NetworkException;
import com.primavera.ServerException;
import com.wits.util.ThreadVariant;
import c2s.essp.common.user.DtoUser;
import com.primavera.common.value.ObjectId;
import server.essp.timesheet.activity.general.dao.IGeneralDao;
import java.util.Date;
import com.primavera.integration.client.bo.enm.ActivityStatus;

/**
 * <p>Description: General方法的实现</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class GeneralDaoImp extends PrimaveraApiBase implements
        IGeneralDao {
    private final static Map<String, String> activityPropertiesMap;
    /**
     * 通过rid查询符合条件的General
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral queryGeneral(Long rid) throws Exception {
        DtoActivityGeneral dtoGeneral = new DtoActivityGeneral();
        Activity activity = (Activity) getActivity(rid);
        DtoUtil.copyBeanToBeanWithPropertyMap(dtoGeneral, activity,
                                              activityPropertiesMap);
        if (activity.getStartDate() != null) {
            dtoGeneral.setStartDate(new Date(activity.getStartDate().
                                             getTime()));
        }
        if (activity.getFinishDate() != null) {
            dtoGeneral.setFinishDate(new Date(activity.getFinishDate().
                                              getTime()));
        }
        if (activity.getExpectedFinishDate() != null) {
            dtoGeneral.setExpectedFinishDate(new Date(activity.
                    getExpectedFinishDate().getTime()));
        }
        if (activity.getSuspendDate() != null) {
            dtoGeneral.setSuspendDate(new Date(activity.getSuspendDate().
                                               getTime()));
        }
        if (activity.getResumeDate() != null) {
            dtoGeneral.setResumeDate(new Date(activity.getResumeDate().
                                              getTime()));
        }

        //转换作业状态
        if (activity.getStatus().equals(ActivityStatus.COMPLETED)) {
            dtoGeneral.setStarted(true);
            dtoGeneral.setFinished(true);
        } else if (activity.getStatus().equals(ActivityStatus.IN_PROGRESS)) {
            dtoGeneral.setStarted(true);
            dtoGeneral.setFinished(false);
        } else {
            dtoGeneral.setStarted(false);
            dtoGeneral.setFinished(false);
        }
        return dtoGeneral;
    }

    /**
     * 更新
     * @param dtoGeneral DtoActivityGeneral
     * @throws Exception
     */
    public void update(DtoActivityGeneral dtoGeneral) throws Exception {
        try {

            Activity dbBean = getActivity(dtoGeneral.getRid());
            if (dtoGeneral.getExpectedFinishDate() != null) {
                dbBean.setExpectedFinishDate(EndDate.createEndDate(dtoGeneral.
                        getExpectedFinishDate()));
            }
            //转换作业状态
            if (dtoGeneral.isStarted() && !dtoGeneral.isFinished()) {
                dbBean.setStatus(ActivityStatus.IN_PROGRESS);
                dbBean.setActualStartDate(BeginDate.createBeginDate(dtoGeneral.
                        getStartDate()));
                dbBean.setActualFinishDate(null);
            } else if (dtoGeneral.isStarted() && dtoGeneral.isFinished()) {
                dbBean.setStatus(ActivityStatus.COMPLETED);
                dbBean.setActualStartDate(BeginDate.createBeginDate(dtoGeneral.
                        getStartDate()));
                dbBean.setActualFinishDate(EndDate.createEndDate(dtoGeneral.
                        getFinishDate()));
            } else {
                dbBean.setStatus(ActivityStatus.NOT_STARTED);
                dbBean.setActualStartDate(null);
                dbBean.setActualFinishDate(null);
            }
            dbBean.setNotesToResources(dtoGeneral.getNoteTo());
            dbBean.update();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("error.logic.GeneralDaoImp.updateGeneralErr",
                                        "update general error.",ex);
        }
    }

    /**
     * 根据RID获得对应的Activity
     * @param rid Long
     * @return Activity
     * @throws BusinessObjectException
     * @throws NetworkException
     * @throws ServerException
     */
    public Activity getActivity(Long rid) throws BusinessObjectException,
            NetworkException, ServerException {
        Activity activity = Activity.load(getSession(),
                                          new String[] {"ObjectId", "Id",
                                          "Name", "StartDate",
                                          "PlannedDuration", "FinishDate",
                                          "ExpectedFinishDate",
                                          "SuspendDate", "ResumeDate",
                                          "WBSName", "Feedback", "Status", 
                                          "PrimaryResourceObjectId"},
                                          new ObjectId(rid));
        return activity;
    }
    
    /**
     * 获取指定用户是否是指定作业的主资源
     * @param activityId
     * @param loginId
     * @return boolean
     */
    public boolean isPrimaryResource(Long activityId, String loginId) {
    	Resource resource = this.getResource(loginId);
    	Activity activity = null;
		try {
			activity = getActivity(activityId);
		} catch (Exception e) {
			log.error(e);
		}
    	if(resource == null || activity == null) {
    		return false;
    	} else {
    		try {
				return activity.getPrimaryResourceObjectId().equals(resource.getObjectId());
			} catch (Exception e) {
				log.error(e);
				return false;
			}
    	}
    }

    /**
     * 通过RID从General中得到相应的NOTE字段
     * @param rid Long
     * @return DtoActivityGeneral
     * @throws Exception
     */
    public DtoActivityGeneral getNoteFromGneral(Long rid) throws Exception {
        DtoActivityGeneral dtoGeneral = new DtoActivityGeneral();
        Activity activity = getActivityNotesFeedback(rid);
        DtoUtil.copyBeanToBeanWithPropertyMap(dtoGeneral,
                                              activity, activityPropertiesMap);
        return dtoGeneral;
    }

    /**
     * 保存Feedback
     * @param rid Long
     * @param feedback String
     * @throws Exception
     */
    public void saveFeeedback(Long rid, String feedback) throws Exception {
        Activity activity = getActivityNotesFeedback(rid);
        activity.setFeedback(feedback);
        activity.update();
    }

    /**
     * 根据rid获取activity
     * @param rid Long
     * @return Activity
     * @throws Exception
     */
    private Activity getActivityNotesFeedback(Long rid) throws Exception {
        return Activity.load(getSession(), new String[] {"Feedback",
                             "NotesToResources"}, new ObjectId(rid));
    }


    public static void main(String[] args) {
        DtoUser user = new DtoUser();
        user.setUserLoginId("WH0607014");
        user.setPassword("");
        ThreadVariant.getInstance().put(user.SESSION_USER, user);
        GeneralDaoImp imp = new GeneralDaoImp();
        try {
            Activity act = Activity.load(imp.getSession(),
                                         new String[] {"Feedback",
                                         "NotesToResources"},
                                         new ObjectId(Long.valueOf(33239)));

            act.setNotesToResources("this is only a test for notes to resource");
            System.out.println("set notes over");
            act.setFeedback("this is only a test for feedback");
            System.out.println("set feedback over");
            act.update();
            System.out.println(act.getObjectId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        imp.logout();
    }


    static {
        activityPropertiesMap = new HashMap();
        activityPropertiesMap.put("objectId", "rid");
        activityPropertiesMap.put("id", "id");
        activityPropertiesMap.put("name", "name");
        activityPropertiesMap.put("startDate", "startDate");
        activityPropertiesMap.put("plannedDuration", "plannedDuration");
        activityPropertiesMap.put("finishDate", "finishDate");

        activityPropertiesMap.put("expectedFinishDate", "expectedFinishDate");
        activityPropertiesMap.put("suspendDate", "suspendDate");
        activityPropertiesMap.put("resumeDate", "resumeDate");
        activityPropertiesMap.put("wBSName", "wbsName");
        activityPropertiesMap.put("feedback", "feedBack");
        activityPropertiesMap.put("notesToResources", "noteTo");
    }


}
