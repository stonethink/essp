

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="kindType" value="<%=request.getParameter("kind")%>"/>

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
	     window.parent.parent.deleteBtn.disabled=true;
	     window.parent.saveBtn.disabled=true;
	   }else{
	     window.parent.parent.deleteBtn.disabled=false;
	     window.parent.saveBtn.disabled=false;
	   }
	   }catch(e){}
	}
    function onAddData(){
       var height = 260;
       var width = 420;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   //var url="<%=contextPath%>/parameter/addParameter.do?compId.kind=<%=kindType%>";
	   var url="<%=contextPath%>/projectpre/parameter/AddParameter.jsp?kind=<%=kindType%>";
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
    	window.location="<%=contextPath%>/parameter/deleteParameter.do?kind=<%=kindType%>&code="+code;	
    }
   }
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    if(rowObj==null){
		//return;
	  //alert("rowObj==null");
	  window.parent.onRefreshData("");
	}else{
	  currentRow=rowObj;
	  var code=rowObj.selfproperty;
	  window.parent.onRefreshData(code);
	}
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
		cells[1].innerHTML=cells[1].innerHTML.replace("Item",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Sequence",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("Status",str3);
		
		var firstRow=table.rows[thead_length];
		disableBtn();
		rowOnClick(firstRow);
		
	    if(table.rows.length>thead_length){
	       //如u26524 有u25968 据
	       //var firstRow=table.rows[thead_length];
		   //rowOnClick(firstRow);
		   onChangeBackgroundColor(firstRow);
	    } 
	   }catch(e){}
	}
     </script>
     </head>
  

<body>
  		<ec:table 
	     tableId="Parameter"
         items="webVo" scope="request"
         var="aParameter"
         action="${pageContext.request.contextPath}/parameter/listAllParameter.do"  
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
         
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
     <script type="text/javascript" language="javascript">
      autoClickFirstRow();
     </script>
  </body>
</html>
