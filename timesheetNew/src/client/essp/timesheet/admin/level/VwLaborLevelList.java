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
     * ����
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
            //����Seq
            Long nextSeq = new Long(nextData.getSeq());
            nextData.setSeq(new Long(data.getSeq()));
            data.setSeq(nextSeq);

            //�����ڵ�
            rows.remove(index);
            rows.add(nextIndex, data);
            this.getModel().fireTableDataChanged();
            this.getTable().setSelectRow(nextIndex);
            actionPerformedBtnStatus();
        }
    }

    /**
     * ����
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
            //����Seq
            Long nextSeq = new Long(nextData.getSeq());
            nextData.setSeq(new Long(data.getSeq()));
            data.setSeq(nextSeq);

            //�����ڵ�
            rows.remove(index);
            rows.add(nextIndex, data);
            this.getModel().fireTableDataChanged();
            this.getTable().setSelectRow(nextIndex);
            actionPerformedBtnStatus();
        }
    }


    /**
     * ��ȡɾ�����ݵ�ActionId
     *
     * @return String
     */
    protected String getDeleteActionId() {
        return actionIdDelete;
    }

    /**
     * ��ȡ����Dto��Key
     *
     * @return String
     */
    protected String getDtoKey() {

        return DtoLaborLevel.DTO;
    }

    /**
     * ��ȡ�����б��ActionId
     *
     * @return String
     */
    protected String getListActionId() {
        return actionIdList;
    }

    /**
     * ��ȡ����List�� Key
     *
     * @return String
     */
    protected String getListKey() {
        return DtoLaborLevel.DTO_LIST;
    }

    /**
     * ��ȡһ�������ݡ�
     *
     * @return IDto
     */
    protected IDto getNewDto() {
        DtoLaborLevel dto = new DtoLaborLevel();
        dto.setIsEnable(true);
        return dto;
    }
}
