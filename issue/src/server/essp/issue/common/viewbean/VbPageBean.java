package server.essp.issue.common.viewbean;

public abstract class VbPageBean implements IPageBean {
    private int pageNo;
    private int pageStandard;
    private int pageAction;
    private boolean first;
    private boolean previous;
    private boolean next;
    private boolean last;
    private int startNo;
    private int endNo;
    private int totalPage;
    //显示链接页的最多数量
    private int pageLinkMaxCount = IPageBean.PAGE_LINK_MAX_COUNT;

    public VbPageBean() {
    }

    public int getEndNo() {
        return endNo;
    }

    public boolean isNext() {
        return next;
    }

    public int getPageAction() {
        return pageAction;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageStandard() {
        return pageStandard;
    }

    public boolean isPrevious() {
        return previous;
    }

    public int getStartNo() {
        return startNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public void setEndNo(int endNo) {
        this.endNo = endNo;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public void setPageAction(int pageAction) {
        this.pageAction = pageAction;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageStandard(int pageStandard) {
        this.pageStandard = pageStandard;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public void setStartNo(int startNo) {
        this.startNo = startNo;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
    public int getPageLinkMaxCount() {
        return pageLinkMaxCount;
    }

    public void setPageLinkMaxCount(int count) {
        this.pageLinkMaxCount = count;
    }
    //显示链接页号
    public int[] getPageNoLink(){
        int[] link = null;
        if(totalPage <= pageLinkMaxCount){
            return getPageRange(1,totalPage);
        }
        if(totalPage > pageLinkMaxCount){
            if(pageNo < pageLinkMaxCount)
                return getPageRange(1,pageLinkMaxCount);
            if(pageNo >= pageLinkMaxCount && pageNo < totalPage)
                return getPageRange(pageNo - pageLinkMaxCount + 2,pageNo + 1);
            if(pageNo >= pageLinkMaxCount && pageNo >= totalPage)
                return getPageRange(pageNo - pageLinkMaxCount + 1,totalPage);
        }
        return link;
    }
    private int[] getPageRange(int begin,int end){
        int length = end - begin + 1;
        int[] link = new int[length];
        for(int i = 0;i < length ;i ++)
            link[i] = begin + i;
        return link;
    }
}
