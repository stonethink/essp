<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	String fileFullName = (String)request.getParameter("fileFullName");
	CvsFile file = reportDate.getRoot().getFile(fileFullName);
%>

<html>

<head> 

<title>概述</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>
<INPUT TYPE="button" VALUE="后退" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="前进" onClick="history.go(1)">
</p>

<p>
<h3>
<%=fileFullName%>
</h3>
</p>

<table border="1" cellpadding="5" cellspacing="0">
  	
  	<tr>
  		<td valign="top"><b>版本号</b></td>
  		<td align="center" valign="top"><b>执行动作</b></td>
  		<td align="center" valign="top"><b>执行时间</b></td>
  		<td align="center" valign="top"><b>执行者</b></td>
  		<td align="center" valign="top"><b>与低一版本的差异</b></td>
  		<td align="center" valign="top"><b>文件内容</b><br></td>
  	</tr>
  	
  	<%
  	if( file.getRevisions()!= null ){
  		for(int i = 0; i < file.getRevisions().size(); i++ ){
  			CvsRevision rev = (CvsRevision)file.getRevisions().get(i);
  			
  			out.print("<tr>");
  			out.print("<td align=\"center\" valign=\"top\">" +rev.getId()+ "</td>" );
  			out.print("<td align=\"center\" valign=\"top\">" +rev.getActionString()+ "</td>" );
  			out.print("<td align=\"center\" valign=\"top\">" +HtmlHelp.toDateTime(rev.getDate())+ "</td>" );
  			out.print("<td align=\"center\" valign=\"top\">" +rev.getAuthor()+ "</td>" );
  			
  			if( i < file.getRevisions().size()-1 ){
  				CvsRevision nextRev = (CvsRevision)file.getRevisions().get(i+1);
  				out.print("<td align=\"left\" valign=\"top\"><a target='_blank' href='fileDiffOfTwoRevision.jsp?fileFullName=" +fileFullName+ "&leftRevision=" +rev.getId()+ "&rightRevision=" +nextRev.getId()+ "'> lines: +" 
  					+rev.getLineAdd()+ " -" +rev.getLineSubstract()+ " </a></td>" );
  			}else{
  				out.print("<td align=\"left\" valign=\"top\">&nbsp;</td>" );
  			}
  			
  			
  			
  			out.print("<td align=\"center\" valign=\"top\"><a target='_blank' href=\"fileContentOfTheRevision.jsp?fileFullName=" +fileFullName+ "&revision=" +rev.getId()+ "\">查看</a></td>" );
  			out.print("</tr>");  			
  		}
  	}
  	%>
</table>
</body>
</html>
