package client.framework.model;

/**
 * @author Stone 20050223
 */

import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.IVWJTableSort;
import org.apache.log4j.Category;
import validator.ValidatorResult;
import java.text.Collator;
import java.util.Locale;

public class VMTable2 extends AbstractTableModel implements Serializable,
        IVWJTableSort, IColumnConfig {
    protected static Category log = Category.getInstance("client");
    List columnConfigs = new ArrayList();
    List rows = new ArrayList();
    List dtoList = new ArrayList();
    Class dtoType;
    VMTableValidatorResult result = new VMTableValidatorResult();
    
    //add by lipengxu 2008-02-03 for asc and desc sorting
    private int sortingColumn = -1;
    private boolean ascending = false;

    //To support column choose display
    //columnMap contains the column and the hidden column
    private List columnMap = new ArrayList();

    private boolean isEditable=true;
    /** @link dependency */
    /*# VMColumnConfig lnkVMColumnConfig; */

    public VMTable2() {
    }

    public VMTable2(Object[][] configs) {
        setColumnConfigs(configs);
    }

    public VMTable2(Object[][] configs, List dtoList) {
        setColumnConfigs(configs);
        setRows(dtoList);
    }

    /**
     * note: 与其它setColumnConfigs()不同的是:参数中含有的column并不会全部加到columnConfigs中用于显示.
     * 会判断column是否hidden,如果是,则不加到columnConifgs中.
     */
    public void setColumnConfigs(Object[][] configs) {
        if (configs == null) {
            return;
        }
        for (int i = 0; i < configs.length; i++) {
            VMColumnConfig columnConfig = new VMColumnConfig(configs[i]);
            if (columnConfig.isHidden == false) {
                columnConfigs.add(columnConfig);
            }

            columnMap.add(columnConfig);
        }

        this.fireTableStructureChanged();
    }

    public void setColumnConfigs(List columnConfigs) {
        setColumnConfigs(columnConfigs, true);
    }

    public void setColumnConfigs(List columnConfigs, boolean bFlag) {
        this.columnConfigs = columnConfigs;
        if (bFlag == true) {
            columnMap.clear();
            for (int i = 0; i < columnConfigs.size(); i++) {
                VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.
                                              get(i);
                columnMap.add(columnConfig);
            }
        }

        this.fireTableStructureChanged();
    }


    public List getColumnConfigs() {
        return this.columnConfigs;
    }

    public VMColumnConfig getColumnConfig(String columnName) {
        for (int i = 0; i < columnConfigs.size(); i++) {
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(i);
            if (columnConfig.getName().equals(columnName)) {
                return columnConfig;
            }
        }
        return null;
    }

    public VMColumnConfig getColumnConfigByDataName(String columnDataName) {
        for (int i = 0; i < columnConfigs.size(); i++) {
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(i);
            if (columnConfig.getDataName().equals(columnDataName)) {
                return columnConfig;
            }
        }
        return null;
    }

    public List getColumnMap() {
        return this.columnMap;
    }

    public void setRows(List dtoList) {
        int oldRowCount = getRowCount();
        if (dtoList == null) {
            return;
        }
        rows.clear();
        for (int i = 0; i < dtoList.size(); i++) {
            if (i == 0 && dtoType == null) {
                this.dtoType = dtoList.get(0).getClass();
            }
            rows.add(dtoList.get(i));
        }
        this.dtoList = dtoList;

        int newRowCount = getRowCount();
        if (oldRowCount <= 0) {
            this.fireTableRowsInserted(0, newRowCount - 1);
        } else if (oldRowCount < newRowCount) {
            this.fireTableRowsUpdated(0, oldRowCount - 1);
            this.fireTableRowsInserted(oldRowCount, newRowCount - 1);
        } else if (oldRowCount == newRowCount) {
            this.fireTableRowsUpdated(0, oldRowCount - 1);
        } else if (oldRowCount > newRowCount) {
            this.fireTableRowsUpdated(0, newRowCount - 1);
            this.fireTableRowsDeleted(newRowCount, oldRowCount - 1);
        }
    }

    public List getRows() {
        return this.rows;
    }

    public Object getRow(int row) {
        int rowIndex = row;
        if (rowIndex < 0) {
            rowIndex = 0;
        }
        if (rowIndex < rows.size()) {
            return rows.get(rowIndex);
        } else {
            return null;
        }
    }

    public Object addRow(int row) {
        int rowIndex = row;
        Object dtoBean = null;
        if (rowIndex < 0) {
            rowIndex = 0;
        }
        try {
            dtoBean = dtoType.newInstance();
            addRow(rowIndex, dtoBean);
        } catch (Exception e) {
            //
            log.debug(e);
        }
        return dtoBean;
    }

    public Object addRow(int row, Object dtoBean) {
        int rowIndex = row;
        if (dtoBean == null) {
            return null;
        }
        if (rowIndex < 0) {
            rowIndex = 0;
        }
        if (dtoBean instanceof IDto) {
            ((IDto) dtoBean).setOp(IDto.OP_INSERT);
        }
        int addRowIndex = 0;
        if (rowIndex >= getRowCount()) {
            addRowIndex = getRowCount();
        } else {
            addRowIndex = rowIndex + 1;
        }
        rows.add(addRowIndex, dtoBean);
        dtoList.add(dtoBean);
        log.debug("addRow:" + addRowIndex + ";rows count=" + rows.size() +
                  ";dtoList count=" + dtoList.size());
        fireTableRowsInserted(addRowIndex, addRowIndex);
        return dtoBean;
    }

    public Object deleteRow(int rowIndex) {
        if (rowIndex < 0) {
            return null;
        }
        Object dtoBean = getRow(rowIndex);
        if (dtoBean != null) {
            if (dtoBean instanceof IDto) {
                IDto thisDtoBean = (IDto) dtoBean;
                if (IDto.OP_INSERT.equals(thisDtoBean.getOp())) {
                    dtoList.remove(thisDtoBean);
                    log.debug("remove new Row:" + rowIndex + thisDtoBean.getOp());
                } else {
                    thisDtoBean.setOp(IDto.OP_DELETE);
                    log.debug("remove old Row:" + rowIndex + thisDtoBean.getOp());
                }
            }
            rows.remove(rowIndex);
            log.debug("deleteRow:" + rowIndex + ";rows count=" + rows.size() +
                      ";dtoList count=" + dtoList.size());
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        return dtoBean;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount() {
        return columnConfigs.size();
    }

    /**
     * Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column*/
    public String getColumnName(int columnIndex) {
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        return columnConfig.getName();
    }

    /**
     * Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.*/
    public Class getColumnClass(int columnIndex) {
        Class cls = super.getColumnClass(columnIndex);
        if (getRowCount() > 0) {
            VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                    columnIndex);
            String dataName = columnConfig.getDataName();
            try {
                Object value = DtoUtil.getProperty(getRow(0), dataName);
                cls = value.getClass();
            } catch (Exception e) {
                //
                log.debug(e);
            }
        }
        return cls;
    }

    /**
     * Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt*/
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //加入可以设置全局是否可以被编辑的设置
        //modify:Robin 20060916
        if(!this.isEditable){
            return false;
        }
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        return columnConfig.getEditable();
        //return true;
    }

    public void setCellEditable(boolean flag){
        this.isEditable=flag;
    }
    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell*/
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = null;
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        String dataName = columnConfig.getDataName();
        try {
            value = DtoUtil.getProperty(getRow(rowIndex), dataName);
        } catch (Exception e) {
            //
            log.debug(e);
        }
        return value;
    }

    /**
     * Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable*/
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Object oldValue = getValueAt(rowIndex, columnIndex);
        if (aValue == null) {
            log.debug("[VMTable2] set value('" + null +"'," + rowIndex + "," +
                      columnIndex + ")");
        } else {
            log.debug("[VMTable2] set value('" + aValue.toString() + "'," +
                      rowIndex + "," + columnIndex + ")");
        }
        VMColumnConfig columnConfig = (VMColumnConfig) columnConfigs.get(
                columnIndex);
        Component comp = columnConfig.getComponent();
        if (comp == null || !(comp instanceof IVWComponent)) {
            super.setValueAt(oldValue, rowIndex, columnIndex);
            fireTableCellUpdated(rowIndex, columnIndex);
            return;
        }

        IVWComponent vwcomp = (IVWComponent) comp;
        vwcomp.setUICValue(aValue);
        vwcomp.validateValue();

        //if validate success,call super.setValueAt() method
        //write model
        //modify:Robin
//        if(validateFlag){
//            super.setValueAt(aValue,rowIndex,columnIndex);
//        }

        ValidatorResult validatorResult = vwcomp.getValidatorResult();

        if (validatorResult != null && validatorResult.isValid() == false) {
            result.setResult(rowIndex, columnIndex, validatorResult);
            log.info(validatorResult.getAllMsg()[0]);
            vwcomp.setToolTipText("");
            vwcomp.setErrorField(false);
            vwcomp.setUICValue(oldValue);
//            this.setValueAt(oldValue,rowIndex,columnIndex);
            super.setValueAt(oldValue, rowIndex, columnIndex);
            fireTableCellUpdated(rowIndex, columnIndex);
            return;
        }
        String dataName = columnConfig.getDataName();
        try {
            Object dtoBean = getRow(rowIndex);

            if (aValue == null) {
                if (oldValue != null) {
                    DtoUtil.setProperty(dtoBean, dataName, aValue);
                    //write model
                    //modify:Robin
                    super.setValueAt(aValue, rowIndex, columnIndex);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    if (dtoBean instanceof IDto) {
                        if (!((IDto) dtoBean).isInsert()) {
                            ((IDto) dtoBean).setOp(IDto.OP_MODIFY);
                        }
                    }
                }
            } else {
                if (aValue.equals(oldValue) == false) {
                    DtoUtil.setProperty(dtoBean, dataName, aValue);
                    //write model
                    //modify:Robin
                    super.setValueAt(aValue, rowIndex, columnIndex);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    if (dtoBean instanceof IDto) {
                        if (!((IDto) dtoBean).isInsert()) {
                            ((IDto) dtoBean).setOp(IDto.OP_MODIFY);
                        }

                    }
                }
            }
            /*if (oldValue != aValue) {
                DtoUtil.setProperty(dtoBean, dataName, aValue);
                if (dtoBean instanceof IDto) {
                    ( (IDto) dtoBean).setOp(IDto.OP_MODIFY);
                }
                         }
             */
        } catch (Exception e) {
            //
            log.debug("", e);
        }
    }

    public List getDtoList() {
        return dtoList;
    }

//    public void setDtoList(List dtoList) {
//        this.dtoList = dtoList;
//        setRows(dtoList);
//    }

    public Class getDtoType() {
        return dtoType;
    }

    public void setDtoType(Class dtoType) {
        this.dtoType = dtoType;
    }

    public ValidatorResult getValidatorResult(int rowIndex, int columnIndex) {
        return result.getResult(rowIndex, columnIndex);
    }

    public void sortByColumnName(String name) {
        int index = -1;
        for (int i = 0; i < columnConfigs.size(); i++) {
            if (((VMColumnConfig) columnConfigs.get(i)).getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            sortByColumnIndex(index);
        }
    }

    //更新列的配置信息
    public void updateColumnConfig(Object[] config) {
        VMColumnConfig columnConfig = new VMColumnConfig(config);
        List list = this.getColumnConfigs();
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (((VMColumnConfig) list.get(i)).getName().equals(columnConfig.
                    getName())) {
                index = i;
                break; //找到一个就结束
            }
        }
        if (index != -1) {
            list.remove(index);
            list.add(index, columnConfig);
            this.setColumnConfigs(list, false);
        }
    }
    
    public void sortByColumnIndex(int index) {
    	sortByColumnIndex(index, resetAscending(index));
    }
    
    private boolean resetAscending(int col) {
    	if(this.sortingColumn == col) {
    		return this.ascending = !this.ascending;
    	} else {
    		this.sortingColumn = col;
    		return this.ascending = true;
    	}
    	
    }
    
    public boolean isAscending() {
    	return this.ascending;
    }
    
    public int getSortingColumn() {
    	return this.sortingColumn;
    }

    public void sortByColumnIndex(int index, boolean asc) {
        VMColumnConfig config = (VMColumnConfig) columnConfigs.get(index);
        String dataName = config.getDataName();
        List list = getRows();
        ArrayList list2 = new ArrayList();
        TreeMap tempMap = new TreeMap(new DTOComparator(dataName, asc));
        for (int i = 0; i < list.size(); i++) {
            tempMap.put(new Integer(i), list.get(i));
        }

        //list.clear();
        while (tempMap.size() > 0) {
        	Object key;
        	key = tempMap.firstKey();
            Object obj = tempMap.get(key);
            list2.add(obj);
            tempMap.remove(key);
        }

        list.clear();
        list.addAll(list2);
        this.fireTableDataChanged();
    }

    private class DTOComparator implements java.util.Comparator {
        String propertyName = "";
        int ascParameter = 1;

        public DTOComparator(String propertyName) {
            this.propertyName = propertyName;
        }
        
        public DTOComparator(String propertyName, boolean asc) {
            this.propertyName = propertyName;
            if(asc) {
            	ascParameter = 1;
            } else {
            	ascParameter = -1;
            }
        }

        public int compare(Object o1, Object o2) {

            int index1 = o1.hashCode();
            int index2 = o2.hashCode();

            try {

                List list = getRows();

                Object value1 = DtoUtil.getProperty((IDto) list.get(index1),
                        propertyName);
                Object value2 = DtoUtil.getProperty((IDto) list.get(index2),
                        propertyName);
                if (value1 == null && value2 == null) {
                    return (index1 - index2);
                } else if (value1 != null && value2 == null) {
                    return 1 * ascParameter;
                } else if (value1 == null && value2 != null) {
                    return -1 * ascParameter;
                } else if(value1 instanceof String) {
                    Collator c = Collator.getInstance(Locale.CHINA);
                    int result = c.compare(value1, value2);
                    if (result == 0) {
                        return (index1 - index2);
                    } else {
                        return result * ascParameter;
                    }
                } else if (value1 instanceof Comparable) {
                    int result = ((Comparable) value1).compareTo((Comparable)
                            value2);
                    if (result == 0) {
                        return (index1 - index2);
                    } else {
                        return result * ascParameter;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return (index1 - index2);
        }

        public boolean equals(Object obj) {
            return false;
        }

    }
}
