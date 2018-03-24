<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="server.essp.issue.issue.viewbean.VbIssue,server.essp.issue.issue.viewbean.VbIssueList,server.essp.issue.common.constant.Status,server.essp.issue.common.constant.PageAction"%>
<%@page import="server.essp.issue.issue.form.AfIssueSearch"%>
<%@page import="itf.hr.HrFactory"%>
<%@page import="c2s.essp.common.user.DtoUser"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="loginId" name="user" property="useLoginName"/>
<bean:define id="hasPrevious" name="webVo" property="previous"/>
<bean:define id="hasNext" name="webVo" property="next"/>
<%
   String firstRid="";
   String firstRowId="";
   String firstIssueStatusBelongto="";
   String firstIsPrincipal="";
   String firstIsPm="";
   String isPrincipal="false";
   String isPm="false";

   AfIssueSearch isf=new AfIssueSearch();
   if(request.getSession().getAttribute("searchForm")!=null){
    isf=(AfIssueSearch)request.getSession().getAttribute("searchForm");
   }
   boolean hasAccountCondition=false;
   boolean hasTypeCondition=false;
   if(isf.getAccountId()!=null&&!isf.getAccountId().equals("")){
     hasAccountCondition=true;
   }
   if(isf.getIssueType()!=null&&!isf.getIssueType().equals("")){
     hasTypeCondition=true;
   }
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
.select_account {width:180;}
.select_type {width:120;}
.select_status {width:120;}
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
<script language="javascript" src="<%=contextPath%>/issue/selectbox.js"></script>
<script language="javaScript" type="text/javascript">
var issueRid = "";
var issueStatusBelongto="";
var currentRowObj;
var isPrincipal="";
var isPm="";

<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	issueRid = rowObj.rid;
        issueStatusBelongto=rowObj.belongto;
        isPrincipal=rowObj.isPrincipal;
        isPm=rowObj.isPm;
    	pass(issueRid);
    }catch( ex ){
        //alert(ex);
    }
}
function ondblclickRow(rowObj){
     var height = 1200;
    var width = 1000;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
    url="<%=contextPath%>/issue/issue/allDetailOfIssueList.do?issueRid="+issueRid;

     var option="toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
    window.open(url,"",option);
}
<%----- 弹出新增窗口 ------%>
function onAddData() {
    openWindow( '<%=contextPath%>/issue/issue/issueAddPre.do?NotForEssp=yes');
}
<%----- 删除操作 ------%>
function onDeleteData() {
    if(currentRowObj==null) {
        alert("Please select a row first!");
        return;
    }
    if(confirm("Do you want to delete this row?")) {
        if(cardFrm.issueGeneral.checkDelete()) {
            issueForm.action="<%=contextPath%>/issue/issue/issueDelete.do";
            issueForm.rid.value=issueRid;
            issueForm.submit();
        }
    }
}
<%------- 弹出选择查询条件窗口 -------%>
function onSearchData(){
    var height = 360;
    var width = 680;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
			    +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
<%--    window.open('<%=contextPath%>/issue/issue/issueReload.do', 'Reload', option);--%>
    var result = new HashMap();
//    window
    window.showModalDialog('<%=contextPath%>/issue/issue/issueReload.do', result, "dialogHeight:360px;dialogWidth:680px;dialogLeft:(screen.width - width) / 2; dialogTop:(screen.height - height) / 2;");
    if(result.size() == 0)
      return;
    issueForm.issueType.value=result.get("issueType");
    issueForm.accountId.value=result.get("accountId");
    issueForm.priority.value=result.get("priority");
    issueForm.scope.value=result.get("scope");
    issueForm.status.value=result.get("status");
    issueForm.fillBy.value=result.get("fillBy");
    issueForm.resolveBy.value=result.get("resolveBy");
    issueForm.principal.value=result.get("principal");
    issueForm.fillDateBegin.value=result.get("fillDateBegin");
    issueForm.fillDateEnd.value=result.get("fillDateEnd");
    issueForm.issueId.value=result.get("issueId");
    issueForm.issueName.value=result.get("issueName");
    issueForm.dueDateBegin.value=result.get("dueDateBegin");
    issueForm.dueDateEnd.value=result.get("dueDateEnd");
    issueForm.pageNo.value="1";
    issueForm.action="<%=contextPath%>/issue/issue/issueList.do";
    issueForm.submit();

}


function onBodyLoad(){
	try{
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
    var height = 430;
    var width = 580;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
			    +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
    window.open(url, windowTitle, option);
}

function pass(issueRid){
    //alert( "pass(" + issueRid + ")" );
    var isPrincipal=false;
    if( issueRid != null && issueRid!=""){
        cardFrm.onRefreshData(issueRid,isPrincipal,isPm);
   }
}
<%---- 按字段排序  ------%>
function sort(itemName) {
    issueForm.functionType.value="SORT";
    issueForm.pageNo.value="1";
    issueForm.sortItem.value=itemName;
    issueForm.submit();
}
<%---- 分页查询  ------%>
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
<%---- 快速查询选项 ------%>
function onChangeSearchIssueType(IssueType){
    issueForm2.action="<%=contextPath%>/issue/issue/issueList.do?CleanAddListSession=yes";
    issueForm2.submit();
}
<%---- 导出数据 ------%>
function onExportData(){
    //若没有数据则禁止导出
    if(issueForm.totalPage.value <= 0){
        alert("No data to export!");
        return;
    }
    var height = 360;
    var width = 750;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
			    +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px";
    var url='<%=contextPath%>/issue/issue/issueExportPre.do';
   // var url='<%=contextPath%>/ExcelExport?actionId=FISIssueListExportAction';
    window.open(url,"",option);
}
</script></head>
<body onload="onBodyLoad()">
<bean:define id="typeNameList" name="webVo" property="typeName"/>


<html:tabpanel id="issueTypeList" width="98%">
  <html:tabtitles>
    <html:tabtitle selected="true" width="95">
      <span class="tabs_title">
        <%String myIssueTitle="Issue List";
        if(session.getAttribute("MyIssue").toString().equals("Submit")){
          myIssueTitle="Submited Issue";
      }else if(session.getAttribute("MyIssue").toString().equals("Responsible")){
        myIssueTitle="Responsible Issue";
      }else if(session.getAttribute("MyIssue").toString().equals("Resolve")){
        myIssueTitle="Resolve Issue";
      }        %>
        <%=myIssueTitle%></span>
    </html:tabtitle>
  </html:tabtitles>
  <html:tabbuttons>
 <html:form action="" name="issueForm2" id="issueForm2" method="POST">
    <bean:define id="accountIdValue" name="searchForm" property="accountId"/>
    <bean:define id="issueTypeValue" name="searchForm" property="issueType"/>
    <input type="hidden" name="functionType" value="SEARCH">
    <html:hidden name="sortInfo" beanName="webVo"/>
    <html:hidden name="sortItem" value=""/>
    <html:hidden name="pageNo" beanName="webVo"/>
    <html:hidden name="pageAction" value=""/>
    <html:hidden name="pageStandard" value="8"/>

   <html:select name="accountId" beanName="webVoQuicklySearch"  onchange="onChangeSearchIssueType(this.value)" styleId="select_account" styleClass="select_account">
            <logic:notPresent name="webVoQuicklySearch" property="accountList">
              <option value="">--Please select account--</option>
            </logic:notPresent>
            <logic:present name="webVoQuicklySearch" property="accountList">
              <html:optionsCollection name="webVoQuicklySearch" property="accountList"/>
            </logic:present>
   </html:select>

          <html:select name="issueType" beanName="webVoQuicklySearch"   onchange="onChangeSearchIssueType(this.value)" styleId="select_type" styleClass="select_type">
            <html:optionsCollection name="webVoQuicklySearch" property="issueTypeList" styleClass=""/>
          </html:select>

          <html:select name="status" beanName="webVoQuicklySearch"   onchange="onChangeSearchIssueType(this.value)" styleId="select_status" styleClass="select_status">
            <html:optionsCollection name="webVoQuicklySearch" property="statusList" styleClass=""/>
          </html:select>

          <input type="button" name="searchBtn" style="width:50px" value='Search' class="button" onClick="onSearchData()"/>

          <logic:equal value="Submit" name="MyIssue" scope="session">
           <input type="button" name="addBtn" style="width:50px" value='Add' class="button" onClick="onAddData()"/>
          <input type="button" name="deleteBtn" style="width:50px" value='Delete' class="button" onClick="onDeleteData()"/>
         </logic:equal>
          <input type="button" name="exportBtn" style="width:50px" value='Export' class="button" onClick="onExportData()"/>
 </html:form>
</html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
      <html:outborder height="140" width="98%">
        <html:table styleId="tableStyle">
          <html:tablehead styleId="tableTitleStyle">
            <html:tabletitle width="10" styleId="oracolumntextheader" toolTip="(Abnormal)" onclick="sort('abnormal')"></html:tabletitle>
            <%if(!hasAccountCondition){%>
            <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Account" onclick="sort('accountId')">Account</html:tabletitle>
            <%}%>
            <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Issue ID" onclick="sort('issueId')">Issue ID</html:tabletitle>
             <html:tabletitle width="140" styleId="oracolumntextheader" toolTip="Issue Title" onclick="sort('issueName')">Issue Title</html:tabletitle>
<%if(!hasTypeCondition){%>
            <html:tabletitle width="40" styleId="oracolumntextheader" toolTip="Issue Type" onclick="sort('issueType')">Type</html:tabletitle>
<%}%>
            <html:tabletitle width="50" styleId="oracolumntextheader" toolTip="Priority" onclick="sort('priority')">Priority</html:tabletitle>
            <html:tabletitle width="65" styleId="oracolumntextheader" toolTip="Filled Date" onclick="sort('filleDate')">Filled Date</html:tabletitle>
            <html:tabletitle width="65" styleId="oracolumntextheader" toolTip="Due Date" onclick="sort('dueDate')">Due Date</html:tabletitle>
            <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Principal" onclick="sort('principal')">Principal</html:tabletitle>
            <html:tabletitle width="60" styleId="oracolumntextheader" toolTip="Status" onclick="sort('issueStatus')">Status</html:tabletitle>
          </html:tablehead>
          <html:tablebody styleId="tableDataStyle" height="140" id="searchTable">
            <logic:present name="webVo">
              <logic:iterate id="oneBean" name="webVo" property="detail" indexId="currentIndex">
                <bean:define id="currentRid" name="oneBean" property="rid"/>
                <bean:define id="issueStatusBelongto" name="oneBean" property="issueStatusBelongto"/>
                <bean:define id="principal" name="oneBean" property="principal"/>
                <bean:define id="principalScope" name="oneBean" property="principalScope"/>
                <bean:define id="currentIsPrincipal" name="oneBean" property="principalFlag"/>
                <bean:define id="currentIsPm" name="oneBean" property="pmFlag"/>
                <bean:define id="extendProperty" value="<%="rid='"+currentRid+"' belongto='"+issueStatusBelongto+"' isPrincipal='"+currentIsPm+"' isPm='"+currentIsPm+"'"%>"/>

                <%
                if(String.valueOf(currentIndex).equals("0")) {
                    firstRid=String.valueOf(currentRid);
                    firstRowId="tr"+firstRid;
                    firstIssueStatusBelongto=String.valueOf(issueStatusBelongto);
                    isPrincipal=String.valueOf(currentIsPrincipal);
                    firstIsPrincipal=isPrincipal;
                    isPm=String.valueOf(currentIsPm);
                    firstIsPm=isPm;
                }
                %>
                <html:tablerow id="<%="tr"+currentRid%>" oddClass="tableDataOdd" evenClass="tableDataEven" onclick="onclickRow(this)" ondblclick="ondblclickRow(this)" height="18px" canSelected="true" selfProperty="<%=extendProperty%>">
                  <bean:define id="abnormal" name="oneBean" property="abnormal"/>
                  <bean:define id="issueType" name="oneBean" property="issueType" />
                  <bean:define id="accountId" name="oneBean" property="accountId"/>
                  <bean:define id="accountName" name="oneBean" property="accountName"/>
                  <bean:define id="issueId" name="oneBean" property="issueId"/>
                  <bean:define id="issueName" name="oneBean" property="issueName"/>
                  <bean:define id="filleDate" name="oneBean" property="filleDate"/>
                  <bean:define id="priority" name="oneBean" property="priority"/>
                  <bean:define id="dueDate" name="oneBean" property="dueDate"/>
                  <bean:define id="issueStatus" name="oneBean" property="issueStatus"/>
                  <%String userName="";
                  if(DtoUser.USER_TYPE_EMPLOYEE.equals(principalScope)) {
                    userName = HrFactory.create().getName((String)principal);
                  } else {
                    userName = HrFactory.create().getCustomerName((String)principal);
                  }
                  %>
                  <html:tablecolumn styleId="oracelltext">
                    <B>
                      <font color="red"><%=abnormal%>                      </font>
                    </B>
                  </html:tablecolumn>
                  <% if(!hasAccountCondition){%>
                 <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(accountId+"--"+accountName)%>"><%=accountId%>--<%=accountName%>                 </html:tablecolumn>
                 <%}%>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueId)%>"><%=issueId%>                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueName)%>"><%=issueName%>                  </html:tablecolumn>
                  <%if(!hasTypeCondition){%>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueType)%>"><%=issueType%>                  </html:tablecolumn>
                  <%}%>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(priority)%>"><%=priority%>                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(filleDate)%>"><%=filleDate%>                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(dueDate)%>"><%=dueDate%>                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(principal)+"/"+userName%>"><%=userName%>                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=String.valueOf(issueStatus)%>"><%=issueStatus%>                  </html:tablecolumn>
                </html:tablerow>
              </logic:iterate>
            </logic:present>
          </html:tablebody>
        </html:table>
      </html:outborder>
      <table valign="top" align="center" width="98%" height="342" cellSpacing="0" cellPadding="0">
        <logic:present name="webVo" property="startNo">
            <logic:greaterThan value="0" name="webVo" property="totalPage">
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
                <td align="left" class="list_range">
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
        </logic:greaterThan>
        </logic:present>
        <tr height="280">
          <td colspan="3">
            <iframe id="cardFrm" src="<%=contextPath%>/issue/issue/issueCard.jsp?rid=<%=firstRid%>&isPrincipal=<%=isPrincipal%>&isPm=<%=isPm%>" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">            </iframe>
          </td>
        </tr>
      </table>
    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>

<script language="javascript">
try {
    onclickRow(<%=firstRowId%>);
    changeRowColorsearchTable(<%=firstRowId%>);
    currentRowObj="<%=firstRowId%>";
    issueRid = "<%=firstRid%>";
    issueStatusBelongto="<%=firstIssueStatusBelongto%>";
    isPrincipal="<%=firstIsPrincipal%>";
    isPm="<%=firstIsPm%>";
} catch(e) {
//    alert(e);
}
</script>
</body>
<form name="issueForm" id="issueForm" action="<%=contextPath%>/issue/issue/issueList.do" method="POST">
    <input type="hidden" name="functionType" value="SEARCH">
    <html:hidden name="rid" beanName="searchForm"/>
    <html:hidden name="issueType" beanName="searchForm"/>
    <html:hidden name="accountId" beanName="searchForm"/>
    <html:hidden name="priority" beanName="searchForm"/>
    <html:hidden name="scope" beanName="searchForm"/>
    <html:hidden name="status" beanName="searchForm"/>
    <html:hidden name="fillBy" beanName="searchForm"/>
    <html:hidden name="resolveBy" beanName="searchForm"/>
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
    <html:hidden name="totalPage" beanName="webVo"/>
    <html:hidden name="pageAction" value=""/>
    <html:hidden name="pageStandard" value="8"/>
    <html:hidden name="addonRidInfo" beanName="webVo"/>
    <html:hidden name="addonRid" value=""/>
</form>
</html>
