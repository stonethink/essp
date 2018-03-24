package server.essp.common.syscode;

import server.framework.logic.AbstractBusinessLogic;
import java.util.Vector;
import java.util.List;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import db.essp.code.SysParameter;
import java.util.Iterator;
import c2s.dto.DtoComboItem;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;

public class LgSysParameter extends AbstractBusinessLogic {
        /**
         * PARAM���߼����ʾ��ʽ
         */
        public static final String PARAM_SHOWSTYLE_DASHED = "code -- name";
        /**
         * PARAM���ż����ʾ��ʽ
         */
        public static final String PARAM_SHOWSTYLE_BRACKET = "(code)name";
  public LgSysParameter() {
  }
  /**
   * ��ȡkind������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * �����ݿⶨ���name -> itemName
   * @param kind String
   * @return Vector {DtoComboItem}*/
  public Vector listComboSysParas(String kind){
    Vector result = new Vector();
    List params = this.listSysParas(kind);
    Iterator i = params.iterator();
    while(i.hasNext()){
       SysParameter param = (SysParameter) i.next();
       DtoComboItem item = new DtoComboItem(param.getName(),
                                            param.getComp_id().getCode());
       result.add(item);
    }
    return result;
  }

  /**
   * ��ȡkind������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * ����showStyle�����÷���itemName��
   * �ֱ����ݿ�������ȡ��code/name/alias�滻showStyle������ -> itemName
   *
   * @param kind String
   * @param showStyle String
   * @return Vector {DtoComboItem}
   * @deprecated showStyle����"$"����ʹ�ַ��������������Ҫ��ʹ��
   * */
  public Vector listComboSysParas(String kind,String showStyle){
      Vector result = new Vector();
      List params = this.listSysParas(kind);
      Iterator i = params.iterator();
      while(i.hasNext()){
         SysParameter param = (SysParameter) i.next();
         String itemName = null;
         //����itemName
         if(showStyle.indexOf("code") == -1 && showStyle.indexOf("name") == -1 && showStyle.indexOf("alias") == -1)
             itemName = param.getName();
         else{
             itemName = showStyle.replaceAll("code", param.getComp_id().getCode());
             itemName = itemName.replaceAll("name", param.getName());
             itemName = itemName.replaceAll("alias", param.getAlias());
         }
         DtoComboItem item = new DtoComboItem(itemName,
                                              param.getComp_id().getCode());
         result.add(item);
    }
      return result;
  }
  /**
   * ��ȡkind������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * �����ݿⶨ���name -> code -- name
   * @param kind String
   * @param showStyle String
   * @return Vector
   */
  public Vector listComboSysParasDashed(String kind){
      return this.listComboSysParas(kind,PARAM_SHOWSTYLE_DASHED);
  }
  /**
   * ��ȡkind������ѡ����б���applet VWCombox�ؼ�ʹ��
   * �����ݿⶨ���code -> itemValue
   * �����ݿⶨ���name -> code -- name
   * @param kind String
   * @param showStyle String
   * @return Vector
   */
  public Vector listComboSysParasBracket(String kind){
      return this.listComboSysParas(kind,PARAM_SHOWSTYLE_BRACKET);
  }

  /**
   * ��ȡkind������ѡ����б����ص�Jspҳ��ʹ��
   * �����ݿⶨ���code -> SelectOptionImpl.label
   * �����ݿⶨ���name -> SelectOptionImpl.value
   * @param kind String
   * @return List
   */
  public List listOptionSysParas(String kind){
     List result = new ArrayList();
     List params = this.listSysParas(kind);
     Iterator i = params.iterator();
     while(i.hasNext()){
        SysParameter param = (SysParameter) i.next();
        SelectOptionImpl item = new SelectOptionImpl(param.getName(),
                param.getComp_id().getCode());
        result.add(item);
    }
     return result;
  }
  /**
   * ����kind��ȡ�Ĳ��������б�
   * select SYS_PARAMETER where kind = :kind and rst = 'N'
   *
   * @param kind String
   * @return List*/
  public List listSysParas(String kind){
    try {
        Session s = this.getDbAccessor().getSession();
        List resut = s.createQuery("from SysParameter param " +
                                   "where param.comp_id.kind=:kind " +
                                   "and param.rst=:rst order by param.sequence "
                      )
                      .setString("kind",kind)
                      .setString("rst","N")
                      .list();

        return resut;
    } catch (Exception ex) {
        log.error(ex);
        throw new BusinessException();
    }
  }

}
