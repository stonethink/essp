package server.essp.pms.pbs.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import db.essp.pms.PbsFiles;
import db.essp.pms.PbsFilesPK;
import net.sf.hibernate.Session;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBSeq;
import db.essp.pms.PbsPK;
import db.essp.pms.Pbs;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgPmsPbsFilesList extends AbstractESSPLogic {

    public List list(Long acntRid, Long pbsRid) throws BusinessException {

        List fileList = new ArrayList();
        try {
            String selSql = "from db.essp.pms.PbsFiles t "
                            + " where t.pk.acntRid=:acntRid and t.pk.pbsRid=:pbsRid order by t.fileName";
            Session session = this.getDbAccessor().getSession();
            List result = session.createQuery(selSql)
                          .setLong("acntRid", acntRid.longValue())
                          .setLong("pbsRid", pbsRid.longValue())
                          .list();
            for (int i = 0; i < result.size(); i++) {
                PbsFiles file = (PbsFiles) result.get(i);
                DtoPmsPbsFiles dto = new DtoPmsPbsFiles();

                try {
                    DtoUtil.copyProperties(dto, file);
                    dto.setAcntRid(file.getPk().getAcntRid());
                    dto.setPbsRid(file.getPk().getPbsRid());
                    dto.setFilesRid(file.getPk().getFilesRid());
                    fileList.add(dto);
                } catch (Exception ex1) {
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select from PbsFiles error.");
        }

        return fileList;
    }

    public void update(List fileList) throws BusinessException {
        try {

            for (int i = 0; i < fileList.size(); i++) {
                DtoPmsPbsFiles dtoPmsPbsFiles = (DtoPmsPbsFiles) fileList.get(i);

                if (dtoPmsPbsFiles.isInsert()) {
                    Long rid = new Long( HBSeq.getSEQ("pms_pbs_files") );
                    dtoPmsPbsFiles.setFilesRid(rid);

                    PbsFiles pmsPbsFiles = new PbsFiles();
                    copyPbsFile(pmsPbsFiles, dtoPmsPbsFiles);

                    getDbAccessor().save(pmsPbsFiles);

                    dtoPmsPbsFiles.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPmsPbsFiles.isDelete()) {

                    fileList.remove(i);
                    i--;
                } else if (dtoPmsPbsFiles.isModify()) {
                    PbsFilesPK pmsPbsFilesPK = new PbsFilesPK(
                        dtoPmsPbsFiles.getAcntRid()
                        , dtoPmsPbsFiles.getPbsRid()
                        , dtoPmsPbsFiles.getFilesRid());
                    PbsFiles pmsPbsFiles = (PbsFiles) getDbAccessor().load(PbsFiles.class, pmsPbsFilesPK);

                    copyPbsFile(pmsPbsFiles, dtoPmsPbsFiles);

                    getDbAccessor().update(pmsPbsFiles);

                    dtoPmsPbsFiles.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Update PbsFiles error.");
        }
    }

    public void copyPbsFile(PbsFiles dbBean, DtoPmsPbsFiles dataBean) throws Exception {
        try {
            DtoUtil.copyProperties(dbBean, dataBean);
        } catch (Exception ex) {
        }

        PbsFilesPK pk = new PbsFilesPK(dataBean.getAcntRid()
                                       , dataBean.getPbsRid()
                                       , dataBean.getFilesRid());
        dbBean.setPk(pk);

        PbsPK pbsPK = new PbsPK(dataBean.getAcntRid()
                                , dataBean.getPbsRid());
        Pbs pbs = (Pbs) getDbAccessor().load(Pbs.class, pbsPK);
        dbBean.setPbs(pbs);
    }

}
