<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
    <script type="text/javascript">
      $(function() {
        var options1 = {
          chart: {
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
            data: [<c:forEach items="${workingHoursStatisticList}" var="workingHours">
              {
                name: '${workingHours.workingDayDate}',
                y: ${workingHours.hoursCount}
              },
              </c:forEach>]
          }]
        };

        var options2 = {
          chart: {
            type: 'pie'
          },
          title: {
            text: 'User Task Priority Statistic'
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
            name: 'Percentage',
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
        $('#container1').highcharts(options1);
        $('#container2').highcharts(options2);
      });
    </script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<%@include file="../errors/errorMap.jsp" %>

<div class="col-sm-9">
    <div class="well col-sm-4">
        <h4>Table working hours</h4>
        <%@include file="../workingDay/createWorkingDayForm.jsp" %>
    </div>
    <div class="row">
        <div class="col-sm-3">
            <div class="well">
                <h4>Create vacation on me</h4>
                <%@include file="../vacation/createVacationForm.jsp" %>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Create new user in system</h4>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="well">
                <h4>Create project</h4>
                <%@include file="../project/createProjectDashboardForm.jsp" %>
            </div>
        </div>
        <c:if test="${not empty workingHoursStatisticList}">
            <div class="col-sm-6">
                <div class="well">
                    <h4>User hours</h4>
                    <div id="container1" style="width: 600px; height: 400px; margin: 0 auto"></div>
                </div>
            </div>
        </c:if>
        <c:if test="${critical ne 0 || high ne 0 || normal ne 0 || low ne 0}">
            <div class="col-sm-6">
                <div class="well">
                    <h4>Task statistic</h4>
                    <div id="container2" style="width: 600px; height: 400px; margin: 0 auto"></div>
                </div>
            </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
                <p>Text</p>
                <p>Text</p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-8">
            <div class="well">
                <p>Text</p>
            </div>
        </div>
        <div class="col-sm-4">
            <div class="well">
                <p>Text</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
