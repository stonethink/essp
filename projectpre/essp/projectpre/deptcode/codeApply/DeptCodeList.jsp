<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<script language="JavaScript" type="text/JavaScript">
  var currentRow=null;  
     //删除操作
    function onDeleteData() {
      if(currentRow==null) {
        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
        return;
      } 
       if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	window.location="<%=contextPath%>/dept/apply/deleteDeptApp.do?CODE="+currentRow.selfproperty;
           
    }
    }
   //新增操作
     function onAddData(){
      var height = 270;
        var width = 650;
        var topis = (screen.height - height) / 2;
        var leftis = (screen.width - width) / 2;
        var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		var url="<%=request.getContextPath()%>/dept/apply/initialDeptApp.do";
		var windowTitle="";
        window.open(url,windowTitle,option);
    }  
    
   function disableBtn(){
	  try{
	   var rowObj = currentRow;
	   var table=document.getElementById("deptCodeList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	    var cell="";
	    if(firstRow!=null){
	      cell=rowObj.cells[4].innerText;
	   }
	   if(firstRow==null||cell=="Submitted"){
	     window.parent.parent.deleteBtn.disabled=true;
	     window.parent.saveBtn.disabled=true;
	     window.parent.submitBtn.disabled=true;

	   }else{
	     window.parent.parent.deleteBtn.disabled=false;
	     window.parent.saveBtn.disabled=false;
	     window.parent.submitBtn.disabled=false;
	   }
	   }catch(e){}
	}
    
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    try{
	if(rowObj==null){
	   window.parent.onRefreshData("");
	    var obj = window.parent;
	   obj.parent.deleteBtn.disabled=true;
	}else{
	currentRow=rowObj;
	var rid=rowObj.selfproperty;
	window.parent.onRefreshData(rid);
	var obj = window.parent;
	var status = rowObj.cells[4].innerText;
   
  	if(status=="Submitted"){  
	   obj.parent.deleteBtn.disabled=true;
	} else {
	   obj.parent.deleteBtn.disabled=false;
	 }
  }
  }catch(e){}
}
     //当进入此页时，自动选中第一行，并国际化表头
    function autoClickFirstRow(){
      try{
	    var table=document.getElementById("deptCodeList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="deptCode.DepartmentManager"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.NickName"/>";
		var str3="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";
		var str4="<bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/>";
		

		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Dept Manager",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Dept Short",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Dept Full",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Application Status",str4);
		cells[0].title=cells[0].title.replace("Apply No",str0);
		cells[1].title=cells[1].title.replace("Dept Manager",str1);
		cells[2].title=cells[2].title.replace("Dept Short",str2);
		cells[3].title=cells[3].title.replace("Dept Full",str3);
		cells[4].title=cells[4].title.replace("Application Status",str4);
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		
	
  </head>
  
<body >
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="deptCodeList"
       items="webVo"
       var="aCode" scope="request" 
       action="${pageContext.request.contextPath}/dept/apply/listAllDeptApp.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        sortable="true"
        locale="<%=_language%>"
       >
        <ec:row onclick="rowOnClick(this);" selfproperty="${aCode.rid}">
             <ec:column style="width:20%" property="rid" tooltip="${aCode.rid}" title="Apply No" />
             <ec:column style="width:20%" property="deptManager" tooltip="${aCode.deptManager}" title="Dept Manager" />         
             <ec:column  style="width:20%" property="acntName" tooltip="${aCode.acntName}" title="Dept Short"/> 
             <ec:column style="width:20%" property="applicationDate" tooltip="${aCode.applicationDate}" title="Dept Full" />      
             <ec:column style="width:20%" property="applicationStatus" tooltip="${aCode.applicationStatus}" title="Application Status" />        
         </ec:row>
         
     </ec:table>
     <SCRIPT language="JavaScript" type="text/JavaScript">
        autoClickFirstRow();
     </SCRIPT>
  </body>
 
</html>
