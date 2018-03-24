/*
 * Created on 2008-5-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.step.monitoring;

import client.essp.common.view.VWGeneralWorkArea;

import com.wits.util.Parameter;

public class VwMonitoring extends VWGeneralWorkArea {
        
        private VwMonitoringList vwMonList;
    
        public VwMonitoring() {
            try {
                jbInit();
            } catch (Exception e) {
                log.error(e);
            }
        }

        /**
         * ��ʼ��UI
         * @throws Exception
         */
        public void jbInit() throws Exception {
            vwMonList = new VwMonitoringList();
            this.addTab("rsid.timesheet.monitoring", vwMonList);
        }
    
        /**
         * ���ݲ�����vwMonList
         * @param parameter Parameter
         */
        public void setParameter(Parameter parameter) {
            vwMonList.setParameter(parameter);
        }
    
        /**
         * ����ˢ���¼���vwMonList
         */
        public void refreshWorkArea() {
            vwMonList.refreshWorkArea();
        }
}
