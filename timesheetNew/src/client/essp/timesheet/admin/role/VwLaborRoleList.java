package client.essp.timesheet.admin.role;

import c2s.dto.ReturnInfo;
import java.util.List;
import client.essp.timesheet.admin.common.VwListBase;
import c2s.dto.InputInfo;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import c2s.dto.IDto;

/**
 * <p>Title: VwLaborRoleList</p>
 *
 * <p>Description: Labor role list card</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborRoleList extends VwListBase {

    private final static String actionIdList = "FTSListLaborRole";
    private static final String actionIdUpMove = "FTSUpMoveLaborRole";
    private static final String actionIdDownMove = "FTSDownMoveLaborRole";
    private static final String actionIdDelete = "FTSDeleteLaborRole";

    /**
     * ����
     */
    protected void actionPerformedDown() {
        DtoLaborRole data = (DtoLaborRole)this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index + 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdDownMove);
        inputInfo.setInputObj(DtoLaborRole.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoLaborRole nextData = (DtoLaborRole) rows.get(nextIndex);
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
        DtoLaborRole data = (DtoLaborRole)this.getSelectedData();
        int index = this.getTable().getSelectedRow();
        int nextIndex = index - 1;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdUpMove);
        inputInfo.setInputObj(DtoLaborRole.DTO, data);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (!returnInfo.isError()) {
            List rows = this.getModel().getRows();
            DtoLaborRole nextData = (DtoLaborRole) rows.get(nextIndex);
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

        return DtoLaborRole.DTO;
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
        return DtoLaborRole.DTO_LIST;
    }

    /**
     * ��ȡһ�������ݡ�
     *
     * @return IDto
     */
    protected IDto getNewDto() {
        DtoLaborRole dto = new DtoLaborRole();
        dto.setIsEnable(true);
        return dto;
    }

}
