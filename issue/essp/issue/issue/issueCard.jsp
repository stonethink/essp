<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="c2s.essp.common.user.DtoUser" %>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<%
   String issueRid=request.getParameter("rid");
   String isPrincipal=request.getParameter("isPrincipal");
   String actionSrc_general="";
   String actionSrc_resolution="";
   String actionSrc_discuss="";
   String actionSrc_conclusion="";
   if(issueRid==null || issueRid.equals("")) {
       issueRid="";
   } else {
       actionSrc_general=contextPath+"/issue/issue/issueUpdatePre.do?rid="+issueRid;
       actionSrc_resolution=contextPath+"/issue/issue/issueResolutionPre.do?rid="+issueRid;
       actionSrc_discuss=contextPath+"/issue/issue/discuss/issueDiscussList.do?issueRid="+issueRid;
       actionSrc_conclusion=contextPath+"/issue/issue/conclusion/issueConclusionPre.do?rid="+issueRid;
   }
   //判断是否为客�?若为客户不能A&A和Solution卡片
   boolean isCustomer = false;
   DtoUser user = (DtoUser)session.getAttribute(DtoUser.SESSION_USER);
   isCustomer = DtoUser.USER_TYPE_CUST.equals(user.getUserType());
%>
<html>
<head>
 <tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" CRM V2.1" />
        <tiles:put name="jspName" value="" />
 </tiles:insert>
	<script language="JavaScript" type="text/javascript">
    var isPrincipal=true;
    var isPm=true;
	var issueRid="<%=issueRid%>";
    tabCount=2;
    var tabMap=new HashMap();
    initMap();

    function initMap() {
        tabMap=new HashMap();
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","issueGeneral");
		tabMap.put("2","issueResolution");
        tabMap.put("3","issueDiscussList");
        tabMap.put("4","issueConclusion");
    }

	function onBodyLoad() {
        initButton();
        onTabChange();
	}

    function checkRid() {
        if(issueRid==null || issueRid=="") {
            alert("Please select a issue first!");
            return false;
        } else {
            return true;
        }
    }

	function onAddData() {
        if(!checkRid()) {
            return;
        }
        invokeSingleMethod("onAddData",issueRid,tabMap);
	}

	function onAddCategoryValue() {
        if(!checkRid()) {
            return;
        }
        invokeSingleMethod("onAddCategoryValue",issueRid,tabMap);
	}
	function onDeleteData() {
        if(!checkRid()) {
            return;
        }
	    invokeSingleMethod("onDeleteData",issueRid,tabMap);
	}

	function onEditData() {
        if(!checkRid()) {
            return;
        }
		invokeSingleMethod("onEditData",issueRid,tabMap);
	}

    function onReplyData(){
        if(!checkRid()) {
            return;
        }
        invokeSingleMethod("onReplyData",issueRid,tabMap);
    }

    function onModifyData(){
        if(!checkRid()) {
            return;
        }
        invokeSingleMethod("onModifyData",issueRid,tabMap);
    }

    function refreshIFrame(iframeid,url,typename) {
        if(!checkRid()) {
            return;
        }
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(issueRidValue,isPrincipalIn,isPmIn) {
        issueRid=issueRidValue;
        isPrincipal=isPrincipalIn;
        isPm=isPmIn;
        issueGeneral.location="<%=contextPath%>/issue/issue/issueUpdatePre.do?rid="+issueRid;
        issueResolution.location="<%=contextPath%>/issue/issue/issueResolutionPre.do?rid="+issueRid;
        issueDiscussList.location="<%=contextPath%>/issue/issue/discuss/issueDiscussList.do?issueRid="+issueRid;
        issueConclusion.location="<%=contextPath%>/issue/issue/conclusion/issueConclusionPre.do?rid="+issueRid;
        onTabChange();
    }
     function onSaveAndSendData() {
        if(!checkRid()) {
            return;
        }
		invokeSingleMethod("onSaveAndSendData",issueRid,tabMap);
    }
    function onSaveData() {
        if(!checkRid()) {
            return;
        }
		invokeSingleMethod("onSaveData",issueRid,tabMap);
    }

    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        	renderTabButton(tabDetail,btnPanel);

            var tabIndex = getTabIndex(tabDetail);
            if(tabIndex==1||tabIndex==2||tabIndex==4){
               saveAndSendBtn.style.width="68px";
            }
            if(tabIndex==3){
		addBtn.style.width="60px";
		replyBtn.style.width="68px";
            }

        } catch(e) {
            //alertError( e );
        }
    }

    function initButton() {
        <%-- general's buttons --%>
        addTabButtonByIndex(tabDetail,1,"saveAndSendBtn","Save&Send","onSaveAndSendData()");
        addTabButtonByIndex(tabDetail,1,"saveBtn","Save","onSaveData()");
        <%-- Analysis & Assign --%>
        addTabButtonByIndex(tabDetail,2,"saveAndSendBtn","Save&Send","onSaveAndSendData()");
        addTabButtonByIndex(tabDetail,2,"saveBtn","Save","onSaveData()");

        <%--discuss's buttons--%>
        addTabButtonByIndex(tabDetail,3,"addBtn","Question","onAddData()");
        addTabButtonByIndex(tabDetail,3,"replyBtn","Response","onReplyData()");
        addTabButtonByIndex(tabDetail,3,"modifyBtn","Modify","onModifyData()");
        addTabButtonByIndex(tabDetail,3,"deleteBtn","Delete","onDeleteData()");
        <%-- conclusion's buttons --%>
        //addTabButtonByIndex(tabDetail,4,"saveAndSendBtn","Save&Send","onSaveAndSendData()");
        addTabButtonByIndex(tabDetail,4,"saveBtn","Save","onSaveData()");
    }
	</script>
</head>
<body onload="onBodyLoad()">

<table cellspacing="0" cellpadding="0" height="90%" width="100%">
  <tr>
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1"  >
        <%-----------客户不能操作A&A和Solution卡片---------------------%>
         <%--THE 1st tab--%>
         <html:tabtitle width="60"><span class="tabs_title">General</span></html:tabtitle>
         <%--THE 2nd tab--%>
         <html:tabtitle width="120"><span class="tabs_title">Analysis &amp; Assign</span></html:tabtitle>
	     <%--THE 3rd tab--%>
         <html:tabtitle width="120"><span class="tabs_title">Discuss &amp; Clarify</span></html:tabtitle>
         <%--THE 4th tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Solution</span></html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
    	<div id="btnPanel">
    	</div>
    </html:tabbuttons>
	<html:tabcontents>
            <%--THE 1st Card--%>
            <html:tabcontent styleClass="wind">
                <html:outborder width="100%" height="100%">
                        <IFRAME
                        id="issueGeneral"
                        name="issueGeneral"
                        SRC="<%=actionSrc_general%>"
                        tabindex="1"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
            <%--The 2nd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueResolution"
                        SRC="<%=actionSrc_resolution%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 3rd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueDiscussList"
                        SRC="<%=actionSrc_discuss%>"
                        tabindex="3"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 4th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueConclusion"
                        SRC="<%=actionSrc_conclusion%>"
                        tabindex="4"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
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
