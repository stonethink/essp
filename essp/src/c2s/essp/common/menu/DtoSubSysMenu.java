package c2s.essp.common.menu;

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
public class DtoSubSysMenu {
    public DtoSubSysMenu() {
    }

    public List getMenus() {
        return menus;
    }

    public DtoMenuItem getRoot() {
        return root;
    }

    public void setMenus(List menus) {
        this.menus = menus;
    }

    public void setRoot(DtoMenuItem root) {
        this.root = root;
    }

    private DtoMenuItem root = new DtoMenuItem();
    private List menus = null;
}
