<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="db.essp.issue.IssueStatus"%>

<%--�����--%>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define id="selectedRow" name="webVo" property="selectedRow" />

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Issue Status"/>
  <tiles:put name="jspName" value="Issue Status"/>
</tiles:insert>

<script language="JavaScript" type="text/javascript">
<!--Begin
var currentRow = ""+"<%=(String)selectedRow%>";  <%--��ǰ��ѡ�е���ݵ�id--%>
var currentRowObj;  <%--��ǰ��ѡ���ж����id�������һ�е�idΪ"tr0"--%>
var statusName = null;

var transferTypeName = "<bean:write name="webVo" property="typeName"/>";

<%--------------------------�����¼�-------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);

    	currentRow = rowObj.id+"";
        statusName = rowObj.statusName;
        //alert(rowObj.statusName);
       // alert(currentRow);
    }catch( ex ){
        // alert( "onclickRow " + ex );
    }
}

<!--==========================================================================================-->
function onBodyLoad(){
	try{
          onclickRow(<%=selectedRow%>);
	}catch( ex ){
     		//alert( "onBodyLoad() " + ex  );
	}

}

function onAddData(param){
    var url =  "<%=request.getContextPath()%>/issue/typedefine/status/AcStatusAddPre.do?typeName="+transferTypeName;
    window.open(encodeURI(url),"status","width=480,height=270,top=250, left=320, toolbar=no, resizable=yes,scrollbars=no,location=no, status=yes");
    //window.open(url,"status","");
}

function onEditData(param){

   if (statusName == "null" || statusName == null){
      alert('<bean:message bundle="issue" key="issue.status.list.edit.error.info"/>');
    }else{
      var url =  "<%=request.getContextPath()%>/issue/typedefine/status/statusUpdatePreAction.do?typeName="+transferTypeName+"&statusName="+statusName;
      window.open(encodeURI(url),"_edit","width=480,height=270,top=250, left=320, toolbar=no,resizable=yes,scrollbars=no,location=no, status=yes");
    }
}

function onDeleteData(param){
  if (statusName == "null" || statusName == null){
      alert('<bean:message bundle="issue" key="issue.status.list.delete.error.info"/>');
    }else{
      var delcomfirm = confirm('<bean:message bundle="issue" key="issue.status.list.delete.confirm"/>');
      var delurl = "<%=request.getContextPath()%>/issue/typedefine/status/statusDeleteAction.do?typeName="+transferTypeName+"&statusName="+statusName+"&selectedRow="+currentRow;
      // alert(delurl);
      if ( delcomfirm == true ){
        //window.location = delurl;
        dataForm.action="<%=contextPath%>/issue/typedefine/status/statusDeleteAction.do";
        dataForm.typeName.value=transferTypeName;
        dataForm.statusName.value=statusName;
        dataForm.selectedRow.value=currentRow;
        dataForm.submit();
      }
    }
}

function refreshSelf(){
    //alert( "refreshSelf" );
    var url = "<%=request.getContextPath()%>/issue/typedefine/status/statusListAction.do?typeName="+transferTypeName+"&selectedRow="+currentRow;
    //setTimeout ('window.location="'+url+'"',300);
    window.location=encodeURI(url);
}

function onRefreshData(url,typeName) {
    issueTypeForm.action=url;
    issueTypeForm.typeName.value=typeName;
    issueTypeForm.submit();
}
//End-->
</script>
</head>
<body bgcolor="#ffffff" onload="onBodyLoad();">
<form name="issueTypeForm" action="" method="POST">
<input type="hidden" name="typeName">
</form>
<form name="dataForm" action="" method="POST">
<input type="hidden" name="typeName">
<input type="hidden" name="statusName">
<input type="hidden" name="statusSequence">
<input type="hidden" name="statusBelongTo">
<input type="hidden" name="statusRelationDate">
<input type="hidden" name="statusDescription">
<input type="hidden" name="selectedRow">
</form>
              <html:table  styleId="tableStyle" >
                <logic:notEqual name="webVo" property="deleterefresh" value="YES" scope="request">
                  <html:tablehead styleId="tableTitleStyle">
                      <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Issue Status">Issue Status</html:tabletitle>
                      <html:tabletitle width="120" styleId="oracolumntextheader" toolTip="Belong To">Belong To</html:tabletitle>
                      <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Relation Date">Relation Date</html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Sequence">Sequence</html:tabletitle>
                      <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
                  </html:tablehead>
                </logic:notEqual>
              <html:tablebody styleId="tableDataStyle" height="150">

              <bean:define id="classRow0" value="tableDataEven"/>
              <bean:define id="classRow1" value="tableDataOdd"/>

              <logic:present name="webVo" scope="request">
                      <logic:iterate indexId="index" id="status" name="webVo" property="statusList" scope="request">
                        <bean:define id="statusName" value="<%=((IssueStatus)status).getComp_id().getStatusName()%>"/>
                        <% String sId = "tr"+index.intValue(); %>
                              <% String sStatusName = "statusName="+(String)statusName; %>
                        <html:tablerow  id="<%=sId%>"
                              onclick="onclickRow(this);"
                              ondblclick="onEditData(this);"
                              styleId="<%=index.intValue()%2==0?classRow0:classRow1%>"
                              selfProperty="<%=sStatusName%>"
                              >
                              <html:tablecolumn id="statusName" styleId="oracelltext" toolTip="<%=((IssueStatus)status).getComp_id().getStatusName()%>">
                                  <bean:write name="status" property="comp_id.statusName"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=((IssueStatus)status).getBelongTo()%>">
                                  <bean:write name="status" property="belongTo"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=((IssueStatus)status).getRelationDate()%>">
                                  <bean:write name="status" property="relationDate"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=((IssueStatus)status).getSequence().toString()%>">
                                  <bean:write name="status" property="sequence"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=((IssueStatus)status).getDescription()%>">
                                  <bean:write name="status" property="description"/>
                              </html:tablecolumn>
                        </html:tablerow>
                    </logic:iterate>
                  </logic:present>
              </html:tablebody>
          </html:table>
<logic:equal name="webVo" property="deleterefresh" value="YES" scope="request">
    <script language="javascript" type="text/javascript">
        refreshSelf();
    </script>
</logic:equal>

</body>
</html>
