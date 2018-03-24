<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.essp.common.menu.DtoMenu,c2s.essp.common.menu.DtoMenuItem"%>
<%@page import="java.util.*,org.apache.struts.taglib.*,org.apache.struts.util.*,org.apache.struts.Globals"%>
<html>
<head>
  <title>left</title>
  <style type="text/css">
    .navPoint{font-family: Webdings; font-size:7pt; color:white; cursor:hand;}
    .table_body { border:0px; border-color:#111111; border-collapse:collapse;
    padding:0px; border-spacing:0px;}
  </style>
  <link rel="stylesheet" type="text/css" href="css/essp.css">
  <link rel=stylesheet href="css/css.css">
  <script language="JavaScript" type="text/JavaScript">
  <!--页面切换动作-->
  function switchSysBar(){
    if (switchPoint.innerText==3){
      switchPoint.innerText=4;
      document.all("mnuList").style.display="none";
      parent.content.cols="12,*";
    }
    else{
      switchPoint.innerText=3;
      document.all("mnuList").style.display="";
      parent.content.cols="200,*";
    }
  }
  </SCRIPT>
</head>
<body leftMargin="1" topMargin="0" MARGINWIDTH="0" MARGINHEIGHT="0">
  <table width="200" cellPadding="0" height="100%">
    <script type="text/javascript" language="JavaScript" src="js/aptree.js"></script>
    <script type="text/javascript" language="JavaScript1.2">
    <%----设置菜单初始化参数---%>
    setShowExpanders(true);
    setExpandDepth(-1);
    setclickType(1);
    setKeepState(true);
    setShowHealth(false);
    setInTable(false);
    setTargetFrame("main");
    openFolder = "image/open_folder.gif";
    closedFolder = "image/closed_folder.gif";
    plusIcon = "image/lplus.gif";
    minusIcon = "image/lminus.gif";
    blankIcon = "image/blank20.gif";

    </script>
    <tr>
      <td id="mnuList" class="LeftFrame" style=" background-image:url(image/left_bg.gif);" height="100%" width="170">
        <div style=" font-size:9pt; left: 0px; top: 0px; height:100%">
          <table hight="100%" class="table_body" id="AutoNumber1">
            <script type="text/javascript" language="JavaScript1.2">
            var companyGif = "";
            var rootGif = "";
            var funcGif = "";

            root  = addRoot(companyGif, '','');
            <%----遍历子系统下所有的菜单,Show页面上---%>
            <logic:present parameter="SubSystem">
              <bean:parameter id="subSystem" name="SubSystem"/>
              <bean:define id="menuDto" name="menu" scope="session" />
              <%
              List subSysMenus = ((DtoMenu)menuDto).getSubSystemMenus(subSystem);
              if(subSysMenus != null && !subSysMenus.isEmpty()){
                for(int i = 0;i < subSysMenus.size() ;i ++){
                  DtoMenuItem item = (DtoMenuItem)subSysMenus.get(i);
                  String funId = item.getFunctionID();
                  //第二层的菜单父节点直接设置成root,其他设置成父菜单的ID
                  String parentNode = (item.getLayer() == 2) ? "root" : item.getFatherID();
				  String itemName =  item.getName();
				  try{
						 MessageResources resources = (MessageResources) pageContext.getAttribute("application",PageContext.APPLICATION_SCOPE);
						 //itemName = resources.getMessage(request.getLocale(), funId);
                         itemName = TagUtils.getInstance().message(pageContext,"application",Globals.LOCALE_KEY,funId,new Object[]{});
					 }catch(Exception e){
					    itemName = item.getName();
					 }
				   if(itemName==null||itemName==""){
				     itemName =  item.getName();
				   }
				   
				 // String itemName = request.getSession().getAttribute("org.apache.struts.Globals.MESSAGE_KEY");
                  //menu类型才显示链接
                  //String link = "";
                  //if("menu".equals(item.getType()))
                  //	link =  item.getLinkAddress();
                  //else if("js".equals(item.getType()))
                  //	link =  item.getFunctionAddress();	
              %>
                <%=funId%> = addItem (  <%=parentNode%>,
                                        funcGif,
                                        '<%=itemName%>',
                                        '<%=item.getLinkAddress()%>'
                                      );
              <%
                }
              }%>
            </logic:present>
          </script>
          <script type="text/javascript" language="JavaScript1.2">
          initialize();
        </script>
      </table>
    </div>
  </td>
    <td class="HalfWindow" width="8" height="100%" valign="center" onclick="switchSysBar()" align="middle">
      <SPAN class="navPoint" id="switchPoint">3</SPAN>
    </td>
  </tr>
</table>
</body>
</html>