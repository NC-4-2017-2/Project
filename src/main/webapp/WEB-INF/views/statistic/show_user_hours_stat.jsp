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
        text: 'User Working Hours Statistics'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y} hours</b>'
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
        name: 'Hours Count',
        colorByPoint: true,
        data: [
          <c:forEach items="${workingHoursStatisticList}" var="workingHours">
          {
            name: '${workingHours.workingDayDate}',
            y: ${workingHours.hoursCount}
          },
          </c:forEach>]
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
