<%@ page contentType="text/html; charset=UTF-8" %>
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
            var obj = window.parent;
            var LoginId=rowObj.selfproperty;
            var loginName = rowObj.cells[1].innerHTML;
            var domain = rowObj.cells[2].innerHTML;
            if(currentRow == null){
             window.parent.parent.deleteBtn.disabled=true;
             window.parent.saveBtn.disabled=true;
            }else{
              window.parent.parent.deleteBtn.disabled=false;
              window.parent.saveBtn.disabled=false;
            }
            window.parent.onRefreshData(LoginId, loginName, domain);
          }
          
     function onAddData(){
             var param =new HashMap();
             allocMultipleInAD(param,"","","");
             if(param.size()<=0) {
				return;
	         }
             var p=document.getElementById("User_table");
             var rows=p.rows;
             var lengthRow = p.tBodies[0].childNodes.length;
       if(rows.length > 0) {
             var exist = "false";
          for(var i = 0;i<param.size();i++) {
		     for(var j = 0; j<rows.length;j++){
		         if(rows[j].cells[0].innerHTML == param.values()[i].loginId) {
		    	      exist = "true";
		    	      break;
                 }
		     }
		     if(exist == "false") {
		         addUser(param.values()[i].loginId,
                        param.values()[i].name,
                        param.values()[i].domain,
                        (i%2==1?"even":"odd"));
             }
              exist = "false";
		  }
       } else {
          for(var i = 0;i<param.size();i++) {
                   addUser(param.values()[i].loginId,
                        param.values()[i].name,
                        param.values()[i].domain,
                        (i%2==1?"even":"odd"));
          }
       }
          var lastRow = p.rows[rows.length - 1]
          rowOnClick(lastRow);
          onChangeBackgroundColor(lastRow);
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
            tddomain.innerText = domain;
            autoClickFirstRow();
          }

         function onDeleteData(){
		 if(currentRow==null) {
          alert("<bean:message bundle="application" key="global.confirm.selectRow"/>");
          return;
          }  
         if(confirm("<bean:message bundle="application" key="global.confirm.delete"/>")) {
    	  window.location="<%=contextPath%>/privilege/deletePrivilege.do?loginId="+currentRow.selfproperty;
          }
		}

          //当进入此页时，自动选中第一行，并国际化表头
         function autoClickFirstRow(){
            var table=document.getElementById("User_table");
            var str0 = "<bean:message bundle="hrbase" key="hrbase.loginId"/>";
            var str1 = "<bean:message bundle="hrbase" key="hrbase.loginName"/>";
            var str2 = "<bean:message bundle="hrbase" key="hrbase.domain"/>";
            var thead_length=table.tHead.rows.length;
            var tds=table.rows[thead_length-1];
            var cells=tds.cells;
         //使EC标签的表头国际化
           cells[0].innerHTML=cells[0].innerHTML.replace("Login Id",str0);
           cells[0].title = cells[0].title.replace("Login Id",str0);
           cells[1].innerHTML=cells[1].innerHTML.replace("User Name", str1)
           cells[1].title = cells[1].title.replace("User Name",str1);
           cells[2].innerHTML=cells[2].innerHTML.replace("Domain", str2)
           cells[2].title = cells[2].title.replace("Domain",str2);
           
            if(table.rows.length>thead_length){
              window.parent.parent.deleteBtn.disabled=false;
              window.parent.saveBtn.disabled=false;
              var firstRow=table.rows[thead_length];
              rowOnClick(firstRow);
              onChangeBackgroundColor(firstRow);
            }else{
              window.parent.parent.deleteBtn.disabled=true;
              window.parent.saveBtn.disabled=true;
              window.parent.onRefreshData(null, null, null);
            }
          }
          </script>
</head>

<body >
  		<ec:table
          var="aUser"
          tableId="User"
          items="webVo" scope="request"   
          action="${pageContext.request.contextPath}/privilege/initUserList.do"
          imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
        >
       <ec:row onclick="rowOnClick(this);" selfproperty="${aUser.loginId}">
             <ec:column  property="loginId"  tooltip="${aUser.loginId}"  title="Login Id"/>
             <ec:column  property="loginName" tooltip="${aUser.loginName}" title="User Name"/>
             <ec:column  property="domain"  tooltip="${aUser.domain}"  title="Domain"/>
         </ec:row>

       </ec:table>
</body>
<SCRIPT language="JavaScript" type="text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
</html>
