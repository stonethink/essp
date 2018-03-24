package server.essp.issue.common.logic;

import server.framework.logic.*;
import c2s.dto.FileInfo;
import com.wits.util.Config;

public class LgDownload extends AbstractBusinessLogic {
    private static String SERVER_ROOT="";

    /**
     * 仅用于测试，在正式版中SERVER_ROOT信息应从property文件中读取
     */
    static {
        try {
            SERVER_ROOT = "/essp/upload";
            Config cfg=new Config("fileserver");
            String virDir=cfg.getValue("virtual_directory");
            if(virDir!=null && !virDir.trim().equals("")) {
                SERVER_ROOT=virDir;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public LgDownload() {
    }

    public String getDownloadUrl(FileInfo fileInfo) {
        fileInfo.setModulename("ISSUE");
        String filePath=fileInfo.getServerPath(SERVER_ROOT);
        if(fileInfo.getFileExtend()!=null) {
            return filePath + "/" + fileInfo.getId() +"."+
                fileInfo.getFileExtend();
        } else {
            return filePath + "/" + fileInfo.getId();
        }
    }
}
