package client.essp.tc.common;

import java.awt.event.ActionListener;

import client.framework.view.vwcomp.IVWComponent;
import client.framework.view.vwcomp.VWJComboBox;
import javax.swing.event.PopupMenuListener;
import java.util.Vector;
import client.framework.model.VMComboBox;
import javax.swing.ComboBoxModel;

public class VWJComboBoxHasAction extends VWJComboBox {
    public IVWComponent duplicate() {

        VWJComboBoxHasAction comp = new VWJComboBoxHasAction();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        //comp.validatorResult=(ValidatorResult)this.validatorResult.duplicate();
        comp.setFont(this.getFont());

        Vector objectList = new Vector();
        for (int i = 0; i < ((VMComboBox) getModel()).getObjectVector().size(); i++) {
            objectList.add(((VMComboBox) getModel()).getObjectVector().get(i));
        }
        ComboBoxModel model = new VMComboBox(objectList);

        comp.setModel(model);

        ActionListener[] actionListeners = this.getActionListeners();
        for (int i = 0; i < actionListeners.length; i++) {
            comp.addActionListener(actionListeners[i]);
        }

        PopupMenuListener[] popupMenuListener = this.getPopupMenuListeners();
        for (int i = 0; i < popupMenuListener.length; i++) {
            comp.addPopupMenuListener(popupMenuListener[i]);
        }

        return comp;
    }
}
