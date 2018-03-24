
package server.essp.projectpre.ui.project.apply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;

import com.wits.util.comDate;
/**
 * 上传邮件附件
 * @author wenhaizheng
 *
 */
public class AcUpload extends AbstractESSPAction {
	/**
	 * 上传邮件附件
	 *    1.获取当前系统时间
	 *    2.在文件名中加入时间字符串
	 *    3.上传文件
	 * @param request
	 * @param response
	 * @param data
	 * @throws BusinessException
	 */
    public void executeAct(HttpServletRequest request, HttpServletResponse response,
            TransactionData data) throws BusinessException {
      try{
        this.getRequest().getSession().removeAttribute("uploadPre");
        AfUpload af = (AfUpload)this.getForm();
        String   dir=servlet.getServletContext().getRealPath("/upload"); 
//        dir="d://test2";
        FormFile   file=af.getFile(); 
        String dateTime = comDate.dateToString(new Date(), "yyyyMMddHHmmss");
        String fname=file.getFileName();
        InputStream streamIN = null;
        OutputStream streamOUT = null;
       if(fname!=null&&!"".equals(fname)){
        String newName = fname.substring(0, fname.lastIndexOf("."));
        String newName1 = fname.substring(fname.lastIndexOf(".")+1);
        fname = newName+"_"+dateTime+"."+newName1;
//        String   fsize   =   Integer.toString(file.getFileSize())   +   "   bytes";   
        File myDir = new File(dir);
        streamIN = file.getInputStream();   
//        OutputStream streamOUT = new   FileOutputStream(dir+"/"+fname);  
        myDir.mkdirs();
        File myFile = new File(myDir,fname);
        streamOUT = new   FileOutputStream(myFile);
        int bytesread = 0;   
        byte[] buffer = new   byte[8192];   
        while((bytesread=streamIN.read(buffer,0,8192)) != -1){   
            streamOUT.write(buffer, 0, bytesread);   
        }   
        this.getSession().setAttribute("attachmentPath", myFile.getPath());
        streamIN.close();   
        streamOUT.close();    
        file.destroy();  
       } else {
           this.getSession().setAttribute("attachmentPath", "");
       }
       String[] otherPerson = null;
       String[] otherPersonId = null;
       String[] otherPersonDomain = null;
       if(af.getOtherPerson()!=null&&!"".equals(af.getOtherPerson())){
           otherPerson = af.getOtherPerson().split(",");
       }
       if(af.getOtherPersonId()!=null&&!"".equals(af.getOtherPersonId())){
           otherPersonId = af.getOtherPersonId().split(",");
       }
       if(af.getOtherPersonDomain()!=null&&!"".equals(af.getOtherPersonDomain())){
           otherPersonDomain = af.getOtherPersonDomain().split(",");
       }
       this.getSession().setAttribute("otherPerson", otherPerson);
       this.getSession().setAttribute("otherPersonId", otherPersonId);
       this.getSession().setAttribute("otherPersonDomain", otherPersonDomain);
      }catch(Exception e){
          throw new BusinessException(e);
      }

    }

}
