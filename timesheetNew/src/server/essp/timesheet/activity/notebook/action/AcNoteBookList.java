package server.essp.timesheet.activity.notebook.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import server.essp.timesheet.activity.notebook.service.INoteBookService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:ÏÔÊ¾NoteBookListµÄAction </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcNoteBookList extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        INoteBookService lg = (INoteBookService)this.getBean("iNoteBookService");
        Long activityRid = (Long) inputInfo.getInputObj(DtoActivityKey.DTO_RID);
        List noteBookList = lg.getNoteBookList(activityRid);
        returnInfo.setReturnObj(DtoActivityKey.NOTE_BOOK_LIST,noteBookList); 
    }
}
