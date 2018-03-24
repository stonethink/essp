package client.essp.tc.search;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoTcSearchCondition;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.vwcomp.VWJButton;
import com.wits.util.Parameter;

public class VwTcSearchBase extends VWTDWorkArea{
    VwTcSearchConditionBase vwTcSearchCondition = null;
    VwTcSearchResult vwTcSearchResult = null;

    public VwTcSearchBase(VwTcSearchConditionBase vwTcSearchCondition) {
        super(150);
        try {
            this.vwTcSearchCondition = vwTcSearchCondition;

            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit()throws Exception{

        vwTcSearchResult = new VwTcSearchResult();

        this.getTopArea().addTab("Condition", vwTcSearchCondition);
        this.getDownArea().addTab("Result", vwTcSearchResult);
    }

    void addUICEvent() {
        //event
        VWJButton btnSearch = new VWJButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSearch();
            }
        });
        vwTcSearchCondition.getButtonPanel().addButton(btnSearch);
    }

    void actionPerformedSearch(){
        DtoTcSearchCondition dtoCondition = new DtoTcSearchCondition();
        if( vwTcSearchCondition.getCondition(dtoCondition) == true ){

            Parameter param = new Parameter();
            param.put(DtoTcKey.SEARCH_CONDITION, dtoCondition);
            vwTcSearchResult.setParameter(param);
            vwTcSearchResult.refreshWorkArea();
        }
    }

    public void refreshWorkArea(){
        vwTcSearchCondition.refreshWorkArea();
    }
}
