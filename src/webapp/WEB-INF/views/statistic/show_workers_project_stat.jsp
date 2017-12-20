<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<script type="text/javascript" src="${ctx}/resources/js/highcharts.js"></script>
<script type="text/javascript"
        src="${ctx}/resources/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/show_workers_project_stat.js"></script>
<script type="text/javascript">
  $(window).load(function () {
    init(${working},${fired});
  });
</script>
<head>
    <title>SprintStatistic</title>
</head>
<body>
<div id="container" style="width: 600px; height: 400px; margin: 0 auto"></div>
</body>
</html>

