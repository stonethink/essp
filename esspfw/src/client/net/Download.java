package client.net;

import client.framework.common.Global;
import c2s.dto.FileInfo;
import javax.swing.JFileChooser;
import java.awt.Component;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.InputStream;
import c2s.dto.DtoUtil;
import java.io.OutputStream;
import java.net.URL;
import java.io.ObjectInputStream;
import java.net.URLConnection;
//import server.framework.common.BusinessException;
import java.io.FileOutputStream;

public class Download {
    private String serverURL;
    private FileInfo info;

    private File selectedFile=null;

    public Download() {
        serverURL = Global.appRoot + "/download";
    }

    public void setFileInfo(FileInfo info) {
        this.info = info;
    }

    public FileInfo getFileInfo() {
        return this.info;
    }

    public void execute() {
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
             * 传递文件信息
             */
            oos.writeObject(info);
            oos.flush();

            /**
             * 接收文件信息
             */
            in = conn.getInputStream();
            ois=new ObjectInputStream(in);
            FileInfo rtnInf=(FileInfo)ois.readObject();
            String fileName = info.getFilename();
            DtoUtil.copyProperties(info,rtnInf,new String[]{"filename"});
            if( fileName != null ){
                info.setFilename(fileName);
            }

            /**
             * 传送文件
             */
            transFile(ois);
            oos.close();
            out.close();
            ois.close();
            in.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void transFile(InputStream in) {
        try {
            FileOutputStream fos = new FileOutputStream(info.getClientFullPath());
            int iRtn = 0;
            long hasReceived = 0;
            byte[] bytes = new byte[1024];
            long maxlength = 1024;
            long length=info.getLength();
            if (length - hasReceived < 1024) {
                maxlength = length - hasReceived;
            }

            while ((iRtn = in.read(bytes, 0, (int) maxlength)) != -1) {
                fos.write(bytes, 0, iRtn);
                hasReceived = hasReceived + iRtn;

                /**
                 * 如果已经收完文件，则退出
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
        } /*catch(BusinessException e) {
            throw e;
        }*/ catch (Exception ex) {
            //throw new BusinessException(ex);
            throw new RuntimeException(ex);
        }
    }

    public boolean showFileDialog(Component parent, String module,
                                  String accountCode,String fileid,String filename) {
        if (this.info == null) {
            this.info = new FileInfo();
        }
        info.setModulename(module);
        info.setAccountcode(accountCode);
        info.setId(fileid);
        //info.setClientFullPath("C:/"+filename);
        info.setFilename(filename);
        if( selectedFile == null ){
            selectedFile = new File("C:/"+filename);
        }else{
            String p = selectedFile.getParent();
            if( p!= null ){
                selectedFile = new File(p+"/"+filename);
            }
        }

        JFileChooser fileChooser = new JFileChooser();
//        File file=new File(info.getClientFullPath());
        fileChooser.setSelectedFile(selectedFile);
        int returnVal = fileChooser.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            info.setClientFullPath(selectedFile.getPath());
            execute();
        } else {
            info = null;
        }

        return info != null;
    }

    public static void main(String[] args) {
        Download download = new Download();
        if(download.showFileDialog(null,"PMS","002645W","F00000008","test.txt")) {
            download.execute();
        }
    }
}
