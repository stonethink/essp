<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="server.essp.issue.typedefine.viewbean.VbType"%>

<bean:define  id="selectedRowObj"  name="webVo" property="selectedRowObj" />
<bean:define  id="selectedTabIndex"  name="webVo" property="selectedTabIndex" />
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define  id="detailSize" name="webVo" property="detailSize" />
<bean:define  id="newAddName" name="webVo" property="addTypeName"/>
<%
   String firstRid="";
   String firstRowId="";
   String firstTypeName="";
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>

<script language="javaScript" type="text/javascript">
var typeName = "";
var currentRowObj;
var newTypeName="";

<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    //alert(rowObj.cells[0].innerHTML);
    //typeName=rowObj.cells[0].innerHTML;
    try{
    	currentRowObj = rowObj;
        //rowObj.scrollIntoView(true);
    	typeName = rowObj.cells[0].title;
    	pass(typeName);
    }catch( ex ){
    }
}

function onDbClickRow(rowObj){
  onclickRow(rowObj);
}

function onAddData() {
    var size=<%=detailSize%>;
    var isize=eval(size+1);
    openWindow( '<%=contextPath%>/issue/typedefine/issueTypeAddPre.do?DetailSize='+isize );
}

function onDeleteData() {
    if(currentRowObj==null) {
        alert("Please select a row first!");
        return;
    }
    if(confirm("Do you want to delete this row?")) {
        issueTypeForm.action="<%=contextPath%>/issue/typedefine/issueTypeDelete.do";
        issueTypeForm.typeName.value=typeName;
        issueTypeForm.submit();
    }
}

function onBodyLoad(){
	try{
       newTypeName="<%=newAddName%> ";
       if (searchAddTypeName()==false){
           onclickRow( <%=selectedRowObj%> );
       }
	}catch( ex ){
    }
}

function searchAddTypeName(){
  var sTypeName;
  for (i=0; i < searchTable.rows.length; i++) {
         sTypeName=searchTable.rows(i).cells(0).innerText;
         if (sTypeName==newTypeName){
           onclickRow(searchTable.rows(i));
           return true;
         }
  }
  return false;
}


function getSelectedTabIndex(){
    try{
        return cardFrm.getSelectedTabIndex();
    }catch( ex ){
        return 1;
    }
}

function hasNoValue(value){
    if( value == null || value == '' || value == "" || value == 'null' ){
        return true;
    }else{
        return false
    }
}

function openWindow(url, windowTitle) {
    var height = 250;
    var width = 500;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
			    +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
    window.open(url, windowTitle, option);
}

function pass(typename){
//    alert( "pass(" + rid + ")" );
    if( typename != null ){
        cardFrm.onRefreshData(typename);
   }
}

function refreshSelf(){
    //alert( "refreshSelf" );
    try{
        if( currentRowObj != null ){
    		currentStatusForm.selectedRowObj.value=currentRowObj.id;
        }
    	currentStatusForm.selectedTabIndex.value=getSelectedTabIndex();
        currentStatusForm.newTypeName.value=newTypeName;
	    currentStatusForm.submit();
    }catch( ex ){
    }
}

</script></head>
<body onload="onBodyLoad()">
<form name="issueTypeForm" action="" method="POST">
    <input type="hidden" name="typeName">
    <input type="hidden" name="sequence">
    <input type="hidden" name="rst">
    <input type="hidden" name="description">
</form>
<html:tabpanel id="issueTypeList" width="98%">
  <html:tabtitles>
     <html:tabtitle selected="true" width="100">
      <span class="tabs_title">Issue Type List</span>
    </html:tabtitle>
  </html:tabtitles>
  <html:tabbuttons>
      <input type="button" name="addBtn" style="width:50px" value='Add' class="button" onClick="onAddData()"/>
      <input type="button" name="deleteBtn" style="width:50px" value='Delete' class="button" onClick="onDeleteData()"/>
  </html:tabbuttons>
  <html:tabcontents>
  <html:tabcontent styleClass="wind">
      <html:outborder height="40%" width="98%">
            <html:table styleId="tableStyle">
              <html:tablehead styleId="tableTitleStyle">
                <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Issue Type">Issue Type</html:tabletitle>
                <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
                <html:tabletitle width="110" styleId="oracolumnnumberheader" toolTip="Sequence">Sequence</html:tabletitle>
                <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
              </html:tablehead>
              <html:tablebody styleId="tableDataStyle" height="186" id="searchTable">
                <logic:iterate name="webVo" property="detail" id="oneBean" indexId="indexId">
                  <bean:define id="selfProperty" value=""/>
                  <bean:define id="ttTypeName" name="oneBean" property="typeName"/>
                  <%
                    selfProperty = " rid='" + ( (VbType)oneBean ).getRid() + "' ";
                  %>
                  <% String sId = "tr"+indexId.intValue(); %>

                  <%
                  if(String.valueOf(indexId).equals("0")) {
                      firstRid=String.valueOf(indexId);
                      firstRowId="tr"+firstRid;
                      firstTypeName=(String)ttTypeName;
                  }
                %>

                  <html:tablerow id="<%=sId%>"
                      oddClass="tableDataOdd"
                      evenClass="tableDataEven"
                      onclick="onclickRow(this);"
                      ondblclick="onDbClickRow(this);"
                      height="18px"
                      selfProperty = "<%=selfProperty%>"
                      canSelected="true"
                      >

                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttTypeName)%>">
                      <%=String.valueOf(ttTypeName)%>
                    </html:tablecolumn>
                    <bean:define id="ttStatus" name="oneBean" property="status"/>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttStatus)%>">
                      <bean:write name="oneBean" property="status"/>
                    </html:tablecolumn>
                    <bean:define id="ttSequence" name="oneBean" property="sequence"/>
                    <html:tablecolumn styleId="oracellnumber" toolTip="<%=String.valueOf(ttSequence)%>">
                      <bean:write name="oneBean" property="sequence"/>
                    </html:tablecolumn>
                    <bean:define id="ttDescription" name="oneBean" property="description"/>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttDescription)%>">
                      <bean:write name="oneBean" property="description"/>
                    </html:tablecolumn>
                  </html:tablerow>

                </logic:iterate>
              </html:tablebody>
            </html:table>
        </html:outborder>
        &nbsp;
        <table valign="top" align="center" width="98%" height="240" cellSpacing="0" cellPadding="0" >
          <tr height="100%" ><td>
          <logic:notEqual name="detailSize" value="0">
            <IFRAME id="cardFrm" name="cardFrm" SRC="<%=contextPath%>/issue/typedefine/issueTypeCard.do?detailSize=<%=((Long)detailSize).longValue()%>&selectedTabIndex=<%=selectedTabIndex%>"
                    width="100%" height="100%" frameborder="no" border="0"
                    MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
			</IFRAME>
          </logic:notEqual>
         </td></tr>
       </table>

    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>

<% String sActionHiddenSubmit = request.getContextPath()+"/hiddenSubmit"; %>
<html:form name="hiddenForm" method="post" action="<%=sActionHiddenSubmit%>">
  <input type="hidden" name="result" value="CANCEL">
  <input type="hidden" name="actionURL" value="">
</html:form>

<% String sAction2 = contextPath+"/issue/typedefine/issueTypeListAction.do"; %>
<html:form name="currentStatusForm" method="POST" action="<%=sAction2%>" target="_self">
    <html:hidden name="selectedRowObj" value=""/>
    <html:hidden name="selectedTabIndex" value=""/>
    <html:hidden name="newTypeName" value=""/>
</html:form>
<script language="javascript">
try {
    changeRowColorsearchTable(<%=firstRowId%>);
    currentRowObj=<%=firstRowId%>;
    typeName ="<%=firstTypeName%>";
    pass(typeName);
} catch(e) {
}
</script>
</body>
</html>
