package server.essp.issue.typedefine.scope.viewbean;

import java.util.*;

public class VbScopeList {
        /**
         * ��ѯ����б�
         */
        private List detail;
        /**
         * ���ص�������Ҫѡ�е���
         */
        private String selectedRowObj;
        /**
         * ��ѯ����
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
