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
   * ��ȡkind������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * �����ݿⶨ���name -> itemName
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
   * ��ȡ������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * ����showStyle�����÷���itemName��
   * �ֱ����ݿ�������ȡ��CURRENCY/NAME/SYMBOL�滻showStyle������ -> itemName
   *
   * @param kind String
   * @param showStyle String
   * @return Vector {DtoComboItem}
   * @deprecated showStyle�а���"$"�������ַ�ʱ�������ʹ��listCurrencyDefaultStyle()����
   * */
  public Vector listComboCurrency(String showStyle){
      Vector result = new Vector();
      List currencies = this.listCurrency();
      Iterator i = currencies.iterator();
      while(i.hasNext()){
          SysCurrency currency = (SysCurrency) i.next();
          String itemName = null;
          //����itemName
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
   * ��ȡ���л��ң�Ĭ����ʾ��ʽΪname(symbol),RelationΪ�û�����ʾС������λ��
   * @param kind String
   * @return Vector
   */
  /**
   * Ĭ�ϻ�����ʾ��ʽ
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
   * ��ȡ���Ҷ�ѡ����б����ص�Jspҳ��ʹ��
   * �����ݿⶨ���code -> SelectOptionImpl.label
   * �����ݿⶨ���name -> SelectOptionImpl.value
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
   * ����kind��ȡ�Ĳ��������б�
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
