package server.essp.issue.common.viewbean;

import java.util.List;

public interface IPageBean {
    public void setFirst(boolean first);
    public void setPrevious(boolean previous);
    public void setNext(boolean next);
    public void setLast(boolean last);
    public boolean isFirst();
    public boolean isPrevious();
    public boolean isNext();
    public boolean isLast();
    public int getPageAction();
    public int getPageNo();
    public void setPageNo(int pageNo);
    public int getPageStandard();
    public int getStartNo();
    public void setStartNo(int startNo);
    public int getEndNo();
    public void setEndNo(int endNo);
    public List getDetail();
    public int getTotalPage();
    public void setTotalPage(int totalPage);
    public int getPageLinkMaxCount();
    public void setPageLinkMaxCount(int count);
    public int[] getPageNoLink();
    //默认显示的最多链接页数
    public static final int PAGE_LINK_MAX_COUNT = 10;
}
