<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<script type="text/javascript" src="${ctx}/resources/js/highcharts.js"></script>
<script type="text/javascript"
        src="${ctx}/resources/js/jquery-2.1.4.js"></script>
<script type="text/javascript">
  jQuery(document).ready(function () {
    var options = {
      chart: {
        renderTo: 'container',
        type: 'pie'
      },
      title: {
        text: 'Project Task Priority Statistic'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y} tasks</b>'
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
            style: {
              color: (Highcharts.theme && Highcharts.theme.contrastTextColor)
              || 'black'
            }
          }
        }
      },
      series: [{
        name: 'Tasks',
        colorByPoint: true,
        data: [{
          name: 'Critical',
          y: <c:if test="${critical eq 0}">
              null
          </c:if>
          <c:if test="${critical ne 0}">
          ${critical}
          </c:if>
        }, {
          name: 'High',
          y: <c:if test="${high eq 0}">
              null
          </c:if>
          <c:if test="${high ne 0}">
          ${high}
          </c:if>,
          sliced: true,
          selected: true
        }, {
          name: 'Normal',
          y: <c:if test="${normal eq 0}">
              null
          </c:if>
          <c:if test="${normal ne 0}">
          ${normal}
          </c:if>
        }, {
          name: 'Low',
          y: <c:if test="${low eq 0}">
              null
          </c:if>
          <c:if test="${low ne 0}">
          ${low}
          </c:if>
        }]
      }]
    };
    var chart = new Highcharts.Chart(options);
  });
</script>
<head>
    <title>SprintStatistic</title>
</head>
<body>
<div id="container" style="width: 600px; height: 400px; margin: 0 auto"></div>
</body>
</html>

