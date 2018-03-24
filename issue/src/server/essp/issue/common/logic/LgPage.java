package server.essp.issue.common.logic;

import server.framework.logic.AbstractBusinessLogic;
import server.essp.issue.common.viewbean.IPageBean;
import server.essp.issue.common.constant.PageAction;

public class LgPage extends AbstractBusinessLogic {
    public LgPage() {
    }

    public void splitPages(IPageBean viewBean) {
        /**
         * ��ʼ��
         */
        viewBean.setTotalPage(0);
        viewBean.setStartNo(0);
        viewBean.setEndNo(0);
        viewBean.setPrevious(false);
        viewBean.setNext(false);

        /**
         * �ж��Ƿ��б�Ҫִ�з�ҳ����
         */
        if(viewBean==null ||
           viewBean.getDetail()==null ||
           viewBean.getDetail().size()==0) {
            viewBean.setPageNo(0);
            return;
        }
        int pageStandard=viewBean.getPageStandard();
        if(pageStandard<=0) {
            pageStandard=10;
        }
        if(viewBean.getDetail().size()<=pageStandard) {
            viewBean.setTotalPage(1);
            viewBean.setPageNo(1);
            viewBean.setStartNo(1);
            viewBean.setEndNo(viewBean.getDetail().size());
            if(viewBean.getEndNo()<1) {
                viewBean.setEndNo(1);
            }
            viewBean.setPrevious(false);
            viewBean.setNext(false);
            return;
        }
        /**
         * ȡ�÷�ҳָ��
         */
        int pageAction=viewBean.getPageAction();
        if(pageAction!=PageAction.ACTION_NEXTPAGE &&
           pageAction!=PageAction.ACTION_PREVPAGE &&
           pageAction!=PageAction.ACTION_NOTHING &&
           pageAction!=PageAction.ACTION_FIRSTPAGE &&
           pageAction!=PageAction.ACTION_LASTPAGE &&
           pageAction!=PageAction.ACTION_GOTOPAGE) {
            return;
        }
        /**
         * ȡ���ϴη�ҳ��ҳ��
         */
        int pageNo=viewBean.getPageNo();
        if(pageAction==PageAction.ACTION_NEXTPAGE) {
            pageNo++;
        }
        if(pageAction==PageAction.ACTION_PREVPAGE) {
            pageNo--;
        }

        if(pageNo<1) {
            pageNo=1;
        }
        /**
         * ������ҳ��
         */
        int totalRecord=viewBean.getDetail().size();
        int totalPage=totalRecord/pageStandard;
        if(totalRecord<pageStandard) {
            if(totalRecord>0) {
                totalPage = 1;
            } else {
                totalPage = 0;
            }
        } else {
            if (totalRecord % pageStandard != 0) {
                totalPage = totalPage + 1;
            }
        }

        if(pageAction==PageAction.ACTION_FIRSTPAGE) {
            pageNo=1;
        } else if(pageAction==PageAction.ACTION_LASTPAGE) {
            pageNo=totalPage;
        }

        if(pageNo>totalPage) {
            pageNo=totalPage;
        }
        /**
         * ������ʼ��¼��
         */
        int startNo=(pageNo-1)*pageStandard+1;
        if(startNo>totalRecord) {
            startNo=totalRecord;
        }
        /**
         * ���������¼��
         */
        int endNo=pageNo*pageStandard;
        if(endNo>totalRecord) {
            endNo=totalRecord;
        }

        /**
         * ����JSP�����Ƿ���ǰ��ҳ����
         */
        if(startNo==1) {
            viewBean.setPrevious(false);
            viewBean.setFirst(false);
        } else {
            viewBean.setPrevious(true);
            viewBean.setFirst(true);
        }

        /**
         * ����JSP�����Ƿ����ҳ����
         */
        if(endNo==totalRecord) {
            viewBean.setLast(false);
            viewBean.setNext(false);
        } else {
            viewBean.setLast(true);
            viewBean.setNext(true);
        }

        viewBean.setPageNo(pageNo);
        viewBean.setTotalPage(totalPage);
        viewBean.setStartNo(startNo);
        viewBean.setEndNo(endNo);
        for(int i=totalRecord-1;i>endNo-1;i--) {
            viewBean.getDetail().remove(i);
        }
        for(int i=startNo-2;i>=0;i--) {
            viewBean.getDetail().remove(i);
        }
    }
}
