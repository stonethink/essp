<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>



<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
  <head>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
<script language="javascript">
function delHumanBase(){
   if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
    if(confirm('<bean:message bundle="application" key="global.confirm.delete"/>')) {
        var url='<%=request.getContextPath()%>/hrbase/humanbase/deleteHumanBase.do?rid='+rid;
        window.open(url, 'popupabort', 'width=1,height=1,left=10000,top=1000000');
    }
}

function editHumanBase(){
  if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
  var url='<%=request.getContextPath()%>/hrbase/humanbase/editHumanBasePre.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-400)/2;
  var pos='width=750,height=400,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
}

function viewHumanBase(){
  if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
  var url='<%=request.getContextPath()%>/hrbase/humanbase/viewHumanBase.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-400)/2;
  var pos='width=750,height=350,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
}

function listEditingLog() {
  var url='<%=request.getContextPath()%>/hrbase/humanbase/listEditingLog.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-400)/2;
  var pos='width=800,height=450,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
}

function clickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	var cells=rowObj.cells
    	//alert(cells.length);
    	rid = rowObj.selfproperty;
    	var canUpdate = cells[cells.length - 1].innerText
    	var pd = window.parent.document.all;
        if(rowObj != null && rid != null) {
        	pd.btnView.disabled=false;
          	pd.btnEditingLog.disabled=false;
          	if(canUpdate != null && canUpdate == "true") {
	          	pd.btnEdit.disabled=false;
	          	pd.btnDelete.disabled=false;
          	} else {
	          	pd.btnEdit.disabled=true;
	          	pd.btnDelete.disabled=true;
          	}
        } else {
        	pd.btnView.disabled=true;
          	pd.btnEdit.disabled=true;
          	pd.btnEditingLog.disabled=true;
          	pd.btnDelete.disabled=true;
        }
    }catch( ex ){
        //alert(ex);
    }
}
//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("SearchTable_table");
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		 var firstRow=table.rows[thead_length];
		   clickRow(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
		   onChangeBackgroundColor(firstRow);
	    } 
	    }catch(e){}
	}		
	function setColumnTitle(){
     try{
      var currentRow;
      var rows=document.getElementById("SearchTable_table").rows;
      var cells=document.getElementById("SearchTable_table").rows[0].cells;
      
        var employeeId="<bean:message bundle="hrbase" key="hrbase.humanBase.employeeId"/>";
       	var chineseName="<bean:message bundle="hrbase" key="hrbase.humanBase.chineseName"/>";
        var englishName="<bean:message bundle="hrbase" key="hrbase.humanBase.englishName"/>";
        var title="<bean:message bundle="hrbase" key="hrbase.humanBase.title"/>";
        var rank="<bean:message bundle="hrbase" key="hrbase.humanBase.rank"/>";
        var resManagerId="<bean:message bundle="hrbase" key="hrbase.humanBase.resManagerId"/>";
        var resManagerName="<bean:message bundle="hrbase" key="hrbase.humanBase.resManager"/>";
        var unitCode="<bean:message bundle="hrbase" key="hrbase.humanBase.unitCode"/>";
        var isDirect ="<bean:message bundle="hrbase" key="hrbase.humanBase.direct"/>";
        var inDate="<bean:message bundle="hrbase" key="hrbase.humanBase.inDate"/>";
        var outDate="<bean:message bundle="hrbase" key="hrbase.humanBase.outDate"/>";
        var effectiveDate="<bean:message bundle="hrbase" key="hrbase.humanBase.effectiveDate"/>";
        var hrAttribute="<bean:message bundle="hrbase" key="hrbase.humanBase.hrAttribute"/>";
        var attribute="<bean:message bundle="hrbase" key="hrbase.humanBase.attribute"/>";
        var isFormal="<bean:message bundle="hrbase" key="hrbase.humanBase.formal"/>";
        var site="<bean:message bundle="hrbase" key="hrbase.humanBase.site"/>";
        var email="<bean:message bundle="hrbase" key="hrbase.humanBase.email"/>";
        var canUpdate="<bean:message bundle="hrbase" key="hrbase.humanBase.canUpdate"/>";
        var onJob="<bean:message bundle="hrbase" key="hrbase.humanBase.isOnJob"/>";
      
     for(var i=0;i<cells.length;i++){
       var tempText = cells[i].innerHTML;
       if(tempText.indexOf("EmployeeId")>=0){
           cells[i].innerHTML=cells[i].innerHTML.replace("EmployeeId",employeeId);
           cells[i].title = cells[i].title.replace("EmployeeId",employeeId);  
        } else if(tempText.indexOf("ChineseName")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("ChineseName",chineseName);
           cells[i].title = cells[i].title.replace("ChineseName",chineseName);  
        } else if(tempText.indexOf("EnglishName")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("EnglishName",englishName);
           cells[i].title = cells[i].title.replace("EnglishName",englishName);  
        } else if(tempText.indexOf("Title")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("Title",title);
           cells[i].title = cells[i].title.replace("Title",title);  
        } else if(tempText.indexOf("Rank")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("Rank",rank);
           cells[i].title = cells[i].title.replace("Rank",rank);  
        } else if(tempText.indexOf("ResManagerId")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("ResManagerId",resManagerId);
           cells[i].title = cells[i].title.replace("ResManagerId",resManagerId);  
        } else if(tempText.indexOf("ResManagerName")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("ResManagerName",resManagerName);
           cells[i].title = cells[i].title.replace("ResManagerName",resManagerName);  
        } else if(tempText.indexOf("Site")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("Site",site);
           cells[i].title = cells[i].title.replace("Site",site);  
        } else if(tempText.indexOf("UnitCode")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("UnitCode",unitCode);
           cells[i].title = cells[i].title.replace("UnitCode",unitCode);  
        } else if(tempText.indexOf("IsDirect")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("IsDirect",isDirect);
           cells[i].title = cells[i].title.replace("IsDirect",isDirect);  
        } else if(tempText.indexOf("InDate")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("InDate",inDate);
           cells[i].title = cells[i].title.replace("InDate",inDate);  
        }  else if(tempText.indexOf("OutDate")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("OutDate",outDate);
           cells[i].title = cells[i].title.replace("OutDate",outDate);  
        }  else if(tempText.indexOf("OnJob")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("OnJob",onJob);
           cells[i].title = cells[i].title.replace("OnJob",onJob);  
        } else if(tempText.indexOf("EffectiveDate")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("EffectiveDate",effectiveDate);
           cells[i].title = cells[i].title.replace("EffectiveDate",effectiveDate);  
        } else if(tempText.indexOf("HrAttribute")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("HrAttribute",hrAttribute);
           cells[i].title = cells[i].title.replace("HrAttribute",hrAttribute);  
        } else if(tempText.indexOf("Attribute")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("Attribute",attribute);
           cells[i].title = cells[i].title.replace("Attribute",attribute);  
        } else if(tempText.indexOf("IsFormal")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("IsFormal",isFormal);
           cells[i].title = cells[i].title.replace("IsFormal",isFormal);  
        } else if(tempText.indexOf("Email")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("Email",email);
           cells[i].title = cells[i].title.replace("Email",email);  
        } else if(tempText.indexOf("CanUpdate")>=0) {
           cells[i].innerHTML=cells[i].innerHTML.replace("CanUpdate",canUpdate);
           cells[i].title = cells[i].title.replace("CanUpdate",canUpdate);  
        }
     }
     } catch (e){}
     }
</script>
  </head>
  
<body style="overflow:auto;">
<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="SearchTable"
       items="webVo"
       var="hrb" scope="request"
       action="${pageContext.request.contextPath}/hrbase/humanbase/humanBaseQuery.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >
        <ec:row onclick="clickRow(this);" selfproperty="${hrb.rid}" >
         
        <logic:equal property="showEmployeeId" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="100" property="employeeId" tooltip="${hrb.employeeId}" title="EmployeeId"/>
        </logic:equal>
        <logic:equal property="showChineseName" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="120" property="chineseName" tooltip="${hrb.chineseName}" title="ChineseName"/>
        </logic:equal>
        <logic:equal property="showEnglishName" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="160" property="englishName" tooltip="${hrb.englishName}" title="EnglishName"/>
        </logic:equal>
        <logic:equal property="showTitle" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="100" property="title" tooltip="${hrb.title}" title="Title"/>
        </logic:equal>
        <logic:equal property="showRank" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="60" property="rank" tooltip="${hrb.rank}" title="Rank"/>
        </logic:equal>
        <logic:equal property="showResManagerId" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="100" property="resManagerId" tooltip="${hrb.resManagerId}" title="ResManagerId"/>
        </logic:equal>
        <logic:equal property="showResManagerName" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="120" property="resManagerName" tooltip="${hrb.resManagerName}" title="ResManagerName"/>
        </logic:equal>
        <logic:equal property="showSite" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="60" property="site" tooltip="${hrb.site}" title="Site"/>
        </logic:equal>
        <logic:equal property="showUnitCode" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="unitCode" tooltip="${hrb.unitCode}" title="UnitCode"/>
        </logic:equal>
        <logic:equal property="showIsDirect" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="isDirectName" tooltip="${hrb.isDirectName}" title="IsDirect"/>
        </logic:equal>
        <logic:equal property="showInDate" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="inDate" tooltip="${hrb.inDate}" title="InDate"/>
        </logic:equal>
        <logic:equal property="showOutDate" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="outDate" tooltip="${hrb.outDate}" title="OutDate"/>
        </logic:equal>
        <logic:equal property="showOnJob" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="onJob" tooltip="${hrb.onJob}" title="OnJob"/>
        </logic:equal>
        <logic:equal property="showEffectiveDate" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="effectiveDate" tooltip="${hrb.effectiveDate}" title="EffectiveDate"/>
        </logic:equal>
        <logic:equal property="showHrAttribute" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="90" property="hrAttribute" tooltip="${hrb.hrAttribute}" title="HrAttribute"/>
        </logic:equal>
        <logic:equal property="showAttribute" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="100" property="attribute" tooltip="${hrb.attribute}" title="Attribute"/>
        </logic:equal>
         <logic:equal property="showIsFormal" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="80" property="isFormalName" tooltip="${hrb.isFormalName}" title="IsFormal"/>
        </logic:equal>
        <logic:equal property="showEmail" name="HrSearchCondition" scope="session" value="checked">
             <ec:column width="220" property="email" tooltip="${hrb.email}" title="Email"/>
        </logic:equal>
            <ec:column width="60" property="canUpdate" tooltip="${hrb.canUpdate}" title="CanUpdate"/>
        </ec:row>
     </ec:table>
     <SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
         setColumnTitle();
     </SCRIPT>
  </body>
  
</html>
