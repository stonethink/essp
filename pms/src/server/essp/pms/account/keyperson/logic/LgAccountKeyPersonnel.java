package server.essp.pms.account.keyperson.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.account.DtoAcntKeyPersonnel;
import db.essp.pms.Account;
import db.essp.pms.Keypesonal;
import server.essp.pms.account.logic.LgAccount;
import server.framework.common.BusinessException;
import itf.hr.IHrUtil;
import itf.hr.HrFactory;
import c2s.essp.common.user.DtoCustomer;
import server.framework.taglib.util.SelectOptionImpl;
import net.sf.hibernate.Session;

public class LgAccountKeyPersonnel extends LgAccountKeyPersonnelBase {
    /**
     * 列出项目所有KeyPersonnel Dto
     * @param acntRid Long
     * @return List
     */
    public List listKeyPersonnelDto(Long acntRid){
        List result = new ArrayList();
        List l = this.listKeyPersonnel(acntRid);
        Iterator i = l.iterator();
        IHrUtil hrUtil = HrFactory.create();
        while(i.hasNext()){
            Keypesonal person = (Keypesonal) i.next();
            DtoAcntKeyPersonnel dto = new DtoAcntKeyPersonnel();
            dto.setAcntRid(acntRid);
            try {
                DtoUtil.copyBeanToBean(dto, person);
            } catch (Exception ex) {
                throw new BusinessException("ACNT_KP_006","error in copying propties while list labor resource dto,[" + person.getLoginId() + "]",ex);
            }
            //如果是客户查找其密码
            if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(dto.getTypeName())){
                DtoCustomer cus = hrUtil.findCustomer(dto.getLoginId());
                if(cus == null)
                    throw new BusinessException("ACNT_KP_007","customer:["+dto.getLoginId()+"] is not in system");
                dto.setPassword(cus.getPassword());
            }
            dto.setIsExisted(true);
            result.add(dto);
        }
        return result;
    }
    /**
     * 列出项目中所有为Customer的Key person
     * @param acntRid Long
     * @return List
     */
    public List listAccountCustomer(Long acntRid){
        List result = new ArrayList();
        List keypersons = listKeyPersonnelDto(acntRid);
        if(keypersons == null || keypersons.size() <= 0)
            return result;
        for(int i = 0;i < keypersons.size();i ++){
            DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) keypersons.get(i);
            if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(dto.getTypeName())){
                result.add(dto);
            }
        }
        return result;
    }
    /**
     * 将DTO转换为Hibernate Model
     * @param dto DtoAccountLoaborRes
     * @return LaborResource
     * @throws BusinessException
     */
    private Keypesonal getDBModel(DtoAcntKeyPersonnel dto) throws BusinessException {
        Long acntRid  = dto.getAcntRid();
        LgAccount acntLogic = new LgAccount();
        Account acnt = acntLogic.load(acntRid);
        try {
            //Account acnt = (Account) getDbAccessor().load(Account.class,acntRid);
            Keypesonal person = new Keypesonal();
            DtoUtil.copyBeanToBean(person, dto);
            person.setAccount(acnt);
            return person;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("ACNT_KP_008","error in copying propties while convert dto to hibernate db: [" + dto.getLoginId() + "]",ex);
        }
    }
    /**
     * 新增KeyPersonnel Dto
     * 如果KeyPersonnel是Customer类型且不存在，则新增该条Customer记录
     * @param dto DtoAcntKeyPersonnel
     */
    public void add(DtoAcntKeyPersonnel dto){
        Keypesonal person = this.getDBModel(dto);
        this.add(person);
        if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(dto.getTypeName())){
             IHrUtil hrUtil = HrFactory.create();
             DtoCustomer customer = new DtoCustomer();
            try {
                DtoUtil.copyBeanToBean(customer, dto);
            } catch (Exception ex) {
                throw new BusinessException("ACNT_KP_009","error while copying customer properties",ex);
            }
            customer.setUserLoginId(dto.getLoginId());
            //如果该Customer已存在，更新其信息，不存在则添加新的Customer
             if(dto.isExisted())
                 hrUtil.updateCustomer(customer);
             else
                 hrUtil.addCustomer(customer);
        }
    }
    /**
     * 更新KeyPersonnel Dto
     * @param dto DtoAcntKeyPersonnel
     */
    public void update(DtoAcntKeyPersonnel dto){
        Keypesonal person = this.getDBModel(dto);
        this.update(person);
        if(DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0].equals(dto.getTypeName())) {
            IHrUtil hrUtil = HrFactory.create();
             DtoCustomer customer = new DtoCustomer();
             try {
                 DtoUtil.copyBeanToBean(customer, dto);
             } catch (Exception ex) {
                 throw new BusinessException("ACNT_KP_010","error while copying customer properties",ex);
             }
             customer.setUserLoginId(dto.getLoginId());
             hrUtil.updateCustomer(customer);
        }
    }
    /**
     * 删除KeyPersonnel Dto
     * @param dto DtoAcntKeyPersonnel
     */
    public void delete(DtoAcntKeyPersonnel dto){
        Keypesonal person = this.getDBModel(dto);
        this.delete(person);
    }
    /**
     * 根据List中Dto元素状态更新所有Dto
     * @param laborResourceList List
     */
    public void updateDtoList(List laborResourceList){
       Iterator i =  laborResourceList.iterator();
       while(i.hasNext()){
          DtoAcntKeyPersonnel dto = (DtoAcntKeyPersonnel) i.next();
          if(dto.isInsert()){
                this.add(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }else if(dto.isDelete()){
                this.delete(dto);
                i.remove();
            }else if(dto.isModify()){
                this.update(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            }
       }
    }
    public List listCustomerAvailableAccount(String loginId){
        List result = new ArrayList();
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from Keypesonal kp " +
                                   "where kp.loginId=:loginId " +
                                   "and kp.typeName=:cusType " +
                                   "and kp.enable=:enable")
                     .setParameter("loginId",loginId)
                     .setParameter("cusType",DtoAcntKeyPersonnel.KEY_PERSON_TYPES[0])
                     .setParameter("enable",DtoAcntKeyPersonnel.ENABLE)
                     .list();
            Iterator i = l.iterator();
            while(i.hasNext()){
                Keypesonal kp =  (Keypesonal) i.next();
                Account acnt = kp.getAccount();
                SelectOptionImpl opt = new SelectOptionImpl();
                opt.setLabel(acnt.getId()+"---"+acnt.getName());
                opt.setValue(acnt.getRid().toString());
                result.add(opt);
            }
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("ACNT_KP_011","error while listing customer available accounts!",ex);
        }
        return result;
    }
    public static void  main(String[] args){
        LgAccountKeyPersonnel lg = new LgAccountKeyPersonnel();
        List list =lg.listKeyPersonnelDto(new Long(6022));
        System.out.println(list.size());
        for(int i=0;i<list.size();i++){
            DtoAcntKeyPersonnel key = (DtoAcntKeyPersonnel)list.get(i);
            System.out.println(key.getUserName());
            System.out.println(key.getOrganization());
            System.out.println(key.getTitle());
            System.out.println(key.getPhone());
            System.out.println(key.getFax());
            System.out.println(key.getEmail());
        }
    }
}
