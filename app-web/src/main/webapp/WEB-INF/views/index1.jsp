<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<title></title>
    <%@include file="/WEB-INF/common/taglibs.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=rDGOeEY67dMvSsKSrR5oAkwj"></script>

</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default navbar-fixed-top h-navbar">
    <div class="navbar-container container" id="navbar-container">
        <div class="navbar-header pull-left ace-navbar-brand">
				<span class="no-skin">
				 <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
                     <span class="sr-only">Toggle sidebar</span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                 </button>
				</span>
            <a href="#" class="navbar-brand">
                <small>
                    &nbsp;<i class="glyphicon glyphicon-book"></i>&nbsp;
                    Help <span class="smaller-75">(v1.3.1)</span>
                </small>
            </a><!-- /.brand -->
        </div>

        <div class="navbar-header pull-right ace-navbar-buttons"></div>

    </div>
</div>
<!-- end navbar -->

<div class="main-container" id="main-container">

    <div class="sidebar responsive sidebar-fixed" id="sidebar">
        <div class="sidebar-shortcuts" id="sidebar-shortcuts">

        </div>

        <ul class="nav nav-list">

            <li class="hover">
                <a href="#changes" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text">协同工作</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <li class="hover"><a href="#changes.v6"><i class="menu-icon fa fa-caret-right"></i>主题空间</a></li>
                    <li class="hover"><a href="#changes.v5"><i class="menu-icon fa fa-caret-right"></i>新建事项</a></li>
                    <li class="hover"><a href="#changes.v4"><i class="menu-icon fa fa-caret-right"></i>待发事项</a></li>
                    <li class="hover"><a href="#changes.v3"><i class="menu-icon fa fa-caret-right"></i>已发事项</a></li>
                    <li class="hover"><a href="#changes.v2"><i class="menu-icon fa fa-caret-right"></i>待办事项</a></li>
                    <li class="hover"><a href="#changes"><i class="menu-icon fa fa-caret-right"></i>已办事项</a></li>
                </ul>
            </li>

            <li class="hover">
                <a href="#changes" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text">协同工作</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <li class="hover"><a href="#changes.v6"><i class="menu-icon fa fa-caret-right"></i>主题空间</a></li>
                    <li class="hover"><a href="#changes.v5"><i class="menu-icon fa fa-caret-right"></i>新建事项</a></li>
                    <li class="hover"><a href="#changes.v4"><i class="menu-icon fa fa-caret-right"></i>待发事项</a></li>
                    <li class="hover"><a href="#changes.v3"><i class="menu-icon fa fa-caret-right"></i>已发事项</a></li>
                    <li class="hover"><a href="#changes.v2"><i class="menu-icon fa fa-caret-right"></i>待办事项</a></li>
                    <li class="hover"><a href="#changes"><i class="menu-icon fa fa-caret-right"></i>已办事项</a></li>
                </ul>
            </li>

            <li class="hover">
                <a href="#changes" class="dropdown-toggle">
                    <i class="menu-icon fa fa-desktop"></i>
                    <span class="menu-text">协同工作</span>
                    <b class="arrow fa fa-angle-right"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    <li class="hover"><a href="#changes.v6"><i class="menu-icon fa fa-caret-right"></i>主题空间</a></li>
                    <li class="hover"><a href="#changes.v5"><i class="menu-icon fa fa-caret-right"></i>新建事项</a></li>
                    <li class="hover"><a href="#changes.v4"><i class="menu-icon fa fa-caret-right"></i>待发事项</a></li>
                    <li class="hover"><a href="#changes.v3"><i class="menu-icon fa fa-caret-right"></i>已发事项</a></li>
                    <li class="hover"><a href="#changes.v2"><i class="menu-icon fa fa-caret-right"></i>待办事项</a></li>
                    <li class="hover"><a href="#changes"><i class="menu-icon fa fa-caret-right"></i>已办事项</a></li>
                </ul>
            </li>

        </ul>
    </div>

    <div class="main-content">
 <%--       <div class="breadcrumbs breadcrumbs-fixed" id="breadcrumbs">
            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
            </ul><!-- /.breadcrumb -->

            <!-- #section:basics/content.searchbox -->
            <div class="nav-search" id="nav-search">
                <form class="form-search">
						<span class="input-icon">
							<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
							<i class="ace-icon fa fa-search nav-search-icon"></i>
						</span>
                </form>
            </div><!-- /.nav-search -->
            <!-- /section:basics/content.searchbox -->
        </div>--%>
        <!-- start page content -->
        <div class="page-content" style="padding: 0px;">
            <div id="allmap" style="width:100%;"></div>
        </div><!-- /.page-content -->
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>

</div><!--end main container-->
</body>
</html>
<script type="text/javascript">
    function resizeWindow(){
        $("#allmap").height(($(window).height() - 62) + "px")
    }
    window.onresize = resizeWindow;
    //
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
    map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    var bottom_left_control = new BMap.ScaleControl();// 添加比例尺
    var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
    map.addControl(bottom_left_control);
    map.addControl(top_left_navigation);
</script>