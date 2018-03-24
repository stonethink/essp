<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="c2s.essp.common.user.DtoUserBase"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<%@page import="java.util.List"%>

<html>
 <head>
        <TITLE>authGeneral</TITLE>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

	<script type="text/javascript" language="javascript">
        var param =new HashMap();
        select="";
        function enter(){

           <bean:define id="UserDto" name="webVo" scope="request" />
           <%
                List list =(List)UserDto;
                for(int i=0;i<list.size();i++){
                  DtoUserBase dto = (DtoUserBase)list.get(i);
           %>
                 addUser( '<%=dto.getUserLoginId()%>',
                    '<%=dto.getUserName()%>',(<%=i%>%2==1?"even":"odd"));
           <%
                }
           %>

           var selectuser='<bean:write name="SelectUser" scope="session"/>';
            if(selectuser!=""){
              if(selectuser.indexOf('.')!=-1){
                selectuser = selectuser.replace(".","");
              }
              eval('document.forms.Agent.checkbox_'+ selectuser).checked = true;
            }
        }

        function addUser(loginId,name,className) {
            var loginIds="";
            if(loginId.indexOf('.')!=-1){
               loginIds = loginId.replace(".","");
            }else{
               loginIds = loginId;
            }
            param.put(loginIds,loginId);
            var p=document.getElementById("Agent_table");
            var tr=document.createElement('<tr class="'+className+'" onclick="onChangeBackgroundColor(this);rowOnClick(this);" >' );
            var tdloginid = document.createElement('<td width="40%" ></td>');
            var tdname=document.createElement('<td width="40%" ></td>');
            var tdselect=document.createElement('<td width="20%" ></td>');
            var tdcheckbox=document.createElement('<input type="checkbox"  name="checkbox_'+loginIds+'"  onclick="setAreaState(this)" />');
            p.tBodies[0].appendChild(tr);
            tr.appendChild(tdloginid);
            tr.appendChild(tdname);
            tr.appendChild(tdselect);
            tdselect.appendChild(tdcheckbox);
            tdloginid.innerText = loginId;
            tdname.innerText = name;
            autoClickFirstRow();

        }

        function setAreaState(chkbx) {
          //make sure that always know the state of the checkbox
          //alert(chkbx.name);
          if (chkbx.checked) {
             var login = chkbx.name.replace("checkbox_","");
             select = param.get(login);
             var elements = document.forms[0].elements;
             for(var i=0;i<elements.length;i++){
                var names = elements[i].name;
                if(names.indexOf('checkbox_')!=-1){
                    if( names!=chkbx.name ){
                        eval('document.forms.Agent.'+names).checked = false;
                    }
                }
             }
          }
        }

          function onQuit() {
             var loginUser = '<bean:write name="loginUser" scope="session"/>';
             var user = '<bean:write name="user" scope="session"/>';
             if(loginUser==user){
                alert("<bean:message bundle="application" key="global.agent.not"/>");
             }else{
               if(confirm("<bean:message bundle="application" key="global.quit.agent"/>")){
                 window.location="<%=contextPath%>/security/agent.do?functionType=dis-agent";
               }
             }
          }

          function onAgent() {
            if(select==""){
               alert("<bean:message bundle="application" key="global.select.first"/>");
            }else if(confirm("<bean:message bundle="application" key="global.login.agent"/>")){
               window.location="<%=contextPath%>/security/agent.do?functionType=agent&selected="+select;
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
		   var str2="<bean:message bundle="application" key="Authorize.card.Select"/>";
            var table=document.getElementById("Agent_table");
            var thead_length=table.tHead.rows.length;
		    var tds=table.rows[thead_length-1];
		    var cells=tds.cells;
            cells[0].innerHTML=cells[0].innerHTML.replace("Login Id",str0);
		    cells[1].innerHTML=cells[1].innerHTML.replace("Name",str1);
		    cells[2].innerHTML=cells[2].innerHTML.replace("Select",str2);
		    cells[0].title=cells[0].title.replace("Login Id",str0);
		    cells[1].title=cells[1].title.replace("Name",str1);
		    cells[2].title=cells[2].title.replace("Select",str2);	
            if(table.rows.length>thead_length){

              var firstRow=table.rows[thead_length];
              rowOnClick(firstRow);
              onChangeBackgroundColor(firstRow);
            }
          }
     </script>
     </head>


<body onload="autoClickFirstRow();enter()">
  		<ec:table
         var="aAgent"
         tableId="Agent"
         imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >

       <ec:row onclick="rowOnClick(this);">
             <ec:column width="40%" property="userLoginId"   tooltip=""  title="Login Id" sortable="false"/>
             <ec:column width="40%" property="userName"  tooltip=""  title="Name" sortable="false"/>
             <ec:column
                 alias="text"
                 title='Select'
                 width="20%"
                 sortable="false"
                 />
         </ec:row>
     </ec:table>
  </body>
</html>
