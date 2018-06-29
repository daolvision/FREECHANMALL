<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>세션종료</title>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	alert("${param.errMsg}");
	gfnGetTopWin().document.location.href = "/";
</script>
</head>
<body style="overflow-x:hidden;background-color:white">
</body>
</html>