/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.service;

import java.util.ArrayList;
import java.util.List;
import c2s.dto.DtoUtil;
import db.essp.hrbase.HrbAttribute;
import server.essp.hrbase.attribute.dao.IAttributeDao;
import server.essp.hrbase.attribute.form.AfAttribute;
import server.framework.common.BusinessException;
import server.framework.taglib.util.SelectOptionImpl;

/**
 * AttributeServiceImp
 * @author TuBaoHui
 *
 */
public class AttributeServiceImp implements IAttributeService{
    
    private IAttributeDao attributeDao;
    
    public List listAllHrbAttribute() {
        return beanList2AfList(attributeDao.listAllHrbAtt());
    }

    public List listEnableHrbAttribute() {
        return beanList2AfList(attributeDao.listAllHrbAtt());
    }

    public AfAttribute loadHrbAttribute(Long rid) {
        HrbAttribute hrbAtt = null;
        try {
            hrbAtt= attributeDao.loadHrbAtt(rid);
        } catch(Exception e) {
            new BusinessException("error.hrbase.logic.site.loadSiete",
                    "load site error");
        }
        return bean2Af(hrbAtt);
    }

    public void saveHrbAttribute(AfAttribute af) {
        if(af.getRid() == null || af.getRid().trim().equals("")){
          HrbAttribute hrbAtt = af2Bean(af);
          attributeDao.saveHrbAtt(hrbAtt);
         }else{
             Long rid = Long.valueOf(af.getRid());
             String isEnable = af.getIsEnable();
             if(isEnable == null || isEnable.trim().equals("")){
                 af.setIsEnable("0");
             }
             HrbAttribute att = attributeDao.loadHrbAtt(rid);
             att.setCode(af.getCode());
             att.setDescription(af.getDescription());
             att.setIsEnable(af.getIsEnable());
             attributeDao.updateHrbAtt(att);
         }
    }

    public void deleteHrbAttribute(Long rid) {
        attributeDao.deleteHrbAtt(rid);
    }

    public List listEnabledHrbAttOption() {
        List attList = attributeDao.listEnableHrbAtt();
        SelectOptionImpl attOption = new SelectOptionImpl("--Please select--","");
        List sList = new ArrayList();
        sList.add(attOption);
        if(attList != null && attList.size() > 0){
            for(int i=0;i<attList.size();i++){
                HrbAttribute hrbAtt = (HrbAttribute)attList.get(i);
                attOption = new SelectOptionImpl(hrbAtt.getCode()+" - "+hrbAtt.getDescription(),hrbAtt.getRid().toString());
                sList.add(attOption);
            }
        }
        return sList;
    }

    
    private static AfAttribute bean2Af(HrbAttribute bean) {
        AfAttribute af = new AfAttribute();
        if(bean != null){
          DtoUtil.copyBeanToBean(af, bean);
        }
        return af;
    }

    private static List beanList2AfList(List beanList) {
        List afList = null;
        try {
            afList = DtoUtil.list2List(beanList, AfAttribute.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return afList;
    }

    private static HrbAttribute af2Bean(AfAttribute af) {
        HrbAttribute bean = new HrbAttribute();
        DtoUtil.copyBeanToBean(bean, af);
        if(af.getRid() == null || "".equals(af.getRid())) {
            bean.setRid(null);
        }
        if(af.getIsEnable() == null || "".equals(af.getIsEnable())) {
            bean.setIsEnable("0");
        }
        return bean;
    }

    public void setAttributeDao(IAttributeDao attributeDao) {
        this.attributeDao = attributeDao;
    }
    
    
}
