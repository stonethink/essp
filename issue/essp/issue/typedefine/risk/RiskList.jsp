<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="db.essp.issue.IssueRisk"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="selectedRow" name="webVo" property="selectedRow" />
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Risk Influence"/>
  <tiles:put name="jspName" value="Risk Influence"/>
</tiles:insert>

<script language="JavaScript" type="text/javascript">
<!--Begin
var currentRow = ""+"<%=(String)selectedRow%>";  <%--\uFFFD\uFFFD\u01F0\uFFFD\uFFFD\u0461\uFFFD��\uFFFD\uFFFD\uFFFD\u0775\uFFFDid--%>
var currentRowObj;  <%--\uFFFD\uFFFD\u01F0\uFFFD\uFFFD\u0461\uFFFD\uFFFD\uFFFD��\uFFFD\uFFFD\uFFFD\uFFFDid\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\u04BB\uFFFD��\uFFFDid\u03AA"tr0"--%>
var riskInfluence = null;
var transferTypeName = "<bean:write name="webVo" property="typeName"/>";

<%--------------------------\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\u00BC\uFFFD-------------------------------------------%>
function onclickRow(rowObj){
//    alert( "onclickRow" );
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);

    	currentRow = rowObj.id+"";
        riskInfluence = rowObj.riskInfluence;
        //alert(rowObj.riskInfluence);
        //alert(rowObj.id);

    }catch( ex ){
        //alert( "onclickRow " + ex );
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

    var url = "<%=contextPath%>/issue/typedefine/risk/riskAddPreAction.do?typeName="+transferTypeName;
    window.open(encodeURI(url),"status","width=450,height=280,top=250, left=320, toolbar=no,resizable=yes,scrollbars=no,location=no, status=yes");
}

function onEditData(param){

   if (riskInfluence == "null" || riskInfluence == null){
      alert('<bean:message bundle="issue" key="issue.risk.list.edit.error.info"/>');
    }else{
      var url =  "<%=contextPath%>/issue/typedefine/risk/RiskUpdatePreAction.do?typeName="+transferTypeName+"&riskInfluence="+riskInfluence;
      window.open(encodeURI(url),"_riskEdit","width=450,height=280,top=250, left=320,resizable=yes, toolbar=no,scrollbars=no,location=no, status=no");
    }
}

function onDeleteData(param){

    if (riskInfluence == "null" || riskInfluence == null){
      alert('<bean:message bundle="issue" key="issue.risk.list.delete.error"/>');
    }else{
      var delcomfirm = confirm('<bean:message bundle="issue" key="issue.risk.list.delete.confirm"/>');
      var delurl = "<%=request.getContextPath()%>/issue/typedefine/risk/riskDeleteAction.do?typeName="+transferTypeName+"&riskInfluence="+riskInfluence+"&selectedRow="+currentRow;;
      if ( delcomfirm == true ){
        window.location = encodeURI(delurl);
        //alert(delurl);
      }
    }
}

function refreshSelf(){
    //alert( "refreshSelf" );
    var url = "<%=request.getContextPath()%>/issue/typedefine/risk/riskListAction.do?typeName="+transferTypeName+"&selectedRow="+currentRow;
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
<input type="hidden" name="riskInfluence">
<input type="hidden" name="riskMinLevel">
<input type="hidden" name="riskMaxLevel">
<input type="hidden" name="riskWeight">
<input type="hidden" name="riskSequence">
<input type="hidden" name="riskDescription">
</form>
              <html:table  styleId="tableStyle" >
                <logic:notEqual name="webVo" property="deleterefresh" value="YES" scope="request">
                  <html:tablehead styleId="tableTitleStyle">
                      <html:tabletitle width="120" styleId="oracolumntextheader" toolTip="Risk Influence"><bean:message bundle="issue" key="issue.risk.list.riskInfluence"/></html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Min Level"><bean:message bundle="issue" key="issue.risk.list.riskMinLevel"/></html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Max Level"><bean:message bundle="issue" key="issue.risk.list.riskMaxLevel"/></html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Weight"><bean:message bundle="issue" key="issue.risk.list.riskWeight"/></html:tabletitle>
                      <html:tabletitle width="100" styleId="oracolumnnumberheader" toolTip="Sequence"><bean:message bundle="issue" key="issue.risk.list.riskSequence"/></html:tabletitle>
                      <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Description"><bean:message bundle="issue" key="issue.risk.list.riskDescription"/></html:tabletitle>
                  </html:tablehead>
                </logic:notEqual>
              <html:tablebody styleId="tableDataStyle" height="150" id="tableList">

              <bean:define id="classRow0" value="tableDataEven"/>
              <bean:define id="classRow1" value="tableDataOdd"/>

              <logic:present name="webVo" scope="request">
                      <logic:iterate indexId="index" id="risk" name="webVo" property="riskList" scope="request">
                        <bean:define id="riskInfluence" value="<%=((IssueRisk)risk).getComp_id().getInfluence()%>"/>
                        <% String sId = "tr"+index.intValue(); %>
                        <% String sRiskInfluence = "riskInfluence="+(String)riskInfluence; %>
                       <html:tablerow  id="<%=sId%>"
                              onclick="onclickRow(this);"
                              ondblclick="onEditData(this);"
                              styleId="<%=index.intValue()%2==0?classRow0:classRow1%>"
                              selfProperty="<%=sRiskInfluence%>"
                              >
                              <html:tablecolumn id="riskInfluence" styleId="oracelltext" toolTip="<%=((IssueRisk)risk).getComp_id().getInfluence()%>">
                                  <bean:write name="risk" property="comp_id.influence"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=((IssueRisk)risk).getMinLevel().toString()%>">
                                  <bean:write name="risk" property="minLevel"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=((IssueRisk)risk).getMaxLevel().toString()%>">
                                  <bean:write name="risk" property="maxLevel"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=((IssueRisk)risk).getWeight().toString()%>">
                                  <bean:write name="risk" property="weight"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=((IssueRisk)risk).getSequence().toString()%>">
                                  <bean:write name="risk" property="sequence"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=((IssueRisk)risk).getDescription()%>">
                                  <bean:write name="risk" property="description"/>
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
