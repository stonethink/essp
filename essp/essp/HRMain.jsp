<%@ page import="com.enovation.essp.org.databean.*"%>
<%@ page import=" c2s.essp.common.user.*" %>
<%
      //为了与原HR程序兼容,判断Session中是否存在Employee对象,若不存在,则将DtoUser转成Employee
      if(session.getAttribute("Employee") == null){	
      	System.out.println("convert DtoUser to Employee!");
      	DtoUser dtoUser  = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);
      	Employee emp = new Employee();
      	emp.setId(dtoUser.getUserID());
      	emp.setName(dtoUser.getUserName());
      	session.setAttribute("Employee",emp);
      
      	try {
	 	     	com.enovation.essp.common.util.SessionVarUtil util=new com.enovation.essp.common.util.SessionVarUtil();
	 	     	util.setValue(session.getId(),"Employee",emp);
	 	     	util.setValue(session.getId(),"LoginID",dtoUser.getUserLoginId());
	    }
      		catch(Exception e) {
      			e.printStackTrace();
            	//e.printStackTrace(new PrintWriter(out));
      			//return;
      	    }
      }	    
%>
<html>
<head>
<title>main</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
    var isloaded = 0;
    var treeopen = new Array();
    var treecontent = new Array();
    var tmptreecontent = new Array();
    var tmptreeopen = new Array();
    var treecounter = 0;
    var pleaseWait = '请稍候...';
    var statusCollection = "no";
  </script>
  <style type="text/css">
<!--
.table_border { border-top:2px solid #94AAD6;
                border-right:2px solid #94AAD6;
				border-left:2px solid #94AAD6;
				border-bottom:2px solid #94AAD6;
                border-spacing:0px;}

--></style>

</head>
<frameset name="content" frameborder="no" border="0" framespacing="0" cols="200,*" class="table_border">
  <frame src="left.jsp?SubSystem=M300000000" name="left" scrolling="no" marginwidth="0" marginheight="0"   >
  <frame src="myactive.htm" name="main" scrolling="no" marginwidth="2" marginheight="2">
</frameset>
<noframes></noframes>

</html>
