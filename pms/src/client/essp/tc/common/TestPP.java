package client.essp.tc.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.essp.common.view.VWWorkArea;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.TestPanel;


public class TestPP extends VWWorkArea {


    VWJTable btn = getTable();
    JButton btn2 = new JButton("btn2");
    JButton btn3 = new JButton("btn3");

    int i = 1;
    public TestPP() {
        TcLayout layout = new TcLayout();
        this.setLayout(layout);
        //layout.setSepecailChildComp(p);

        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btn.addRow(getDto());

                p.setPreferredSize(new Dimension(getWidth(), btn.getPreferredSize().height + 30));
                p.setVisible(p.isVisible()?false:true);
                i++;

                updateUI();
            }
        });
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("--------------------------");
                System.out.println("btn1.getPreferredSize -- " + btn.getPreferredSize());
                System.out.println("btn1.getSize -- " + btn.getSize());
                System.out.println("btn1.scrollpanel.getPreferredSize -- " + btn.getScrollPane().getPreferredSize());
                System.out.println("btn1.scrollpanel.getSize -- " + btn.getScrollPane().getSize());
                System.out.println("btn2.getPreferredSize -- " + btn2.getPreferredSize());
                System.out.println("btn2.getSize -- " + btn2.getSize());
            }
        });

        p.setLayout(new BorderLayout());
        p.add(btn.getScrollPane(), BorderLayout.CENTER);
        p.setBorder(BorderFactory.createLineBorder(Color.blue));

        btn.getScrollPane().setBorder(BorderFactory.createLineBorder(Color.red));

        this.add(p);
        this.add(btn2);
        this.add(btn3);
    }

    JPanel p = new JPanel();
    public void init() {
        p.setPreferredSize(new Dimension(getWidth(), btn.getPreferredSize().height + 30));
        p.setMinimumSize(new Dimension(getWidth(), 100));

        btn2.setMinimumSize(new Dimension(getWidth(), 100));
        btn2.setPreferredSize(new Dimension(getWidth(), 200));

        btn3.setMinimumSize(new Dimension(getWidth(), 100));
        btn3.setPreferredSize(new Dimension(getWidth(), 200));

        updateUI();
    }

    Dto dto;
    VWJTable getTable() {
        String[][] configs = { {"name", "name"}, {"value", "value"}
        };

        Dto dto = new Dto("row1", "1111");
        Dto dto2 = new Dto("row2", "22");
        List list = new ArrayList();
        list.add(dto);
        list.add(dto2);
        list.add(dto2);
        list.add(dto2);

        VMTable2 model = new VMTable2(configs);
        model.setDtoType(Dto.class);
        model.setRows(list);
        VWJTable table = new VWJTable(model);

        return table;
    }

    Dto getDto() {
        return new Dto("" + i, "a " + i);
    }

    public static void main(String[] args) {
        TestPP p = new TestPP();
        TestPanel.show(p);
        p.init();
    }

    public static class Dto {
        String name;
        String value;
        public Dto(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }
    }

}
