<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
 <head>
        <TITLE>agent</TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
        var currentRow;
        function onAddData(){
          var param =new HashMap();
          allocMultipleInAD(param,"","","");
          var loginId="";
          var name="";
          if(param.size()>0){
            for(var i = 0;i<param.size();i++) {
              if(i==0){
                loginId = param.values()[i].loginId;
                name = param.values()[i].name;
              }else{
                loginId = loginId +","+ param.values()[i].loginId;
                name = name +","+ param.values()[i].name;
              }

            }
          }

          if(loginId!="" && name!=""){
            addAuthorize.loginId.value=loginId;

            addAuthorize.loginName.value=name;
            addAuthorize.action="<%=contextPath%>/security/AddAuthorize.do";
            addAuthorize.submit();
          }
        }

        function onDeleteData() {
          if(currentRow==null) {
            <!--alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");-->
            return;
          }
          if(confirm("Are you sure delete it?")) {
            window.location="<%=contextPath%>/security/deleteAuthorize.do?loginId="+currentRow.selfproperty;
          }
        }

        function rowOnClick(rowObj) {
          if(rowObj==null){
            return;
          }
          currentRow=rowObj;
        }

        function autoClickFirstRow(){
           var str0="<bean:message bundle="application" key="UserAssignRoles.UserList.loginId"/>";
		   var str1="<bean:message bundle="application" key="UserAssignRoles.UserList.loginName"/>";
          var table=document.getElementById("Auth_table");
          var thead_length=table.tHead.rows.length;
            var tds=table.rows[thead_length-1];
		    var cells=tds.cells;
            cells[0].innerHTML=cells[0].innerHTML.replace("Login Id",str0);
		    cells[1].innerHTML=cells[1].innerHTML.replace("Name",str1);
		 
		    cells[0].title=cells[0].title.replace("Login Id",str0);
		    cells[1].title=cells[1].title.replace("Name",str1);
		   
          if(table.rows.length>thead_length){

            var firstRow=table.rows[thead_length];
            rowOnClick(firstRow);
            onChangeBackgroundColor(firstRow);
          }
        }
     </script>
     </head>


<body onload="autoClickFirstRow()">
  		<ec:table
         var="aAuth"
         tableId="Auth"
         items="webVo" scope="request"
         action=""
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >

       <ec:row onclick="rowOnClick(this);" selfproperty="${aAuth.userLoginId}">
             <ec:column width="50%" property="userLoginId"   tooltip=""  title="Login Id" sortable="false"/>
             <ec:column width="50%" property="userName"  tooltip=""  title="Name" sortable="false"/>
       </ec:row>
     </ec:table>
  </body>
<html:form name="addAuthorize" action="" method="post">
   <input type="hidden" name="loginId" >
   <input type="hidden" name="loginName" >
</html:form>
</html>
