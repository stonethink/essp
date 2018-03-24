/*
 * 1.2+ version.  Used by CustomLayoutDemo.java.
 */

package client.essp.tc.test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class TcLayout implements LayoutManager {
    private int minWidth = 0, minHeight = 0;
    private int preferredWidth = 0, preferredHeight = 0;

    /* Required by LayoutManager. */
    public void addLayoutComponent(String name, Component comp) {
    }

    /* Required by LayoutManager. */
    public void removeLayoutComponent(Component comp) {
    }

    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d = null;
        Dimension minD = null;

        //Reset preferred/minimum width and height.
        preferredWidth = parent.getWidth();
        preferredHeight = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();

                preferredHeight += d.height;

                minD = c.getMinimumSize();
                minHeight += minD.height;

                if (i == 0) {
                    minWidth = minD.width;
                } else {
                    minWidth = Math.min(minD.width, minWidth);
                }
            }
        }
    }


    /* Required by LayoutManager. */
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();

        setSizes(parent);

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = preferredWidth
                    + insets.left + insets.right;
        dim.height = preferredHeight
                     + insets.top + insets.bottom;

        return dim;
    }

    /* Required by LayoutManager. */
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();

        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = minWidth
                    + insets.left + insets.right;
        dim.height = minHeight
                     + insets.top + insets.bottom;

        return dim;
    }

    /* Required by LayoutManager. */
    /*
     * This is called when the panel is first displayed,
     * and every time its size changes.
     * Note: You CAN'T assume preferredLayoutSize or
     * minimumLayoutSize will be called -- in the case
     * of applets, at least, they probably won't be.
     */
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int parentWidth = parent.getWidth()
                          - (insets.left + insets.right);
        int parentHeight = parent.getHeight()
                           - (insets.top + insets.bottom);
        parentHeight = parentHeight - 20; //�ײ�����հ�

        int nComps = parent.getComponentCount();
        int y = insets.top;

        /**
         * ÿ���ؼ���width����parentһ����
         * ÿ���ؼ���height������ΪpreferredSize.height�����߶Ȳ���ʱ��
         * 1. ��Щ�߶ȴ���maximumsize�Ŀؼ��ĸ߶ȿ�ʼ����
         * 2. ����߶��Բ�������childComp���������˳�򣩵ĸ߶ȿ�ʼ���٣���СֵΪ����minSize.height
         */
        int h[] = new int[nComps];
        int minH[] = new int[nComps];
        int maxH[] = new int[nComps];
        int totalH = 0;

        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);

            Dimension d = c.getPreferredSize();
            Dimension minD = c.getMinimumSize();
            Dimension maxD = c.getMaximumSize();

            if (minD.height < 0) {
                minH[i] = 0;
            } else {
                minH[i] = minD.height;
            }

            if( maxD.height < 0 ){
                maxH[i] = 65535;
            }else{
                maxH[i] = maxD.height;
            }

            h[i] = d.height;

            totalH += h[i];
        }

        //1.�߶Ȳ���ʱ���ȼ��ٸ߶ȴ����Լ�maximumSize.height�Ŀؼ��ĸ߶�
        if (totalH > parentHeight) {
            //��˳�����comp�ĸ߶�,ֱ��maximumSize.height
            for (int i = 0; i < h.length; i++) {
                int minus = h[i] - maxH[i];
                if (minus > 0) {
                    if ((totalH - minus) <= parentHeight) {

                        //it's ok
                        h[i] = parentHeight - (totalH - h[i]);
                        totalH = parentHeight;
                        break;
                    } else {

                        h[i] = maxH[i];
                        totalH = totalH - minus;
                    }
                }
            }
        }

        //2.�߶Ȳ���ʱ�����ٸ߶ȴ����Լ�minimumSize.height�Ŀؼ��ĸ߶�
        if (totalH > parentHeight) {
            //��˳�����comp�ĸ߶�,ֱ��minimumSize.height
            for (int i = 0; i < h.length; i++) {
                int minus = h[i] - minH[i];
                if (minus > 0) {
                    if ((totalH - minus) <= parentHeight) {

                        //it's ok
                        h[i] = parentHeight - (totalH - h[i]);
                        totalH = parentHeight;
                        break;
                    } else {

                        h[i] = minH[i];
                        totalH = totalH - minus;
                    }
                }
            }
        }

        //3.�߶Ȳ���ʱ�����ٿؼ��ĸ߶�
        if (totalH > parentHeight) {
            //��˳�����comp�ĸ߶�,ֱ��0
            for (int i = 0; i < h.length; i++) {
                int minus = h[i];
                if (minus > 0) {
                    if ((totalH - minus) <= parentHeight) {

                        //it's ok
                        h[i] = parentHeight - (totalH - h[i]);
                        totalH = parentHeight;
                        break;
                    } else {

                        h[i] = 0;
                        totalH = totalH - minus;
                    }
                }
            }
        }

        for (int i = 0; i < h.length; i++) {
            Component c = parent.getComponent(i);
            c.setBounds(0, y, parentWidth, h[i]);

            y += h[i];
        }
    }
}
