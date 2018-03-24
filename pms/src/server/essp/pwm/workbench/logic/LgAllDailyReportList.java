package server.essp.pwm.workbench.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.pwm.workbench.DtoDailyReport;
import com.wits.util.comDate;
import net.sf.hibernate.Query;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import com.wits.util.StringUtil;
import java.math.BigDecimal;

public class LgAllDailyReportList extends AbstractESSPLogic{

    public ITreeNode selectAllDlrpt(Date wkitemDate) {
        ITreeNode root = null;

        try {
            List workItems = new ArrayList();
            if( wkitemDate != null ){
                String pattern = "yyyyMMdd";
                String sWkitemDate = comDate.dateToString(wkitemDate, pattern );

                Query q = getDbAccessor().getSession().createQuery("from essp.tables.PwWkitem t " +
                    " where t.wkitemIsdlrpt ='1' "
                    + " and TO_CHAR(t.wkitemDate,'" + pattern + "') = :wkitemDate "
                     +" order by TO_CHAR(t.wkitemStarttime,'HHmmss') ");
                q.setString("wkitemDate", sWkitemDate);

                List orgWkitems = q.list();
                workItems = DtoUtil.list2List(orgWkitems, DtoDailyReport.class);

                root = buildTree(comDate.dateToString(wkitemDate, "yyyy-MM-dd" ), workItems);
            }
         } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw new BusinessException("E000000",
                                        "Error when select daily reports.", ex);
        }

        return root;
    }

    private ITreeNode buildTree(String date, List items){
        DtoDailyReport dtoRoot = new DtoDailyReport();
        dtoRoot.setWkitemOwner(date);

        //create root
        ITreeNode root = new DtoTreeNode(dtoRoot);
        double totalWkhours = 0;

        for (int i = 0; i < items.size(); i++) {
            DtoDailyReport first = (DtoDailyReport)items.get(i);
            String owner = StringUtil.nvl(first.getWkitemOwner());

            //create a node for owner
            DtoDailyReport dto = new DtoDailyReport(first);
            ITreeNode node = new DtoTreeNode(dto);
            double totalWkhour = 0;
            if( dto.getWkitemWkhours() != null ){
                totalWkhour = dto.getWkitemWkhours().doubleValue();
            }

            //find the same owner
            for (int j = i+1; j < items.size(); j++) {
                DtoDailyReport other = (DtoDailyReport)items.get(j);
                if( owner.equals( other.getWkitemOwner() ) == true ){

                    //create a child node
                    ITreeNode childNode = new DtoTreeNode(other);
                    node.addChild(childNode);
                    if( other.getWkitemWkhours() != null ){
                        totalWkhour += other.getWkitemWkhours().doubleValue();
                    }

                    items.remove(j);
                    j--;
                }
            }

            if( node.getChildCount() > 0 ){
                //当同一个人有多余一条记录时，才有子节点；否则没有

                //create the first node
                ITreeNode firstNode = new DtoTreeNode(first);
                node.addChild(0, firstNode);
                dto.setWkitemName("");
                dto.setWkitemWkhours(new BigDecimal(totalWkhour) );
            }

            root.addChild(node);
            totalWkhours += totalWkhour;
        }

        dtoRoot.setWkitemWkhours(new BigDecimal(totalWkhours));
        return root;
    }

    public static void main(String args[]){
        LgAllDailyReportList logic = new LgAllDailyReportList();
        Calendar c = Calendar.getInstance();
        c.set(2005,9,10);
        ITreeNode root = logic.selectAllDlrpt(c.getTime());
        System.out.println(root.getChildCount());
    }
}
