<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<title>岩心位置展示</title>

<div id="allmap" style="width: 100%;height: 100%;">

</div>

<!-- page specific plugin scripts -->
<script type="text/javascript">
    var baiduKey = "rDGOeEY67dMvSsKSrR5oAkwj";
    window.BMap_loadScriptTime = (new Date).getTime();
    var mapapi = "http://api.map.baidu.com/getscript?v=2.0&ak="+baiduKey+"&services=&t="+window.BMap_loadScriptTime;
    var scripts = [mapapi];
    ace.load_ajax_scripts(scripts, function() {
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
    });
</script>
