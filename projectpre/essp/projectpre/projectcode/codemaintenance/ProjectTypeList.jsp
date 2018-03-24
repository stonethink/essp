<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<html>
 <head>
        <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectData.Title"/></TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
	
	<script type="text/javascript" language="javascript">	
	function disableBtn(){
	  try{
	   var table=document.getElementById("Parameter_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   if(firstRow==null){
	     window.parent.parent.delBtn.disabled=true;    
	     window.parent.saveBtn.disabled=true;

	   }else{
	     window.parent.parent.delBtn.disabled=false;
	     window.parent.saveBtn.disabled=false;
	   }
	   }catch(e){}
	}
    function onAddData(){
       var height = 260;
       var width = 410;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   //var url="<%=contextPath%>/parameter/addParameter.do?";
	   var url="<%=contextPath%>/projectpre/projectcode/codemaintenance/AddProjectType.jsp";
	   var windowTitle="";
       window.open(url,windowTitle,option);
    }     
    //执行删除按钮的动作
	function onDeleteData() {
    if(currentRow==null) {
        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
        return;
    }
    if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
        var code=currentRow.selfproperty;
    	window.location="<%=contextPath%>/codemaintenance/deleteProjectType.do?code="+code;	
    }
   }
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    try{
    if(rowObj==null){
		//return;
	  //alert("rowObj==null");
	  window.parent.onRefreshData("");
	}else{
	  currentRow=rowObj;
	  var code=rowObj.selfproperty;
	  window.parent.onRefreshData(code);
	}
	}catch(e){}
   }

	//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	  try{
	    var table=document.getElementById("Parameter_table");
        var str0="<bean:message bundle="projectpre" key="projectCode.No"/>";
		var str1="<bean:message bundle="projectpre" key="projectCode.Item"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.Sequence"/>";
		var str3="<bean:message bundle="projectpre" key="customer.Enable"/>";
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使C标u31614 的u-30616 头u22269 际u21270 
		cells[0].innerHTML=cells[0].innerHTML.replace("Code",str0);
		cells[0].title = cells[0].title.replace("Code",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Item",str1);
		cells[1].title = cells[1].title.replace("Item",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Sequence",str2);
		cells[2].title = cells[2].title.replace("Sequence",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Status",str3);
		
		
		var firstRow=table.rows[thead_length];
		disableBtn();
		rowOnClick(firstRow);
		
	    if(table.rows.length>thead_length){
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
	     tableId="Parameter"
         items="webVo" scope="request"
         var="aParameter"
         action="${pageContext.request.contextPath}/codemaintenance/listProjectType.do"  
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
         locale="<%=_language%>"
        >

       <ec:row onclick="rowOnClick(this);" selfproperty="${aParameter.code}"> 
             <ec:column width="25%" property="code" tooltip="${aParameter.code}" title="Code"/>
             <ec:column width="25%" property="name" tooltip="${aParameter.name}" title="Item"/>
             <ec:column width="25%" property="sequence" tooltip="${aParameter.sequence}" title="Sequence"/>
              <ec:column 
                 alias="checkbox"
                 title='Status' 
                 width="25%"
                 sortable="false"
                 cell="server.essp.projectpre.ui.parameter.EcProjectTypeStatus"
                 />
         </ec:row>
     </ec:table>
     <SCRIPT language="JavaScript" type="text/JavaScript">
       autoClickFirstRow();
     </SCRIPT>
  </body>
</html>
