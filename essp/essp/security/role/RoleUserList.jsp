<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
 <head>
        <TITLE><bean:message bundle="application"  key="UserAssignRoles.AssignAuthorityMaintenance.tabtitle"/></TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
        var currentRow;
 
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    if(rowObj==null){
		return;
	}
	currentRow=rowObj;
	var roleId=rowObj.selfproperty;

   }

	//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){

            var table=document.getElementById("RoleUser_table");
            var str0 = "<bean:message bundle="application"  key="UserAssignRoles.UserList.loginId"/>";
            var str1 = "<bean:message bundle="application"  key="UserAssignRoles.UserList.loginName"/>";
            var thead_length=table.tHead.rows.length;
            var tds=table.rows[thead_length-1];
            var cells=tds.cells;
		//使EC标签的表头国际化
            cells[0].innerHTML=cells[0].innerHTML.replace("Role Id",str0);
            cells[0].title = cells[0].title.replace("Role Id",str0);
            cells[1].innerHTML=cells[1].innerHTML.replace("Role Name", str1);
            cells[1].title = cells[1].title.replace("Role Name",str1);
	    if(table.rows.length>thead_length){
	       //如u26524 有u25968 据
	       var firstRow=table.rows[thead_length];
		   rowOnClick(firstRow);
		   onChangeBackgroundColor(firstRow);
	    }
	}
     </script>
     </head>


<body onload="autoClickFirstRow()">
  		<ec:table
         var="aRoleUser"
         tableId="RoleUser"
         items="webVo" scope="request"
         action=""
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >
       <ec:row onclick="rowOnClick(this);" selfproperty="${aRoleUser.roleId}">
             <ec:column width="20%" property="roleId"   tooltip=""  title="Role Id"/>
             <ec:column width="20%" property="roleName"  tooltip=""  title="Role Name"/>
         </ec:row>
     </ec:table>
  </body>
</html>
