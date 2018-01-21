<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
  jQuery(document).ready(function () {
    var options = {
      chart: {
        renderTo: 'container',
        type: 'line'
      },
      title: {
        text: 'Sprint Statistic'
      },
      subtitle: {
        text: ''
      },
      xAxis: {
        categories: [<c:forEach items="${sprintStatList}" var="sprintElement">
          '${sprintElement.sprintName}',
          </c:forEach>]
      },
      legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle'
      },
      yAxis: {
        title: {
          text: 'Number of Days'
        }
      },
      plotOptions: {
        series: {
          label: {
            connectorAllowed: false
          }
        }
      },
      series: [

        {
          name: 'Planned',
          data: ${plannedDateList}
        },

        {
          name: 'Actual',
          data: ${currentEndList}
        }
      ],

      responsive: {
        rules: [{
          condition: {
            maxWidth: 500
          },
          chartOptions: {
            legend: {
              layout: 'horizontal',
              align: 'center',
              verticalAlign: 'bottom'
            }
          }
        }]
      }
    };
    var chart = new Highcharts.Chart(options);
  });
</script>
<head>
    <title>Sprint statistic</title>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<c:if test="${errorMap eq null}">
    <div id="container"
         style="width: 600px; height: 400px; margin: 0 auto"></div>
</c:if>
<div class="form-group">
    <input onclick="window.history.go(-1); return false;"
           type="button" class="btn btn-primary btn-md"
           value="Back"/>
</div>
<%@include file="../errors/errorMap.jsp" %>
</body>
</html>
