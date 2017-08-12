<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!--logo-->
<link rel="shortcut icon" href="${ctx}/assets/img/favicon.ico" type="image/x-icon">
<link rel="icon" href="${ctx}/assets/img/favicon.ico" type="image/x-icon">
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/assets/css/font-awesome.min.css" />
<!-- text fonts -->
<link rel="stylesheet" href="${ctx}/assets/css/ace-fonts.css" />
<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/assets/css/ace.min.css" id="main-ace-style" />
<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/assets/css/ace-part2.min.css" />
<![endif]-->
<link rel="stylesheet" href="${ctx}/assets/css/ace-skins.min.css" />
<link rel="stylesheet" href="${ctx}/assets/css/ace-rtl.min.css" />
<!--[if lte IE 9]>
<link rel="stylesheet" href="${ctx}/assets/css/ace-ie.min.css" />
<![endif]-->
<!-- ace settings handler -->
<script src="${ctx}/assets/js/ace-extra.min.js"></script>
<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->
<!--[if lte IE 8]>
<script src="${ctx}/assets/js/html5shiv.min.js"></script>
<script src="${ctx}/assets/js/respond.min.js"></script>
<![endif]-->

<!--[if !IE]> -->
<script src="${ctx}/assets/js/jquery.min.js"></script>
<!-- <![endif]-->
<!--[if IE]>
<script src="${ctx}/assets/js/jquery1x.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
    var ctx = "${ctx}";
</script>
<script src="${ctx}/assets/js/bootstrap.min.js"></script>
<!-- ace scripts -->
<script src="${ctx}/assets/js/ace-elements.min.js"></script>
<script src="${ctx}/assets/js/ace.min.js"></script>