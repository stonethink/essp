
<script language="JavaScript">
   var time = '<%=request.getAttribute("LeaveTime")%>';
   if(opener.leaveForm.totalHours != null)
   opener.leaveForm.totalHours.value = time;
   if(opener.leaveForm.actualTotalHours != null)
   opener.leaveForm.actualTotalHours.value = time;
   window.close();
</script>
