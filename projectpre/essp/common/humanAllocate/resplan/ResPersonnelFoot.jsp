<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="accountId" name="accountId"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<body topmargin="0" leftmargin="0">
  <form name="form1" method="post" action="">
    <input type="hidden" name="use_id" value = "1">
      <table class="tablecenter" CELLPADDING="0" CELLSPACING="0" width="98%"  align="center" >
        <tr>
          <!--Title of card Start-->
          <td width="10"><img id="img1" src="../images/now_left.jpg" width="10" height="23"></td>
          <td id="td1" width="100" valign="bottom" background="../images/now_back.jpg" class="list_desc">
              <div align="center" onclick=";" style="CURSOR: hand">Record</div></td>
          <td width="10"><img id="img2" src="../images/now_right.jpg" width="10" height="23"></td>
          <!--Title of card End -->
          <td class="oracelltext"></td>
          <td class="oracelltext"><div align="right">
            <input type="button" name="AddBtn" value="Add" onclick="Add();" class="button">
            <input type="button" name="DeleteBtn" value="Delete" onclick="Delete();" class="button">
            <input type="button" name="SaveBtn" value="Save" onclick="Submit();" class="button">
           </div>
      </td>
    </tr>
  </table>
      <div valign="top" id="Layer1" class="divtype" style="position:absolute; z-index:3; left: 5px; visibility: visible; top: 25px; height:90%;width=99%">
        <table width="100%"  height="90%"  valign="top"  bordercolor="#94AAD6">
          <tr class="orarowheader"><td height="1" colspan="5" nowrap class="orarowheader"><a id="a1">&nbsp;</a></td>
          </tr>
          <tr >
            <td background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext" width="25%" nowrap>Begin Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext" width="24%" nowrap>End Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td background="../photo/alloc_tool/alloc_tool_back3.jpg" align="right"class="oracelltext" width="24%" nowrap>%Usage</td>
            <td background="../photo/alloc_tool/alloc_tool_back3.jpg" align="right"class="oracelltext"  nowrap>Total Hours</td>
            <td background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext" width="7%"  nowrap></td>
          </tr>
          <tr>
            <td class="wind" colspan="5"><p align="left">
              <iframe name="main_view"
                  src = "ResPersonDetail.jsp";
                  frameBorder=0 width="100%"
                  height="85%"
                  align="left">
              </iframe>
            </td>
          </tr>
        </table>
       </div>
<script language="javascript">
function Submit(){
  main_view.submitit();
  parent.personnel_view.location="ResPersonnelView.jsp?accountId=<%=accountId%>";
}

function Delete(){
  main_view.deletit()
}
function Add(){
  main_view.add();
}
</script>

</form>
</body>
</html>
