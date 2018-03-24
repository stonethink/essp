package server.essp.issue.common.constant;

import java.util.ArrayList;
import java.util.List;
import server.framework.taglib.util.SelectOptionImpl;

public class Status {
    //Issue Type定义的内部状态
    public final static String RECEIVED="Received";
    public final static String PROCESSING="Processing";
    public final static String DELIVERED="Delivered";
    public final static String CLOSED="Closed";
    public final static String REJECTED="Rejected";
    public final static String DUPLATION="Duplation";
    public final static String NONACCEPTANCE="Nonacceptance";

    //新增的用于查询的一个状态
    public final static String NOT_CLOSED="Not Closed";
    //未结案状态:包含PROCESSING和DELIVERED
    public final static String LEFT = "Left";
    //非正常状态:当前日期>问题解决期限("Due Date")且状态为Processing或Delivered
    public final static String ABNORMAL="Abnormal";
    public final static List INNER_STATUS_OPTIONS = new ArrayList();
    static{
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl("-- Please Select --", ""));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.RECEIVED,Status.RECEIVED));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.PROCESSING,Status.PROCESSING));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.DELIVERED,Status.DELIVERED));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.CLOSED,Status.CLOSED));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.REJECTED,Status.REJECTED));
        INNER_STATUS_OPTIONS.add(new SelectOptionImpl(Status.DUPLATION,Status.DUPLATION));
    }

    public final static List SEARCH_STATUS_OPTIONS = new ArrayList();
    static{
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl("-- Please Select --", ""));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.RECEIVED,Status.RECEIVED));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.PROCESSING,Status.PROCESSING));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.DELIVERED,Status.DELIVERED));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.CLOSED,Status.CLOSED));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.REJECTED,Status.REJECTED));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.DUPLATION,Status.DUPLATION));
        SEARCH_STATUS_OPTIONS.add(new SelectOptionImpl(Status.NOT_CLOSED,Status.NOT_CLOSED));
    }


}
