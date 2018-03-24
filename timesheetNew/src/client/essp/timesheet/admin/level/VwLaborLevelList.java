package client.essp.timesheet.admin.level;

import c2s.dto.IDto;
import client.essp.timesheet.admin.common.VwListBase;
import c2s.dto.ReturnInfo;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;

/**
 * <p>Title: VwLaborLevelList</p>
 *
 * <p>Description: Labor level list card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborLevelList extends VwListBase {

    private final static String actionIdList = "FTSListLaborLevel";
    private static final String actionIdUpMove = "FTSUpMoveLaborLevel";
    private static final String actionIdDownMove = "FTSDownMoveLaborLevel";
    private static final String actionIdDelete = "FTSDeleteLaborLevel";

    /**
     * 下移
     */
    protected void actionPerformedDown() {
        DtoLaborLevel data = (DtoLaborLevel)this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index + 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdDownMove);
        inputInfo.setInputObj(DtoLaborLevel.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoLaborLevel nextData = (DtoLaborLevel) rows.get(nextIndex);
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
        DtoLaborLevel data = (DtoLaborLevel)this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index - 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpMove);
        inputInfo.setInputObj(DtoLaborLevel.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoLaborLevel nextData = (DtoLaborLevel) rows.get(nextIndex);
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
     * 获取删除数据的ActionId
     *
     * @return String
     */
    protected String getDeleteActionId() {
        return actionIdDelete;
    }

    /**
     * 获取传递Dto的Key
     *
     * @return String
     */
    protected String getDtoKey() {

        return DtoLaborLevel.DTO;
    }

    /**
     * 获取更新列表的ActionId
     *
     * @return String
     */
    protected String getListActionId() {
        return actionIdList;
    }

    /**
     * 获取传递List的 Key
     *
     * @return String
     */
    protected String getListKey() {
        return DtoLaborLevel.DTO_LIST;
    }

    /**
     * 获取一条新数据。
     *
     * @return IDto
     */
    protected IDto getNewDto() {
        DtoLaborLevel dto = new DtoLaborLevel();
        dto.setIsEnable(true);
        return dto;
    }
}
