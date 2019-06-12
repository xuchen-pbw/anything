<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
$(function(){
	$("#browser").treeview({
		url: "v_tree.do"
	});
});
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 类型管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">
</div>
<ul id="browser" class="filetree"></ul>
</body>
</html>