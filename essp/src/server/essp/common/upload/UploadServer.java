package server.essp.common.upload;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Category;

public class UploadServer extends HttpServlet {
    String root = "E://essp/upLoad"; //存放文件的目录
    Category log = Category.getInstance( "server" );

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        run(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        run(request, response);
    }

    public void run(HttpServletRequest request, HttpServletResponse response) {
        int result;

        String fileName;
        String folder;
        String file;
        DataInputStream in = null;
        OutputStream out = null;

        try {
            in = new DataInputStream(request.getInputStream());
            out = response.getOutputStream();
            in.read();
            out.write(1);
            out.flush();

            //read file name
            fileName = readString(in,out);
            log.debug("read fileName: " + fileName);
            log.debug( String.valueOf( System.currentTimeMillis() ) );

            //read 存放文件的目录
            folder = readString(in,out);
            log.debug("read folder: " + folder);
            log.debug( String.valueOf( System.currentTimeMillis() ) );

            //检查返回结果：是否有同名文件
            in.read();
            folder = root + "/" + folder;
            if (folder.endsWith("/") == false) {
                folder = folder + "/";
            }
            createFolder(folder);
            file = folder + fileName;
            if (checkFileExist(file) == true) {
                out.write( -1 );
            log.debug( String.valueOf( System.currentTimeMillis() ) );
                out.flush();

                result = in.read();
                if (result != 1) {

                    //有同名文件，但不想覆盖它，so return
                    in.close();
                    out.close();
                    return;
                }
            } else {
                out.write(1);
                out.flush();
                log.debug(String.valueOf(System.currentTimeMillis()));
            }

            //read file length

            //read file
            System.out.println( "upload file to '" + file + "'"  );
            OutputStream fileOutputStream = new FileOutputStream( file );
            rwFile(fileOutputStream, in , out);

            //write result: succeed
            out.write(1);
            out.flush();
            log.debug( String.valueOf( System.currentTimeMillis() ) );
            in.read();
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            try{
                //write result:fail
                if( out != null ){
                    out.write(255);
                    out.flush();
                }

                out.close();
                in.close();
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
    }

    public void rwFile(OutputStream destFile, InputStream src,OutputStream out) throws IOException {
        int iRtn ;

        byte[] bytes = new byte[1024];
        while ( (iRtn = src.read(bytes)) != -1) {
            destFile.write(bytes);
            destFile.flush();

            out.write(1);
            out.flush();
        }

        System.out.println( "finish write file " );
    }

    private boolean checkFileExist( String file ){
        File fileObj = new File( file );
        return fileObj.exists();
    }

    private void createFolder( String folderName ){
        File folder = new File( folderName );
        if( folder.exists() ){
            if( folder.isDirectory() == false ){
                folder.mkdir();
            }
        }else{
            folder.mkdirs();
        }
    }

    private String readString(DataInputStream in, OutputStream out ) throws IOException {
        int len;
        byte[] bytes = new byte[1024];
        String str;

        len = in.read();
        out.write(1);
        out.flush();
        bytes = new byte[len];  //read string
        in.read(bytes);
        out.write(1);
        out.flush();
        str = new String(bytes);

        return str;
    }

}
