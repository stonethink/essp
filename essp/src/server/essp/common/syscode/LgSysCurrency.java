package server.essp.common.syscode;

import java.util.*;

import c2s.dto.*;
import db.essp.code.*;
import net.sf.hibernate.*;
import server.framework.common.*;
import server.framework.logic.*;
import server.framework.taglib.util.*;

public class LgSysCurrency extends AbstractBusinessLogic {
  public LgSysCurrency() {
  }
  /**
   * 获取kind参数多选框的列表，供applet VWCombox控件使用
   * 将数据库定义的code -> itemValue
   * 将数据库定义的name -> itemName
   * @param kind String
   * @return Vector {DtoComboItem}*/
  public Vector listComboCurrency(){
    Vector result = new Vector();
    List currencies = this.listCurrency();
    Iterator i = currencies.iterator();
    while(i.hasNext()){
        SysCurrency currency = (SysCurrency) i.next();
        DtoComboItem item = new DtoComboItem(currency.getSymbol(),
                                             currency.getCurrency());
        result.add(item);
    }
    return result;
  }

  /**
   * 获取参数多选框的列表，供applet VWCombox控件使用
   * 将数据库定义的code -> itemValue
   * 依据showStyle的配置返回itemName：
   * 分别将数据库中所获取的CURRENCY/NAME/SYMBOL替换showStyle的配置 -> itemName
   *
   * @param kind String
   * @param showStyle String
   * @return Vector {DtoComboItem}
   * @deprecated showStyle中包含"$"等特殊字符时会出错，请使用listCurrencyDefaultStyle()方法
   * */
  public Vector listComboCurrency(String showStyle){
      Vector result = new Vector();
      List currencies = this.listCurrency();
      Iterator i = currencies.iterator();
      while(i.hasNext()){
          SysCurrency currency = (SysCurrency) i.next();
          String itemName = null;
          //设置itemName
          if(showStyle.indexOf("currency") == -1 && showStyle.indexOf("name") == -1 && showStyle.indexOf("symbol") == -1)
              itemName = currency.getSymbol();
          else{
              itemName = showStyle.replaceAll("currency",currency.getSymbol() );
              itemName = itemName.replaceAll("name", currency.getName());
              itemName = itemName.replaceAll("symbol", currency.getSymbol());
          }
          DtoComboItem item = new DtoComboItem(itemName,
                                               currency.getCurrency());
          result.add(item);
      }
      return result;
  }
  /**
   * 获取所有货币，默认显示样式为name(symbol),Relation为该货币显示小数点后的位数
   * @param kind String
   * @return Vector
   */
  /**
   * 默认货币显示样式
   */
  public static final String CURRENCY_DEFAULT_SHOWSTYLE = "name(symbol)";
  public Vector listCurrencyDefaultStyle(){
      Vector result = new Vector();
      List currencies = this.listCurrency();
      Iterator i = currencies.iterator();
      while(i.hasNext()){
          SysCurrency currency = (SysCurrency) i.next();
          String itemName = currency.getName() + "(" + currency.getSymbol() + ")";
          DtoComboItem item = new DtoComboItem(itemName,
                                               currency.getCurrency());
          item.setItemRelation(currency.getDecimalNum());
          result.add(item);
      }
      return result;
  }
  /**
   * 获取货币多选框的列表，返回到Jsp页面使用
   * 将数据库定义的code -> SelectOptionImpl.label
   * 将数据库定义的name -> SelectOptionImpl.value
   * @return List
   */
  public List listOptionCurrency(){
      List result = new ArrayList();
      List currencies = this.listCurrency();
      Iterator i = currencies.iterator();
      while(i.hasNext()){
          SysCurrency currency = (SysCurrency) i.next();
          SelectOptionImpl item = new SelectOptionImpl(currency.getSymbol(),
                  currency.getCurrency());
          result.add(item);
      }
      return result;
  }
  /**
   * 依据kind获取的参数对象列表
   * select SYS_PARAMETER where rst = 'N'
   *
   * @param kind String
   * @return java.util.List*/
  public List listCurrency(){
      try {
          Session s = this.getDbAccessor().getSession();
          List resut = s.createQuery("from SysCurrency currency " +
                                     "where currency.rst=:rst " +
                                     "order by currency.sequence"
                       )
                       .setString("rst","N")
                       .list();
          return resut;
      } catch (Exception ex) {
          log.error(ex);
          throw new BusinessException();
    }
  }

  public Double getExchRate(String fromCurrency,String toCurrency){
      try {
          Session s = this.getDbAccessor().getSession();
          List l = s.createQuery("from SysExchRate ex " +
                                 "where ex.comp_id.fromCurrency=:fromCurrency " +
                                 "and ex.comp_id.toCurrency=:toCurrency " +
                                 "order by ex.comp_id.exchDate"
                   )
                   .setParameter("fromCurrency",fromCurrency)
                   .setParameter("toCurrency",toCurrency)
                   .list();
          if(l == null || l.size() == 0){
              l = s.createQuery("from SysExchRate ex " +
                                "where ex.comp_id.fromCurrency=:toCurrency " +
                                "and ex.comp_id.toCurrency=:fromCurrency " +
                                "order by ex.comp_id.exchDate"
                  )
                  .setParameter("fromCurrency",fromCurrency)
                  .setParameter("toCurrency",toCurrency)
                  .list();
              if(l == null || l.size() == 0){
                  throw new BusinessException("CBS_PRICE_008","No exchange rate,from:["+fromCurrency+"] to:["+toCurrency+"]!");
              }else{
                  SysExchRate ex = (SysExchRate) l.get(0);
                  Double rate = ex.getRate();
                  if(rate != null && rate.doubleValue() != 0.0D){
                      return new Double(1/rate.doubleValue());
                  }else{
                      throw new BusinessException("CBS_PRICE_009","No exchange rate,from:["+fromCurrency+"] to:["+toCurrency+"]!");
                  }
              }
          }else{
              SysExchRate ex = (SysExchRate) l.get(0);
              return ex.getRate();
          }
      } catch(BusinessException ex){
          throw ex;
      }
      catch (Exception ex) {
          throw new BusinessException("CBS_PRICE_100","Exchange rate errot,from:["+fromCurrency+"] to:["+toCurrency+"]!",ex);
      }

    }
}
