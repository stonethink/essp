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
function onTimeoutSearch() {
	setTimeout("onSearch()", 500);
}

function onAdd() {
	var searchTb = document.getElementById("SiteList_table");
	var tbody = searchTb.firstChild;
	var tr=document.createElement('<tr class="odd" selfproperty="" onclick="onChangeBackgroundColor(this);rowOnClick(this);" />');
	var addTd1=document.createElement('<td style="width:30%"  title="" ></td>');
	var addTd2=document.createElement('<td style="width:30%"  title="" ></td>');
	var addTd3=document.createElement('<td style="width:30%"  title="" ></td>');
	//var chk = document.createElement('<input id="addChk" name="status" value="true" next="" prev="" msg="" req="false" sreq="false" class="Checkbox" onclick="return false;" onblur="doBlur();" onfocus="doFocus();" type="checkbox" checked />');
	tr.appendChild(addTd1);
	tr.appendChild(addTd2);
	tr.appendChild(addTd3);
	//addTd3.appendChild(chk);
	tbody.appendChild(tr);
	rowOnClick(tr);
	onChangeBackgroundColor(tr);
	window.parent.document.all.btnAdd.disabled="disable";
}

function onDelete() {
	if(currentRow==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
    if(confirm('<bean:message bundle="application" key="global.confirm.delete"/>')) {
    	var rid = currentRow.selfproperty;
        window.parent.deleteForm.action="<%=request.getContextPath()%>/hrbase/site/deleteSite.do?siteRid=" + rid;
        window.parent.deleteForm.submit();
        onTimeoutSearch();
    }
}

//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
     try{
	if(rowObj==null){
		window.parent.generalFrm.location="<%=request.getContextPath()%>/hrbase/Blank.jsp";
		window.parent.btnDelete.disabled=true;
		return;
	} else {
	currentRow=rowObj;
	var rid=rowObj.selfproperty;
	window.parent.generalForm.action="<%=request.getContextPath()%>/hrbase/site/loadSite.do?siteRid=" + rid;
    window.parent.generalForm.submit();
    window.parent.btnDelete.disabled=false;
	}
	}catch(e){}
   }
    
   //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("SiteList_table");
	    
        var str0="<bean:message bundle="hrbase" key="hrbase.site.name"/>";
		var str1="<bean:message bundle="hrbase" key="hrbase.site.description"/>";
		var str2="<bean:message bundle="hrbase" key="hrbase.site.enable"/>";

		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Site Name",str0);
		cells[0].title = cells[0].title.replace("Site Name",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Desc",str1);
		cells[1].title = cells[1].title.replace("Desc",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("isEnable",str2);
		 var firstRow=table.rows[thead_length];
		   rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	      
		   onChangeBackgroundColor(firstRow);
	    } 
	    window.parent.document.all.btnAdd.disabled=false;
	    }catch(e){}
	}
</script>
  </head>
  
<body >
<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="SiteList"
       items="webVo"
       var="site" scope="request"
       action="${pageContext.request.contextPath}/hrbase/site/siteList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${site.rid}">
             <ec:column style="width:30%" property="name" tooltip="${site.name}" title="Site Name"/>
             <ec:column style="width:30%" property="description" tooltip="${site.description}" title="Desc"/>
             <ec:column 
                 alias="checkbox"
                 title="isEnable" 
                 style="width:30%" 
                 filterable="false" 
                 sortable="false" 
                 cell="server.essp.hrbase.site.action.EcSiteEnable"
                 />            
         </ec:row>
     </ec:table>
     <SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
  </body>
  
</html>
