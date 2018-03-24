package c2s.essp.attendance;

import java.util.List;
import java.util.ArrayList;
import server.framework.taglib.util.SelectOptionImpl;
import java.util.Date;
import com.wits.util.comDate;



public final class Constant {
    //����ٱ��״̬,���úͲ�����
    public static final String LEAVE_TYPE_STATUS_EN="Enabled";
    public static final String LEAVE_TYPE_STATUS_DIS="Disabled";

    //���̵�״̬
    public static final String STATUS_APPLYING = "Applying";
    public static final String STATUS_REVIEWING = "Reviewing";
    public static final String STATUS_DISAGREED = "Disagreed";
    public static final String STATUS_FINISHED = "Finished";
    public static final String STATUS_ABORTED = "Aborted";

    public static final String DAY_BEGIN_TIME = "00:00"; //ÿ�쿪ʼʱ��
    public static final String DAY_END_TIME = "24:00"; //ÿ�����ʱ��

    public static final int DECIMAL_TRUN = 2;//��ʱС���㱣��λ��
    //��˶���
    public static final String DECISION_AGREE = "Agree";
    public static final String DECISION_DISAGREE = "Disagree";
    public static final String DECISION_MODIFY = "Modify";

    public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static Date parseDate(String date,String time){
        return comDate.toDate(date + " " + time,DATE_TIME_FORMAT);
    }
    //���֧�������ַ�ʽ
    public static final String SETTLEMENT_WAY_FULL_SALARY = "Full Salary";
    public static final String SETTLEMENT_WAY_HALF_SALARY = "Half Salary";
    public static final String SETTLEMENT_WAY_NO_SALARY = "No Salary";

    public static final List SETTLEMENT_WAY_OPTIONS = new ArrayList();
    static{
        SelectOptionImpl opt1 = new SelectOptionImpl(SETTLEMENT_WAY_FULL_SALARY,
                SETTLEMENT_WAY_FULL_SALARY);

        SelectOptionImpl opt2 = new SelectOptionImpl(SETTLEMENT_WAY_HALF_SALARY,
                SETTLEMENT_WAY_HALF_SALARY);

        SelectOptionImpl opt3 = new SelectOptionImpl(SETTLEMENT_WAY_NO_SALARY,
                SETTLEMENT_WAY_NO_SALARY);
        SETTLEMENT_WAY_OPTIONS.add(opt1);
        SETTLEMENT_WAY_OPTIONS.add(opt2);
        SETTLEMENT_WAY_OPTIONS.add(opt3);
    }

    //����������ƶ���
    public static final String WF_PACKAGE_ID = "Wistron.ITS.WH";
    public static final String WF_LEAVE_PROCESS_ID = "Leave";
    public static final String WF_LEAVE_PROCESS_NAME = "Leave Application";
    //�Ӱ��������ƶ���
    public static final String WF_OVERTIME_PROCESS_ID = "OverTime";
    public static final String WF_OVERTIME_PROCESS_NAME = "Over Time Application";
    //�������̶���
    public static final String WF_MODIFYLEAVE_PROCESS_ID = "ModifyLeave";
    public static final String WF_MODIFYLEAVE_PROCESS_NAME = "Modify Leave Application";

    //���������
    public static final String LEAVE_RELATION_SHIFT = "Shift Adjustment";
    public static final String LEAVE_RELATION_ANNU_LEAVE = "Annual Leave";
    public static final String LEAVE_RELATION_NO = "No Relation";
    public static final List LEAVE_RELATION_OPTIONS = new ArrayList();
    static{
        SelectOptionImpl opt3 = new SelectOptionImpl(LEAVE_RELATION_NO,LEAVE_RELATION_NO);
        LEAVE_RELATION_OPTIONS.add(opt3);
        SelectOptionImpl opt1 = new SelectOptionImpl(LEAVE_RELATION_SHIFT,
                LEAVE_RELATION_SHIFT);
        LEAVE_RELATION_OPTIONS.add(opt1);
        SelectOptionImpl opt2 = new SelectOptionImpl(LEAVE_RELATION_ANNU_LEAVE,
                LEAVE_RELATION_ANNU_LEAVE);
        LEAVE_RELATION_OPTIONS.add(opt2);
    }

    //��ǿ���ʱ�䲻����
    public static final String INFINITE_USABLE_HOURS = "-";
}
