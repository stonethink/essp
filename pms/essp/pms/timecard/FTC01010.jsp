<%@include  file="/inc/langpage.jsp"%>
<%@ page import="com.enovation.essp.org.databean.Employee,java.util.Calendar,client.framework.common.Global"%>
<%@include  file="/inc/appletdef.jsp"%>

<html>
<head>
<%@include  file="/inc/langhtm.jsp"%>

<%!    //将null转为空串
       String transfer( String prm_s ) {
		String s = "";
		if ( prm_s == null ) {
			s = "";
		} else {
			s = prm_s.trim();
		}
		return s;
	}
 String transfer0( String prm_s ){
    if( transfer(prm_s).equals("")){
      return "0";
    }else{
      return prm_s.trim();
    }
  }
%>

<%
		Employee ep = (Employee)session.getAttribute("Employee");
		
		String appServerUrl2 = "http://" + request.getServerName() +":"
						+ request.getServerPort() + request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>
PM Assurance
</title>
<script language="JavaScript" src="<%=contextPath%>/js/hr_allocate.js"></script>
</head>
<body>
<applet
  archive = "<%=appletDir%>/timecard_client.jar,<%=comJar%>"
  code     = "client.essp.timecard.timecard.weeklyreport.FTC01010Applet.class"
  name     = "FTC01010"
  width    = "100%"
  height   = "100%"
  hspace   = "0"
  vspace   = "0"
  align    = "top">
  <PARAM NAME="userId"    VALUE="<%=userId%>">
  <PARAM NAME="userName"  VALUE="<%=userName%>">
  <PARAM NAME="userType"  VALUE="<%=userType%>">
  <PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
  <PARAM NAME="todayDate" VALUE="<%=todayDate%>">
  <PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
  <PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
</applet>
<script language="JavaScript">
	setAppRoot("<%=appServerUrl2%>");
	setAppletInstance(FTC01010);
</script>
<%

	String empID = ep.getId();
//    String empID = "000010";//"000001";
    if(transfer(empID).equals("")){
        empID = "------";
    }
	com.enovation.common.util.DbAccessor dbAccessor1 = com.enovation.common.util.DbAccessor.getDefaultInstance();
    java.sql.Connection conn1 = dbAccessor1.getConnection();
    java.sql.Statement stateMent1 = conn1.createStatement();
    com.enovation.common.util.DbAccessor dbAcc4 = com.enovation.common.util.DbAccessor.getDefaultInstance();
    java.sql.Connection conn4 =dbAcc4.getConnection();
    java.sql.Statement stateMent4 = conn4.createStatement();
    java.sql.ResultSet querySet4=null;

	  //处理EBS的子节点的account的显示sql语句
	  String sqlstring = "";
	  String organization = "";
	  int unit_layer = 0;
	  int new_layer = 0;

      sqlstring  = " 1!=1 " ;

      java.sql.ResultSet querySet2= stateMent1.executeQuery(""+"select unit.*,result.layer from essp_upms_unit unit,essp_upms_alloc_result result where unit.manager ='"+ empID +"' and unit.unit_id=result.unit_id");
      System.out.println("select unit.*,result.layer from essp_upms_unit unit,essp_upms_alloc_result result where unit.manager ='"+ empID +"' and unit.unit_id=result.unit_id");

	  while(querySet2.next()) {

	  	unit_layer = querySet2.getInt("layer");
	  	organization = querySet2.getString("unit_id");
			querySet4=stateMent4.executeQuery(
				""+"select account.*,result.unit_id,result.layer from essp_sys_account_t account, essp_upms_alloc_result result where account.organization=result.unit_id and account.status != 'Closed' and result.unit_id ='"+ organization +"'");
			while(querySet4.next()){
			          sqlstring = sqlstring + " or (ebs.id = " + querySet4.getString("id") +")";
			          organization = querySet4.getString("unit_id");
			          unit_layer = querySet4.getInt("layer");
			}
			querySet4.close();

	  	if(unit_layer!=new_layer){
	  		new_layer = unit_layer;
			querySet4=stateMent4.executeQuery(
				""+"select account.*,result.unit_id,result.layer from essp_sys_account_t account, essp_upms_alloc_result result where account.organization=result.unit_id and account.status != 'Closed' and result.parent_id ='"+ organization +"'");
			while(querySet4.next()){
			          sqlstring = sqlstring + " or ( ebs.id = " + querySet4.getString("id") +" )";
			          organization = querySet4.getString("unit_id");
			          unit_layer = querySet4.getInt("layer");
			}
			querySet4.close();
	  	}
	  }

      querySet2.close();

      String appServerUrl = "http://" + "127.0.0.1" +":"+ request.getServerPort() + request.getContextPath();
          Global.appRoot=appServerUrl;
	  String[] accIds = com.enovation.essp.ebs.AccountGroup.getUserAccIds(empID);

       String sql_ebs = " select distinct ebs.id ,ebs.MANAGER,ebs.PLAN_START,ebs.PLAN_FINISH,ebs.account_code, ebs.account_name "
			+ "from essp_sys_account_t ebs  , essp_sys_account_t a  "
			+ "where a.account_code = ebs.account_code  and ebs.id in (  ";

	  int nlist=0;
	  int j = 0;
    if(accIds!=null){
    	nlist = accIds.length;
    }
    for( j=0;j<nlist;j++){
    	sql_ebs=sql_ebs + accIds[j] ;
        if ( j < nlist - 1) {
        	sql_ebs = sql_ebs + " , " ;
        } else {
        	sql_ebs = sql_ebs + " ) " ;
        }
    }

	String acc_sql = "select id, account_code , account_name , manager , plan_start , plan_finish  "
		+ " from ( "
		+ "select distinct ebs.id,MANAGER,PLAN_START,PLAN_FINISH,account_code,account_name "
		+ "from essp_sys_account_t ebs,essp_sys_account_personal_t hr "
		+ "where "
		+ "( ebs.status != 'Closed' "
		+ " and ebs.id = hr.account_id and hr.hr_code='" +  empID + "')  "
		+ "union "
		+ " select distinct ebs.id,MANAGER,PLAN_START,PLAN_FINISH,account_code,account_name "
		+ " from essp_sys_account_t ebs "
		+ " where " + sqlstring;

    if ( j != 0 )  {
		acc_sql = acc_sql + " union " + sql_ebs ;
	}

	acc_sql = acc_sql + " ) order by plan_start desc";

	System.out.println("Acc SQL is " + acc_sql);

	java.sql.ResultSet querySet1= stateMent1.executeQuery(acc_sql);

    String projectList = "";
    String prjIDList = "";
    String prjAccountList = "";
    String prjNameList = "";
    String isPMList = "";
    int prjCount = 0;
    while(querySet1.next()) {
        prjCount++;
//    	String projectInfo = querySet1.getString(1)+"!@#$%^&*(llyy)"+querySet1.getString(2)+"!@#$%^&*(llyy)"+querySet1.getString(3)+"!@#$%^&*(llyy)"+querySet1.getString(4);
        String isPM = "0";
        if(transfer(querySet1.getString(4)).equals(empID)){
            isPM = "1";
        }
%>
<script language="javascript">
	FTC01010.setPrjID("<%=querySet1.getString(1)%>");
	FTC01010.setPrjAccount("<%=querySet1.getString(2)%>");
	FTC01010.setPrjName("<%=querySet1.getString(3)%>");
	FTC01010.setIsPrjPM("<%=isPM%>");
	FTC01010.addParameter();
</script>
<%
//        if(prjCount==1){
//         	 projectList = projectInfo;
//             prjIDList = querySet1.getString(1);
//             prjAccountList = querySet1.getString(2);
//             prjNameList = querySet1.getString(3);
//             isPMList = isPM;
//        }else{
//        	projectList += "(llyy)*&^%$#@!" + projectInfo;
//        	prjIDList += "(llyy)*&^%$#@!" + querySet1.getString(1);
//        	prjAccountList += "(llyy)*&^%$#@!" + querySet1.getString(2);
//        	prjNameList += "(llyy)*&^%$#@!" + querySet1.getString(3);
//        	isPMList += "(llyy)*&^%$#@!" + isPM;
//        }
    }
//    System.out.print(projectList+"\n"+prjIDList+"\n"+prjAccountList+"\n"+prjNameList+"\n"+prjNameList+"\n");
%>

<script language="javascript">

	try{
		FTC01010.jbInit();
	}catch (ex) {
//		alert(ex);
	}
</script>
</body>
</html>
