package client.net;

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import c2s.dto.FileInfo;
import java.io.ObjectInputStream;
import c2s.dto.DtoUtil;
import javax.swing.JFileChooser;
import java.awt.Component;
import client.framework.common.Global;

public class Upload {
    private String serverURL;
    private FileInfo info;
    private File selectedFile=null;

    public Upload() {
        serverURL = Global.appRoot+"/upload";
    }

    public void setFileInfo(FileInfo info) {
        this.info = info;
    }

    public FileInfo getFileInfo() {
        return this.info;
    }

    public boolean showFileDialog(Component parent,String module,String accountCode,String fileId) {
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setSelectedFile(selectedFile);
        int returnVal = fileChooser.showOpenDialog(parent);
        if(returnVal==JFileChooser.APPROVE_OPTION) {
             selectedFile=fileChooser.getSelectedFile();
            if(this.info==null) {
                this.info=new FileInfo();
            }
            info.setModulename(module);
            info.setAccountcode(accountCode);
            info.setId(fileId);
            info.setClientFullPath(selectedFile.getPath());
            info.initLength();
        } else {
            info=null;
        }

        return info!=null;
    }

    public boolean validateFile() {
        if (info == null || info.getFilename()==null) {
            return false;
        }
        try {
            File file = new File(info.getClientFullPath());
            return file.exists();
        } catch (Exception ex) {
            return false;
        }
    }



    private void transFile(OutputStream out) {
        try {
            FileInputStream fileInputStream = new FileInputStream(info.getClientFullPath());
            int iRtn = 0;
            byte[] bytes = new byte[1024];

            while ((iRtn = fileInputStream.read(bytes)) != -1) {
                out.write(bytes,0,iRtn);
            }
            out.flush();
            fileInputStream.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void execute() {
        /**
         * 校验文件有效性
         */
        if (!validateFile()) {
            throw new RuntimeException("File not available : " + info.getFilename());
        }
        OutputStream out=null;
        ObjectOutputStream oos = null;
        InputStream in = null;
        ObjectInputStream ois=null;
        try {

            /**
             * 连接至文件服务器
             */
            URL messageServlet = new URL(serverURL);
            URLConnection conn = messageServlet.openConnection();
            conn.setDoOutput(true);
            out=conn.getOutputStream();
            oos=new ObjectOutputStream(out);

            /**
             * 传递文件名和文件长度
             */
            info.initLength();
            oos.writeObject(info);
            oos.flush();

            /**
             * 传送文件
             */
            transFile(oos);

            /**
             * 接收文件完毕信息
             */
            in = conn.getInputStream();
            ois=new ObjectInputStream(in);
            FileInfo rtnInf=(FileInfo)ois.readObject();
            DtoUtil.copyProperties(info,rtnInf);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Upload uploader=new Upload();
        if(uploader.showFileDialog(null,"PMS","ESSP",null)) {
            uploader.execute();
        }
    }
}
