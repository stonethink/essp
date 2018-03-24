<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTimeSearch"/>
  <tiles:put name="jspName" value="OverTimeSearch"/>
</tiles:insert>
<script language="JavaScript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
  try{
    currentRowObj = rowObj;
    changeRowColor(rowObj);
  }catch( ex ){
  }
}
function sumbitImport(){
  var fileName = document.getElementsByName("localFile")[0].value;
  if(fileName == null || fileName == "") {
    alert("Local File is required!");
    return;
  }

 imForm.submit();
}
</script>
</head>
<body >
<html:tabpanel id="condition" width="98%">
  <%-- card title--%>
  <html:tabtitles>
    <html:tabtitle selected="true" width="80">
      <center><a class="tabs_title">Import</a></center>
    </html:tabtitle>
  </html:tabtitles>
  <%-- card buttons--%>
  <html:tabbuttons>
  </html:tabbuttons>
  <%-- card --%>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
      <html:outborder height="8%" width="98%">
      <html:form id="imForm" action="/attendance/overtime/OverTimeCleanImport" enctype="multipart/form-data" target="cardFrm" method="POST">
          <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
            <tr>
              <td class="list_range" width="70" height="40" align="left">Local File</td>
              <td width="380" height="40" class="list_range">
                <html:file name="localFile" styleId="input_common" next="submit" prev="" req="true"/>

                <input type="button" class="button" name="Import" onclick="sumbitImport();" value="Import">

                <input type="reset" value="Reset" name="reset" class="button" >
              </td>

            </tr>
          </table>
      </html:form >
      </html:outborder>

      <table align="center" width="100%" cellSpacing="0" cellPadding="0" >
        <tr ><td height="550">
          <IFRAME id="cardFrm" name="cardFrm" SRC='<%=request.getContextPath()%>/attendance/overtime/OverTimeCleanConfirm.jsp'
          width="100%" height="100%" frameborder="no" border="0"
          MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
          </IFRAME>
</td></tr>
      </table>
    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>
</body>
</html>
