package client.essp.pwm.wp;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJComboBox;

public class FPW01010TypeAndUnit {
//    private static String[] wpTypes = new String[] {
//                                      "RD", "RD Review", "AD", "AD Review",
//                                      "HD",
//                                      "HD Review", "DD", "DD Review",
//                                      "Coding&UT",
//                                      "WT", "IT", "RT", "AT", "Maintenance",
//                                      "Project Management", "Others"
//    };
    private static String[] wpSizeUnits = new String[] {"KLOC", "FP"};
    private static String[] wpDensityrateUnits = new String[] {"Case/KLOC",
                                                 "Case/FP"};
    private static String[] wpDefectrateUnits = new String[] {
                                                "Defects/KLOC", "Defects/FP"
    };
    private VWJComboBox[] vwJComboBox = new VWJComboBox[5];
    private VMComboBox[] vmComboBoxs = new VMComboBox[5];

    public FPW01010TypeAndUnit() {
    }

    public void setVWJComboBox(VWJComboBox wpType,
                               VWJComboBox wpSizeUnit,
                               VWJComboBox wpDensityrateUnit,
                               VWJComboBox wpDefectrateUnit) {
        vwJComboBox[0] = wpType;
        vwJComboBox[1] = wpSizeUnit;
        vwJComboBox[2] = wpDensityrateUnit;
        vwJComboBox[3] = wpDefectrateUnit;
        setVWJComboBox(vwJComboBox);
    }

    private void setVWJComboBox(VWJComboBox[] vwJComboBox) {
        this.vwJComboBox = vwJComboBox;
//        vmComboBoxs[0] = VMComboBox.toVMComboBox(wpTypes);
        vmComboBoxs[1] = VMComboBox.toVMComboBox(wpSizeUnits);
        vmComboBoxs[2] = VMComboBox.toVMComboBox(wpDensityrateUnits);
        vmComboBoxs[3] = VMComboBox.toVMComboBox(wpDefectrateUnits);

        //        vmComboBoxs[4] = new VMComboBox(arrayToCIVector(wpProductivityUnits));
//        vwJComboBox[0].addItemListener(new ItemListener() {
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    cmbWpTypeProcessDataChanged();
//                }
//            }
//        });

        vwJComboBox[1].addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cmbWpSizeUnitProcessDataChanged();
                }
            }
        });

        for (int i = 1; i < 4; i++) {
            vmComboBoxs[i].addNullElement();
            vwJComboBox[i].setModel(vmComboBoxs[i]);
        }
    }

//    public void cmbWpTypeProcessDataChanged() {
//        String sType = (String) vwJComboBox[0].getUICValue();
//
//        if ((sType == null) || sType.equals("") || sType.equals("Others")
//            || sType.equals("Maintenance")
//            || sType.equals("Project Management")) {
//            vwJComboBox[1].setUICValue(null);
//            vwJComboBox[2].setUICValue(null);
//            vwJComboBox[3].setUICValue(null);
//        } else if (sType.equals("RD") || sType.equals("RD Review")
//                   || sType.equals("AD") || sType.equals("AD Review")
//                   || sType.equals("HD") || sType.equals("HD Review")
//                   || sType.equals("DD") || sType.equals("DD Review")) {
//            vwJComboBox[1].setUICValue("FP");
//
//        } else if (sType.equals("Coding&UT") || sType.equals("WT")
//                   || sType.equals("IT") || sType.equals("RT")
//                   || sType.equals("AT")) {
//            vwJComboBox[1].setUICValue("KLOC");
//        }
//    }

    public void cmbWpSizeUnitProcessDataChanged() {
        String sType = (String) vwJComboBox[1].getUICValue();

        if ((sType == null) || sType.equals("")) {
            //            vwJComboBox[1].setUICValue(null);
            vwJComboBox[2].setUICValue(null);
            vwJComboBox[3].setUICValue(null);
        } else if (sType.equals("FP")) {
            //            vwJComboBox[1].setUICValue("FP");
            vwJComboBox[2].setUICValue("Case/FP");
            vwJComboBox[3].setUICValue("Defects/FP");

        } else if (sType.equals("KLOC")) {
            //            vwJComboBox[1].setUICValue("KLOC");
            vwJComboBox[2].setUICValue("Case/KLOC");
            vwJComboBox[3].setUICValue("Defects/KLOC");

        }
    }
}
