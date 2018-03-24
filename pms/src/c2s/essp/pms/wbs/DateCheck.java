package c2s.essp.pms.wbs;

import c2s.dto.ITreeNode;
import java.util.Date;
import c2s.dto.DtoUtil;
import java.util.List;
import com.wits.util.comDate;

public class DateCheck {
    public final static int TYPE_START_DATE = 1;
    public final static int TYPE_FINISH_DATE = 2;

    public static void upModifyDate(ITreeNode node, String fieldName,
                                    int DATE_TYPE) {
        if (node == null || node.getParent() == null) {
            return;
        }
        ITreeNode parent = node.getParent();
        Object parentBean = parent.getDataBean();
        Date currentValue = null;
        Date dateValue = null;

        List children = parent.children();
        if(DATE_TYPE == TYPE_START_DATE) {
            System.out.println("从以下日期中取出最小日期:");
        } else {
            System.out.println("从以下日期中取出最大日期:");
        }

        for (int i = 0; i < children.size(); i++) {
            try {
                ITreeNode child=(ITreeNode)children.get(i);
                dateValue = (Date) DtoUtil.getProperty(child.getDataBean(), fieldName);
                if(dateValue!=null) {
                    System.out.println("["+comDate.dateToString(dateValue)+"]");
                } else {
                    System.out.println("[]");
                }
                if (currentValue == null) {
                    currentValue = dateValue;
                } else if (dateValue != null) {
                    if (DATE_TYPE == TYPE_START_DATE) {
                        if (!currentValue.before(dateValue)) {
                            currentValue = dateValue;
                        }
                    } else if (DATE_TYPE == TYPE_FINISH_DATE) {
                        if (!currentValue.after(dateValue)) {
                            currentValue = dateValue;
                        }
                    }
                }
            } catch (Exception ex) {
            }
        }

        try {
            DtoUtil.setProperty(parentBean, fieldName,currentValue);
        } catch (Exception ex1) {
        }

        upModifyDate(parent,fieldName,DATE_TYPE);
    }
}
