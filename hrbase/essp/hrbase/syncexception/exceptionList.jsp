
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>



<bean:define id="contextPath" value="<%=request.getContextPath()%>" />

<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

		<script language="javascript" type="text/javascript">
	var currentRow=null;
	function disableBtn(){
	  try{
	   var table=document.getElementById("ExceptionList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   //alert(table);
	   //alert(window.parent.parent.confirmBtn);
	   if(firstRow==null){
	     window.parent.parent.confirmBtn.disabled=true;
	     window.parent.parent.cancelBtn.disabled=true;
	   }else{
	     window.parent.parent.confirmBtn.disabled=false;
	     window.parent.parent.cancelBtn.disabled=false;
	   }
	   }catch(e){}
	}
	 //执行新增按钮的动作
	function onConfirmData(){    
	try{  
  		var rid = currentRow.selfproperty;
  		window.location="<%=contextPath%>/syncexception/carryForward.do?rid="+rid;
  	}catch(e){}
    }
    function onCancelData() {
    try{
    	if(confirm('<bean:message bundle="hrbase" key="hrbase.syncexception.confirmCancel"/>')){
    		var rid = currentRow.selfproperty;
    		window.location="<%=contextPath%>/syncexception/cancel.do?rid="+rid;
    	}
    }catch(e){}
    }
	//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
     try{
	if(rowObj==null){
		window.parent.onRefreshData("");
	} else {
	currentRow=rowObj;
	var rid=rowObj.selfproperty;
	window.parent.onRefreshData(rid);
	}
	}catch(e){}
   }
    
   //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("ExceptionList_table");
	    
        var str0="<bean:message bundle="hrbase" key="hrbase.syncexception.model"/>";
		var str1="<bean:message bundle="hrbase" key="hrbase.syncexception.effectiveDate"/>";
		var str2="<bean:message bundle="hrbase" key="hrbase.syncexception.operation"/>";
		var str3="<bean:message bundle="hrbase" key="hrbase.syncexception.syncContent"/>";
		var str4="<bean:message bundle="hrbase" key="hrbase.syncexception.status"/>";
		var str5="<bean:message bundle="hrbase" key="hrbase.syncexception.syncRct"/>";
		

		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Model",str0);
		cells[0].title = cells[0].title.replace("Model",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Effective Date",str1);
		cells[1].title = cells[1].title.replace("Effective Date",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Operation",str2);
		cells[2].title=cells[2].title.replace("Operation",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Content",str3);
		cells[3].title=cells[3].title.replace("Content",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Status",str4);
		cells[4].title=cells[4].title.replace("Status",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("Rct",str5);
		cells[5].title=cells[5].title.replace("Rct",str5);
		 var firstRow=table.rows[thead_length];
	       disableBtn();
		   rowOnClick(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
	      
		   onChangeBackgroundColor(firstRow);
	    } 
	    }catch(e){}
	}
	
	</script>
	</head>

	<body>
		<%
			java.util.Locale locale = (java.util.Locale) session
					.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
			String _language = locale.toString();
		%>
		<ec:table tableId="exceptionList" items="webVo" var="exception"
			scope="request"
			action="${pageContext.request.contextPath}/syncexception/listException.do"
			imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
			locale="<%=_language%>">

			<ec:row onclick="rowOnClick(this);" selfproperty="${exception.rid}">

				<ec:column style="width:20%" property="model" tooltip="${exception.model}" title="Model" />
				<ec:column style="width:20%" property="effectiveDate" tooltip="${exception.effectiveDate}" title="Effective Date" />
				<ec:column style="width:20%" property="operation" tooltip="${exception.operation}" title="Operation" />
				<ec:column style="width:20%" property="resTypeName" tooltip="${exception.resTypeName}" title="Content" />
				<ec:column style="width:20%" property="status" tooltip="${exception.status}" title="Status" />
				<ec:column style="width:20%" property="rct" tooltip="${exception.rct}" title="Rct" />

			</ec:row>
		</ec:table>
		<SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
	</body>

</html>
