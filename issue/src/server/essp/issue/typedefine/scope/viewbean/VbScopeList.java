package server.essp.issue.typedefine.scope.viewbean;

import java.util.*;

public class VbScopeList {
        /**
         * 查询结果列表
         */
        private List detail;
        /**
         * 返回到界面上要选中的行
         */
        private String selectedRowObj;
        /**
         * 查询条件
         */
        private String typeName;
        private int detailSize;
        public VbScopeList() {
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
