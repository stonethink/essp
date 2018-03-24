<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="server.essp.issue.typedefine.priority.viewbean.VbPriority"%>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define  id="selectedRowObj"  name="webVo" property="selectedRowObj" />
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define  id="detailSize" name="webVo" property="detailSize" />
<bean:define  id="addPriority" name="webVo" property="addPriority"/>
<bean:define  id="typeName" name="webVo" property="typeName"/>

<%--
String sTName=String.valueOf(typeName);
sTName.trim();
--%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueType Priority"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<%-----------------------------script begin------------------------------%>
<script language="javaScript" type="text/javascript">
var priority = "";
var currentRowObj;
var addPriority="";

<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
        rowObj.scrollIntoView(true);
    	priority = rowObj.cells[0].innerHTML;
    }catch( ex ){
    }
}

function onDblClickEvent(rowObj){
  currentRowObj=rowObj;
  changeRowColor(rowObj);
  rowObj.scrollIntoView(true);
  priority=rowObj.cells[0].innerHTML;
  onEditData();
}

function onAddData() {
    var size=<%=detailSize%>;
    var isize=eval(size+1);
    openWindow( encodeURI('<%=contextPath%>/issue/typedefine/priority/issuePriorityAddPre.do?typeName=<%=typeName%>&DetailSize='+isize) );
}

function onEditData(){
  var i=0;
  i=<%=detailSize%>;
  if (i>0){
  openWindow(encodeURI('<%=contextPath%>/issue/typedefine/priority/issuePriorityUpdatePre.do?typeName=<%=typeName%>&priority='+priority));
  }
}

function onDeleteData(){
  var i=0;
  i=<%=detailSize%>;
  if (i>0){
      if (confirm("Are you really want to delete this item?")==true){
          dataForm.action="<%=contextPath%>/issue/typedefine/priority/issuePriorityDeleteAction.do";
          dataForm.typeName.value="<%=typeName%>";
          dataForm.priority.value=priority;
          dataForm.submit();
      }
  }
}


function onBodyLoad(){
	try{
       addPriority="<%=addPriority%> ";
       if (searchAddTypeName()==false){
           onclickRow( <%=selectedRowObj%> );
       }
	}catch( ex ){
    }
}

function searchAddTypeName(){
  var sPriority;
  var i=<%=detailSize%>;
  if (i<=1){
    return false;
  }
  for (i=0; i < searchTable.rows.length; i++) {
         sPriority=searchTable.rows(i).cells(0).innerText;
         if (sPriority==addPriority){
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
    var width = 400;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
			    +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
    window.open(url, windowTitle, option);
}


function refreshSelf(){
    try{
        if( currentRowObj != null ){
    		currentStatusForm.selectedRowObj.value=currentRowObj.id;
        }
        currentStatusForm.addPriority.value=addPriority;
        currentStatusForm.typeName.value="<%=typeName%>";
	    currentStatusForm.submit();
    }catch( ex ){
    }
}

function onRefreshData(url,typeName) {
    currentStatusForm.action=url;
    currentStatusForm.typeName.value=typeName;
    currentStatusForm.submit();
}

</script>
<%-----------------------------script end--------------------------------%>
</head>
<body onload="onBodyLoad()">
<form name="dataForm" action="<%=contextPath%>/issue/typedefine/priority/issuePriorityAddAction.do" method="POST">
<input type="hidden" name="typeName" value=""/>
<input type="hidden" name="priority" value=""/>
<input type="hidden" name="sequence" value=""/>
<input type="hidden" name="duration" value=""/>
<input type="hidden" name="description" value=""/>
</form>

<html:tabpanel id="issuePriorityList" width="98%">

  <html:tabcontents>
  <html:tabcontent styleClass="wind">

            <html:table styleId="tableStyle">
              <html:tablehead styleId="tableTitleStyle">
                <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Priority">Priority</html:tabletitle>
                <html:tabletitle width="160" styleId="oracolumnnumberheader" toolTip="Duration">Duration of Due Date(days)</html:tabletitle>
                <html:tabletitle width="110" styleId="oracolumnnumberheader" toolTip="Sequence">Sequence</html:tabletitle>
                <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
              </html:tablehead>
              <html:tablebody styleId="tableDataStyle" id="searchTable" height="150">
                <logic:iterate name="webVo" property="detail" id="oneBean" indexId="indexId">

                  <html:tablerow id="<%="tr"+indexId.intValue()%>"
                      oddClass="tableDataOdd"
                      evenClass="tableDataEven"
                      onclick="onclickRow(this);"
                      ondblclick="onDblClickEvent(this)"
                      height="18px"
                      >
                    <bean:define id="ttPriority" name="oneBean" property="priority"/>
                    <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(ttPriority)%>">
                      <bean:write name="oneBean" property="priority"/>
                    </html:tablecolumn>
                    <bean:define id="ttDuration" name="oneBean" property="duration"/>
                    <html:tablecolumn styleId="oracellnumber" toolTip="<%=String.valueOf(ttDuration)%>">
                      <bean:write name="oneBean" property="duration"/>
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

    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>

<% String sAction = contextPath+"/issue/typedefine/priority/issuePriorityList.do"; %>
<html:form name="currentStatusForm" method="POST" action="<%=sAction%>" target="_self">
    <html:hidden name="selectedRowObj" value=""/>
    <html:hidden name="addPriority" value=""/>
    <html:hidden name="typeName" value=""/>
</html:form>

</body>
</html>
