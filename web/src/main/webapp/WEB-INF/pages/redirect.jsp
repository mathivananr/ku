<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$( document ).ready(function() {
	 alert(${url});
	 var win = window.open('${url}', '_blank');
	 if (win) {
	     //Browser has allowed it to be opened
	     win.focus();
	 }
 });
</script>
 