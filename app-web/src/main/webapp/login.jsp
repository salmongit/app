<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>shirodemo login page</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src='${pageContext.request.contextPath}/assets/js/jquery.min.js'></script>
	<script type="text/javascript">
	<!--
	function reloadValidateCode(){
		$("#validateCodeImg").attr("src","${pageContext.request.contextPath}/validateCode?data=" + new Date() + Math.floor(Math.random()*24));
	}
	//-->
	</script>
  </head>
  
  <body>
    <form action="${pageContext.request.contextPath}/login" method="post">
    <ul>
    	<li>姓　名：<input type="text" name="account" /> </li>
    	<li>密　码：<input type="text" name="password" /> </li>
    	<li>验证码：<input type="text" name="validateCode" />&nbsp;&nbsp;<img id="validateCodeImg" src="${pageContext.request.contextPath}/validateCode" />&nbsp;&nbsp;<a href="javascript:void(0);" onclick="javascript:reloadValidateCode();">看不清？</a></li>
    	<li><input type="submit" value="登录" /> </li>
    	
    </ul>
    </form>
  </body>
</html>
