<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/common/taglibs.jsp"%>
</head>

<body class="no-skin">
<!-- #section:basics/navbar.layout -->
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
                    <li class="hover">
                        <a href="#page/blank">
                            <i class="menu-icon fa fa-caret-right"></i>
                            Blank Page
                        </a>

                        <b class="arrow"></b>
                    </li>
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

    <!-- /section:basics/sidebar -->
    <div class="main-content">
        <!-- #section:basics/content.breadcrumbs -->
        <div class="breadcrumbs breadcrumbs-fixed" id="breadcrumbs">
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
        </div>

        <div class="page-content" style="padding: 40px 0px 0px 0px">
            <!-- /section:settings.box -->
            <div class="page-content-area" style="width: 100%;">
                <!-- ajax content goes here -->
            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->

</div><!-- /.main-container -->

<script type="text/javascript">

    jQuery(function($) {

        function resizeWindow(){
            $(".page-content-area").height($(window).height() - 86);
        }
        window.onresize = resizeWindow;

        if('enable_ajax_content' in ace) {
            resizeWindow();//先调整 page-content-area 区域内的高度，使展示区域适合浏览器高度
            var options = {
                content_url: function(url) {

                    if(!url.match(/^page\//)) return false;

                    var path = document.location.pathname;

                    //if(path.match(/ajax\.html/)) return path.replace(/ajax\.html/, url.replace(/^page\//, '')+'.html') ;

                    //for Ace PHP demo version convert "page/dashboard" to "?page=dashboard" and load it
                    return path + "/" + url; //url.replace(/\//, "=");
                },
                default_url: 'page/map'//default url
            }
            ace.enable_ajax_content($, options)
        }
    });

</script>
</body>
</html>
