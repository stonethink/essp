<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="server.essp.issue.stat.viewbean.VbIssueStatPerAccount,server.essp.issue.common.constant.Status"%>

<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<%
String firstRowId="";
String firstTypeName="";
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Stat."/>
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
    if(clickTarget == "Account"){
        cardFrm.location="<%=contextPath%>/issue/stat/issueStatAccountByType.do?accountRid="+Obj.accountRid;
    }
    else if(clickTarget == "Status"){
        var paramStr="rid=&priority=&scope=&fillBy=&principal=&fillDateBegin=&fillDateEnd=&issueId=&issueName=&dueDateBegin=&dueDateEnd=&pageStandard=15&sortInfo=&sortItem=&pageAction=&pageNo=1&issueType=";
//        alert("currentStatus:"+currentStatus);
        paramStr +=("&status="+currentStatus+"&accountId="+Obj.accountRid);
        cardFrm.location="<%=contextPath%>/issue/issue/issueListSingle.do?"+paramStr;
    }
}

function onBodyLoad(){
	try{
        onClickAccount();
        changeRowColorsearchTable(tr0)
        onClickRow(tr0);
	}catch( ex ){
    }
}


function onClickAccount() {
    clickTarget = "Account"
}

function onClickStatus(rowObj) {
    clickTarget = "Status"
    currentStatus = rowObj.id;
}

</script></head>
<body onload="onBodyLoad()">
<html:tabpanel id="issueStatList" width="98%">
  <html:tabtitles>
     <html:tabtitle selected="true" width="100">
         <span class="tabs_title">Issue Stat.</span>
     </html:tabtitle>
  </html:tabtitles>
   <html:tabbuttons>

  </html:tabbuttons>

<html:tabcontents>
  <html:tabcontent styleClass="wind">
      <html:outborder height="90" width="98%">
            <html:table styleId="tableStyle">
              <html:tablehead styleId="tableTitleStyle">
                <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Issue Type">Account</html:tabletitle>
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
              <html:tablebody styleId="tableDataStyle" height="125" id="searchTable">
                  <logic:present name="webVo" scope="request">
                      <logic:iterate id="account" name="webVo" indexId="index">
                          <bean:define id="accountName" name="account" property="accountName" type="java.lang.String" />
                          <bean:define id="accountCode" name="account" property="accountCode" type="java.lang.String" />
                          <bean:define id="accountRid" name="account" property="accountRid" type="java.lang.String" />
                          <bean:define id="receivedCount" name="account" property="receivedCount"  />
                          <bean:define id="processingCount" name="account" property="processingCount"  />
                          <bean:define id="deliveredCount" name="account" property="deliveredCount" />
                          <bean:define id="closedCount" name="account" property="closedCount"  />
                          <bean:define id="rejectedCount" name="account" property="rejectedCount"  />
                          <bean:define id="duplationCount" name="account" property="duplationCount"  />
                          <bean:define id="sum" name="account" property="sum"  />
                          <bean:define id="leftSum" name="account" property="leftSum" />
                          <bean:define id="abnormalCount" name="account" property="abnormalCount"  />
                          <%String selfProperty = "accountRid="+accountRid;%>
                          <html:tablerow
                              oddClass="tableDataOdd"
                              evenClass="tableDataEven"
                              onclick="onClickRow(this)"
                              height="18px"
                              canSelected="true"
                              selfProperty="<%=selfProperty%>"
                              id="<%="tr"+index%>"
                              >
                              <html:tablecolumn styleId="oracelltext" toolTip="<%=(accountCode +" -- " + accountName)%>" ondblclick="onClickAccount();" onclick="onClickAccount();">
                                  <bean:write name="account" property="accountCode"/> -- <bean:write name="account" property="accountName"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=receivedCount.toString()%>" ondblclick="onClickStatus(this);" id="<%=Status.RECEIVED%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="receivedCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=processingCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.PROCESSING%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="processingCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber" toolTip="<%=deliveredCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.DELIVERED%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="deliveredCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=closedCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.CLOSED%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="closedCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=rejectedCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.REJECTED%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="rejectedCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=duplationCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.DUPLATION%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="duplationCount"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=sum.toString()%>"  ondblclick="onClickStatus(this);" id="" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="sum"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=leftSum.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.LEFT%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="leftSum"/>
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber"  toolTip="<%=abnormalCount.toString()%>"  ondblclick="onClickStatus(this);" id="<%=Status.ABNORMAL%>" onclick="onClickStatus(this);">
                                  <bean:write name="account" property="abnormalCount"/>
                              </html:tablecolumn>
                          </html:tablerow>
                      </logic:iterate>
                  </logic:present>
              </html:tablebody>
            </html:table>
        </html:outborder>
      <table valign="top" align="center" width="98%" height="360" cellSpacing="0" cellPadding="0">
        <tr height="3">
            <td></td>
        </tr>
        <tr height="357">
          <td>
            <iframe id="cardFrm" src="" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">            </iframe>
          </td>
        </tr>
      </table>
    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>
</body>
</html>
