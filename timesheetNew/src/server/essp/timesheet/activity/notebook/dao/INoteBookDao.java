package server.essp.timesheet.activity.notebook.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import java.util.List;
import c2s.essp.timesheet.activity.DtoNotebookTopic;

/**
 * <p>Description:NoteBookDao的接口 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface INoteBookDao {
    /**
     * 根据activityRid查询出符合条件的记录
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
    public List queryNoteBook(Long activityRid) throws Exception;

}
