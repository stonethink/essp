package client.essp.common.excelUtil;

import java.awt.event.*;
import java.util.Iterator;
import java.util.Map;

import javax.swing.*;

import c2s.essp.common.excelUtil.*;
import client.essp.common.view.*;
import client.framework.common.*;
import client.framework.view.vwcomp.*;
import client.image.*;
import com.wits.util.*;
import netscape.javascript.*;

public class VWJButtonExcel extends VWJButton {
    IExcelParameter parameter;
    public VWJButtonExcel(IExcelParameter parameter) {
        this.parameter = parameter;

        this.setIcon(new ImageIcon( ComImage.getImage("export.png")));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accessData();
            }
        });
    }

    public int accessData() {
    	return VWJSExporterUtil.excuteJSExporter(parameter.getUrlAddress());
    }
    
    public void setParameter(IExcelParameter parameter) {
        this.parameter = parameter;
    }

    public static void main(String args[]) {
        VWWorkArea w = new VWWorkArea();
        w.setLayout(null);

        IExcelParameter p = new IExcelParameter() {
            public String getUrlAddress(){
                return "http://localhost:8080/essp/hr/outExcel.jsp?acntRid=1&beginPeriod=20051203&endPeriod=20051209&periodType=week";
            }
        };
        VWJButtonExcel btnExcel = new VWJButtonExcel(p);
        btnExcel.setBounds(100, 100, 100, 20);
        w.add(btnExcel);


        TestPanel.show(w);
    }
}
