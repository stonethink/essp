<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	
  	String fileFullName = (String)request.getParameter("fileFullName");
  	String revision = (String)request.getParameter("revision");
	FileDownloader downloader = new FileDownloader();
	String content = downloader.run2(request, response, out);
%>


<html>
<head> 

<title><%out.print("Revision " +revision+" of " + fileFullName);%></title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<script type="text/javascript" language="JavaScript">
	var name="none";
	function setRelation(){
		//alert(name);
		if( name == "left" ){
			if( btnScroll.value == "�ұ�ͬ������" ){
				parent.rightFrame.body.scrollTop=parent.leftFrame.body.scrollTop;
			}
		}else if( name=="right" ){
			if( btnScroll.value == "���ͬ������" ){
				parent.leftFrame.body.scrollTop=parent.rightFrame.body.scrollTop;
			}
		}
		
	}
	
	function setScollable(){
		if( name=="left" ){
			if( btnScroll.value == "�ұ�ͬ������" ){
				btnScroll.value = "�ұ߲�ͬ������";
			}else{
				btnScroll.value = "�ұ�ͬ������";
			}
		}
		if( name=="right" ){
			if( btnScroll.value == "���ͬ������" ){
				btnScroll.value = "��߲�ͬ������";
			}else{
				btnScroll.value = "���ͬ������";
			}
		}
	}
	
	function onload(num){
		if( num > 0 ){
			setTimeout("onload(0)", num);
		}else{			
			if(name=='none') {
				btnScroll.style.visibility='hidden';
			}else if( name=='left' ){
				btnScroll.value = "�ұ߲�ͬ������";
			}else if( name="right" ){
				btnScroll.value = "��߲�ͬ������";
			}
			
			line.style.fontSize=fileContent.style.fontSize;
		}
	}
</script>

<body id=body onload="onload(100);" onscroll="setRelation();" nowrap='true'>
<input type="button" id="btnScroll" VALUE="�ұ�ͬ������" onclick="setScollable()"/>
&nbsp;<%out.print("Revision " +revision+" of " + fileFullName);%>
<table>
	<tr>
	<td valign="top" id="line">
		<font id="lineFont">
		<br>
		<%
		for( int i = 1; i <= downloader.getLineNum(); i++ ){
			out.print(i+"<br>");
		}
		%>
		</font>
	</td>
	
	<td width="1500" noWrap="true"><textarea id="fileContent" type="text" id="Textobj"  WRAP="off"
											 style="border:1px solid #000000;padding:13px 10px 10px 10px;overflow-x:visible;overflow-y:visible;text-overflow:ellipsis"
									><%=content%></textarea>
	</td>
	</tr>
</body>
</html>
