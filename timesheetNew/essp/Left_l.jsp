<%@ page contentType="text/html; charset=UTF-8" %>
<html>

<head>
		<title>Menu</title>
		<style>
		.navPoint{font-family: Webdings;font-size:9pt;color:white;cursor:hand;}
		p{
			font-size:9pt;
		}
		</style>
		<link rel="stylesheet" type="text/css" href="css/essp.css">
		<link rel=stylesheet href="css/css.css">

		<script language="JavaScript" type="text/JavaScript">
		<%----隐藏和展开菜单事件---%>
		function switchSysBar(){
			if (switchPoint.innerText==3){
				switchPoint.innerText=4;
				document.all("mnuList").style.display="none";
				top.content.cols="10,*";
			}
			else{
				switchPoint.innerText=3;
				document.all("mnuList").style.display="";
				top.content.cols="200,*";
			}
		}
		</SCRIPT>
</head>

	<body class="navtree" leftMargin=0 topMargin=0 MARGINWIDTH="0" MARGINHEIGHT="0"">
		<table width="200" border="0" cellSpacing=0 cellPadding=0 height="100%">
  			<script type="text/javascript" language="JavaScript" src="js/aptree.js"></script>
  			<script type="text/javascript" language="JavaScript1.2">
   		 	setShowExpanders(true);

    		//全部展开
    		setExpandDepth(-1);
    		//采用卡片形式时，点击的样式
   			// setclickType(1);
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
			<tr>
				<td id='mnuList' class="LeftFrame" background="image/left_bg.gif" height="100%" width="170">
					<div style=" left: 0px; top: 0px;height:100%">
						<table  hight=100% border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" id="AutoNumber1">


<script type="text/javascript" language="JavaScript1.2">

	var companyGif = "";
	var rootGif = "";
	var funcGif = "";

	root  = addRoot(companyGif, '','');
	//Project Code維護系統
	M510000000 = addItem(  root ,  rootGif , '专案代码維護系統'     , '' );
	M511000000 = addItem( M510000000,  funcGif , '专案代码資料維護作業'      ,  '<%=request.getContextPath()%>/projectpre/projectcode/codemaintenance/ProjectCodeMaintenance.jsp' );
	M512000000 = addItem( M510000000,  funcGif , '专案代码申請作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/codeapply/ProjectCodeApply.jsp' );
	M513000000 = addItem( M510000000,  funcGif , '专案變更作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/codechange/ProjectCodeChange.jsp' );
	//M513000010 = addItem( M510000000,  funcGif , '簽約變更作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/contractchange/ContractChange.jsp' );
	M514000000 = addItem( M510000000,  funcGif , '专案結案作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/codeconfirm/ProjectCodeConfirm.jsp' );
	M515000000 = addItem( M510000000,  funcGif , '专案覆核作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/codecheck/ProjectCodeCheck.jsp' );
	M516000000 = addItem( M510000000,  funcGif , '专案查詢作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/codequery/ProjectCodeQuery.jsp' );
	M517000000 = addItem( M510000000,  funcGif , '產品技術資料維護作業'     ,  '<%=request.getContextPath()%>/projectpre/projectcode/productmaintenance/productMaintenance.jsp' );
	
	//Dept Code維護系統
	M540000000 = addItem( root,  rootGif , '部门代码維護系統'     ,  '' );
	M541000000 = addItem( M540000000,  funcGif , '部门代码申請作業'     ,  '<%=request.getContextPath()%>/projectpre/deptcode/codeApply/CodeApply.jsp' );
	M542000000 = addItem( M540000000,  funcGif , '部门變更作業'     ,  '<%=request.getContextPath()%>/projectpre/deptcode/codeEdit/DeptCodeModify.jsp' );
	M543000000 = addItem( M540000000,  funcGif , '部门核准作業'     ,  '<%=request.getContextPath()%>/projectpre/deptcode/codeCheck/DeptCodeCheck.jsp' );
	M544000000 = addItem( M540000000,  funcGif , '部门代碼查詢作業'     ,  '<%=request.getContextPath()%>/projectpre/deptcode/codeQuery/DeptCodeQuery.jsp' );
	
    //客戶主檔維護系統
	M560000000 = addItem( root,  rootGif , '客戶主檔維護系統',  '' );
	M561000000 = addItem( M560000000,  funcGif , '客戶編碼資料維護作業',  '<%=request.getContextPath()%>/projectpre/customer/infomaintenance/CustomerInfoMaintenance.jsp' );
	M562000000 = addItem( M560000000,  funcGif , '客戶基本資料新建作業',  '<%=request.getContextPath()%>/projectpre/customer/infoadd/CustomerInfoAdd.jsp' );
	M563000000 = addItem( M560000000,  funcGif , '客戶基本資料變更作業',  '<%=request.getContextPath()%>/projectpre/customer/infochange/CustomerInfoChange.jsp' );
	M564000000 = addItem( M560000000,  funcGif , '客戶基本資料覆核作業',  '<%=request.getContextPath()%>/projectpre/customer/infocheck/CustomerInfoCheck.jsp' );
	M565000000 = addItem( M560000000,  funcGif , '分類資料維護作業',  '<%=request.getContextPath()%>/projectpre/customer/typeinfo/CustomerTypeInfo.jsp' );
	M566000000 = addItem( M560000000,  funcGif , '客戶資料查詢作業',  '<%=request.getContextPath()%>/projectpre/customer/infoquery/CustomerInfoQuery.jsp' );

    //权限管理
	//M580000000 = addItem( root,  rootGif , '权限管理',  '' );
	//M581000000 = addItem( M580000000,  funcGif , '权限分配',  '<%=request.getContextPath()%>/xx.do' );
	//M582000000 = addItem( M580000000,  funcGif , '角色管理',  '<%=request.getContextPath()%>/xx.do' );
    </script>
    <script type="text/javascript" language="JavaScript1.2">
  
     initialize();
    
    </script>
	</table>
	</div>
	</td>
    <td rowspan="3" class="HalfWindow" width="10" height="100%" vAlign=center onclick=switchSysBar() align=middle>
	  <SPAN class=navPoint id=switchPoint>3</SPAN>
	</td>
 </tr>

		</table>

	</body>
</html>

