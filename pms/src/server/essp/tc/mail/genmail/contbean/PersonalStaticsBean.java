package server.essp.tc.mail.genmail.contbean;

import java.util.ArrayList;
/**
 *此类用于个人所有信息提醒内容的Bean
 *author:Robin.Zhang
 */
public class PersonalStaticsBean {
    private VbTcExcelCopy baseInfo;
    private boolean hasWR;
    private boolean hasWF;
    private boolean hasATT;
    private ArrayList wrContent;
    private ArrayList wfContent;
    private ArrayList attContent;
    private String startDate;
    private String finishDate;

    public PersonalStaticsBean() {
    }

    public void setBaseInfo(VbTcExcelCopy baseInfo) {
        this.baseInfo = baseInfo;
    }

    public void setHasWR(boolean hasWR) {

        this.hasWR = hasWR;
    }

    public void setHasWF(boolean hasWF) {
        this.hasWF = hasWF;
    }

    public void setHasATT(boolean hasATT) {
        this.hasATT = hasATT;
    }

    public void setWrContent(ArrayList wrContent) {
        this.wrContent = wrContent;
    }

    public void setWfContent(ArrayList wfContent) {
        this.wfContent = wfContent;
    }

    public void setAttContent(ArrayList attContent) {
        this.attContent = attContent;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setFinishDate(String finishDate) {

        this.finishDate = finishDate;
    }

    public VbTcExcelCopy getBaseInfo() {
        return baseInfo;
    }

    public boolean isHasWR() {

        return hasWR;
    }

    public boolean isHasWF() {
        return hasWF;
    }

    public boolean isHasATT() {
        return hasATT;
    }

    public ArrayList getWrContent() {
        return wrContent;
    }

    public ArrayList getWfContent() {
        return wfContent;
    }

    public ArrayList getAttContent() {
        return attContent;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {

        return finishDate;
    }
}
