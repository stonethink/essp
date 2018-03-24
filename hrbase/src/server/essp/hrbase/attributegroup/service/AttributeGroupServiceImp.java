/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.service;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoUtil;
import db.essp.hrbase.HrbAttribute;
import db.essp.hrbase.HrbAttributeGroup;
import db.essp.hrbase.HrbSite;
import server.essp.hrbase.attribute.dao.IAttributeDao;
import server.essp.hrbase.attribute.service.IAttributeService;
import server.essp.hrbase.attributegroup.dao.IAttributeGroupDao;
import server.essp.hrbase.attributegroup.form.AfAttributeGroup;
import server.essp.hrbase.site.dao.ISiteDao;
import server.framework.taglib.util.SelectOptionImpl;

public class AttributeGroupServiceImp implements IAttributeGroupService{
    
    private IAttributeGroupDao attributeGroupDao;
    
    private IAttributeDao attributeDao;
    
    private IAttributeService attributeService;
    
    private ISiteDao siteDao;
    
    public List listAllAttributeGroup() {
        return beanList2AfList(attributeGroupDao.listAllAttGroup());
    }

    public List listEnableAttributeGroup() {
        return beanList2AfList(attributeGroupDao.listEnableAttGroup());
    }

    public AfAttributeGroup loadAttributeGroup(Long rid) {
        HrbAttributeGroup attGroup = null;
        HrbAttribute hebAtt = null;
        AfAttributeGroup af = new AfAttributeGroup();
        try{
       if(rid != null){
         attGroup = attributeGroupDao.loadAttGroup(rid);
         hebAtt = attributeDao.loadHrbAtt(attGroup.getAttributeRid());
         af = bean2Af(attGroup);    
         if(attGroup.getIsFormal()!= null && attGroup.getIsFormal().equals("0")){
             af.setIsFormal("0"); 
         }else{
             af.setIsFormal("1");
         }
         if(hebAtt != null){
             af.setHrbAttCode(hebAtt.getCode());
         }
        }else{
            af.setIsEnable("1");
            af.setIsFormal("1");
        }
        List hrbAttList = attributeService.listEnabledHrbAttOption();
        af.setHrbAttList(hrbAttList);
        List siteList = listEnabledSiteOption();
        af.setSiteList(siteList);     
        }catch(Exception e){
            e.printStackTrace();
        }
        return af;
    }

    public void deleteAttributeGroup(Long rid) {
        attributeGroupDao.deleteAttGroup(rid);
    }

    public List listEnabledAttGroupOption() {
        List attList = attributeGroupDao.listEnableAttGroup();
        SelectOptionImpl attOption = new SelectOptionImpl("--Please select--","");
        List sList = new ArrayList();
        sList.add(attOption);
        if(attList != null && attList.size() > 0){
            for(int i=0;i<attList.size();i++){
                HrbAttributeGroup attGroup = (HrbAttributeGroup)attList.get(i);
                attOption = new SelectOptionImpl(attGroup.getCode(),attGroup.getCode());
                sList.add(attOption);
            }
        }
        return sList;
    }

    private static AfAttributeGroup bean2Af(HrbAttributeGroup bean) {
        AfAttributeGroup af = new AfAttributeGroup();
        if(bean != null){
          DtoUtil.copyBeanToBean(af, bean);
        }
        return af;
    }

    private List beanList2AfList(List beanList) {
            List afList = new ArrayList();
            AfAttributeGroup af = new AfAttributeGroup();
            if(beanList == null) return null;
            for(int i=0;i<beanList.size();i++){
                HrbAttributeGroup attGroup = (HrbAttributeGroup)beanList.get(i);
                af = new AfAttributeGroup();
                DtoUtil.copyBeanToBean(af,attGroup);
                if(attGroup.getIsFormal() != null && attGroup.getIsFormal().equals("1")){
                    af.setIsFormal("1");
                }else{
                    af.setIsFormal("0");
                }
                String hrbAttCode = getHrbAttCode(attGroup.getAttributeRid());
                if(hrbAttCode == null){
                    af.setHrbAttCode("");
                }else{
                    af.setHrbAttCode(hrbAttCode);
                }
                afList.add(af);
            }
            return afList;
        }
    
    private String getHrbAttCode(Long rid){
        HrbAttribute hrbAtt = attributeDao.loadHrbAtt(rid);
        if(hrbAtt != null){
            return hrbAtt.getCode();
        }
        return null;
    }

    private static HrbAttributeGroup af2Bean(AfAttributeGroup af) {
        HrbAttributeGroup bean = new HrbAttributeGroup();
        DtoUtil.copyBeanToBean(bean, af);
        if(af.getRid() == null || "".equals(af.getRid())) {
            bean.setRid(null);
        }
        if(af.getAttributeRid() == null || "".equals(af.getAttributeRid())){
            bean.setAttributeRid(null);
        }
        if(af.getIsFormal() == null || "".equals(af.getIsFormal())){
            bean.setIsFormal("0");
        }
        if(af.getIsEnable() == null || "".equals(af.getIsEnable())) {
            bean.setIsEnable("0");
        }
        return bean;
    }
   
    public void saveAttGroup(AfAttributeGroup af) {
        String rid = af.getRid();
        if(rid == null || "".equals(rid.trim())){
          HrbAttributeGroup hrbAtt = af2Bean(af);
          attributeGroupDao.saveAttGroup(hrbAtt);
        }else{
         HrbAttributeGroup att = attributeGroupDao.loadAttGroup(Long.valueOf(af.getRid()));
         att.setCode(af.getCode());
         att.setDescription(af.getDescription());
         att.setAttributeRid(Long.valueOf(af.getAttributeRid()));
         if(af.getIsFormal() == null || af.getIsFormal().equals("0")){
             att.setIsFormal("0");
         }else{
             att.setIsFormal("1");
         }
         if(af.getIsEnable() == null){
             att.setIsEnable("0");
         }else{
             att.setIsEnable(af.getIsEnable());
         }
         att.setSite(af.getSite());
         attributeGroupDao.updateAttGroup(att);
        }
    }
    
    public List listEnabledSiteOption() {
        List siteList = siteDao.listEnableSites();
        SelectOptionImpl siteOption = new SelectOptionImpl("--Please select--","");
        List sList = new ArrayList();
        String lable = "";
        sList.add(siteOption);
        if(siteList != null && siteList.size() > 0){
            for(int i=0;i<siteList.size();i++){
                HrbSite site = (HrbSite)siteList.get(i);
                lable = site.getName() + " - " + site.getDescription();
                siteOption = new SelectOptionImpl(lable,site.getName());
                sList.add(siteOption);
            }
        }
        return sList;
    }

    public void setAttributeDao(IAttributeDao attributeDao) {
        this.attributeDao = attributeDao;
    }

    public void setAttributeGroupDao(IAttributeGroupDao attributeGroupDao) {
        this.attributeGroupDao = attributeGroupDao;
    }

    public void setAttributeService(IAttributeService attributeService) {
        this.attributeService = attributeService;
    }

    public void setSiteDao(ISiteDao siteDao) {
        this.siteDao = siteDao;
    }



}
