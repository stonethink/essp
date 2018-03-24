package server.framework.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;

public interface IBusinessAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, TransactionData data) throws
        Exception;

}
