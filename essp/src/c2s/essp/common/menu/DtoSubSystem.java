package c2s.essp.common.menu;

import java.util.*;

/**
 * <p>Title: ��ϵͳ����:����һ������ϵͳ�ĸ��˵��������е��Ӳ˵�</p>
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
