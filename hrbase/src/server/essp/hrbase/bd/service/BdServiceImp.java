/*
 * Created on 2007-12-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.bd.service;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoUtil;
import db.essp.hrbase.HrbBd;
import server.essp.hrbase.bd.dao.IBdDao;
import server.essp.hrbase.bd.form.AfBd;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;
/**
 * BD維護 
 * @author TBH
 */
public class BdServiceImp implements IBdService{

        private IBdDao hrbBdDao;
        /**
         * 列出所有的BD資料
         * @return List
         */
        public List listAllBdCode() {
            List bdList = hrbBdDao.queryBdCode();
            return bdList;
        }
        
        /**
         * 列出有效的BD集合
         * @return List
         */
        public List listEnabledBdCode() {
            List enabledBdList = hrbBdDao.queryEnabledBdCode();
            SelectOptionImpl bdOption = new SelectOptionImpl("--Please select--","");
            List bdList = new ArrayList();
            bdList.add(bdOption);
            if(enabledBdList != null && enabledBdList.size() > 0){
                for(int i=0;i<enabledBdList.size();i++){
                    HrbBd bd = (HrbBd)enabledBdList.get(i);
                    bdOption = new SelectOptionImpl(bd.getBdCode(),bd.getBdCode());
                    bdList.add(bdOption);
                }
            }
            return bdList;
        }
    
    
        /**
         * 根據BdCode得到詳細信息
         * @param bdCode
         * @return AfBd
         */
        public AfBd getDetailInfo(String bdCode) {
            HrbBd bd = hrbBdDao.queryByBdCode(bdCode);
            AfBd afBd = new AfBd();
            DtoUtil.copyBeanToBean(afBd,bd);
            if(bd.getStatus()){
                afBd.setStatus("true");
            }else{
                afBd.setStatus("false");
            }
            if(bd.getAchieveBelong()){
                afBd.setAchieveBelong("true");
            }else{
                afBd.setAchieveBelong("false");
            }
            return afBd;
        }
    
        /**
         * 新增
         * @param afBd
         */
        public void add(AfBd afBd) {
            HrbBd bdCode = hrbBdDao.queryByBdCode(afBd.getBdCode());
            if(bdCode == null){
                HrbBd bd = new HrbBd();
                if(afBd.getBdCode() != null && !afBd.getBdCode().trim().equals("")){
                    bd.setBdCode(afBd.getBdCode().trim());
                }
                if(afBd.getBdName() != null && !afBd.getBdName().trim().equals("")){
                    bd.setBdName(afBd.getBdName().trim());
                }
                if(afBd.getDescription() != null && !afBd.getDescription().trim().equals("")){
                    bd.setDescription(afBd.getDescription().trim());
                }
                if(afBd.getAchieveBelong() != null && afBd.getAchieveBelong().equals("true")){
                    bd.setAchieveBelong(true);
                }else{
                    bd.setAchieveBelong(false);
                }
                if(afBd.getStatus() != null && afBd.getStatus().equals("true")){
                    bd.setStatus(true);
                }else{
                    bd.setStatus(false);
                }
                bd.setSequence(hrbBdDao.getMaxSeq()+1);
                try{
                 hrbBdDao.save(bd);
                }catch(BusinessException e) {
                 String message = "BD Code is exist!";
                 throw new BusinessException(e.getErrorCode(), message);
                }
            }
        }
    
        /**
         * 更新
         * @param afBd
         */
        public void update(AfBd afBd) {
             HrbBd bdCode = hrbBdDao.queryByBdCode(afBd.getBdCode());
             if(bdCode != null){
              if(afBd.getBdName() != null){
                bdCode.setBdName(afBd.getBdName());
              }
              if(afBd.getDescription() != null){
                bdCode.setDescription(afBd.getDescription());
              }
              if(afBd.getStatus()!= null && afBd.getStatus().equals("true")){
                bdCode.setStatus(true);
              }else{
                bdCode.setStatus(false);
              }
              if(afBd.getAchieveBelong()!= null && afBd.getAchieveBelong().equals("true")){
                  bdCode.setAchieveBelong(true);
              }else{
                  bdCode.setAchieveBelong(false);
              }
              try{
                hrbBdDao.update(bdCode);
              }catch(BusinessException e){
                String message = "BD Code is not exist!";
                throw new BusinessException(e.getErrorCode(), message);
             }
            }
        }
    
        /**
         * 刪除
         * @param bdCode
         */
        public void delete(String bdCode) {
            HrbBd bd = hrbBdDao.queryByBdCode(bdCode);
            if(bd != null){
                try{
                hrbBdDao.delete(bd);
            }catch(BusinessException e){
                String message = "BD Code is not exist!";
                throw new BusinessException(e.getErrorCode(), message);
             }
            }
            
        }
        
        public void setHrbBdDao(IBdDao hrbBdDao) {
            this.hrbBdDao = hrbBdDao;
        }

        public List listAchieveBelongUnit() {
            List achieveBdList = hrbBdDao.queryAchieveBelongUnit();
            SelectOptionImpl bdOption = new SelectOptionImpl("--Please select--","");
            List bdList = new ArrayList();
            bdList.add(bdOption);
            if(achieveBdList != null && achieveBdList.size() > 0){
                for(int i=0;i<achieveBdList.size();i++){
                    HrbBd bd = (HrbBd)achieveBdList.get(i);
                    bdOption = new SelectOptionImpl(bd.getBdCode(),bd.getBdCode());
                    bdList.add(bdOption);
                }
            }
            return bdList;
        }

}
