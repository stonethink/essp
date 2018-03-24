<%@page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="FCK" uri="/tags/fckeditor"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="accountRid" name="inputData" property="acntRid" scope="request"/>
<bean:define id="mailContent" name="webVo" property="content" scope="request"/>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Send Mail"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<meta name="robots" content="noindex, nofollow">
<link href="../sample.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
  #title_display_250{
  width:250;
  }
  #title_display_w692{
  width:692;
  }
  #title_display_w710{
  width:710;
  }
</style>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>

<script type="text/javascript">
var xmlHttp;
function createXMLHttpRequest()
{
 if (window.XMLHttpRequest)
 {
  xmlHttp = new XMLHttpRequest();
 }

 else if (window.ActiveXObject)
 {
  xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function changeTemplate(val)
{
  createXMLHttpRequest();
  var url = "/essp/issue/changeTemplate.do?TemplateId="+val;//定义响应地址
  xmlHttp.open("POST",url, true);    //发出响应
  xmlHttp.onreadystatechange = changeTemplateHander;  //把从服务器得到的响应再传给另个函数
  xmlHttp.send(null)
}

function changeTemplateHander()
{
 if (xmlHttp.readyState == 4) {
  if (xmlHttp.status == 200)  {
//    alert(xmlHttp.responseText);
    var result=xmlHttp.responseText;
    var strArr=result.split(",,,,,");
    document.getElementById("mailTo").value=strArr[0];
    document.getElementById("cc").value=strArr[1];
    var oEditor = FCKeditorAPI.GetInstance('content');
    oEditor.SetHTML(strArr[2],true);
   }else {
        alert("Problem: " + xmlHttp.statusText);
      }
 }
}
</script>
<script type="text/javascript">

function allocateHrTo(){
  var oldValue=document.getElementById("mailTo").value;
  var acntRid=<%=accountRid%>;
  //sType="single";
  sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("No account error");
      return;
  }
    var result=allocMultipleInJsp(oldValue,acntRid,'');
    document.getElementById("mailTo").value=result;
}

function allocateHrCc(){
  var oldValue=document.getElementById("cc").value;
  var acntRid=<%=accountRid%>;
  //sType="single";
   sType="multi";
   var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("No account error");
      return;
  }
    var result=allocMultipleInJsp(oldValue,acntRid,'');
    document.getElementById("cc").value=result;
}

function FCKeditor_OnComplete( editorInstance )
{
//    alert(editorInstance.Name);
//	window.status = editorInstance.Description ;
}

function changeWindowSize(){
	window.resizeTo(850,650);
    window.moveTo((screen.width-850)/2,(screen.height-650)/2);

}

function test(){
  //var iii=content___Frame.xEditingArea.innerHTML;
  var newStr="<html>Bush in this interview breaks the norm and busts out his ipod.<html>";
  var oEditor = FCKeditorAPI.GetInstance('content');
  oEditor.SetHTML(newStr,true);

}

function changeTemp(val){
  retrieveURL("/essp/issue/changeTemplate.do?TemplateId="+val);
}

function SendMail(){
  var to=document.getElementById("mailTo").value;
  if(to==null||to==""){
    alert("please select addressee.");
    return;
  }
  var tempId=document.getElementById("defaultTemp").value;
  send.action="/essp/issue/sendmail.do?TemplateId="+tempId;
  send.submit();
}

</script>
<title>mailEdit</title>
</head>
<body bgcolor="#ffffff" onLoad="createXMLHttpRequest();changeWindowSize();">
<center>
  <font class="form_title">Send Mail</font>
  <br/>
  <br/>
  <table width="800" border="0" cellspacing="0" cellpadding="0">
  <html:form name="selectTemp" id="selectTemp" action="" method="Post">
    <tr>
      <td width="400">&nbsp;</td>
      <td width="150" align="right" class="list_range">Please Select Template</td>
      <td width="250">
        <html:select name="defaultTemp" styleId="title_display_250" onchange="changeTemplate(this.value);"
          beanName="webVo" onfocus="NO_DOFOCUS" >
          <logic:present name="webVo" property="templateList">
            <html:optionsCollection name="webVo" property="templateList"/>
          </logic:present>
        </html:select>
      </td>
    </tr>  </html:form>
  </table>
  <hr size="1" width="800"/>
<html:form name="send" id="send" action="" method="POST">
  <table width="800" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="80" height="25" class="list_range">To</td>
      <td width="10">&nbsp;</td>
      <td width="710">
        <html:text
          name="mailTo"
          beanName="webVo"
          fieldtype="text"
          styleId="title_display_w692"
          next="cc" req="true"
          imageSrc="<%=request.getContextPath()+"/image/humanAllocate.gif"%>"
          imageWidth="18"
          imageOnclick="allocateHrTo()"
          maxlength="800"
          onfocus="true"/>
      </td>
    </tr>
    <tr>
      <td height="25" class="list_range">CC</td>
      <td>&nbsp;</td>
      <td>
        <html:text
          name="cc"
          beanName="webVo"
          fieldtype="text"
          styleId="title_display_w692"
          next="title"
          imageSrc="<%=request.getContextPath()+"/image/humanAllocate.gif"%>"
          imageWidth="18"
          imageOnclick="allocateHrCc()"
          maxlength="800"
          onfocus="true"/>
      </td>
    </tr>
    <tr>
      <td height="25" class="list_range">Title</td>
      <td>      </td>
      <td>
        <html:text name="title" beanName="webVo" fieldtype="text" styleId="title_display_w710" req="true" />
      </td>
    </tr>
    <tr>
      <td height="25" valign="top" colspan="3" class="list_range">

        <FCK:editor id="content" basePath="/essp/" height="320" debug="true" stylesXmlPath="/essp/css/fckstyles.xml" fullPage="true"><%=mailContent%>        </FCK:editor>

      </td>
    </tr><logic:notEmpty name="webVo" property="attachments" scope="request">
    <tr>
      <td height="25" class="list_range" valign="top" style="padding-top:2">Attachments</td>
      <td>&nbsp;</td>
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">

        <logic:iterate id="att" name="webVo" property="attachments">
        <tr>
          <td width="1%" scope="col">
            <input name="attachment" type="checkbox" value="<bean:write name="att" property="key" />" checked>
          </td>
          <td align="left" scope="col"><a style="text-decoration:underline;" href="<bean:write name="att" property="value"/>">
            <bean:write name="att" property="key" /></a></td>
        </tr>
        </logic:iterate>

      </table></td>
    </tr></logic:notEmpty>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  <input type="button" name="send" value="Send" class="button" style="width:80" onClick="SendMail();"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type="button" name="cancel" value="Cancel" class="button" onClick="window.close();" style="width:80"/>
  </html:form>
</center>
</body>
</html>
