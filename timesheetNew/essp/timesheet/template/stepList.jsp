
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
	   
	    function onDeleteData() {	 
	    try{
	    	if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")){
	    		var rid = currentRow.selfproperty;	
	    		if(rid=="0"){	   
		    		var searchTb = document.getElementById("stepList_table");	    		    					
		    		searchTb.firstChild.deleteRow();
		    		autoClickFirstRow();	    			
	    		}else{    		
	    			window.location="<%=contextPath%>/timesheet/template/deleteStep.do?rid="+rid;
	    		}
	    	}
	    }catch(e){}
	    }
	    
	   	function onAddData() {
			var searchTb = document.getElementById("stepList_table");
			var tbody = searchTb.firstChild;
			var tr=document.createElement('<tr class="odd" selfproperty="0" onclick="onChangeBackgroundColor(this);rowOnClick(this);" />');
			var addTd1=document.createElement('<td style="width:10%"  title="" ></td>');
			var addTd2=document.createElement('<td style="width:20%"  title="" ></td>');
			var addTd3=document.createElement('<td style="width:10%"  title="" ></td>');
			var addTd4=document.createElement('<td style="width:20%"  title="" ></td>');
			var addTd5=document.createElement('<td style="width:20%"  title="" ></td>');
			var addTd6=document.createElement('<td style="width:10%"  title="" ></td>');
			var addTd7=document.createElement('<td style="width:10%"  title="" ></td>');	
			tr.appendChild(addTd1);
			tr.appendChild(addTd2);	
			tr.appendChild(addTd3);	
			tr.appendChild(addTd4);	
			tr.appendChild(addTd5);	
			tr.appendChild(addTd6);	
			tr.appendChild(addTd7);	
			tbody.appendChild(tr);
			rowOnClick(tr);
			onChangeBackgroundColor(tr);
		}
	    
		//单击行时的动作，更新下帧中的数据
	    function rowOnClick(rowObj) {
	     try{
	        if(rowObj==null){		    		  
			     window.parent.onRefreshData("");			    
		   	}else{	
				currentRow=rowObj;
				var rid=rowObj.selfproperty;
				window.parent.onRefreshData(rid);
			}
		}catch(e){}
	   }
    
	   //当进入此页时，自动选中第一行，并国际化表头
		function autoClickFirstRow(){
		try{
		    var table=document.getElementById("stepList_table");	
		    var str0="<bean:message bundle="timesheet" key="timesheet.template.catogory"/>";	    
	        var str1="<bean:message bundle="timesheet" key="timesheet.template.stepName"/>";		
			var str2="<bean:message bundle="timesheet" key="timesheet.template.stepSeq"/>";
			var str3="<bean:message bundle="timesheet" key="timesheet.template.startOffset"/>";
			var str4="<bean:message bundle="timesheet" key="timesheet.template.finishOffset"/>";
			var str5="<bean:message bundle="timesheet" key="timesheet.template.weight"/>";
			var str6="<bean:message bundle="timesheet" key="timesheet.template.isCorp"/>";		
	
			var thead_length=table.tHead.rows.length;
			var tds=table.rows[thead_length-1];
			var cells=tds.cells;
			//使EC标签的表头国际化
			cells[0].innerHTML=cells[0].innerHTML.replace("Category",str0);
			cells[0].title=cells[0].title.replace("Category",str0);
			cells[1].innerHTML=cells[1].innerHTML.replace("Step Name",str1);
			cells[1].title = cells[1].title.replace("Step Name",str1);			
			cells[2].innerHTML=cells[2].innerHTML.replace("Seq Number",str2);
			cells[2].title=cells[2].title.replace("Seq Number",str2);			
			cells[3].innerHTML=cells[3].innerHTML.replace("Start Offset",str3);
			cells[3].title=cells[3].title.replace("Start Offset",str3);
			cells[4].innerHTML=cells[4].innerHTML.replace("Finish Offset",str4);
			cells[4].title=cells[4].title.replace("Finish Offset",str4);
			cells[5].innerHTML=cells[5].innerHTML.replace("Weight",str5);
			cells[5].title=cells[5].title.replace("Weight",str5);			
			cells[6].innerHTML=cells[6].innerHTML.replace("Is Corp",str6);
			cells[6].title=cells[6].title.replace("Is Corp",str6);

			var firstRow=table.rows[thead_length];		 
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
		<ec:table tableId="stepList" items="webVo" var="step"
			scope="request"
			action=""
			imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
			locale="<%=_language%>">
			<ec:row onclick="rowOnClick(this);" selfproperty="${step.rid}">	
				<ec:column style="width:10%" property="category" tooltip="${step.category}" title="Category" />				
				<ec:column style="width:20%" property="procName" tooltip="${step.procName}" title="Step Name" />
				<ec:column style="width:10%" property="seqNum" tooltip="${step.seqNum}" title="Seq Number" />
				<ec:column style="width:20%" property="planStartOffset" tooltip="${step.planStartOffset}" title="Start Offset" />
				<ec:column style="width:20%" property="planFinishOffset" tooltip="${step.planFinishOffset}" title="Finish Offset" />
				<ec:column style="width:10%" property="procWt" tooltip="${step.procWt}" title="Weight" />
				<ec:column style="width:10%" property="isCorp" tooltip="${step.isCorp}" title="Is Corp" />
			</ec:row>
		</ec:table>
		<SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
	</body>

</html>
