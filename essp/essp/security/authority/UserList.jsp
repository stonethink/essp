<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="c2s.essp.common.user.DtoUser" %>
<%@ page import="java.util.*" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
 <head>
        <TITLE>UserList</TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
          //单击行时的动作，更新下帧中的数据
          function rowOnClick(rowObj) {
            if(rowObj==null){
              return;
            }
            currentRow=rowObj;
            var LoginId=rowObj.selfproperty;
            var Domain=rowObj.cells[2].innerText;
            window.parent.onRefreshData(LoginId,Domain);

          }
          function onAddData(){

             var param =new HashMap();
             allocMultipleInAD(param,"","","");
             var p=document.getElementById("User_table");
             var lengthRow = p.tBodies[0].childNodes.length;
             if(param.size()>0){
               for(var r = 0;r<lengthRow;r++){
                 p.tBodies[0].removeChild(p.tBodies[0].childNodes[0]);
               }
             }
             for(var i = 0;i<param.size();i++) {
              addUser(param.values()[i].loginId,
               param.values()[i].name,
               param.values()[i].domain,(i%2==1?"even":"odd"));
             }

          }

          function addUser(loginId,name,domain,className) {
            var p=document.getElementById("User_table");
            var tr=document.createElement('<tr class="'+className+'" selfproperty="'+loginId+'"  onclick="onChangeBackgroundColor(this);rowOnClick(this);" >' );
            var tdloginid = document.createElement('<td width="25%" ></td>');
            var tdname=document.createElement('<td width="25%" ></td>');
            var tddomain = document.createElement('<td width="25%" ></td>');
            //var tdemptype = document.createElement('<td width="25%" ></td>>');
            p.tBodies[0].appendChild(tr);
            tr.appendChild(tdloginid);
            tr.appendChild(tdname);
            //tr.appendChild(tdemptype);
            tr.appendChild(tddomain);
            tdloginid.innerText = loginId;
            tdname.innerText = name;
            //tdemptype.innerText = type;
            tddomain.innerText = domain;

            autoClickFirstRow();
          }

          //当进入此页时，自动选中第一行，并国际化表头
          function autoClickFirstRow(){
       
            var table=document.getElementById("User_table");
            var str0 = "<bean:message bundle="application"  key="UserAssignRoles.UserList.loginId"/>";
            var str1 = "<bean:message bundle="application"  key="UserAssignRoles.UserList.loginName"/>";
            var str2 = "<bean:message bundle="application"  key="UserAssignRoles.UserList.domain"/>";
            var thead_length=table.tHead.rows.length;
           var tds=table.rows[thead_length-1];
           var cells=tds.cells;
      //使EC标签的表头国际化
      cells[0].innerHTML=cells[0].innerHTML.replace("Login Id",str0);
      cells[0].title = cells[0].title.replace("Login Id",str0);
      cells[1].innerHTML=cells[1].innerHTML.replace("User Name", str1)
      cells[1].title = cells[1].title.replace("User Name",str1);
      cells[2].innerHTML=cells[2].innerHTML.replace("Domain",str2);
      cells[2].title = cells[2].title.replace("Domain",str2);
            if(table.rows.length>thead_length){
              //如u26524 有u25968 据
              var firstRow=table.rows[thead_length];
              rowOnClick(firstRow);
              onChangeBackgroundColor(firstRow);
            }
          }
          </script>
</head>

<body onload="autoClickFirstRow();">
  		<ec:table
         var="aUser"
         tableId="User"
          items="webVo" scope="request"   
          sortable="false"     
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >

       <ec:row onclick="rowOnClick(this);" selfproperty="${aUser.userLoginId}">
             <ec:column width="25%" property="userLoginId"  tooltip=""  title="Login Id"/>
             <ec:column width="25%" property="userName"   tooltip="" title="User Name"/>
             <ec:column width="25%" property="domain"   tooltip=""  title="Domain"/>
         </ec:row>

     </ec:table>
</body>
</html>
