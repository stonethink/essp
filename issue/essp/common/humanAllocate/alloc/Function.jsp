<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<%
//title卡片标题的名称，Ltitle卡片标题栏的宽度，Ntitle卡片标题的个数,UrlLay卡片对应的层的页面，HeiLay层的高度
	String[] title = {"Direct Allocation","OBS Allocation","Conditional Allocation"};
	String[] Ltitle = {"110","100","130"};
	int Ntitle = 0;
	if(title!=null){
		Ntitle = title.length;
	}
%>
<script language="JavaScript">
<%-- 卡片切换方法 --%>
function turnto(www) {
    if(www == "1") {
	    Layer1.style.visibility = "visible";
		Layer2.style.visibility = "hidden";
		Layer3.style.visibility = "hidden";
	} else if(www == "2") {
	    Layer1.style.visibility = "hidden";
		Layer2.style.visibility = "visible";
		Layer3.style.visibility = "hidden";

	} else if(www == "3") {
	    Layer1.style.visibility = "hidden";
		Layer2.style.visibility = "hidden";
		Layer3.style.visibility = "visible";
	}
}

function loadview() {
    Layer1.style.visibility = "visible";
	Layer2.style.visibility = "hidden";
	Layer3.style.visibility = "hidden";
}
//将所有的样式至为未点中的样式
function clearall(){
	<%
		for(int j=1;j<Ntitle;j++){
	%>
	document.all.img<%=j+1%>.src="../images/fun_lr.jpg";
	td<%=j+1%>.background="../images/fun_back.jpg";
	Layer<%=j+1%>.style.visibility="hidden";
	<%
		}
		if(Ntitle>1){
	%>
	document.all.img<%=Ntitle+1%>.src="../images/fun_right.jpg";
	document.all.img1.src="../images/fun_left.jpg";
	td1.background="../images/fun_back.jpg";
	Layer1.style.visibility="hidden";
	<%
		}
	%>
}
//将点中的卡片样式的转化
function change(n){
	clearall();
	var imgleft=document.getElementById('img'+n);
	var imgright=document.getElementById('img'+(n+1));
	var imgback=document.getElementById('td'+n);
	var vLayer=document.getElementById('Layer'+n);

	imgleft.src="../images/now_left.jpg";
	imgright.src="../images/now_right.jpg";
	imgback.background="../images/now_back.jpg";
	vLayer.style.visibility="visible";
}
</script>
<body leftmargin="0" topmargin="0">
<form name="alloc_tool_function">
    <table class="tablecenter" CELLPADDING="0" CELLSPACING="0" width="98%"  align="center">
      <tr>
<!--Title of card Start-->
          <%
          	if(Ntitle>0){
          %>
          <td width="10"><img id="img1" src="../images/now_left.jpg" width="10" height="23"></td>
          <td id="td1" width="<%=Ltitle[0]%>" valign="bottom" background="../images/now_back.jpg" class="list_desc">
              <div align="center" onclick=change(1); style="CURSOR: hand"><%=title[0]%></div></td>
          <td width="10"><img id="img2" src="../images/now_right.jpg" width="10" height="23"></td>
          <%
          	}
          	for(int i=1;i<Ntitle-1;i++){
          %>
          <td id="td<%=i+1%>" width="<%=Ltitle[i]%>" valign="bottom" background="../images/fun_back.jpg" class="list_desc">
              <div align="center" onclick=change(<%=i+1%>); style="CURSOR: hand"><%=title[i]%></div></td>
          <td width="10"><img id="img<%=i+2%>" src="../images/fun_lr.jpg" width="10" height="23"></td>
          <%
          	}
          	if(Ntitle>1){
          %>
          <td id="td<%=Ntitle%>" width="<%=Ltitle[Ntitle-1]%>" valign="bottom" background="../images/fun_back.jpg" class="list_desc">
              <div align="center" onclick=change(<%=Ntitle%>); style="CURSOR: hand"><%=title[Ntitle-1]%></div></td>
          <td width="10"><img id="img<%=Ntitle+1%>" src="../images/fun_right.jpg" width="10" height="23"></td>
          <%
          	}
          %>
<!--Title of card End -->
          <td height="23"  ID="t3" width="*">&nbsp;</td>
</table>
<table width="100%" height="86%" border="0" cellpadding="0" cellspacing="0">
  <tr bordercolordark="#0033CC" width="100%" height="100%"  border="0" >
    <td width="1%" align="left" bordercolor="#003366"></td>
    <td width="99%" align="left">
      <table width="98%" height="100%" border="0" cellpadding="1" cellspacing="0">
        <tr>
          <td bgcolor="#94AAD6"><table width="100%" height="100%" border="0" cellpadding="1" cellspacing="0">
              <tr>
                <td bgcolor="#FFFFFF">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<div id="Layer1" class="divtype" style="position:absolute; z-index:2; left: 5px; visibility: visible; top: 25px; height:87%;width=99%">
    <table width="100%"  height="100%">
      <tr>
        <td valign="top" >
<p align="center">
            <iframe name=certain
                  src="IfmCertain.jsp"
                  frameBorder=0 width="98%"
                  height="98%"
				  scrolling="no"
                  align="left">
          </iframe>
    </td></tr>
  </table>
</div>
<div id="Layer2" class="divtype" style="position:absolute; z-index:1; left: 5px; visibility: visible; top: 25px; height:87%;width=99%">
    <table width="100%"  height="100%">
      <tr>
        <td valign="top" >
<p align="center">
              <iframe name=obs
                  src="/essp/common/humanAllocate/alloc/UserAllocByOBSPre.do"
                  frameBorder=0 width="98%"
                  height="98%"
				  scrolling="no"
                  align="left">
          </iframe>

    </td></tr>
  </table>
</div>
  <div id="Layer3" class="divtype" style="position:absolute; z-index:0; left: 5px; visibility: visible; top: 25px; height:87%; width=99%;">
    <table width="100%"  height="100%">
    <tr>
        <td valign="top" >
<p align="center">
            <iframe name=condition
                  src="/essp/common/humanAllocate/alloc/UserAllocByConPre.do"
                  frameBorder=0 width="98%"
                  height="98%"
				  scrolling="no"
                  align="left">
          </iframe>
    </td></tr>
  </table>
</div>
</form>
<script language="JavaScript">
loadview();
</script>
</body>
</html>

