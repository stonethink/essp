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
   String timeZoneID = "";
   String todayDate = "";
   {
	  java.text.DateFormat df = new java.text.SimpleDateFormat(todayDatePattern);
	  todayDate = df.format(new java.util.Date());
	  timeZoneID = java.util.TimeZone.getDefault().getID();
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

<%
  java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  String _language = "";
  locale.toString();
  if(locale != null) {
  	_language = locale.getLanguage();
  }
%>
