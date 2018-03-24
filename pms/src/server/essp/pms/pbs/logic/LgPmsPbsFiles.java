package server.essp.pms.pbs.logic;

import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import db.essp.pms.PbsFiles;
import db.essp.pms.PbsFilesPK;
import server.framework.common.BusinessException;
import db.essp.pms.Pbs;
import db.essp.pms.PbsPK;
import c2s.dto.DtoUtil;
import server.framework.hibernate.HBSeq;
import server.essp.framework.logic.AbstractESSPLogic;

public class LgPmsPbsFiles extends AbstractESSPLogic {
    public void delete(DtoPmsPbsFiles dtoPmsPbsFiles) throws
        BusinessException {
        try {
            PbsFilesPK pmsPbsFilesPK = new PbsFilesPK(
                dtoPmsPbsFiles.getAcntRid()
                , dtoPmsPbsFiles.getPbsRid()
                , dtoPmsPbsFiles.getFilesRid());

            PbsFiles file = new PbsFiles();
            file.setPk(pmsPbsFilesPK);

            getDbAccessor().delete(file);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete file error.", ex);
        }
    }

    public void add(DtoPmsPbsFiles dtoPmsPbsFiles)  {
        try {
            Long rid = new Long(HBSeq.getSEQ("pms_pbs_files"));
            dtoPmsPbsFiles.setFilesRid(rid);
            PbsFiles file = createPbsFile(dtoPmsPbsFiles);

            getDbAccessor().save(file);
        }catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Add file error.", ex);
        }
    }

    public void updateOne(DtoPmsPbsFiles dtoPmsPbsFiles)  {
        try {
            PbsFiles file = createPbsFile(dtoPmsPbsFiles);

            getDbAccessor().update(file);
        }catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Update file error.", ex);
        }
    }

    private PbsFiles createPbsFile(DtoPmsPbsFiles dataBean) throws Exception {
        PbsFiles dbBean = new PbsFiles();

        DtoUtil.copyProperties(dbBean, dataBean);

        PbsFilesPK pk = new PbsFilesPK(dataBean.getAcntRid()
                                       , dataBean.getPbsRid()
                                       , dataBean.getFilesRid());
        dbBean.setPk(pk);

        PbsPK pbsPK = new PbsPK(dataBean.getAcntRid()
                                , dataBean.getPbsRid());
        Pbs pbs = (Pbs) getDbAccessor().load(Pbs.class, pbsPK);
        dbBean.setPbs(pbs);

        return dbBean;
    }

}
