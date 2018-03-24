<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
 <head>
        <TITLE>templateList</TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
		var currentRow=null;
         function onDeleteData(){
         try{
         	if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {  
         		var rid = currentRow.selfproperty;
         		if(rid=="0"){	
		    		var searchTb = document.getElementById("templateList_table");	    		    					
		    		searchTb.firstChild.deleteRow();
		    		autoClickFirstRow();	    			
	    		}else{
    	  			window.location="<%=contextPath%>/timesheet/template/deleteTempalte.do?rid="+rid;
    	  		}
          }
          }catch(e){}	 
		}
		
		function onAddData(){
			var searchTb = document.getElementById("templateList_table");
			var tbody = searchTb.firstChild;
			var tr=document.createElement('<tr class="odd" selfproperty="0" onclick="onChangeBackgroundColor(this);rowOnClick(this);" />');
			var addTd1=document.createElement('<td style="width:25%"  title="" ></td>');
			var addTd2=document.createElement('<td style="width:25%"  title="" ></td>');
			var addTd3=document.createElement('<td style="width:25%"  title="" ></td>');
			var addTd4=document.createElement('<td style="width:25%"  title="" ></td>');	
			tr.appendChild(addTd1);
			tr.appendChild(addTd2);	
			tr.appendChild(addTd3);	
			tr.appendChild(addTd4);	
			tbody.appendChild(tr);
			rowOnClick(tr);
			onChangeBackgroundColor(tr);
		}
		
		function rowOnClick(rowObj) {		 
            if(rowObj==null){
            	window.parent.parent.deleteBtn.disabled=true;
            	window.parent.onRefreshData("");
            }else {
            	currentRow=rowObj;
            	var rid=rowObj.selfproperty;            		         	    
              	window.parent.onRefreshData(rid);
	            window.parent.parent.deleteBtn.disabled=false;    
            }        	
          } 

          //当进入此页时，自动选中第一行，并国际化表头
         function autoClickFirstRow(){
         try{
            var table=document.getElementById("templateList_table");
            var str0 = "<bean:message bundle="timesheet" key="timesheet.template.code"/>";
            var str1 = "<bean:message bundle="timesheet" key="timesheet.template.name"/>";
            var str2 = "<bean:message bundle="timesheet" key="timesheet.template.method"/>";
            var str3 = "<bean:message bundle="timesheet" key="timesheet.template.activity"/>"
            var thead_length=table.tHead.rows.length;
            var tds=table.rows[thead_length-1];
            var cells=tds.cells;
         //使EC标签的表头国际化
           cells[0].innerHTML=cells[0].innerHTML.replace("Template Code",str0);
           cells[0].title = cells[0].title.replace("Template Code",str0);
           cells[1].innerHTML=cells[1].innerHTML.replace("Template Name", str1)
           cells[1].title = cells[1].title.replace("Template Name",str1);
           cells[2].innerHTML=cells[2].innerHTML.replace("Method Type", str2)
           cells[2].title = cells[2].title.replace("Method Type",str2);
           cells[3].innerHTML=cells[3].innerHTML.replace("Status", str3)
           cells[3].title = cells[3].title.replace("Status",str3);
           
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

<body >
		<%
			java.util.Locale locale = (java.util.Locale) session
					.getAttribute(org.apache.struts.Globals.LOCALE_KEY);
			String _language = locale.toString();		
		%>
  		<ec:table var="template"  tableId="templateList"  items="webVo" scope="request"   
          action="${pageContext.request.contextPath}/timesheet/template/listTemplate.do"
          imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >
       <ec:row onclick="rowOnClick(this);" selfproperty="${template.rid}">
             <ec:column  property="templateCode"  tooltip="${template.templateCode}"  title="Template Code" />
             <ec:column  property="templateName" tooltip="${template.templateName}" title="Template Name"/>
             <ec:column  property="methodType"  tooltip="${template.methodType}"  title="Method Type"/>
             <ec:column  property="rst"  tooltip="${template.rst}"  title="Status" />
         </ec:row>

       </ec:table>
</body>
<SCRIPT language="JavaScript" type="text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
</html>
