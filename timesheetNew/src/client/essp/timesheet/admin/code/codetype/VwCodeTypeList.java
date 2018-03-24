package client.essp.timesheet.admin.code.codetype;

import java.util.List;

import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.code.DtoCodeType;
import client.essp.timesheet.admin.common.VwListBase;
import com.wits.util.Parameter;

public class VwCodeTypeList extends VwListBase {
    private static final String actionIdListCodeTypes = "FTSListCodeType";
    private static final String actionIdUpMove = "FTSUpMoveCodeType";
    private static final String actionIdDownMove = "FTSDownMoveCodeType";
    private static final String actionIdDelete = "FTSDeleteCodeType";
    /**
     * 下移
     */
    protected void actionPerformedDown() {
        DtoCodeType data = (DtoCodeType) this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index + 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdDownMove);
        inputInfo.setInputObj(DtoCodeType.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoCodeType nextData = (DtoCodeType) rows.get(nextIndex);
            //交换Seq
            Long nextSeq = new Long(nextData.getSeq());
            nextData.setSeq(new Long(data.getSeq()));
            data.setSeq(nextSeq);

            //交换节点
            rows.remove(index);
            rows.add(nextIndex, data);
            this.getModel().fireTableDataChanged();
            this.getTable().setSelectRow(nextIndex);
            actionPerformedBtnStatus();
        }
    }

    /**
     * 上移
     */
    protected void actionPerformedUp() {
        DtoCodeType data = (DtoCodeType) this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index - 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpMove);
        inputInfo.setInputObj(DtoCodeType.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoCodeType nextData = (DtoCodeType) rows.get(nextIndex);
            //交换Seq
            Long nextSeq = new Long(nextData.getSeq());
            nextData.setSeq(new Long(data.getSeq()));
            data.setSeq(nextSeq);

            //交换节点
            rows.remove(index);
            rows.add(nextIndex, data);
            this.getModel().fireTableDataChanged();
            this.getTable().setSelectRow(nextIndex);
            actionPerformedBtnStatus();
        }
    }

    /**
     * 激活刷新
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        isLeaveType = (Boolean) param.get(DtoCodeType.DTO_IS_LEAVE_TYPE);
    }

    protected IDto getNewDto() {
        DtoCodeType dto = new DtoCodeType();
        dto.setIsEnable(true);
        dto.setIsLeaveType(isLeaveType);
        return dto;
    }

    protected String getDeleteActionId() {
        return actionIdDelete;
    }

    protected String getListActionId() {
        return actionIdListCodeTypes;
    }

    protected String getDtoKey() {
        return DtoCodeType.DTO;
    }

    protected String getListKey() {
        return DtoCodeType.DTO_LIST;
    }

}
