package client.essp.cbs.config;

import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.essp.cbs.DtoPrice;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import com.wits.util.StringUtil;
import org.apache.log4j.Category;
import c2s.essp.cbs.CbsConstant;

public class PriceTableModel extends VMTable2 {
    private Vector jobCodeList;
    private String priceScope;
    private boolean locked;

    public PriceTableModel(Object[][] configs){
        super(configs);
    }
    static Category log = Category.getInstance("client");
    //在项目下自定义价格的货币不能修改的,从系统Copy所有栏位都不能改
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) this.getColumnConfigs().get(columnIndex);
        String dataName = columnConfig.getDataName();
        if(CbsConstant.SCOPE_ACCOUNT.equals(priceScope)){
            DtoPrice dtoBean = (DtoPrice) getRow(rowIndex);
            if(CbsConstant.SCOPE_GLOBAL.equals(dtoBean.getPriceScope()))
                return false;
            else if("currency".equals(dataName))
                return false;
        }

        return super.isCellEditable(rowIndex,columnIndex);
    }
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if( locked == false ){
            VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                          get(columnIndex);
            String dateName = columnConfig.getDataName();
            DtoPrice dtoBean = (DtoPrice) getRow(rowIndex);
//            if (!dateName.equals("subjectCode") &&
//                "".equals(StringUtil.nvl(aValue))) {
//                locked = true;
//                comMSG.dispErrorDialog("Please input the value!");
//                locked = false;
//                return;
//            }
            if (!dateName.equals("name") && !dateName.equals("id")) {
                super.setValueAt(aValue, rowIndex, columnIndex);
            } else {
                if (dateName.equals("id")) {
                    if (checkId(dtoBean.getCatalog(), aValue.toString(),rowIndex)) {
                        super.setValueAt(aValue, rowIndex, columnIndex);
                        String name = getJobCodeNameById(aValue.toString());
                        if (name != null)
                            dtoBean.setName(name);
                    }
                } else if (dateName.equals("name")) {
                    String id = getJobCodeIdByName(aValue.toString());
                    if (id != null) {
                        if (checkId(dtoBean.getCatalog(), id, rowIndex)) {
                            super.setValueAt(aValue, rowIndex, columnIndex);
                            dtoBean.setId(id);
                        }
                    } else {
                        super.setValueAt(aValue, rowIndex, columnIndex);
                    }
                }
                fireTableDataChanged();
            }
        }
    }

    public Vector getJobCodeIdList() {
        return jobCodeList;
    }

    public void setJobCodeList(Vector jobCodeList) {
        this.jobCodeList = jobCodeList;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
    }

    private String getJobCodeNameById(String id){
        if(jobCodeList == null)
            return null;
        for(int i = 0;i < jobCodeList.size() ;i ++){
            DtoComboItem item = (DtoComboItem) jobCodeList.get(i);
            if(item.getItemValue().equals(id))
                return (String) item.getItemName();
        }
        return null;
    }
    private String getJobCodeIdByName(String name){
        if(jobCodeList == null)
            return null;
        for(int j = 0;j < jobCodeList.size(); j ++){
            DtoComboItem item = (DtoComboItem) jobCodeList.get(j);
            if(item.getItemName().equals(name)){
                return (String) item.getItemValue();
            }
        }
        return null;
    }
    private boolean checkId(String catalog,String id,int rowIndex){
        List rows = this.getRows();
        if(rows == null || rows.size() <= 0)
            return true;
        for(int i = 0 ;i < rows.size() ;i ++){
            if(i == rowIndex)
                continue;
            DtoPrice dtoBean = (DtoPrice)getRow(i);
            if(StringUtil.nvl(catalog).equals(dtoBean.getCatalog()) &&
               StringUtil.nvl(id).equals(dtoBean.getId())){
                locked = true;
                comMSG.dispErrorDialog("The id["+id+"] has been in the catalogue["+catalog+"]");
                locked = false;
                return false;
            }
        }
        return true;
    }

    public String getPriceScope() {
        return priceScope;
    }
}
