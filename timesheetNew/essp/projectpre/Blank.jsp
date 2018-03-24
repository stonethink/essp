<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <SCRIPT language="javascript" type="text/javascript">
        function onSaveData(){
        }
        function disableBtn(){
         try{
             window.parent.saveBtn.disabled=true;
             window.parent.submitBtn.disabled=true;
          }catch(e){}
        }
     </SCRIPT>
  </head>
  
  <body onload="disableBtn();">
    
  </body>
</html>
