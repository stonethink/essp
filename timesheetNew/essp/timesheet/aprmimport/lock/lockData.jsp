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
function onAdd() {
	var searchTb = document.getElementById("SearchList_table");
	var tbody = searchTb.firstChild;
	var tr=document.createElement('<tr class="odd" selfproperty="" onclick="onChangeBackgroundColor(this);rowOnClick(this);" />');
	var addTd1=document.createElement('<td style="width:30%"  title="" ></td>');
	var addTd2=document.createElement('<td style="width:30%"  title="" ></td>');
	var addTd3=document.createElement('<td style="width:30%"  title="" ></td>');
	var chk = document.createElement('<input id="addChk" name="status" value="true" next="" prev="" msg="" req="false" sreq="false" class="Checkbox" onclick="return false;" onblur="doBlur();" onfocus="doFocus();" type="checkbox" checked />');
	tr.appendChild(addTd1);
	tr.appendChild(addTd2);
	tr.appendChild(addTd3);
	tr.rid="";
	addTd3.appendChild(chk);
	tbody.appendChild(tr);
	rowOnClick(tr);
	onChangeBackgroundColor(tr);
	window.parent.document.all.btnAdd.disabled="disable";
}

function onDelete() {
	if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
    if(confirm('<bean:message bundle="application" key="global.confirm.delete"/>')) {
    	var rid = currentRowObj.selfproperty;
        window.parent.deleteForm.action="<%=request.getContextPath()%>/timesheet/aprmimport/lock/DeleteImportLock.do?importLockRid=" + rid;
        window.parent.deleteForm.submit();
    }
    //alert("Deleted");
    window.parent.onSearch();
}

//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
     try{
	if(rowObj==null){
		return;
	} else {
	currentRowObj=rowObj;
	var rid=rowObj.selfproperty;
	
	window.parent.generalForm.action="<%=request.getContextPath()%>/timesheet/aprmimport/lock/LoadImportLock.do?importLockRid=" + rid;
    window.parent.generalForm.submit();
	}
	}catch(e){}
   }

//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("SearchList_table");
	    
        var str0="<bean:message bundle="timesheet" key="importLock.common.beginDate"/>";
		var str1="<bean:message bundle="timesheet" key="importLock.common.endDate"/>";
		var str2="<bean:message bundle="timesheet" key="importLock.common.lock"/>";
		
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("BeginDate",str0);
		cells[0].title = cells[0].title.replace("BeginDate",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("EndDate",str1);
		cells[1].title = cells[1].title.replace("EndDate",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("status",str2);
		 var firstRow=table.rows[thead_length];
		   rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	      
		   onChangeBackgroundColor(firstRow);
	    } 
	    window.parent.document.all.btnAdd.disabled=false;
	    }catch(e){alert(e);}
	}
</script>
  </head>
  
<body >
<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="SearchList"
       items="webVo"
       var="lock" scope="request"
       action="${pageContext.request.contextPath}/timesheet/aprmimport/lock/ListImportLock.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${lock.rid}">
             <ec:column style="width:30%" property="beginDate" tooltip="${lock.beginDate}" title="BeginDate"/>
             <ec:column style="width:30%" property="endDate" tooltip="${lock.endDate}" title="EndDate"/>
             <ec:column 
                 alias="checkbox"
                 title="status" 
                 style="width:30%" 
                 filterable="false" 
                 sortable="false" 
                 cell="server.essp.timesheet.aprm.lock.action.EcStatus"
                 />            
         </ec:row>
     </ec:table>
     <SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
  </body>
  
</html>
