package server.essp.pms.wbs.logic;

import c2s.essp.pms.wbs.DtoWbs;
import c2s.essp.pms.wbs.DtoWbsActivity;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface WbsNodeCopy {
    public DtoWbsActivity copyWBS(DtoWbsActivity srcWbs,DtoWbsActivity desActivity);
}
