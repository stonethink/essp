package server.essp.issue.common.logic;

import org.apache.struts.upload.FormFile;
import server.framework.common.BusinessException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import c2s.dto.FileInfo;
import java.io.File;
import java.util.ResourceBundle;
import java.util.Locale;

public class LgUpload extends server.framework.logic.LgUpload {
    private static String SERVER_ROOT="";

    /**
     * 仅用于测试，在正式版中SERVER_ROOT信息应从property文件中读取
     */
    public LgUpload() {
        if(SERVER_ROOT==null || SERVER_ROOT.trim().equals("")) {
            String root = server.essp.issue.common.logic.LgUpload.class.
                          getResource("/").toString();
            root = root.replace('\\', '/');
            int index = root.lastIndexOf("/");
            root = root.substring(0, index);
            index = root.lastIndexOf("/");
            root = root.substring(0, index);
            index = root.lastIndexOf("/");
            root = root.substring(0, index);
            root = root.substring(6);
            SERVER_ROOT = root + "/../attachmentFiles";

            try {
                ResourceBundle resources = ResourceBundle.getBundle(
                    "fileserver", Locale.getDefault());
                String realDir = resources.getString("real_directory");
                if (realDir != null && !realDir.trim().equals("")) {
                    /**
                     * validate realDir
                     */

                    try {
                        File file = new File(realDir);
                        if (file.exists() && file.isDirectory()) {
                            SERVER_ROOT = realDir;
                        }
                    } catch (Exception ex) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public FileInfo initFileInfo(FormFile fileFrom,FileInfo fileInfo) {
        String fileName=fileFrom.getFileName();
        fileInfo.setFilename(fileName);
        fileInfo.setLength(fileFrom.getFileSize());
        fileInfo.setClientFullPath("");
        fileInfo.setModulename("ISSUE");
        return fileInfo;
    }

    public FileInfo upload(FormFile fileFrom,FileInfo fileInfo) {
        try {
            if(fileInfo.getId()==null || fileInfo.getId().trim().equals("")) {
                super.getFileId(fileInfo);
            }

            String size = (fileFrom.getFileSize() + " bytes");
            log.info("file size:" + size);
            InputStream stream = fileFrom.getInputStream();
            //write the file to the file specified
            String filePath=fileInfo.getServerPath(SERVER_ROOT);
            File file=new File(filePath);
            file.mkdirs();
            String fullPath=fileInfo.getServerFullPath(SERVER_ROOT);

            OutputStream bos = new FileOutputStream(fullPath);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ( (bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            log.info("The file has been written to <" + fullPath + ">");
            //close the stream
            stream.close();

            super.upload(fileInfo);

        } catch(Exception e) {
            throw new BusinessException("Upload File Error",e.getMessage(),e);
        }
        return fileInfo;
    }

    public static void main(String args[]) {
        new LgUpload();
    }
}
