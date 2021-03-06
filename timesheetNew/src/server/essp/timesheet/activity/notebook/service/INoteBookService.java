package server.essp.timesheet.activity.notebook.service;

import java.util.List;
import c2s.essp.timesheet.activity.DtoNotebookTopic;

/**
 * <p>Description: NoteBookService的接口</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface INoteBookService {
    /**
     * 根据activityRid查询出符合条件的记录
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
    public List getNoteBookList(Long activityRid);

}
