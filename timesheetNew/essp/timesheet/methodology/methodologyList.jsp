
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
		   	var table=document.getElementById("MethodList_table");
		   	var thead_length=table.tHead.rows.length;
		   	var firstRow=table.rows[thead_length];
		   	if(firstRow==null){
			    window.parent.parent.addBtn.disabled=false;
			    window.parent.parent.cancelBtn.disabled=true;
		   	}else{
			    window.parent.parent.addBtn.disabled=false;
			    window.parent.parent.cancelBtn.disabled=false;
		   	}
	  	}catch(e){}
		}	          	    
	   
	   function onAddData() {
			var searchTb = document.getElementById("MethodList_table");
			var tbody = searchTb.firstChild;		
			var tr=document.createElement('<tr class="even" selfproperty="" onclick="onChangeBackgroundColor(this);rowOnClick(this);" />');
			var addTd1=document.createElement('<td style="width:30%"  title="" ></td>');
			var addTd2=document.createElement('<td style="width:30%"  title="" ></td>');	
			var addTd3=document.createElement('<td style="width:30%"  title="" ></td>');
			tr.appendChild(addTd1);
			tr.appendChild(addTd2);	
			tr.appendChild(addTd3);
			tbody.appendChild(tr);			
			rowOnClick(tr);
			onChangeBackgroundColor(tr);
		}
		
	    function onCancelData() {	 
	    try{
	    	if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")){
	    		var rid = currentRow.selfproperty;
	    		if(null==rid||""==rid)
	    		{	 
	    			var searchTb = document.getElementById("MethodList_table");	    		    					
	    			searchTb.firstChild.deleteRow();
	    			autoClickFirstRow();	    			
	    		}else{
	    			window.location="<%=contextPath%>/timesheet/methodology/deleteMethod.do?rid="+rid;
	    		}
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
		    var table=document.getElementById("MethodList_table");		    
	        var str0="<bean:message bundle="timesheet" key="timesheet.template.name"/>";		
			var str1="<bean:message bundle="timesheet" key="timesheet.template.description"/>";	
			var str2="<bean:message bundle="timesheet" key="timesheet.template.activity"/>"	
			var thead_length=table.tHead.rows.length;
			var tds=table.rows[thead_length-1];
			var cells=tds.cells;
			//使EC标签的表头国际化
			cells[0].innerHTML=cells[0].innerHTML.replace("Name",str0);
			cells[0].title = cells[0].title.replace("Name",str0);			
			cells[1].innerHTML=cells[1].innerHTML.replace("Description",str1);
			cells[1].title=cells[1].title.replace("Description",str1);
			cells[2].innerHTML=cells[2].innerHTML.replace("Status", str2)
           cells[2].title = cells[2].title.replace("Status",str2);

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
		<ec:table tableId="methodList" items="webVo" var="method"
			scope="request"
			action="${pageContext.request.contextPath}/timesheet/methodology/listMethod.do"
			imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
			locale="<%=_language%>">
			<ec:row onclick="rowOnClick(this);" selfproperty="${method.rid}">
				<ec:column style="width:20%" property="name" tooltip="${method.name}" title="Name" />	
				<ec:column style="width:20%" property="description" tooltip="${method.description}" title="Description" />
				<ec:column style="width:20%" property="rst" tooltip="${method.rst}" title="Status" />
			</ec:row>
		</ec:table>
		<SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
	</body>

</html>
