package server.essp.pms.pbs.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import c2s.essp.pms.pbs.DtoPmsPbs;
import db.essp.pms.PbsPK;
import db.essp.pms.Pbs;
import server.framework.common.BusinessException;
import java.util.Iterator;
import com.wits.util.StringUtil;

public class LgPmsPbsMove extends AbstractESSPLogic {

    public void leftMove(DtoPmsPbs dataBean) {
        try {
            checkParameter(dataBean);

            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs pbs = (Pbs)this.getDbAccessor().load(Pbs.class, pk);

            Pbs parent = pbs.getParent();
            if (parent != null) {
                Pbs gradFather = parent.getParent();
                if (gradFather != null) {
                    int index = gradFather.getChilds().indexOf(parent);
                    parent.getChilds().remove(pbs);
                    gradFather.getChilds().add(index + 1, pbs);
                    pbs.setParent(gradFather);
                    dataBean.setPbsParentRid(gradFather.getPk().getPbsRid());
                    this.getDbAccessor().update(pbs);
                    this.getDbAccessor().update(parent);
                    this.getDbAccessor().update(gradFather);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when move left.", ex);
        }
    }

    public void rightMove(DtoPmsPbs dataBean) {
        try {
            checkParameter(dataBean);

            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs pbs = (Pbs)this.getDbAccessor().load(Pbs.class, pk);

            Pbs parent = pbs.getParent();
            if (parent != null) {
                int index = parent.getChilds().indexOf(pbs);
                if (index - 1 >= 0) {
                    Pbs brother = (Pbs) parent.getChilds().get(index - 1);
                    if (brother != null) {
                        parent.getChilds().remove(pbs);
                        brother.getChilds().add(pbs);
                        pbs.setParent(brother);
                        dataBean.setPbsParentRid(brother.getPk().getPbsRid());
                        this.getDbAccessor().update(pbs);
                        this.getDbAccessor().update(parent);
                        this.getDbAccessor().update(brother);
                    }
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when move right.", ex);
        }
    }

    public void upMove(DtoPmsPbs dataBean) {
        try {
            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs pbs = (Pbs)this.getDbAccessor().load(Pbs.class, pk);

            Pbs parent = pbs.getParent();
            if (parent != null) {
                int index = parent.getChilds().indexOf(pbs);
                if (index - 1 >= 0) {

                    parent.getChilds().remove(pbs);
                    parent.getChilds().add(index - 1, pbs);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when up right.", ex);
        }
    }

    public void downMove(DtoPmsPbs dataBean) {
        try {
            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs pbs = (Pbs)this.getDbAccessor().load(Pbs.class, pk);

            Pbs parent = pbs.getParent();
            if (parent != null) {
                int index = parent.getChilds().indexOf(pbs);
                if (index + 1 < parent.getChilds().size()) {

                    parent.getChilds().remove(pbs);
                    parent.getChilds().add(index + 1, pbs);
                    this.getDbAccessor().update(parent);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when down right.", ex);
        }
    }


    private void checkParameter(DtoPmsPbs dataBean) {
        if (dataBean.getPbsParentRid() == null) {
            return;
        }

        String code = StringUtil.nvl(dataBean.getProductCode());
        Long pbsRid = dataBean.getPbsRid();
        PbsPK parentPk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsParentRid());
        try {
            Pbs parent = (Pbs) getDbAccessor().load(Pbs.class, parentPk);
            if (parent != null) {
                for (Iterator it = parent.getChilds().iterator(); it.hasNext(); ) {
                    Pbs child = (Pbs) it.next();
                    if (code.equals(child.getProductCode()) == true) {
                        if (pbsRid == null || pbsRid.equals(child.getPk().getPbsRid()) == false) {
                            throw new BusinessException("E000", "The production code is exist.");
                        }
                    }
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when check data.", ex);
        }
    }


}
