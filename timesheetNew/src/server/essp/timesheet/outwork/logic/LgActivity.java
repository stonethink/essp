package server.essp.timesheet.outwork.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.essp.timesheet.workscope.DtoActivity;

import com.primavera.ServerException;
import com.primavera.integration.client.bo.BOIterator;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.bo.object.Resource;
import com.primavera.integration.client.bo.object.ResourceAssignment;
import com.primavera.integration.client.bo.object.User;
import com.primavera.integration.network.NetworkException;

import server.essp.common.primavera.PrimaveraApiBase;
import server.framework.common.BusinessException;

public class LgActivity extends PrimaveraApiBase {
	
	public List listActivityByAcntId(String acntId) {
		List dtoList = new ArrayList();
		try {
			Project project = null;
			BOIterator boi = this.getGOM().loadProjects(Project.getAllFields(), "Id = '" + acntId + "'", "");
			if(boi.hasNext()) {
				project = (Project) boi.next();
			} else {
				return new ArrayList();
			}
			BOIterator boiActivities = project.loadAllActivities(
			        new String[] {"ObjectId", "Id", "Name"}, "", "Id");
			while(boiActivities.hasNext()) {
	            dtoList.add(p3Activity2DtoActivity((Activity)boiActivities.next()));
	        }
		} catch (Exception e) {
			log.error(e);
			throw new BusinessException("logic.LgActivity.listActivity", 
					"list activity error", e);
		} finally {
			this.logout();
		}
//		转换Bean
        
        
        return dtoList;
    }


    /**
     * 将P3 Activity对象转换成DtoActivity
     * @param activity Activity
     * @return DtoActivity
     */
    public DtoActivity p3Activity2DtoActivity(Activity activity) {
        DtoActivity dto = new DtoActivity();
        try {
        	dto.setId(activity.getObjectId().toInteger());
            dto.setCode(activity.getId());
            //将StartDate和EndDate转换成Date类型
            dto.setName(activity.getName());
        } catch (BusinessObjectException ex) {
            log.error(ex);
        }
        return dto;
    }

}
