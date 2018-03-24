package server.essp.pms.account.baseline.logic;

import db.essp.pms.AccountBaseline;
import c2s.essp.pms.account.DtoAcntBL;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import server.essp.pms.account.logic.AccountBaseLineExport;
import com.wits.util.Parameter;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import c2s.dto.FileInfo;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;
import java.io.File;
import client.framework.common.Global;
import client.net.Upload;
import com.wits.util.StringUtil;
import server.framework.logic.LgUpload;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BaseLineLogExport extends AbstractBusinessLogic
     {

    public static String outfileName="Baseline_History_";
    public static String modulename="PMS";

    /**
     * approveBaseLine
     * ���appStauts == "Approved"������Ŀ�Ļ��߼ƻ���Ϊ��ʷ�汾����
     * @param accountBaseline AccountBaseline
     * @todo Implement this
     *   server.essp.pms.account.baseline.logic.BaseLineApprovedListener
     *   method
     */
    public String exportExcel(AccountBaseline accountBaseline) {

        try{
            String baselineId = accountBaseline.getBaselineId();
            String _outfileName=outfileName+baselineId+".xls";

            //�����ļ���Ϣinfo
            LgAccountUtilImpl lgAccountUtilImpl=new LgAccountUtilImpl();
            IDtoAccount account=lgAccountUtilImpl.getAccountByRID(accountBaseline.getRid());
            String accountCode=account.getId();


            //�����ļ���·��Ϊ��upload/{modulename}/{accountCode},����:essp/upload/pms/002645W
            String fileRoot=FileInfo.getFileRoot();
            String outDir;

            //like: "file:/D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/"
            String classRoot=this.getClass().getResource("/").toString();
            classRoot=classRoot.substring(0,classRoot.length()-2);
            //like: "D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/../../"
            int point1=classRoot.lastIndexOf("/");
            classRoot=classRoot.substring(0,point1);
            int point2=classRoot.lastIndexOf("/");
            classRoot=classRoot.substring(0,point2);
            int point3=classRoot.lastIndexOf("/");
//            String webRoot = classRoot.substring(6)+"../../";
            String webRoot=classRoot.substring(point3+1);
            log.info("webRoot:"+webRoot);

            outDir = webRoot+"/" + fileRoot + "/" + modulename + "/" +
                         accountCode + "/";


            log.info("outDir:"+outDir);
            log.info("outfileName:"+_outfileName);

            FileInfo info=new FileInfo();
            info.setModulename(modulename);
            info.setAccountcode(accountCode);

           //��ȡfile id
           LgUpload lgUpload=new LgUpload();
           info=lgUpload.getFileId(info);
           //��һ������ΪĿ¼���ڶ���Ϊ�ļ���������F00000001.xls
//           AccountBaseLineExport baselineExport = new AccountBaseLineExport(
//                outDir,StringUtil.nvl(info.getId())+".xls");
//
//            Parameter parameter = new Parameter();
//            parameter.put(DtoTcKey.ACNT_RID, accountBaseline.getRid());
            //����˵�BaseLine����Ϊexcel�ļ����ļ���ΪBaseline_History_+{baselineId}.xls
//            baselineExport.export(parameter);


          //��fileInfo�е�id�͵����ļ����ϲ���id@�ļ��������µ�pms_acnt_baseline_log��
          String fileLink=StringUtil.nvl(info.getId())+"@"+_outfileName;

        return fileLink;

        }catch(Exception ex){
           log.error(ex);
           throw new BusinessException("export baseline error",ex);
        }

    }
}
