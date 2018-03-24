<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="com.wits.util.comDate,java.util.Locale,org.apache.struts.Globals" %>
<%---Session中有用户对象时才执行该页面内容,否则直接跳至登录页面---%>
<logic:present name="user" scope="session">
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="top"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
  body {
  margin-left: 0px;
  margin-right: 0px;
  margin-top: 0px;
  margin-bottom: 0px;
  }
  .font1 { font-size:9pt; font-family:Times New Roman, Times, serif;
  overflow:hidden; white-space:nowrap;}
  .SubsysImg  { width:28px; height:28px;}
  .img2  { width:26px; height:25px;}
  .SubsysPanel { position:absolute;left:95px ; top:13px; z-index:1;
  width:100%; padding:0px;}
</style>
<%
  Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY); 	
  String _language = locale.toString();
  //System.out.println("_language:"+_language);
  if(_language==null) _language = "";
%>

<script>
   var imgMap = new HashMap();
   var imgUpMap = new HashMap();
   var imgDownMap = new HashMap();

   <%---将菜单对应的三种不同状态的图标放入Map中，Key:functionID,Value:三种图片的地址---%>
   function initMap(){
     imgMap.put("essp","image/essp_slog.gif");
     imgUpMap.put("essp","image/essp_slog.gif");
     imgDownMap.put("essp","image/essp_slog.gif");

     <logic:iterate id="sub" name="menu" property="subSystem">
     imgMap.put('<bean:write name="sub" property="root.functionID" />','<bean:write name="sub" property="root.icon" />');
     imgUpMap.put('<bean:write name="sub" property="root.functionID" />','<bean:write name="sub" property="root.iconOn" />');
     imgDownMap.put('<bean:write name="sub" property="root.functionID" />','<bean:write name="sub" property="root.iconDown" />');
     </logic:iterate>

     imgMap.put("issue","image/orderForm.gif");
     imgUpMap.put("issue","image/orderForm_On.gif");
     imgDownMap.put("issue","image/orderForm_Down.gif");
     imgMap.put("exit","image/exit.gif");
     imgUpMap.put("exit","image/exit_On.gif");
     imgDownMap.put("exit","image/exit_Down.gif");
     imgMap.put("agent","image/attorney.gif");
     imgUpMap.put("agent","image/attorney_On.gif");
     imgDownMap.put("agent","image/attorney_Down.gif");
   }
   var lastClickedImg = null;
   <%---鼠标移动到子系统图标上,显示up图标--%>
    function onImgMouseOver(imgObj){
      if(imgObj.status == "unclicked"){
        imgObj.src = imgUpMap.get(imgObj.id);
      }
    }
    <%---鼠标离开子系统图标上,还原图标--%>
    function onImgMouseOut(imgObj){
      if(imgObj.status == "unclicked"){
        imgObj.src = imgMap.get(imgObj.id);;
      }
    }
    <%---鼠标点击子系统图标上,显示down图标--%>
    function onImgClick(imgObj,location){
      if(imgObj.status == "unclicked"){
        imgObj.status = "clicked";
        imgObj.src = imgDownMap.get(imgObj.id);
        parent.content.location=location;
        <%---还原上一次点击的图片的状态--%>
        if(lastClickedImg != null){
          lastClickedImg.status = "unclicked";
          lastClickedImg.src = imgMap.get(lastClickedImg.id);
        }
        lastClickedImg = imgObj;
      }
    }
    <%--进入页面,默认点ESSP图标--%>
    function onBodyLoad(){
      initMap();
      var esspLog = document.getElementById("essp");
      onImgClick(esspLog,'<%=request.getContextPath()%>/projectpre/ActiveBase.jsp');
    }
    <%---退出---%>
    function onExit(){
      parent.location="<%=request.getContextPath()%>/logout.do";
    }
    <%---提ESSP的问题单---%>
    function addNewEsspIssue() {
      openWindow('<%=request.getContextPath()%>/issue/issue/issueAddPre.do?accountCode=002645W&accountId=&issueType=SPR&typeName=SPR&issueStatus=Received&scope=Company&priority=NORMAL');
    }
    function onAgent(){
      openWindow('<%=request.getContextPath()%>/security/agent/authorize.jsp');
    }
    function openWindow(url, windowTitle) {
      var height = 430;
      var width = 580;
      var topis = (screen.height - height) / 2;
      var leftis = (screen.width - width) / 2;
      var option = "height=" + height + "px"
      +", width=" + width + "px"
      +", top=" + topis + "px"
      +", left=" + leftis + "px"
      +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
      window.open(url, windowTitle, option);
    }
 function onLanguage(){
    var language = document.getElementsByName("language")[0].value;
    if(language!=null&&language!=""){
      changeLocaleFrm.action="<%=request.getContextPath()%>/changeLocal.do?language="+language;
      changeLocaleFrm.submit();
    }
  }
  function selectLanguage(){
     var selecrValue = '<%=_language%>';
     if(selecrValue.indexOf("en")>=0){
       document.getElementsByName("language")[0].options[1].selected="selected";
     }else if(selecrValue.indexOf("zh")>=0){
       document.getElementsByName("language")[0].options[2].selected="selected";
     }
  }
  </script>
</head>
<body onload="onBodyLoad();selectLanguage()">
<html:form id="changeLocaleFrm" action="" method="post">
<table width="100%" height="55" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="130" align="left" valign="middle" bgcolor="#426194" scope="col">
      <img src="image/essp_slog.gif"
            alt=""
            onmouseover="onImgMouseOver(this)"
            onmouseout="onImgMouseOut(this)"
            onclick="onImgClick(this,'<%=request.getContextPath()%>/projectpre/ActiveBase.jsp')"
            id="essp" status="unclicked">
    </td>
    <td width="2000px" align="left" valign="middle" bgcolor="#426194" scope="col">
      <div class="SubsysPanel" id="SubsysPanel">
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <logic:present name="menu" scope="session">
             <logic:iterate id="sub" name="menu" property="subSystem">
               <td style="width:31px">
                 <img src='<bean:write name="sub" property="root.icon" />'
                       id="<bean:write name="sub" property="root.functionID" />"
                       class="SubsysImg"
                       alt="<bean:write name="sub" property="root.description" />"
                       onmouseover="onImgMouseOver(this)"
                       onmouseout="onImgMouseOut(this)"
                       onclick="onImgClick(this,'<bean:write name="sub" property="root.linkAddress" />')"
                       status="unclicked"/>
               </td>
            </logic:iterate>
            </logic:present>
          </tr>
        </table>
      </div>
    </td>
    <td align="left" valign="middle" scope="col">
      <table width="40" height="55" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td style=" " height="35" align="right" bgcolor="#426194">&nbsp;</td>
        </tr>
        <tr>
          <td height="22" background="image/leftbg.gif">&nbsp;</td>
        </tr>
      </table>
    </td>
    <td align="right" scope="col">
      <table height="55" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="35" align="right" bgcolor="#426194">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
               
                <td height="1" width="4">                </td>
                <td>
                  <img src="image/attorney.gif" class="img2"
                       id="agent"
                       onmouseover="onImgMouseOver(this)"
                       onmouseout="onImgMouseOut(this)"
                       onclick="onAgent()"
                       status="unclicked">
                </td>
                <td height="1" width="4">                </td>
                <td>
                  <img src="image/exit.gif" class="img2"
                    id="exit"
                    onmouseover="onImgMouseOver(this)"
                    onmouseout="onImgMouseOut(this)"
                    onclick="onExit();"
                    status="unclicked"
                    >
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right" height="20" class="font1">
            <bean:write name="user" property="userName"/>
            &nbsp;
            <%java.util.Date today = new java.util.Date();%>
            <%=comDate.dateToString(today)%>
              <html:select  name="language" styleId="height:18" beanName=""     onchange="onLanguage();">
                <option value="" title="" >--<bean:message bundle="application" key="global.select.language"/>--</option>
                <option value="en" title="English">English</option>
                <option value="cn" title="中文">中文</option>
             </html:select>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</html:form>
 <iframe id="languageChangeFrm" name="languageChangeFrm" src="" width="0" height="0"></iframe>
</body>
</html>
</logic:present>
<logic:notPresent name="user" scope="session">
  <script>
      parent.location="<%=request.getContextPath()%>/login.jsp";
   </script>
</logic:notPresent>

