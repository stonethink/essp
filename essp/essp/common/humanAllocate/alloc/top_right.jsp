<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value="TOP LEFT"/>
</tiles:insert>
</head>
<script language="JavaScript">
function default_title(){
        td1.style.display = "none";
	td2.style.display = "none";
	td3.style.display = "none";
	td4.style.display = "none";
	td5.style.display = "none";
}
<%--  根据type_list判断显示哪列，如“34”即显示td3和td4,"Industry"和"Language" --%>
function add(type_list){
	var num_sort = parseInt(type_list);
	if(num_sort>0){
          td1.style.display = "none";
          td2.style.display = "none";
          td3.style.display = "none";
          td4.style.display = "none";
          td5.style.display = "none";
          do{
            var x = num_sort%10;
            num_sort = (num_sort-x)/10;
            if(x==1){
              td1.style.display = "";
            }else if(x==2){
              td2.style.display = "";
            }else if(x==3){
              td3.style.display = "";
            }else if(x==4){
              td4.style.display = "";
            }else if(x==5){
              td5.style.display = "";
            }
          }while(num_sort>0);
        }
}
</script>
<body leftmargin="0" topmargin="0" onload="default_title()">
<table width="500" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#999999"><table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
          <td id="td6" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Code</div></td>
          <td id="td7" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Name</div></td>
          <td id="td8" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Sex</div></td>
          <td id="td1" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">PostRank</div></td>
          <td id="td2" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Skill</div></td>
          <td id="td3" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Industry</div></td>
          <td id="td4" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Language</div></td>
          <td id="td5" width="100" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
            <div align="center">Management</div></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
