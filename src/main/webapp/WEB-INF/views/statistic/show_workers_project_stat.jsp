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
        text: 'Project Workers Statistic'
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
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
        name: 'Percentage',
        colorByPoint: true,
        data: [{
          name: 'Working',
          y: ${working}
        }, {
          name: 'Worked',
          y: ${fired},
          sliced: true,
          selected: true
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

