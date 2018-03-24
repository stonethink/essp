package server.essp.issue.typedefine.category.viewbean;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbCategoryList {
        /**
         * 保存查询Category的结果
         */
        private List detail;
        /**
         * 返回界面被选中的行
         */
        private String selectedRowObj;
        /**
         * 查询条件
         */
        private String typeName;
        private int detailSize;
        public VbCategoryList() {
        }

        public void setDetail(List detail) {
            this.detail = detail;
        }

        public void setSelectedRowObj(String selectedRowObj) {
            this.selectedRowObj = selectedRowObj;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List getDetail() {
            return detail;
        }

        public String getSelectedRowObj() {
            return selectedRowObj;
        }

        public String getTypeName() {
            return typeName;
        }

        public int getDetailSize() {
            if(detail == null)
                detailSize = 0;
            else
                detailSize = detail.size();
            return detailSize;
        }

}
