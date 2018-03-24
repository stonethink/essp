
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Project Code Apply"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>


<script language="JavaScript" type="text/javascript">
 var currentRow;
 function getCurrentRow(){
    return currentRow;
 
 }
 function disableBtn(){
	var rowObj = currentRow;
	  try{
	   //alert("ok");
	   
	   var table=document.getElementById("applyRecordList_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   var cell="";
	   if(firstRow!=null){
	    cell=rowObj.cells[5].innerText;
	   }
	  
	   //alert(cell);
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
   //点击申请按钮打开新窗口
    function onAddData(isFinance){
       
       var height = 670;
       var width = 840; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/projectpre/projectcode/codeapply/PopCodeApply.jsp?appType=add&isFinance=' + isFinance;
	   var windowTitle="";
       window.open(url,windowTitle,option);

    }
    //删除当前选择的记录
    function onDeleteData(){
      if(currentRow==null) {
        alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
        return;
    }
    if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	window.location="<%=contextPath%>/project/apply/deleteProjectAcntApp.do?appType=add&rid="+currentRow.selfproperty;
    }
    }
//单击行时的动作，更新下帧中的数据
    function rowOnClick(rowObj) {
    try{
	if(rowObj==null){
		window.parent.onRefreshData("");
	} else {
	currentRow=rowObj;
	var rid=rowObj.selfproperty;
	var status = rowObj.cells[5].innerText;
	var obj = window.parent;
	window.parent.onRefreshData(rid);
	
	
	//if(status=="Submitted"){
	  
	   //obj.parent.deleteBtn.disabled=true;
	//} else {
	 //  obj.parent.deleteBtn.disabled=false;
	 //}
	
	}
	}catch(e){}
   }
   //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	  try{
	    var table=document.getElementById("applyRecordList_table");
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>";
		var str1="<bean:message bundle="projectpre" key="projectCode.NickName"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/>";
        var str3="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.AchieveBelong"/>";
        var str4="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate"/>";
        var str5="<bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/>";
        
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Apply No",str0);
		cells[0].title = cells[0].title.replace("Apply No",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Code Type",str1);
		cells[1].title = cells[1].title.replace("Code Type",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Applicant",str2);
		cells[2].title = cells[2].title.replace("Applicant",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Achieve Belong",str3);
		cells[3].title = cells[3].title.replace("Achieve Belong",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("Apply Date",str4);
		cells[4].title = cells[4].title.replace("Apply Date",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("Status Code",str5);
		cells[5].title = cells[5].title.replace("Status Code",str5);
		 var firstRow=table.rows[thead_length];
		  
		 rowOnClick(firstRow);
		disableBtn();
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
       tableId="applyRecordList"
       items="webVo"
       var="aRecord" scope="request"
       action="${pageContext.request.contextPath}/project/apply/listAllProjectAcntApp.do?appType=add"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif" 
       locale="<%=_language%>" 
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${aRecord.rid}">
             
             <ec:column style="width:18%"  property="rid" tooltip="${aRecord.rid}" title="Apply No"/>                   
             <ec:column style="width:18%"   property="acntName" tooltip="${aRecord.acntName}" title="Code Type" />           
             <ec:column style="width:18%"  property="PMName" tooltip="${aRecord.PMName}" title="Applicant" />       
             <ec:column style="width:18%"   property="achieveBelong" tooltip="${aRecord.achieveBelong}" title="Achieve Belong" />        
             <ec:column style="width:18%"  property="applicationDate" tooltip="${aRecord.applicationDate}" title="Apply Date" />
             <ec:column style="width:18%"  property="applicationStatus" tooltip="${aRecord.applicationStatus}" title="Status Code" />                      
         </ec:row>
         
     </ec:table>
   
    <script language="JavaScript" type="text/javascript">
	
	   autoClickFirstRow();
	
	</script>
	
	</body>
	
</html>
