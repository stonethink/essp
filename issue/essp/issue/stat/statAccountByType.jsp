<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="server.essp.issue.stat.viewbean.VbIssueStatAccountByType,server.essp.issue.common.constant.Status"%>

<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define  id="accountRid" value="<%= (String)request.getAttribute("accountRid")%>" />

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" CRM V2.1"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
.oracellnumber {
    text-overflow: ellipsis;
    overflow: hidden;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	text-align: right;
    cursor:hand;
}
.oracelltext {
	text-overflow: ellipsis;
	overflow: hidden;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	text-align: left;
     cursor:hand;
}
</style>
<script language="javaScript" type="text/javascript">
var typeName = "";
var currentRowObj;
var newTypeName="";
var clickTarget = "";
var currentStatus = "";
<%-------根据点击的是Account还是Status,下面的卡片链接不同的Action----%>
function onClickRow(Obj){

        var paramStr="rid=&priority=&scope=&fillBy=&principal=&fillDateBegin=&fillDateEnd=&issueId=&issueName=&dueDateBegin=&dueDateEnd=&pageStandard=15&sortInfo=&sortItem=&pageAction=&pageNo=1";
//        alert("currentStatus:"+currentStatus);
        paramStr +=("&accountId=<%=accountRid%>&status="+currentStatus+"&issueType="+Obj.accountRid);
        window.location="<%=contextPath%>/issue/issue/issueListSingle.do?"+paramStr;

}
function onBodyLoad(){
	try{
        onClickAccount();

	}catch( ex ){
    }
}
function onClickAccount() {
    clickTarget = "Account";
}

function onClickStatus(rowObj) {
    clickTarget = "Status"
    currentStatus = rowObj.id;
}
</script>
</head>
<body onload="onBodyLoad();">
<table cellspacing="0" cellpadding="0" height="90%" width="100%">
  <tr>
    <td>
      <html:tabpanel id="issueTypeList" width="100%">
        <html:tabtitles>
          <html:tabtitle selected="true" width="100">
             <span class="tabs_title">Issue Type</span>
          </html:tabtitle>
        </html:tabtitles>
        <html:tabbuttons>        </html:tabbuttons>
        <html:tabcontents>
          <html:tabcontent styleClass="wind">
            <html:outborder height="260" width="100%">
              <html:table styleId="tableStyle">
                <html:tablehead styleId="tableTitleStyle">
                <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Issue Type">Issue Type</html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.RECEIVED%>"><%=Status.RECEIVED%></html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.PROCESSING%>"><%=Status.PROCESSING%></html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.DELIVERED%>"><%=Status.DELIVERED%></html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.CLOSED%>"><%=Status.CLOSED%></html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.REJECTED%>"><%=Status.REJECTED%></html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="<%=Status.DUPLATION%>"><%=Status.DUPLATION%></html:tabletitle>
                <html:tabletitle width="60" styleId="oracolumnnumberheader" toolTip="Sum">Sum</html:tabletitle>
                <html:tabletitle width="60" styleId="oracolumnnumberheader" toolTip="Left Sum">Left Sum</html:tabletitle>
                <html:tabletitle width="70" styleId="oracolumnnumberheader" toolTip="Abnormal">Abnormal</html:tabletitle>
              </html:tablehead>
                <html:tablebody styleId="tableDataStyle" height="260" id="searchTable">
                    <logic:present name="webVo" scope="request">
                        <logic:iterate id="issueTypeStat"  name="webVo" indexId="index" >
                            <bean:define id="issueType" name="issueTypeStat" property="issueType"  type="java.lang.String" />
                            <bean:define id="receivedCount" name="issueTypeStat" property="receivedCount"  />
                            <bean:define id="processingCount" name="issueTypeStat" property="processingCount"  />
                            <bean:define id="deliveredCount" name="issueTypeStat" property="deliveredCount" />
                            <bean:define id="closedCount" name="issueTypeStat" property="closedCount"  />
                            <bean:define id="rejectedCount" name="issueTypeStat" property="rejectedCount"  />
                            <bean:define id="duplationCount" name="issueTypeStat" property="duplationCount"  />
                            <bean:define id="sum" name="issueTypeStat" property="sum"  />
                            <bean:define id="leftSum" name="issueTypeStat" property="leftSum" />
                            <bean:define id="abnormalCount" name="issueTypeStat" property="abnormalCount"  />
                             <%String selfProperty = "accountRid="+issueType;%>
                            <html:tablerow
                                oddClass="tableDataOdd"
                                evenClass="tableDataEven"
                                onclick="onClickRow(this)"
                                height="18px"
                                canSelected="true"
                                selfProperty="<%=selfProperty%>"
                                id="<%="tr"+index%>"
                                >
                                <html:tablecolumn styleId="oracelltext" toolTip="<%=issueType%>" ondblclick="onClickAccount();" onclick="onClickAccount();">
                                    <bean:write name="issueTypeStat" property="issueType"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=receivedCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.RECEIVED%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="receivedCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=processingCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.PROCESSING%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="processingCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=deliveredCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.DELIVERED%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="deliveredCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=closedCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.CLOSED%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="closedCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=rejectedCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.REJECTED%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="rejectedCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=duplationCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.DUPLATION%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="duplationCount"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=sum.toString()%>" ondblclick="onClickStatus(this);" id="" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="sum"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=leftSum.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.LEFT%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="leftSum"/>
                                </html:tablecolumn>
                                <html:tablecolumn styleId="oracellnumber" toolTip="<%=abnormalCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.ABNORMAL%>" onclick="onClickStatus(this);">
                                    <bean:write name="issueTypeStat" property="abnormalCount"/>
                                </html:tablecolumn>
                            </html:tablerow>
                        </logic:iterate>
                    </logic:present>
              </html:tablebody>
              </html:table>
          </html:outborder>
          </html:tabcontent>
        </html:tabcontents>
      </html:tabpanel>
    </td>
  </tr>
</table>
<%----------------------------------------------------------------------------%>
</body>
</html>
