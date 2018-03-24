package server.essp.timesheet.activity.notebook.dao;

import server.essp.common.primavera.PrimaveraApiBase;
import java.util.List;
import c2s.essp.timesheet.activity.DtoNotebookTopic;

/**
 * <p>Description:NoteBookDao�Ľӿ� </p>
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
     * ����activityRid��ѯ�����������ļ�¼
     * @param activityRid Long
     * @return List
     * @throws Exception
     */
    public List queryNoteBook(Long activityRid) throws Exception;

}
