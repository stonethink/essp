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
  
  function loadMain() {
   var htmheight = parent.document.all.left.scrollHeight;
   var contentH = parseInt(document.all.AutoNumber1.scrollHeight);
   if(contentH<htmheight){
       Layer1.style.display = "none";
   }else{
       Layer1.style.display = "block";
   }
}
  </SCRIPT>
</head>
<body onload='loadMain();' leftMargin="1" topMargin="0" MARGINWIDTH="0" MARGINHEIGHT="0">
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
        <div id="Layer2" style="position:absolute; font-size:9pt; left: 0px; top: 0px; height:100%; width:170px; clip:rect(0 170 1000 0)">
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
<script language="JavaScript" type="text/JavaScript">
var Layer2 = document.all.Layer2;
var layerW=parseInt(Layer2.style.width);
var layerH=parseInt(Layer2.style.height);
var layerL=parseInt(Layer2.style.left);
var layerT=parseInt(Layer2.style.top);
layerH = parent.document.all.left.scrollHeight;
var step=0;
var movx;
var alertAble = true;
function move(a){
  Layer2.style.top = (step+=a) + layerT;
  clipL=0;
  clipR=layerW;
  clipB=layerH-step;
  clipT=0-step;
  Layer2.style.clip="rect("+clipT+" "+clipR+" "+clipB+" "+clipL+")";
}
function scrollup(){
  if(step>-parseInt(Layer2.scrollHeight)+layerH) {
	move(-20);
	movx = setTimeout("scrollup()",120);
  }
}
function scrolldown(){
  if(step < 0) {
	move(20);
	movx = setTimeout("scrolldown()",120);
  }
}
function mouseUp(){
  clearTimeout(movx);
}
//-->
</script>
  <div id="Layer1" style="position:absolute; right:0px; bottom:5px; width:58px; height:21px; z-index:2">
    <%--
    <img src="image/aup.gif" width="16" height="16" onClick="mousestart(2);scrollup();" onMouseOver="mousestart(2);mouseup();" onMouseOut="mousestop()">
      <img src="image/adn.gif" width="16" height="16" onClick="mousestart(1);scrolldown();" onMouseOver="mousestart(1);mousedown();" onMouseOut="mousestop()"></div>
      --%>
      <img src="image/aup.gif" width="16" height="16" onClick="" onMouseDown="scrollup();" onMouseUp="mouseUp()" alt="Move Up" >
      <img src="image/adn.gif" width="16" height="16" onClick="" onMouseDown="scrolldown();" onMouseUp="mouseUp()" alt="Move Down" >
  </div>
</body>
</html>
