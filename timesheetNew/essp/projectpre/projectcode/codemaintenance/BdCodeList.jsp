
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>



<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

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
	  
	   var table=document.getElementById("BdCodeList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	  // alert(table);
	   if(firstRow==null){
	     window.parent.parent.delBtn.disabled=true;
	     window.parent.saveBtn.disabled=true;
	   }else{
	     window.parent.parent.delBtn.disabled=false;
	     window.parent.saveBtn.disabled=false;
	   }
	   }catch(e){}
	}
	 //执行新增按钮的动作
	function onAddData(){      
       var height = 230;
       var width = 500;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/projectpre/projectcode/codemaintenance/BDAdd.jsp';
	   var windowTitle="";
       window.open(url,windowTitle,option);

    }
    //执行删除按钮的动作
	function onDelData(){
	 if(currentRow==null) {
        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
        return;
    }
    if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	window.location="<%=contextPath%>/project/maintenance/deleteBdCode.do?code="+currentRow.selfproperty;
    }
	  
	}
	//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
     try{
	if(rowObj==null){
		window.parent.onRefreshData("");
	} else {
	currentRow=rowObj;
	var code=rowObj.selfproperty;
	window.parent.onRefreshData(code);
	}
	}catch(e){}
   }
    
   //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("BdCodeList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.BDCode"/>";
		var str1="<bean:message bundle="projectpre" key="projectCode.Name"/>";
		var str2="<bean:message bundle="projectpre" key="customer.Enable"/>";

		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("BD Code",str0);
		cells[0].title = cells[0].title.replace("BD Code",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("BD Name",str1);
		cells[1].title = cells[1].title.replace("BD Name",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("status",str2);
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
  
<body >
<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="BdCodeList"
       items="webVo"
       var="bCode" scope="request"
       action="${pageContext.request.contextPath}/project/maintenance/listAllBdCode.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${bCode.bdCode}">
             
             <ec:column style="width:30%" property="bdCode" tooltip="${bCode.bdCode}" title="BD Code"/>
             <ec:column style="width:30%" property="bdName" tooltip="${bCode.bdName}" title="BD Name"/>
             <ec:column 
                 alias="checkbox"
                 title="status" 
                 style="width:30%" 
                 filterable="false" 
                 sortable="false" 
                 cell="server.essp.projectpre.ui.project.maintenance.EcAreaCodeStatus"
                 />             
         </ec:row>
     </ec:table>
     <SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
  </body>
  
</html>
