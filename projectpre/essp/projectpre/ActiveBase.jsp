<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="c2s.essp.common.user.DtoUser,c2s.essp.common.user.DtoRole,java.util.*" %>
<%
    DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
   
    List roleList = (List)user.getRoles();
     boolean isProjectOffice = false;
        for(int i=0;i<roleList.size();i++){
            DtoRole role = (DtoRole)roleList.get(i);
            if(role!=null){
                String roleId = role.getRoleId();
                if(roleId.equals("R0000SU00")||roleId.equals("RH00APO00")){
                    isProjectOffice = true;
                }
            }
        }
%>


    <SCRIPT language="javascript" type="text/javascript">
       
<%if(isProjectOffice){%>
            window.location="<%=request.getContextPath()%>/projectpre/ProjectOfficeWorkPage.jsp";
             //window.location="<%=request.getContextPath()%>/myactive.htm";
<%}else{%>
            window.location="<%=request.getContextPath()%>/myactive.htm";
<%}%>
     </SCRIPT>
  
