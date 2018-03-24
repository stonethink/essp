<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="server.essp.issue.issue.viewbean.VbIssue,server.essp.issue.issue.viewbean.VbIssueList,server.essp.issue.common.constant.Status,server.essp.issue.common.constant.PageAction"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="hasPrevious" name="webVo" property="previous"/>
<bean:define id="hasNext" name="webVo" property="next"/>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" CRM V2.1"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
.oracolumntextheader {
	BACKGROUND-COLOR: #94aad6;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	text-align: left;
    cursor:hand;
}

.oracolumnnumberheader {
	BACKGROUND-COLOR: #94aad6;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	font-style: normal;
	font-weight: bold;
	text-align: right;
    cursor:hand;
}
/* standard link behaviors */
a:visited, a:active,hyperlink
{
	color: #4682B4;
	text-decoration: underline;
}
a:hover,
{
	color: #ff6600;
	text-decoration: underline;
}


</style>
<script language="JavaScript" type="text/javascript">
<%--- 翻页功能 ----%>
    function sort(itemName) {
        issueForm.sortItem.value=itemName;
        issueForm.pageNo.value="1";
        issueForm.submit();
    }
    function gotoIssue(rowObj) {
        currentRowObj = rowObj;
        issueRid = rowObj.rid;
        var height = 1200;
        var width = 1000;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
        url="<%=contextPath%>/issue/issue/allDetailOfIssueList.do?issueRid="+issueRid;
        var option="toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
        window.open(url,"",option);
    }

function firstPage() {
    issueForm.pageAction.value="<%=PageAction.ACTION_FIRSTPAGE%>";
    issueForm.submit();
}

function lastPage() {
    issueForm.pageAction.value="<%=PageAction.ACTION_LASTPAGE%>";
    issueForm.submit();
}

function nextPage() {
    issueForm.pageAction.value="<%=PageAction.ACTION_NEXTPAGE%>";
    issueForm.submit();
}

function prevPage() {
    issueForm.pageAction.value="<%=PageAction.ACTION_PREVPAGE%>";
    issueForm.submit();
}
function goto(pageNo) {
    issueForm.pageAction.value="<%=PageAction.ACTION_GOTOPAGE%>";
    issueForm.pageNo.value = pageNo;
    issueForm.submit();
}
function onBodyLoad(){
    try{
        changeRowColorsearchTable(tr0);
    }catch(e){
    }
}
</script>
</head>
<body onload="onBodyLoad();">
<form name="issueForm" action="<%=contextPath%>/issue/issue/issueListSingle.do" method="POST">
    <html:hidden name="rid" beanName="searchForm"/>
    <html:hidden name="issueType" beanName="searchForm"/>
    <html:hidden name="accountId" beanName="searchForm"/>
    <html:hidden name="priority" beanName="searchForm"/>
    <html:hidden name="scope" beanName="searchForm"/>
    <html:hidden name="status" beanName="searchForm"/>
    <html:hidden name="fillBy" beanName="searchForm"/>
    <html:hidden name="principal" beanName="searchForm"/>
    <html:hidden name="fillDateBegin" beanName="searchForm"/>
    <html:hidden name="fillDateEnd" beanName="searchForm"/>
    <html:hidden name="issueId" beanName="searchForm"/>
    <html:hidden name="issueName" beanName="searchForm"/>
    <html:hidden name="dueDateBegin" beanName="searchForm"/>
    <html:hidden name="dueDateEnd" beanName="searchForm"/>
    <html:hidden name="sortInfo" beanName="webVo"/>
    <html:hidden name="sortItem" value=""/>
    <html:hidden name="pageNo" beanName="webVo"/>
    <html:hidden name="pageAction" value=""/>
    <input type="hidden" name="pageStandard" value="15">
</form>
<table cellspacing="0" cellpadding="0" height="90%" width="100%">
  <tr>
    <td>
      <html:tabpanel id="issueTypeList" width="100%">
        <html:tabtitles>
          <html:tabtitle selected="true" width="100"><span class="tabs_title">Issue List</span></html:tabtitle>
        </html:tabtitles>
        <html:tabbuttons>        </html:tabbuttons>
        <html:tabcontents>
          <html:tabcontent styleClass="wind">
            <html:outborder height="260" width="100%">
              <html:table styleId="tableStyle">
                <html:tablehead styleId="tableTitleStyle">
                    <html:tabletitle width="10" styleId="oracolumntextheader" toolTip="(Abnormal)" onclick="sort('abnormal')"></html:tabletitle>
                    <html:tabletitle width="180" styleId="oracolumntextheader" toolTip="Account" onclick="sort('accountId')">Account</html:tabletitle>
                    <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Issue Title" onclick="sort('issueName')">Issue Title</html:tabletitle>
                    <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Issue Type" onclick="sort('issueType')">Issue Type</html:tabletitle>
                    <html:tabletitle width="50" styleId="oracolumntextheader" toolTip="Priority" onclick="sort('priority')">Priority</html:tabletitle>
                    <html:tabletitle width="70" styleId="oracolumntextheader" toolTip="Filled Date" onclick="sort('filleDate')">Filled Date</html:tabletitle>
                    <html:tabletitle width="70" styleId="oracolumntextheader" toolTip="Due Date" onclick="sort('dueDate')">Due Date</html:tabletitle>
                    <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Principal" onclick="sort('principal')">Principal</html:tabletitle>
                    <html:tabletitle width="70" styleId="oracolumntextheader" toolTip="Status" onclick="sort('issueStatus')">Status</html:tabletitle>
                </html:tablehead>
                <html:tablebody styleId="tableDataStyle" height="260" id="searchTable">
                  <logic:present name="webVo">
                    <logic:iterate id="oneBean" name="webVo" property="detail" indexId="currentIndex">
                        <bean:define id="currentRid" name="oneBean" property="rid"/>
                        <bean:define id="extendProperty" value="<%="rid='"+currentRid+"'"%>"/>
                      <html:tablerow id="<%="tr"+currentIndex%>" oddClass="tableDataOdd" evenClass="tableDataEven" height="18px" canSelected="true" ondblclick="gotoIssue(this);" selfProperty="<%=extendProperty%>">
                        <bean:define id="abnormal" name="oneBean" property="abnormal"/>
                        <bean:define id="accountId" name="oneBean" property="accountId"/>
                        <bean:define id="accountName" name="oneBean" property="accountName"/>
                        <bean:define id="issueType" name="oneBean" property="issueType"/>
                        <bean:define id="issueName" name="oneBean" property="issueName"/>
                        <bean:define id="filleDate" name="oneBean" property="filleDate"/>
                        <bean:define id="priority" name="oneBean" property="priority"/>
                        <bean:define id="dueDate" name="oneBean" property="dueDate"/>
                        <bean:define id="principal" name="oneBean" property="principal"/>
                        <bean:define id="issueStatus" name="oneBean" property="issueStatus"/>
                        <%String principalName=HrFactory.create().getName((String)principal);%>
                        <html:tablecolumn styleId="oracelltext">
                          <B>
                            <font color="red"><%=abnormal%>                            </font>
                          </B>
                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(accountId+"---"+accountName)%>"><%=accountId+"---"+accountName%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueName)%>"><%=issueName%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueType)%>">  <%=issueType%>                      </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(priority)%>"><%=priority%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(filleDate)%>"><%=filleDate%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(dueDate)%>"><%=dueDate%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=principal+"/"+principalName%>"><%=principalName%>                        </html:tablecolumn>
                        <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueStatus)%>"><%=issueStatus%>                        </html:tablecolumn>
                      </html:tablerow>
                    </logic:iterate>
                  </logic:present>
                </html:tablebody>
              </html:table>
            </html:outborder>
            <logic:present name="webVo" property="startNo">
                 <logic:greaterThan value="0" name="webVo" property="totalPage">
            <table valign="top" align="center" width="100%" height="23" cellSpacing="0" cellPadding="0">
                <tr valign="bottom">
                <td height="18" align="right" width="35%">
               <%--  <font class="resultsNavigation">Results:<bean:write name="webVo" property="startNo"/> - <bean:write name="webVo" property="endNo"/></font>
               --%>
                <%-- 可以向前翻页 --%>
                <% if(((Boolean)hasPrevious).booleanValue()) { %>
                   <a href="#" onclick="firstPage()"><img alt="First" src="/essp/image/alllf.gif" border="0" /></a>
                   <a href="#" onclick="prevPage()"><img alt="Previous" src="/essp/image/alf.gif" border="0" /></a>
                <% }else{ %>
                  <img alt="First" src="/essp/image/alllf.gif" border="0" />
                  <img alt="Previous" src="/essp/image/alf.gif" border="0" />
                <% }%>
                &nbsp;
                </td>
                <td align="left" class="list_range" width="30%" >
                    <%/////显示链接页号
                    VbIssueList webVo = (VbIssueList)request.getAttribute("webVo");
                    int[] range = webVo.getPageNoLink();
                    int pageNo = webVo.getPageNo();
                    for(int i = 0;i < range.length ;i ++){
                        boolean hasComma = (i != (range.length - 1));//最后一项之后没有逗号
                        if(range[i] == pageNo){
                            %>
                            <%=range[i]%><%=hasComma?",":""%>
                            <%
                        }else{
                            %>
                            <a href="#" onclick="goto('<%=range[i]%>')"><%=range[i]%></a><%=hasComma?",":""%>
                            <%
                        }
                    }
                    %>
                </td>
                <td align="left" width="35%">
                    &nbsp;
                    <%-- 可以向后翻页 --%>
                <% if(((Boolean)hasNext).booleanValue()) { %>
                   <a href="#" onclick="nextPage()"><img alt="Next" src="/essp/image/art.gif" border="0" /></a>
                   <a href="#" onclick="lastPage()"><img alt="Last" src="/essp/image/allrt.gif" border="0" /></a>
                <% }else{ %>
                   <img alt="Next" src="/essp/image/art.gif" border="0" />
                   <img alt="Last" src="/essp/image/allrt.gif" border="0" />
                <% } %>
               </td>
               <%--
               <td align="right"><font class="resultsNavigation">Page:<bean:write name="webVo" property="pageNo"/> / <bean:write name="webVo" property="totalPage"/></font>&nbsp;&nbsp;</td>
               --%>
               </tr>
            </table>
            </logic:greaterThan>
            </logic:present>
          </html:tabcontent>
        </html:tabcontents>
      </html:tabpanel>
    </td>
  </tr>
</table>
<%----------------------------------------------------------------------------%>
</body>
</html>
