package client.essp.common.upload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JOptionPane;
import org.apache.log4j.Category;

public class Upload {
    private static String uploadServer = "UploadServer"; //http://localhost:8080/essp/UploadServer
    public static final String ATTACHMENT = "ATTACHMENT";
    static Category log = Category.getInstance( "client" );

    //toDo:断点续传
    public static void uploadFile( File uploadFile, String prmServerUrl, String folder ){
    	String serverUrl=prmServerUrl;
        String fileName;
        int result;
        DataOutputStream out = null;
        InputStream in = null;

        try {
            if( serverUrl.endsWith("/") == false ){
                serverUrl = serverUrl + "/";
            }
            serverUrl = serverUrl + uploadServer;

            URL messageServlet = new URL(serverUrl);
            URLConnection conn = messageServlet.openConnection();
            conn.setDoOutput( true );
            out = new DataOutputStream(conn.getOutputStream());
            in = conn.getInputStream();
            out.write(1);
            out.flush();
            in.read();

            //write file name first
            fileName = uploadFile.getName();
            writeString( in, out, fileName );
            log.debug("write fileName: " + fileName);
            log.debug( String.valueOf( System.currentTimeMillis() ) );

            //write 存放文件的目录
            writeString( in, out, folder );
            log.debug("write folder: " + folder);
            log.debug( String.valueOf( System.currentTimeMillis() ) );

            //检查返回结果：是否有同名文件
            out.write(1);
            log.debug( String.valueOf( System.currentTimeMillis() ) );
            out.flush();
            result = in.read();
            log.debug("read result: " + result);
            if (result == -99) {
                JOptionPane.showMessageDialog(null, "上传文件失败");

                out.write(-1);
            log.debug( String.valueOf( System.currentTimeMillis() ) );
                out.flush();

                in.close();
                out.close();
                return;
            }
            if (result != 1) {
               int i = JOptionPane.showConfirmDialog(null, "文件已存在，是否覆盖原文件。", "确认", JOptionPane.YES_NO_OPTION );
               if (i == JOptionPane.NO_OPTION) {
                   out.write( -1);
                   log.debug(String.valueOf(System.currentTimeMillis()));
                   out.flush();

                   out.close();
                   in.close();
                   return;
               } else {
                   out.write(1);
                   log.debug(String.valueOf(System.currentTimeMillis()));
                   out.flush();
               }
           }

            //write file length

            //write file
            FileInputStream fileInputStream = new FileInputStream(uploadFile);
            rwFile(fileInputStream, in, out);
            fileInputStream.close();

            //get result of upload
            in = conn.getInputStream();
            result = in.read();
            if( result == 255 ){
                JOptionPane.showMessageDialog(null, "上传文件失败");
            }

            out.write(1);
            out.flush();
            out.close();
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "上传文件失败");
            try{
                out.write( -1);
                out.flush();
                out.close();
                in.close();
            }catch( Exception e ){
            	e.printStackTrace();
            }
        }
    }

    private static void writeString( InputStream in, DataOutputStream out, String str) throws IOException {
        byte[] bytes = str.getBytes();
        System.out.println("len: " + bytes.length);
        System.out.println("file name: " + str);

        out.write( bytes.length ); //write length of file name
        out.flush();
        in.read();
        out.write(bytes); //write file name
        out.flush();
        in.read();
    }

    private static void rwFile( InputStream inFile, InputStream in, OutputStream out ) throws IOException{
        int iRtn = 0;
        byte[] bytes = new byte[1024];

        while ( (iRtn = inFile.read(bytes) ) != -1) {
            out.write(bytes);
            out.flush();
            in.read();
        }
    }

    public static void main( String[] args ){
        String fileName = "E:/essp/doc/log4j.properties";
        File file = new File( fileName );
        Upload.uploadFile( file, "http://localhost:8080/essp", Upload.ATTACHMENT );
        Upload.uploadFile( file, "http://localhost:8080/essp", "test/subfolder" );

    }

}
