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
     * 如果appStauts == "Approved"，则将项目的基线计划作为历史版本导出
     * @param accountBaseline AccountBaseline
     * @todo Implement this
     *   server.essp.pms.account.baseline.logic.BaseLineApprovedListener
     *   method
     */
    public String exportExcel(AccountBaseline accountBaseline) {

        try{
            String baselineId = accountBaseline.getBaselineId();
            String _outfileName=outfileName+baselineId+".xls";

            //生成文件信息info
            LgAccountUtilImpl lgAccountUtilImpl=new LgAccountUtilImpl();
            IDtoAccount account=lgAccountUtilImpl.getAccountByRID(accountBaseline.getRid());
            String accountCode=account.getId();


            //导出文件的路径为：upload/{modulename}/{accountCode},比如:essp/upload/pms/002645W
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

           //获取file id
           LgUpload lgUpload=new LgUpload();
           info=lgUpload.getFileId(info);
           //第一个参数为目录，第二个为文件名：形如F00000001.xls
//           AccountBaseLineExport baselineExport = new AccountBaseLineExport(
//                outDir,StringUtil.nvl(info.getId())+".xls");
//
//            Parameter parameter = new Parameter();
//            parameter.put(DtoTcKey.ACNT_RID, accountBaseline.getRid());
            //把审核的BaseLine导出为excel文件，文件名为Baseline_History_+{baselineId}.xls
//            baselineExport.export(parameter);


          //把fileInfo中的id和导出文件名合并（id@文件名）更新到pms_acnt_baseline_log表
          String fileLink=StringUtil.nvl(info.getId())+"@"+_outfileName;

        return fileLink;

        }catch(Exception ex){
           log.error(ex);
           throw new BusinessException("export baseline error",ex);
        }

    }
}
