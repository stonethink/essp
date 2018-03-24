package server.essp.tc.hrmanage.viewbean;

import java.math.BigDecimal;
import java.util.Date;

import c2s.dto.ITreeNode;
import com.wits.util.comDate;

public class VbTcLaborReport {
    private Date beginPeriod;
    private Date endPeriod;
    private ITreeNode root;
//    Utilization Rate=��Project��������Ŀ����������ʱ/���������й�����Ա�ı�׼��ʱ֮��
//    private BigDecimal utilizationRate;
    private BigDecimal productivityRate;
    private BigDecimal totalProjectNormalTime ;//����Type="Project"��Ŀ������ʱ����
    private BigDecimal totalEngineerStandardTime ;//���������й�����Ա�ı�׼��ʱ֮��
    public void setBeginPeriod(Date beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public void setProductivityRate(BigDecimal productivityRate) {
        this.productivityRate = productivityRate;
    }

    public void setTotalProjectNormalTime(BigDecimal totalProjectNormalTime) {
        this.totalProjectNormalTime = totalProjectNormalTime;
    }

    public void setTotalEngineerStandardTime(BigDecimal totalEngineerStandardTime) {
        this.totalEngineerStandardTime = totalEngineerStandardTime;
    }

    public void setRoot(ITreeNode root) {
        this.root = root;
    }

    public String getEndPeriod() {
        if(endPeriod == null)
            return "";
        return comDate.dateToString(endPeriod);
    }

    public String getBeginPeriod() {
        if(beginPeriod == null)
            return "";
        return comDate.dateToString(beginPeriod);
    }

    public BigDecimal getProductivityRate() {
        return productivityRate;
    }

    public BigDecimal getUtilizationRate() {
        if(totalProjectNormalTime == null || totalProjectNormalTime.doubleValue() == 0D ||
           totalEngineerStandardTime == null || totalEngineerStandardTime.doubleValue() == 0D)
            return new BigDecimal(0);
        return totalProjectNormalTime.multiply(new BigDecimal(100))
                .divide(totalEngineerStandardTime,BigDecimal.ROUND_HALF_UP)
                .setScale(2,BigDecimal.ROUND_HALF_UP);
//        return utilizationRate;
    }

    public BigDecimal getTotalProjectNormalTime() {
        return totalProjectNormalTime;
    }

    public BigDecimal getTotalEngineerStandardTime() {
        return totalEngineerStandardTime;
    }

    public ITreeNode getRoot() {
        return root;
    }
}
