<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>Site Privilege List</title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
       input_field {width:60}
       #input_text {width:60}

     </STYLE>
     <script language="javascript" type="text/javascript">
     var loginId = "";
     var select="";
     var unselect="";
     
     function setAreaState(chkbx) {
       if (chkbx.checked) {
         eval('document.forms.Role.chkbx_' + chkbx.name).value='selected';
         select = select + "," + chkbx.name;
         
       } else {
         eval('document.forms.Role.chkbx_' + chkbx.name).value='unselected';
         unselect = unselect + "," + chkbx.name;
       }
    }

    function onSaveData(){
    	  if(select == "" && unselect == "") {
    	  	  //submit_flug = false;
    	      return;
    	  }
          var form=document.forms[0];
          form.action = "<%=contextPath%>/privilege/updatePrivilegeSite.do?select="+select+"&unselect="+unselect;
          form.submit();
    }


    //当进入此页时，自动选中第一行，并国际化表头
    function autoClickFirstRow(){
      var table=document.getElementById("Role_table");
      var str0 = "<bean:message bundle="hrbase" key="hrbase.domain"/>";
      var str1 = "<bean:message bundle="hrbase" key="hrbase.choice"/>";
      var thead_length=table.tHead.rows.length;
      var tds=table.rows[thead_length-1];
      var cells=tds.cells;
      //使EC标签的表头国际化
      cells[0].innerHTML=cells[0].innerHTML.replace("Domain",str0);
      cells[0].title = cells[0].title.replace("Domain",str0);
      cells[1].innerHTML=cells[1].innerHTML.replace("Choice", str1)
      cells[1].title = cells[1].title.replace("Choice",str1);
      var firstRow=table.rows[thead_length];
	    if(table.rows.length>thead_length){
	       //如果有数据
		   onChangeBackgroundColor(firstRow);
	    }
    }


     </script>

  </head>
  <body>

       <ec:table
         var="aRole"
         tableId="Role"
         items="webVo" scope="request"
         action="${pageContext.request.contextPath}/privilege/privilegeSiteList.do"
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >
       <ec:row  selfproperty="${aRole.loginId}">
             <ec:column width="20%" property="belongSite"  tooltip="${aRole.belongSite}"  title="Domain"/>
             <ec:column 
                 alias="checkbox"
                 title="Choice"
                 width="20%"
                 sortable="false"
                 cell="server.essp.hrbase.privilege.action.SelectedRoles"
              />
         </ec:row>
     </ec:table>
  </body>
  <SCRIPT language="JavaScript" type="text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
</html>
