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
	 
	 function onAddData(){      
       var height = 150;
       var width = 350;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/projectpre/customer/typeinfo/TypeCodeAdd.jsp';
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
    	window.location="<%=contextPath%>/pc/deleteClassCode.do?code="+currentRow.selfproperty;
    }
   }
   function setAreaCodeState(chkbx) {
      //make sure that always know the state of the checkbox
       if (chkbx.checked) {
             eval('document.forms.ac_setting.chkbx_' + chkbx.name).value='SELECTED';
           } else {
                   eval('document.forms.ac_setting.chkbx_' + chkbx.name).value='UNSELECTED';
           }
      }
	
	  //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
	if(rowObj==null){
		return;
	}
	currentRow=rowObj;
	var code=rowObj.selfproperty;
	window.parent.onRefreshData(code);
   }
    
    
     //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	  try{
	    var table=document.getElementById("TypeCodeListTable_table");    
        var str0="<bean:message bundle="projectpre" key="customer.BusinessType"/>";
		var str1="<bean:message bundle="projectpre" key="customer.ChineseInstruction"/>";
		var str2="<bean:message bundle="projectpre" key="customer.Enable"/>";
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Business Type",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Chinese Instruction",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("Status",str2);
	    if(table.rows.length>thead_length){
	       //如果有数据
	       var firstRow=table.rows[thead_length];
		   rowOnClick(firstRow);
		   onChangeBackgroundColor(firstRow);
	    }
	    }catch(e){}
	}
	</script>
  </head>
  
<body topmargin="0">
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="TypeCodeListTable"
       items="webVo"
       var="aCode"  scope="request"
       action="${pageContext.request.contextPath}/pc/listAllByKind.do?kind=BusinessType"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
      locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" selfproperty="${aCode.id.code}">
             
             <ec:column width="30%" property="id.code" tooltip="${aCode.id.code}" title="Business Type"/>
             <ec:column width="30%" property="name" tooltip="${aCode.name}" title="Chinese Instruction"/>
             <ec:column 
                 alias="checkbox"
                 title="Status " 
                 width="30%"
                 filterable="false" 
                 sortable="false" 
                 cell="server.essp.projectpre.ui.customer.kind.EcClassCodeStatus"
                 />           
         </ec:row>
     </ec:table>
     <script type="text/javascript" language="javascript">
     autoClickFirstRow();
     </script>
  </body>

</html>
