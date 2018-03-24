<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
 <head>
        <TITLE><bean:message bundle="application"  key="RoleDefine.RoleList.title"/></TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
        var currentRow;
    function onAddData(){

       var height = 250;
       var width = 600;
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url="<%=contextPath%>/security/AllParentList.do";
	   var windowTitle="";
       window.open(url,windowTitle,option);
    }
    //执行删除按钮的动作
    function onDeleteData() {
    if(currentRow==null) {
        <!--alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");-->
        return;
    }
    if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	window.location="<%=contextPath%>/security/deleteRole.do?roleId="+currentRow.selfproperty;
    }
   }
     //单击行时的动作，更新下帧中的数据
   function rowOnClick(rowObj) {
    if(rowObj==null){
		return;
	}
	currentRow=rowObj;
	var roleId=rowObj.selfproperty;
	window.parent.onRefreshData(roleId);

   }

	//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){

            var table=document.getElementById("Role_table");
            var str0 = "<bean:message bundle="application"  key="RoleDefine.Role.roleId"/>";
            var str1 = "<bean:message bundle="application"  key="RoleDefine.Role.roleName"/>";
            var str2 = "<bean:message bundle="application"  key="RoleDefine.Role.parentId"/>";
            var str3 = "<bean:message bundle="application"  key="RoleDefine.Role.status"/>";
            var str4 = "<bean:message bundle="application"  key="RoleDefine.Role.isVisiable"/>";
            var thead_length=table.tHead.rows.length;
            var tds=table.rows[thead_length-1];
            var cells=tds.cells;
		//使EC标签的表头国际化
            cells[0].innerHTML=cells[0].innerHTML.replace("Role Id",str0);
            cells[0].title = cells[0].title.replace("Role Id",str0);
            cells[1].innerHTML=cells[1].innerHTML.replace("Role Name", str1);
            cells[1].title = cells[1].title.replace("Role Name",str1);
            cells[2].innerHTML=cells[2].innerHTML.replace("Parent Id",str2);
            cells[2].title = cells[2].title.replace("Parent Id",str2);
            cells[3].innerHTML=cells[3].innerHTML.replace("Status",str3);
            cells[3].title = cells[3].title.replace("Status",str3);
            cells[4].innerHTML=cells[4].innerHTML.replace("Visible",str4);
            cells[4].title = cells[4].title.replace("Visible",str4);
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
         var="aRole"
         tableId="Role"
         items="webVo" scope="request"
         action="${pageContext.request.contextPath}/security/listAllRole.do"
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >

       <ec:row onclick="rowOnClick(this);" selfproperty="${aRole.roleId}">
             <ec:column width="20%" property="roleId"   tooltip=""  title="Role Id"/>
             <ec:column width="20%" property="roleName"  tooltip=""  title="Role Name"/>
             <ec:column width="20%" property="parentId"   tooltip="" title="Parent Id"/>
             <ec:column
                 alias="text"
                 title="Status"
                 width="20%"
                 sortable="false"
                 cell="server.essp.security.ui.role.EcRoleTypeStatus"
                 />
             <ec:column
                 alias="checkbox"
                 title="Visible"
                 width="20%"
                 sortable="false"
                 cell="server.essp.security.ui.role.EcRoleTypeVisible"
                 />
         </ec:row>
     </ec:table>
  </body>
</html>
