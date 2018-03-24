






<html>

<base target="main">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>无标题文档</title>
<style>
.navPoint{font-family: Webdings;font-size:9pt;color:white;cursor:hand;}
p{
	font-size:9pt;
}
</style>
<link rel="stylesheet" type="text/css" href="css/essp.css">
<link rel=stylesheet href="css/css.css">

</head>
<SCRIPT language=javascript>

function switchSysBar(){
	if (switchPoint.innerText==3){
		switchPoint.innerText=4
		document.all("mnuList").style.display="none"
		document.all("mnuList1").style.display="none"
		//document.all("mnuList2").style.display="none"
		top.content.cols="30,*"
	}
	else{
		switchPoint.innerText=3
		document.all("mnuList").style.display=""
		document.all("mnuList1").style.display=""
		//document.all("mnuList2").style.display=""

		top.content.cols="190,*"
	}
}
function switchSysBar2(){
	if (switchPoint.innerText==5){
		switchPoint.innerText=6
		document.all("mnuList").style.display="none"
		left.content.rows="10,*"

	}
	else{
		switchPoint.innerText=5
		document.all("mnuList").style.display=""
		left.content.rows="*,50%"

	}
}
/************************************************
*   Stuff from menu_functions.js *
************************************************/

// Global variables

var skin;
var isNav4, isIE, isDomvar, foropera = window.navigator.userAgent.toLowerCase();
// alert(foropera);

var itsopera = foropera.indexOf("opera",0) + 1;
var itsgecko = foropera.indexOf("gecko",0) + 1;
var itsmozillacompat = foropera.indexOf("mozilla",0) + 1;
var itsmsie = foropera.indexOf("msie",0) + 1;        if (itsopera > 0){
                //thebrowser = 3;
                //alert("its opera");
                isNav4 = true;
        }        if (itsmozillacompat > 0){
                //alert("its mozilla compatible");
                if (itsgecko > 0) {
                        //thebrowser = 4
                       // alert("its gecko");
                       isNav4 = true;
                       isDom = true;

                }
                else if (itsmsie > 0) {
                      //  alert("its msie");
                       // thebrowser = 2
                        isIE = true;
                }
                else {
                        if (parseInt(navigator.appVersion) < 5) {
                                // alert("its ns4.x")
                                //thebrowser = 1
                                isNav4 = true;
                        }
                        else {
                                thebrowser = 2
                                isIE = true;
                        }
                }
        }
function NSResize() {
   top.header.location.reload();
   top.detail.location.reload();
   top.HelpNavigation.location.reload();
   return false;
}

if (isNav4 && !isDom) {
   window.captureEvents(Event.RESIZE);
   window.onresize = NSResize;
}

</SCRIPT>


<body class="navtree" leftMargin=0 topMargin=0 MARGINWIDTH="0" MARGINHEIGHT="0"">
<table width="100%" border="0" cellSpacing=0 cellPadding=0  height="100%" >


<script type="text/javascript" language="JavaScript" src="js/aptree.js"></script>
<script type="text/javascript" language="JavaScript1.2">

setShowExpanders(true);

//全部展开
setExpandDepth(-1);

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
    <td id='mnuList' class="LeftFrame" background="image/left_bg.gif" height="200">
    <div style=" left: 0px; top: 0px;height:100%">
    <table hight=100% border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" id="AutoNumber1" >

    <div class='indent1'  ><b>ISSUE</b> </div>

	<script type="text/javascript" language="JavaScript1.2">
	var companyGif = "";
	var rootGif = "";
	var funcGif = "";

	root  = addRoot(companyGif, '','');
	M510000000 = addItem(  root ,  rootGif , 'Issue Management'     , '' );
	M511000000 = addItem( M510000000,  funcGif , 'Issue Stat.'      ,  '<%=request.getContextPath()%>/issue/stat/issueStat.do' );
	M512000000 = addItem( M510000000,  funcGif , 'Issue List'     ,  '<%=request.getContextPath()%>/issue/issue/issueList.do?MyIssue=All' );
	//M513000000 = addItem( M510000000,  rootGif , 'Issue Create'     ,  '<%=request.getContextPath()%>/issue/issue/issueAddPre.do' );
	M514000000 = addItem( M510000000,  rootGif , 'Issue Report'     ,  '<%=request.getContextPath()%>/issue/report/issueReportPre.do' );
	M530000000 = addItem(  root ,  rootGif , 'Administrator'        ,  '' );
	M531000000 = addItem( M530000000,  funcGif , 'Issue Type Define',  '<%=request.getContextPath()%>/issue/typedefine/issueTypeList.do' );
    //M532000000 = addItem( M530000000,  funcGif , 'Issue Issue',  '<%=request.getContextPath()%>/issue/issue/issueList.do' );
	M540000000 = addItem( root,  rootGif , 'My Issue',  '' );
	M541000000 = addItem( M540000000,  funcGif , 'Submit Issue',  '<%=request.getContextPath()%>/issue/issue/issueList.do?MyIssue=Submit' );
	M542000000 = addItem( M540000000,  funcGif , 'Responsible Issue',  '<%=request.getContextPath()%>/issue/issue/issueList.do?MyIssue=Responsible' );
	M543000000 = addItem( M540000000,  funcGif , 'Resolve Issue',  '<%=request.getContextPath()%>/issue/issue/issueList.do?MyIssue=Resolve' );

    </script>
    <script type="text/javascript" language="JavaScript1.2">
    initialize()
    </script>    </table>
    </div>
    </td>
    <td rowspan="3" class="HalfWindow"  width="15" height="100%"  vAlign=center onclick=switchSysBar() align=middle><SPAN class=navPoint id=switchPoint >3</SPAN> </td></tr>
<tr><td id='mnuList1'  class="HalfWindow"  width="100%" height="5"  vAlign=center align=middle></td>
 </tr>
</table>

</body>
</html>
