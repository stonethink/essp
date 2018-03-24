package client.essp.pwm.workbench.workitem;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import c2s.dto.IDto;
import c2s.essp.pwm.workbench.DtoPwWkitem;
import client.framework.common.Global;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJTable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import c2s.essp.pwm.PwmUtil;

public class VMTableWorkItem extends VMTable2{
    private Date wkitemDate = null;
    VWJTable table = null;

    /**
     * 存放最后一条数据，这条数据不在model的dtoList中
     * 在getValueAt时会自动new一个
     */
    DtoPwWkitem newDto = null;

    public VMTableWorkItem(Object[][] configs) {
        super(configs);
    }

    public void setTable(VWJTable table){
        this.table = table;
    }


    public void setRows(List workItemList) {
        if(workItemList == null)
            return;
        //处理workItemList中有op='delete'的情况
        List tempList=new ArrayList();
        List deleteList = new ArrayList();
        for (Iterator iter = workItemList.iterator(); iter.hasNext(); ) {
            DtoPwWkitem item = (DtoPwWkitem) iter.next();
            if (item.isDelete() == true) {
                deleteList.add(item);
            } else {
                tempList.add(item);
            }
        }

        super.setRows(tempList);
        for (Iterator iter = deleteList.iterator(); iter.hasNext(); ) {
            Object deleteWkitem = (Object) iter.next();
            getDtoList().add(deleteWkitem);
        }
    }


    public Class getColumnClass(int col){
        if( col == 1 ){
            return Calendar[].class;
        }else{
            return super.getColumnClass(col);
        }
    }

    public int getRowCount(){
        return super.getRows().size()+1;
    }

    public void setValueAt(Object value, int row, int col){
       if( row == this.getRows().size() && newDto != null){

           //自动加一行
           newDto.setOp(IDto.OP_INSERT);
           getRows().add(newDto);
           getDtoList().add(newDto);
           newDto = null;

           //保证新增行的前后选中同一行
           int i = table.getSelectedRow();
           fireTableRowsInserted(row, row);
           table.setSelectRow(i);
       }

        //设置开始时间和结束时间
        if( col == 1 ){
            DtoPwWkitem dto = (DtoPwWkitem)this.getRow(row);

            Calendar dataRange[] = (Calendar[])value;
            setTimeRange(dto, dataRange);
            this.fireTableRowsUpdated(row, row);
        }else{
            super.setValueAt(value, row, col);
        }
    }

    public Object getValueAt(int row, int col){
        if( row == this.getRows().size() ){

            //最后一列的值不在dtoList中
            if( newDto == null ){
                newDto = createDefaultPwWkitem();
            }

            if( col == 0 ){
                return newDto.getWkitemName();
            }else if( col == 1 ){
                return getTimeRange(newDto);
            }else if( col == 2 ){
                return newDto.getWkitemWkhours();
            }else{
                return newDto.getWkitemBelongto();
            }
        }else{

            if( col == 1 ){

                return getTimeRange((DtoPwWkitem)this.getRow(row));
            }else{
                return super.getValueAt(row, col);
            }
        }
    }

    public int[] getMinStartAndMaxFinish(){
        int min = 8;
        int max = 19;
        for (int i = 0; i < this.getRows().size(); i++) {
            DtoPwWkitem dto = (DtoPwWkitem)this.getRow(i);
            Date start = dto.getWkitemStarttime();
            Date finish = dto.getWkitemFinishtime();
            if( start != null ){
                int s = start.getHours();
                min = Math.min(min, s);
            }

            if( finish != null ){
                int f = finish.getHours();
                if (start.getDate() != finish.getDate()) {
                    if (f == 0 && finish.getMinutes() == 0
                        ) {
                        max = 23;
                    }else{
                        max = Math.max(max, f);
                    }
                }else{
                    max = Math.max(max, f);
                }
            }
        }

        return new int[]{min, max};
    }

    public DtoPwWkitem getTheNewData(){
        return newDto;
    }

    private Calendar[] getTimeRange(DtoPwWkitem dto) {

        Calendar[] ret = new Calendar[2];
        ret[0] = Calendar.getInstance();
        if( dto != null && dto.getWkitemStarttime() != null ){
            ret[0].setTime(dto.getWkitemStarttime());
        }else{
            ret[0] = c2s.essp.pwm.PwmUtil.getMinHMS();
        }

        ret[1] = Calendar.getInstance();
        if( dto != null && dto.getWkitemFinishtime() != null){
            ret[1].setTime( dto.getWkitemFinishtime() );
        } else {
            ret[1] = PwmUtil.getMinHMS();
        }

        //System.out.print("range: " + comDate.dateToString(ret[0].getTime(), "yyyyMMdd HH:mm:ss"));
        //System.out.println(" -- " + comDate.dateToString(ret[1].getTime(), "yyyyMMdd HH:mm:ss"));

        return ret;
    }

    private void setTimeRange(DtoPwWkitem dto, Calendar[] timeRange){
        dto.setWkitemStarttime( timeRange[0].getTime() );
        dto.setWkitemFinishtime( timeRange[1].getTime() );
        dto.setWkitemWkhours(PwmUtil.computeWorkHours(
            dto.getWkitemStarttime()
            , dto.getWkitemFinishtime()));
        dto.setOp(IDto.OP_MODIFY);
    }

    private DtoPwWkitem createDefaultPwWkitem(){
        DtoPwWkitem dto = new DtoPwWkitem();
        dto.setRid(null);
        dto.setProjectId(null);
        dto.setActivityId(null);
        dto.setWpId(null);
        dto.setWkitemOwner(Global.userId);
        dto.setWkitemPlace(null);
        dto.setWkitemBelongto(null);
        //dto.setWkitemName("Please input the name of workitem");
        dto.setWkitemName("");
        dto.setWkitemDate(wkitemDate);

        Calendar minHMS = PwmUtil.getMinHMS();
        minHMS.set(Calendar.HOUR_OF_DAY, 8);
        dto.setWkitemStarttime(minHMS.getTime());
        dto.setWkitemFinishtime(minHMS.getTime());
        dto.setWkitemWkhours(new BigDecimal(0));
        dto.setWkitemIsdlrpt("0");

        return dto;
    }

    public void setWkitemDate(Date wkitemDate){
        this.wkitemDate = wkitemDate;

        if( newDto != null ){
            newDto.setWkitemDate(wkitemDate);
        }
    }
}
