package c2s.essp.common.menu;

import java.util.*;

/**
 * <p>Title: 子系统对象:包含一个该子系统的根菜单和其所有的子菜单</p>
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
public class DtoSubSystem {
    public DtoSubSystem(DtoMenuItem root) {
        this.root = root;
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

    public void addMenu(DtoMenuItem menu){
        menus.add(menu);
    }

    private DtoMenuItem root = null;
    private List menus = new ArrayList();
}
