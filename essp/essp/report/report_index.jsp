<%@ page import="c2s.dto.TransactionData"%>
<%@ page import="c2s.essp.common.treeTable.TreeTableConfig"%>
<%@ page import="server.essp.report.logic.LgListReport"%>
<%@ page import="itf.treeTable.*"%>
<%@include  file="../inc/langpage.jsp"%>
<html>
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


<script language="JavaScript" type="text/JavaScript">

</script>
<head >
<%@include  file="../inc/langhtm.jsp"%>
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>Report List</title>

<link rel="stylesheet" type="text/css" href="css/essp.css">
<style type="text/css">
		A:link {
	COLOR: #000000; TEXT-DECORATION: none;  }
A:visited {
	COLOR: #000000; TEXT-DECORATION: none; 
}
A:hover {
	COLOR: #000000; TEXT-DECORATION: none;
}
</style>
</head>

<body  leftMargin=0 topMargin=0 MARGINWIDTH="0" MARGINHEIGHT="0" rightmargin="0" >

<table width="100%" border="0" cellSpacing=0 cellPadding=0  height="100%">


<script type="text/javascript" language="JavaScript" src="js/treetable.js"></script>
<script type="text/javascript" language="JavaScript1.2">

setShowExpanders(true);

//全部展开
setExpandDepth(-1);
//采用卡片形式时，点击的样式
setclickType(1);

setKeepState(true);
setShowHealth(false);
setInTable(false);
setTargetFrame("main");
openFolder = "image/open_folder.gif";
closedFolder = "image/closed_folder.gif";
plusIcon = "image/lplus.gif";
minusIcon = "image/lminus.gif";
blankIcon = "image/blank20.gif";

</script>

    <td >
    <div  id="Layer2" style=" left: 0px; top: 0px;height:100%">
    <table hight=100% width="100%" border="0" cellspacing="1" cellpadding="1" class="tableout" id="AutoNumber1" >
			<tr class="rang_desc">
				<td  class="oracolumntextheader" width="35%" >Subject</td>
				<td  class="oracolumntextheader" width="25%" >Code</td>
				<td  class="oracolumntextheader" width="30%" >Description</td>
			</tr>
<%

 				String kindcode = request.getParameter("kindcode");

	      TransactionData tranData = new TransactionData();
        tranData.getInputInfo().setInputObj("ReportID", kindcode);
        ITreeTable iTreeTable = TreeTableFactory.create();

        TreeTableConfig[] treeTableConfig = new TreeTableConfig[] {
                                            new TreeTableConfig(
                                                 new Object[] {"url", Boolean.FALSE, TreeTableConfig.STYLE_TEXT, Boolean.TRUE}),
                                            new TreeTableConfig( 
                                                new Object[]{"code", Boolean.FALSE, TreeTableConfig.STYLE_TEXT, Boolean.FALSE }),
                                            new TreeTableConfig(
                                                new Object[]{"description", Boolean.FALSE, TreeTableConfig.STYLE_TEXT, Boolean.FALSE })
        };
        
        String treeTableStr = iTreeTable.createTreeTable(treeTableConfig,LgListReport.class,tranData);
 

%>

<script type="text/javascript" language="JavaScript1.2">
	var companyGif = "";
	var rootGif = "";
	var funcGif = "";
 
  <%=treeTableStr%>


</script>
<script type="text/javascript" language="JavaScript1.2">
	initialize()
</script>    
</table>

</table>
</body>
</html>