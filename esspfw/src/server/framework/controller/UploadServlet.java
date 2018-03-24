package server.framework.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import c2s.dto.FileInfo;
import server.framework.common.BusinessException;
import server.framework.logic.LgUpload;
import com.wits.util.Config;

public class UploadServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=GBK";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
        process(request, response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
        process(request, response);
    }

    public void process(HttpServletRequest request,
                        HttpServletResponse response) throws
        ServletException, IOException {
        InputStream in = request.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        /**
         * ��ȡ�ļ������ļ�������Ϣ
         */
        FileInfo fileInfo = getFileInfo(ois);
        StringBuffer sInfo = new StringBuffer("");

        /**
         * У���ļ���Ϣ
         */
        if (!validateFile(fileInfo, sInfo)) {
            throw new BusinessException("UPLOAD", sInfo.toString());
        }

        /**
         * ���ɱ����ļ���(�ļ�ID)
         */
        LgUpload logic = new LgUpload();
        logic.getFileId(fileInfo);

        String path = getWebRoot() + fileInfo.getFileRoot();
        String localPath = fileInfo.getServerPath(path);
        String localFile = fileInfo.getServerFullPath(path);
        File file = new File(localPath);
        file.mkdirs();

        /**
         * �����ļ�
         */
        transFile(ois, localFile, fileInfo.getLength());

        /**
         * �������ݿ�
         */
        logic.upload(fileInfo);

        /**
         * �����ļ������Ϣ���ͻ���
         */
        OutputStream out = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(fileInfo);
        oos.flush();
        ois.close();
        oos.close();
    }

    private void transFile(InputStream in, String filename, long length) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            int iRtn = 0;
            long hasReceived = 0;
            byte[] bytes = new byte[1024];
            long maxlength = 1024;
            if (length - hasReceived < 1024) {
                maxlength = length - hasReceived;
            }


            while ((iRtn = in.read(bytes, 0, (int) maxlength)) != -1) {
                fos.write(bytes, 0, iRtn);
                hasReceived = hasReceived + iRtn;

                /**
                 * ����Ѿ������ļ������˳�
                 */
                if (hasReceived == length) {
                    break;
                }

                if (length - hasReceived < 1024) {
                    maxlength = length - hasReceived;
                } else {
                    maxlength = 1024;
                }
            }
            fos.flush();
            fos.close();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

    private FileInfo getFileInfo(ObjectInputStream in) {
        try {
            FileInfo fileInfo = (FileInfo) in.readObject();
            return fileInfo;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

    private String getWebRoot() {

        //like: "file:/D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/"
        String classRoot=this.getClass().getResource("/").toString();

        //like: "D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/../../"
        String webRoot = classRoot.substring(6)+"../../";

        return webRoot;
    }

    /**
     * У���ļ���Ϣ
     * @param fileInfo FileInfo
     * @return boolean
     */
    private boolean validateFile(FileInfo fileInfo, StringBuffer msg) {
        if (fileInfo == null) {
            msg.append("File information not exist!!!");
            return false;
        }

        if (fileInfo.getFilename() == null) {
            msg.append("File's name are null!!!");
            return false;
        }

        if (fileInfo.getFilename().trim().equals("")) {
            msg.append("File's name are empty!!!");
            return false;
        }

        if (fileInfo.getLength() <= 0) {
            msg.append("File's length not available : length=" +
                       fileInfo.getLength());
            return false;
        }

        return true;
    }

    //Clean up resources
    public void destroy() {
    }
}
