package c2s.dto;

import java.io.*;
import com.wits.util.Config;

public class FileInfo implements java.io.Serializable {
    private String id;
    private String clientpath;
    private String filename;
    private long length;
    private String modulename;
    private String accountcode;
    private String msg;

    static Config config = null;
    public FileInfo() {
    }

    public static String getFileRoot() {

        if (config == null) {
            config = new Config("c2s/dto/upload");
        }

        //like: "upload"
        return config.getValue("fileFolderRoot");
    }

    public String getFilename() {
        return filename;
    }

    public String getClientpath() {
        return clientpath;
    }

    public String getClientFullPath() {
        return clientpath+"/"+filename;
    }

    public String getId() {
        return id;
    }

    public long getLength() {
        return length;
    }

    public String getMsg() {
        return msg;
    }

    public String getAccountcode() {
        return accountcode;
    }

    public String getModulename() {
        return modulename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setClientFullPath(String fullPath) {
        String sFilename = fullPath.replace('\\','/');
        int index = sFilename.lastIndexOf("/");
        if (index >= 0) {
            clientpath=sFilename.substring(0,index);
            sFilename = sFilename.substring(index+1);
        } else {
            clientpath=".";
        }

        this.filename=sFilename;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public void initLength() {
        File file = new File(this.getClientFullPath());
        this.length=file.length();
    }

    public String getServerPath(String root) {
        String sRoot="";
        if(root==null || root.trim().equals("")) {
            sRoot="./";
        } else {
            sRoot=root.replace('\\','/');
        }
        if(!sRoot.endsWith("/")) {
            sRoot=sRoot+"/";
        }
        return sRoot+modulename+"/"+accountcode;
    }

    public String getServerFullPath(String root) {
        return getServerPath(root)+"/"+id+"."+getFileExtend();
    }

    public String getFileExtend(){
        if( filename != null ){
            int i = filename.lastIndexOf(".");
            if( i >= 0 ){
                return filename.substring(i+1);
            }
        }

        return "";
    }
}
