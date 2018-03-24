package server.essp.timesheet.activity.notebook.service;

import server.framework.common.BusinessException;
import java.util.List;
import server.essp.timesheet.activity.notebook.dao.INoteBookDao;

/**
 * <p>Description:NoteBookService的实现 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class NoteBookServiceImp implements INoteBookService {
     private INoteBookDao inoteBookDao;
     /**
      * 根据activityRid查询出符合条件的记录
      * @param activityRid Long
      * @return List
      * @throws Exception
      */
    public List getNoteBookList(Long activityRid) {
          List noteBookList = null;
          try {
            noteBookList = inoteBookDao.queryNoteBook(activityRid);
           }catch (Exception ex) {
            throw new BusinessException("error.logic.NoteBookServiceImp.loadNoteBookErr",
                                        "load notebook list error", ex);
        }
          return noteBookList;
    }

    public void setInoteBookDao(INoteBookDao inoteBookDao) {
        this.inoteBookDao = inoteBookDao;
    }

}
