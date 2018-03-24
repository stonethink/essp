package server.essp.cbs.config.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.cbs.CbsConstant;
import c2s.essp.cbs.DtoPrice;
import c2s.essp.common.account.IDtoAccount;
import com.wits.util.StringUtil;
import db.essp.cbs.SysPrice;
import db.essp.code.SysCurrency;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.hr.HrFactory;
import net.sf.hibernate.Session;
import server.essp.common.syscode.LgSysCurrency;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import net.sf.hibernate.*;
import c2s.essp.cbs.DtoCbs;
import c2s.essp.cbs.DtoSubject;

public class LgPrice extends AbstractESSPLogic {
    /**
     * 系统定义价格列表，系统价格对应的AcntRid＝0
     * @return List
     */
    public List listSysPrice(){
        return listAcntPrice(DtoPrice.SYS_PRICE_ACNTRID);
    }
    public List listSysPriceDto(){
        return listAcntPriceDto(DtoPrice.SYS_PRICE_ACNTRID);
    }
    /**
     * 列出项目定义的价格
     * @param acntRid Long
     * @return List
     */
    public List listAcntPrice(Long acntRid){
        if(acntRid == null){
            throw new BusinessException("CBS_PRICE_002","Acnt Rid can not be null!");
        }
        try {
            Session s = this.getDbAccessor().getSession();
            List l = s.createQuery("from SysPrice price " +
                                   "where price.acntRid=:acntRid " +
                                   "order by PRICE_SCOPE,catalog,id"
                                   )
                     .setParameter("acntRid",acntRid)
                     .list();
            return l;
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_001","error while loading system price config!",ex);
        }
    }
    public List listAcntPriceDto(Long acntRid){
        List result = new ArrayList();
        List l = listAcntPrice(acntRid);
        if(l != null){
            for(int i = 0;i < l.size() ;i ++){
                SysPrice price = (SysPrice) l.get(i);
                DtoPrice dto = new DtoPrice();
                DtoUtil.copyBeanToBean(dto,price);
                result.add(dto);
            }
        }
        return result;
    }
    /**
     * 初始化系统价格的配置
     * 1.从HR中取得所有JobCode列表.
     * 2.遍历每个JobCode，对每个JobCode设置默认SubjectCode(根科目)；类型(人力成本)；计价单位(PM);币别(任意选一)和价格,
     * JobCode的ID作为Price的Id，JobCode Name做Price的Name
     */
    public List initSysPrice(){
        List result = new ArrayList();
        HrFactory hrFactory = new HrFactory();
        List jobCodeList = hrFactory.create().listComboJobCode();
        if(jobCodeList == null)
            return result;
        //任意选取一种币别
        LgSysCurrency lgCurrency = new LgSysCurrency();
        List currencies = lgCurrency.listCurrency();
        if(currencies == null || currencies.size() <= 0){
            throw new BusinessException("CBS_PRICE_003","Before configuring system price,you must configure currency!");
        }
        SysCurrency currency = (SysCurrency) currencies.get(0);
        //获得根科目
        LgCbs lgCbs = new LgCbs();
        DtoCbs dtoCbs = lgCbs.loadCbsDefine(DtoCbs.DEFAULT_TYPE);
        if(dtoCbs == null || dtoCbs.getCbsRoot() == null || dtoCbs.getCbsRoot().getDataBean() == null){
             throw new BusinessException("CBS_PRICE_013","Before configuring system price,you must configure cbs subject!");
        }
        DtoSubject rootSubject = (DtoSubject) dtoCbs.getCbsRoot().getDataBean();

        for(int i = 0; i < jobCodeList.size(); i ++){
            DtoComboItem item = (DtoComboItem) jobCodeList.get(i);
            DtoPrice price = new DtoPrice();
            price.setSubjectCode(rootSubject.getSubjectCode());
            price.setAcntRid(DtoPrice.SYS_PRICE_ACNTRID);//系统价格AcntRid＝0
            price.setCatalog(CbsConstant.LABOR_RESOURCE);
            price.setId(StringUtil.nvl(item.getItemValue()));
            price.setName(item.getItemName());
            price.setUnit(CbsConstant.LABOR_UNIT_PM);
            price.setPrice(new Double(0));
            price.setCurrency(currency.getCurrency());
            price.setPriceScope(CbsConstant.SCOPE_GLOBAL);
            addPrice(price);
            result.add(price);
        }
        return result;
    }
    /**
     * 初始化项目的价格配置
     * @param acntRid Long
     */
    public List intAcntPrice(Long acntRid){
        List result = new ArrayList();
        if(acntRid == null){
            throw new BusinessException("CBS_PRICE_002","Acnt Rid can not be null!");
        }
        if(acntRid.longValue() == 0){
            throw new BusinessException("CBS_PRICE_0012","The acnt Rid can not be 0!It's system price!");
        }
        List sysPrices = listSysPriceDto();
        if(sysPrices == null || sysPrices.size() == 0){
            sysPrices = initSysPrice();
            //throw new BusinessException("CBS_PRICE_006","Before configuring account price data,you must configure system price!");
        }
        List l = listAcntPrice(acntRid);
            //删除Account中从系统获得的价格
        for(int i = 0 ;i < l.size(); i ++){
            SysPrice price = (SysPrice) l.get(i);
               if(CbsConstant.SCOPE_GLOBAL.equals(price.getPriceScope())){
                  this.deletePrice(price);
              }
        }
        AccountFactory acountFactory = new AccountFactory();
        IAccountUtil acntUtil = acountFactory.create();
        IDtoAccount account = acntUtil.getAccountByRID(acntRid);
        String toCurrency = account.getCurrency();//获得项目选择币别
        LgSysCurrency lgCurrency = new LgSysCurrency();
        for(int i = 0;i < sysPrices.size();i ++){
            DtoPrice price = (DtoPrice) sysPrices.get(i);
            price.setAcntRid(acntRid);
            String fromCurrency = price.getCurrency();
            if(toCurrency != null && !toCurrency.equals(fromCurrency)){
                Double rate = lgCurrency.getExchRate(fromCurrency, toCurrency);
                if (price.getPrice() == null || rate == null) {
                    price.setPrice(new Double(0));
                } else {
                    //转换币值
                    double fromPrice = price.getPrice().doubleValue();
                    price.setPrice(new Double(fromPrice * rate.doubleValue()));
                }
                price.setCurrency(toCurrency);
            }
            this.addPrice(price);
            result.add(price);
        }
        return result;
    }
    /**
     * 新增价格
     * @param dto DtoPrice
     */
    public void addPrice(DtoPrice dto){
        if(dto == null || dto.getAcntRid() == null
           || dto.getCatalog() == null || dto.getId() == null){
            throw new BusinessException("CBS_PRICE_005","can not add illegal price data!");
        }

        SysPrice price = new SysPrice();
        DtoUtil.copyBeanToBean(price,dto);
        try {
            this.getDbAccessor().assignedRid(price);
            dto.setRid(price.getRid());
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_0013","can not add illegal price data!",ex);
        }
        addPrice(price);
    }
    private void addPrice(SysPrice price) throws BusinessException {
        try {
            this.getDbAccessor().save(price);
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("CBS_PRICE_004","error while adding system price!",ex);
        }
    }
    /**
     * 查找价格，Account，Catalog和Id应能唯一确定一个Price
     * @param acntRid Long
     * @param id String
     * @param catalog String
     * @return DtoPrice
     */
    public DtoPrice getPrice(Long acntRid,String id,String catalog){
        try {
            Session s = this.getDbAccessor().getSession();
            Object obj = s.createQuery("from SysPrice price " +
                                       "where price.acntRid=:acntRid " +
                                       "and price.catalog=:catalog " +
                                       "and id=:id "
                         )
                         .setParameter("acntRid", acntRid)
                         .setParameter("catalog", catalog)
                         .setParameter("id", id)
                         .uniqueResult();
            if(obj == null)
                return null;
            SysPrice price = (SysPrice) obj;
            DtoPrice dto = new DtoPrice();
            DtoUtil.copyBeanToBean(dto,price);
            return dto;
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_0014","error while getting price!",ex);
        }
    }
    /**
     * 删除价格
     * @param price SysPrice
     */
    public void deletePrice(DtoPrice dto){
        if(dto == null || dto.getAcntRid() == null
           || dto.getCatalog() == null || dto.getId() == null){
            throw new BusinessException("CBS_PRICE_009","can not delete illegal price data!");
        }
        SysPrice price = new SysPrice();
        DtoUtil.copyBeanToBean(price,dto);
        deletePrice(price);
    }
    private void deletePrice(SysPrice price){
        try {
            this.getDbAccessor().delete(price);
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_008","error while deleting system price!",ex);
        }
    }
    /**
     * 更新价格
     * @param price SysPrice
     */
    private void updatePrice(SysPrice price){
        try {
            this.getDbAccessor().update(price);
        } catch (Exception ex) {
            throw new BusinessException("CBS_PRICE_0010","error while updating system price!",ex);
        }
    }
    public void updatePrice(DtoPrice dto){
        if(dto == null || dto.getAcntRid() == null
           || dto.getCatalog() == null || dto.getId() == null){
            throw new BusinessException("CBS_PRICE_0011","can not update illegal price data!");
        }
        SysPrice price = new SysPrice();
        DtoUtil.copyBeanToBean(price,dto);
        updatePrice(price);
    }
    /**
     * 更新Client传来的DtoPrice列表
     * @param dtos List
     */
    public void updateDtoList(List dtos){
        if(dtos == null)
           throw new BusinessException("CBS_PRICE_0011","can not update illegal price data!");
       for(int i = 0;i < dtos.size() ;i ++){
           DtoPrice dto = (DtoPrice)dtos.get(i);
           if (dto.isInsert()) {
               this.addPrice(dto);
               dto.setOp(IDto.OP_NOCHANGE);
           } else if (dto.isDelete()) {
               this.deletePrice(dto);
               dtos.remove(i);
           } else if (dto.isModify()) {
               this.updatePrice(dto);
               dto.setOp(IDto.OP_NOCHANGE);
           }
       }
    }
    public static void main(String[] args){
        LgPrice lg = new LgPrice();
        lg.getPrice(new Long(1),"332","Labor");
    }

}
