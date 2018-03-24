package server.essp.common.excelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.wits.util.Parameter;

public interface IExcelBusinessAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os) throws Exception;


}
