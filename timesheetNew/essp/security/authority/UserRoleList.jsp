<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title><bean:message bundle="application"  key="UserAssignRoles.UserRoleList.title"/></title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
       input_field {width:60}
       #input_text {width:60}

     </STYLE>
     <script language="javascript" type="text/javascript">
     var select="";
     var unselect="";
     function setAreaState(chkbx) {
       //make sure that always know the state of the checkbox
       //alert(chkbx.name);
       if (chkbx.checked) {
         eval('document.forms.Role.chkbx_' + chkbx.name).value='selected';
         select = select + "," + chkbx.name;
       } else {
         eval('document.forms.Role.chkbx_' + chkbx.name).value='unselected';
         unselect = unselect + "," + chkbx.name;
       }
    }

    function onSaveData(){
          //alert("ok");
          var form=document.forms[0];
          //alert(form);
          form.action = "<%=contextPath%>/security/Authority.do?select="+select+"&unselect="+unselect;
          form.submit();
    }

    //当进入此页时，自动选中第一行，并国际化表头
    function autoClickFirstRow(){
      var table=document.getElementById("Role_table");
      var str0 = "<bean:message bundle="application"  key="RoleDefine.Role.roleId"/>";
      var str1 = "<bean:message bundle="application"  key="RoleDefine.Role.roleName"/>";
      var str2 = "<bean:message bundle="application"  key="RoleDefine.Role.description"/>";
      var str3 = "<bean:message bundle="application"  key="RoleDefine.Role.choice"/>";
      var thead_length=table.tHead.rows.length;
      var tds=table.rows[thead_length-1];
      var cells=tds.cells;
      //使EC标签的表头国际化
      cells[0].innerHTML=cells[0].innerHTML.replace("Role Id",str0);
      cells[0].title = cells[0].title.replace("Role Id",str0);
      cells[1].innerHTML=cells[1].innerHTML.replace("Role Name", str1)
      cells[1].title = cells[1].title.replace("Role Name",str1);
      cells[2].innerHTML=cells[2].innerHTML.replace("Description",str2);
      cells[2].title = cells[2].title.replace("Description",str2);
      cells[3].innerHTML=cells[3].innerHTML.replace("Choice",str3);
      cells[3].title = cells[3].title.replace("Choice",str3);
    }


     </script>

  </head>
  <body onload="autoClickFirstRow();">

       <ec:table
         var="aRole"
         tableId="Role"
         items="webVo" scope="request"
         action="${pageContext.request.contextPath}/security/UserRoleList.do"
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >

       <ec:row  selfproperty="${aRole.roleId}">
             <ec:column width="20%" property="roleId"   tooltip=""  title="Role Id"/>
             <ec:column width="20%" property="roleName"  tooltip=""  title="Role Name"/>
             <ec:column width="20%" property="description"   tooltip="" title="Description"/>
             <ec:column
                 alias="checkbox"
                 title="Choice"
                 width="20%"
                 sortable="false"
                 cell="server.essp.security.ui.role.SelectedRoles"
              />
         </ec:row>
     </ec:table>
  </body>
</html>
