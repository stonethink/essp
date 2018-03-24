package server.framework.controller;

import c2s.dto.FileInfo;
import server.framework.common.BusinessException;
import server.framework.logic.LgDownload;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;
import com.wits.util.Config;

public class DownloadServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=GBK";
    Config config = null;

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
         * ��ȡ�ļ���Ϣ
         */
        FileInfo fileInfo = getFileInfo(ois);

        /**
         * �����ļ���Ϣ
         */
        OutputStream out = response.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(fileInfo);
        oos.flush();

        /**
         * ��ͻ��˴����ļ�����
         */
        transFile(oos, fileInfo);

        /**
         * ���������
         */
        ois.close();
        in.close();
        oos.close();
        out.close();
    }

    private void transFile(OutputStream out, FileInfo info) {
        try {
            /**
             * ��֯���������ļ���
             */
            String filename = info.getServerFullPath(getWebRoot()+info.getFileRoot());

            FileInputStream fileInputStream = new FileInputStream(filename);
            int iRtn = 0;
            byte[] bytes = new byte[1024];

            while ((iRtn = fileInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, iRtn);
            }
            out.flush();
            fileInputStream.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getWebRoot() {

        //like: "file:/D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/"
        String classRoot=this.getClass().getResource("/").toString();

        //like: "D:/essp/essp2005/essp2/esspruntime/web/WEB-INF/classes/../../"
        String webRoot = classRoot.substring(6)+"../../";

        return webRoot;
    }

    private FileInfo getFileInfo(ObjectInputStream in) {
        try {
            /**
             * ��Client��ȡ���ļ���Ϣ
             */
            FileInfo fileInfo = (FileInfo) in.readObject();
            if (fileInfo.getId() == null) {
                throw new BusinessException("DOWNLOAD", "File ID is null");
            }
            if (fileInfo.getId().trim().equals("")) {
                throw new BusinessException("DOWNLOAD", "File ID is empty");
            }

            LgDownload logic = new LgDownload();
            logic.getFileInfo(fileInfo);

            /**
             * ��֯���������ļ���
             */
            String filename = fileInfo.getServerFullPath(getWebRoot()+fileInfo.getFileRoot());

            /**
             * �����ļ�����
             */
            File file = new File(filename);
            fileInfo.setLength(file.length());

            return fileInfo;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    //Clean up resources
    public void destroy() {
    }
}
