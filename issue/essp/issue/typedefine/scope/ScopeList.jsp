<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="db.essp.issue.IssueScope"%>
<bean:define  id="selectedRowObj"  name="webVo" property="selectedRowObj" />
<bean:define  id="typeName"  name="webVo" property="typeName" />
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define  id="detailSize" name="webVo" property="detailSize" />
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Issue Scope"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>

<script language="javascript" type="text/javascript" >
var oldColor=null;
var oldObj=null;
var currentScope = null;
var currentRowObj = null;
function changeRowColor(obj) {
	if(oldObj!=null) {
		oldObj.style.backgroundColor=oldColor;
	}
    oldColor=obj.style.backgroundColor;
    oldObj=obj;
    obj.style.backgroundColor="#ccccff";
}

function onclickRow(rowObj){
    try{
    	currentScope = rowObj.scope;
        currentRowObj = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
       // alert( "onclickRow " + ex );
    }
}

function ondbClick(rowObj){
  onclickRow(rowObj);
  onEditData(rowObj);
}
function onAddData(){
    var option = "Top=200,Left=300,Height=250,Width=450,toolbar=no,resizable=yes,scrollbars=no,status=yes";
    window.open(encodeURI("<%=contextPath%>/issue/typedefine/scope/scopeAddPreAction.do?typeName=<%=typeName%>"),"",option);
}
function onDeleteData(param){
    if( currentScope!=null && confirm('<bean:message bundle="issue" key="issue.scope.delete.confirm" />') ){
        //window.location="<%=contextPath%>/issue/typedefine/scope/scopeDeleteAction.do?typeName=<%=typeName%>&scope=" + currentScope + "&selectedRowObj=" + currentRowObj.id;
        dataForm.action="<%=contextPath%>/issue/typedefine/scope/scopeDeleteAction.do";
        dataForm.typeName.value="<%=typeName%>";
        dataForm.scope.value=currentScope;
        dataForm.selectedRowObj.value=currentRowObj.id;
        dataForm.submit();
    }
}
function onEditData(param){
    if( currentScope!=null){
    	var option = "Top=200,Left=300,Height=250,Width=450,toolbar=no,resizable=yes,scrollbars=no,status=yes";
    	window.open(encodeURI("<%=contextPath%>/issue/typedefine/scope/scopeUpdatePreAction.do?typeName=<%=typeName%>&scope=" + currentScope),
                "",option);
    }
}

function onBodyLoad(){
	try{
          if(document.getElementById("<%=selectedRowObj%>")==null)
              onclickRow( tr0 );
           else
              onclickRow( <%=selectedRowObj%> );
              //alert(parent.btnPanel.innerHTML)   ;
	}catch( ex ){
     		//alert( "onBodyLoad() " + ex  );
	}
}
function refreshSelf(){
        if( currentRowObj != null )
    		currentStatusForm.selectedRowObj.value=currentRowObj.id;
        currentStatusForm.submit();
}

function onRefreshData(url,typeName) {
    currentStatusForm.action=url;
    currentStatusForm.typeName.value=typeName;
    currentStatusForm.submit();
}
</script>

</head>
<body onload="onBodyLoad()">
<form name="dataForm" action="" method="POST">
<input type="hidden" name="typeName">
<input type="hidden" name="scope">
<input type="hidden" name="vision">
<input type="hidden" name="sequence">
<input type="hidden" name="description">
<input type="hidden" name="selectedRow">
<input type="hidden" name="selectedRowObj">
</form>
              <html:table  styleId="tableStyle" >
                  <html:tablehead styleId="tableTitleStyle">
                      <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Scope">Scope</html:tabletitle>
                      <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Vision To Customer">Vision To Customer</html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Sequence">Sequence</html:tabletitle>
                      <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
                  </html:tablehead>
              <html:tablebody styleId="tableDataStyle" height="150">
                  	<bean:define id="dataCss" value=""/>
                      <logic:iterate id="element" name="webVo" property="detail" type="db.essp.issue.IssueScope" indexId="index">
                         <% IssueScope issueScope = (IssueScope)element;
                            String scope = issueScope.getComp_id().getScope();
                            String vision = issueScope.getVision();
                            Long seq = issueScope.getSequence();
                            String des = issueScope.getDescription();
                            String selfProperty="scope='"+scope+"'";%>
                        <% String sId = "tr"+index.intValue(); %>
                        <% String sStyleId = index.intValue()%2==1?"tableDataEven":"tableDataOdd"; %>
                        <html:tablerow  id="<%=sId%>"
                              onclick="onclickRow(this);"
                              ondblclick="ondbClick(this);"
                              styleId="<%=sStyleId%>"
                              selfProperty="<%=selfProperty%>"
                              >
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=scope%>">
                                  <bean:write name="element" property="comp_id.scope"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=vision%>">
                                  <bean:write name="element" property="vision"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=seq.toString()%>">
                                  <bean:write name="element" property="sequence"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=des%>">
                                  <bean:write name="element" property="description"/>
                              </html:tablecolumn>
                          </html:tablerow>
                      </logic:iterate>
              </html:tablebody>
          </html:table>
</body>

<% String sAction = contextPath+"/issue/typedefine/scope/scopeListAction.do"; %>
<html:form name="currentStatusForm" method="POST" action="<%=sAction%>" target="_self">
    <html:hidden name="selectedRowObj" value=""/>
    <input type="hidden" name="typeName" value="<%=typeName%>">
    <input type="hidden" name="detailSize" value="<%=detailSize%>">
</html:form>
</html>
