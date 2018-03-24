package server.framework.taglib.html;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import java.util.ArrayList;
import org.apache.struts.util.ResponseUtils;
import org.apache.struts.util.ResponseUtils;
import javax.servlet.http.HttpServletRequest;

public class TabTitlesTag extends AbstractBaseHandlerTag {
  private ArrayList itemList = new ArrayList();
  private TabPanelTag parent = null;

  private int selectIndex = -1;
  private String sSelectedIndex = "";

  private static String imagesDir = "../image";
  String tabCountVar = "tabCount";

  public TabTitlesTag() {
    selectIndex = -1;
  }

  public int doStartTag() throws JspException {
    Object request = this.pageContext.getRequest();
    String webContext = "";

    if (request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      webContext = httpRequest.getContextPath();
      imagesDir = webContext + "/image";
    }

    itemList.clear();
    Tag tag = this.getParent();
    while (tag != null && ! (tag instanceof TabPanelTag)) {
      tag = tag.getParent();
    }
    if (tag == null) {
      throw new JspException("ERROR: Can't find TabPanelTag tag");
    }
    parent = (TabPanelTag) tag;

    tabCountVar = "tabCount";
    if (parent.getId() != null && !parent.getId().trim().equals("")) {
      tabCountVar = parent.getId() + "_" + tabCountVar;
    }

    return 1;
  }

  protected String addTitle(boolean isSelected, String itemWidth) throws JspException {
    boolean currIsSelected=isSelected;

    if(this.getSelectedindex()!=null && !this.getSelectedindex().equals("")) {
      try {
        selectIndex=Integer.parseInt(this.getSelectedindex());
      } catch(Exception e) {
        throw new JspException(e);
      }
    }
    StringBuffer results = new StringBuffer("");
    TabItem tabItem = new TabItem();
    tabItem.title = "";
    itemList.add(tabItem);

    if(selectIndex==itemList.size()) {
      currIsSelected=true;
    }

    if (itemList.size() == 1) {
      tabItem.imgleft = imagesDir + "/fun_left.jpg";
      tabItem.imgback = imagesDir + "/fun_back.jpg";
      tabItem.vLayer = "hidden";
    } else {
      tabItem.imgleft = imagesDir + "/fun_lr.jpg";
      tabItem.imgback = imagesDir + "/fun_back.jpg";
      tabItem.vLayer = "hidden";
    }

    if (currIsSelected) {
      tabItem.imgleft = imagesDir + "/now_left.jpg";
      tabItem.imgback = imagesDir + "/now_back.jpg";
      tabItem.vLayer = "visible";
      selectIndex = itemList.size();
    } else {
      if (selectIndex == itemList.size() - 1) {
        tabItem.imgleft = imagesDir + "/now_right.jpg";
      }
    }

    String curID = itemList.size() + "";
    String imgId = "";
    String tdId = "";

    if (parent.getId() != null && !parent.getId().trim().equals("")) {
      imgId = parent.getId() + "_img" + curID;
    } else {
      imgId = "img" + curID;
    }

    if (parent.getId() != null && !parent.getId().trim().equals("")) {
      tdId = parent.getId() + "_td" + curID;
    } else {
      tdId = "td" + curID;
    }

    String titleWidth = itemWidth;
    if (titleWidth == null || titleWidth.trim().equals("")) {
      titleWidth = "80";
    }
    results.append("<td width=\"10\"><img id=\"" + imgId + "\" src=\""
                   + tabItem.imgleft +
                   "\" width=\"10\" height=\"23\"></td>");
    results.append("<td id=\"" + tdId
                   + "\" width=\"" + titleWidth + "\" valign=\"bottom\" background=\""
                   + tabItem.imgback + "\" class=\"Grid2\">");

    results.append("<div align=\"center\" onclick=\"change('" +
                   parent.getId() + "'," + curID
                   + "," + tabCountVar + ");\" style=\"CURSOR: hand\">");
    //+ tabItem.title + "</div>");
    //results.append("</td>");
    //ResponseUtils.write(pageContext, results.toString());
    return results.toString();

  }

  public int doEndTag() throws JspException {
    TabPanelTag tabPanel = parent;
    tabPanel.selectIndex = selectIndex;

    StringBuffer results = new StringBuffer("");
    String lastImg = imagesDir + "/fun_right.jpg";

    if (selectIndex == itemList.size()) {
      lastImg = imagesDir + "/now_right.jpg";
    }

    String imgId = "";

    if (parent.getId() != null && !parent.getId().trim().equals("")) {
      imgId = parent.getId() + "_img" + (itemList.size() + 1);
    } else {
      imgId = "img" + (itemList.size() + 1);
    }

    results.append("<td width=\"10\"><img id=\""
                   + imgId + "\" src=\"" + lastImg
                   + "\" width=\"10\" height=\"23\"></td>");
    if (tabCountVar.equals("tabCount")) {
      results.append("<script language='javascript'>" + tabCountVar + "=" +
                     itemList.size() + ";imagesDir=\"" + imagesDir + "\";</script>");
    } else {
      results.append("<script language='javascript'>var " + tabCountVar + "=" +
                     itemList.size() + ";imagesDir=\"" + imagesDir + "\";document.getElementById('" + tabPanel.getId() + "').tabindex=" + selectIndex +
                     ";</script>");
    }
    ResponseUtils.write(pageContext, results.toString());
    release();
    return 1;
  }

  protected void setSelectIndex() {
    selectIndex = itemList.size();
  }

  public String getSelectedindex() {
    return sSelectedIndex;
  }

  public void setSelectedindex(String selectedIndex) {
    this.sSelectedIndex = selectedIndex;
  }

  public void release() {
    selectIndex = -1;
    sSelectedIndex = "";
    itemList.clear();
  }

  class TabItem {
    public String imgleft = "";

    public String imgback = "";

    public String vLayer = "";

    public String title = "";
  }
}
