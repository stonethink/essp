package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import server.essp.pms.quality.activity.logic.LgTestResultChart;
import java.io.ByteArrayOutputStream;

public class AcQualityActivityConclusion extends AbstractESSPAction {
    public AcQualityActivityConclusion() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoQualityActivity dtoQualityActivity = (DtoQualityActivity) inputInfo.
                                                getInputObj(
            "dto_for_image");
        LgTestResultChart lgTestResultChart = new LgTestResultChart();
        ByteArrayOutputStream byteArrayOut = lgTestResultChart.
                                             getTestResultChart(dtoQualityActivity);
        byte[] image = byteArrayOut.toByteArray();
        Byte[] imageByte = new Byte[image.length];
        for (int i = 0; i < image.length; i++) {
            imageByte[i] = new Byte(image[i]);
        }
        returnInfo.setReturnObj("imageByte",imageByte);

    }


}
