<% String serverURL = "http://" + request.getServerName() +":"
						+ request.getServerPort() + request.getContextPath();
   String controllerURL = serverURL + "/Controller";
%>
<% String contextPath = request.getContextPath();
   String appletDir = contextPath + "/applet";
	 String comJar = appletDir + "/common_client.jar,"
                 + appletDir + "/esspfw_client.jar,"
                 + appletDir + "/esspcom_client.jar";
%>

<% String todayDatePattern = "yyyyMMdd";
   String todayDate = "";
   {
	  java.text.DateFormat df = new java.text.SimpleDateFormat(todayDatePattern);
	  todayDate = df.format(new java.util.Date());
   }

%>

<% c2s.essp.common.user.DtoUser user = (c2s.essp.common.user.DtoUser)
       session.getAttribute(c2s.essp.common.user.DtoUser.SESSION_USER);
   String userId= "";
   String userName= "";
   String userType= "";
   if(user != null){
      userId = user.getUserLoginId();
      userName = user.getUserName();
      userType = user.getUserType();
   }
%>
