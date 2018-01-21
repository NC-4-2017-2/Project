<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
    <script type="text/javascript">
      $(function () {
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
                  color: (Highcharts.theme
                      && Highcharts.theme.contrastTextColor)
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
                  color: (Highcharts.theme
                      && Highcharts.theme.contrastTextColor)
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
<div class="col-lg-11">
    <table>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errorMap}">
                    <div class="alert alert-danger col-lg-8 form-group">
                        <c:forEach items="${errorMap}" var="error">
                            ${error.value}<br>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty success}">
                    <div class="col-lg-6">
                        <div class="alert alert-success">
                            <strong>Successful operation!</strong>
                        </div>
                    </div>
                </c:if>
            </td>
        </tr>
        <tr>
            <td rowspan="3">
                <div class="well col-sm-4"
                     style="width: 270px; height: 500px; margin: 10px">
                    <h4>Create working hours</h4>
                    <%@include file="../workingDay/createWorkingDayForm.jsp" %>
                    <h4>Find working hours by status</h4>
                    <%@include file="../workingDay/findPMWorkingDayForm.jsp" %>
                </div>
            </td>
            <td>
                <div class="col-sm-3">
                    <div class="well"
                         style="width: 270px; height: 130px; margin: 10px">
                        <h4>Create business trip</h4>
                        <form action="/businessTrip/createBusinessTrip">
                            <button type="submit"
                                    class="btn btn-primary btn-md">
                                Create
                            </button>
                        </form>
                    </div>
                </div>
            </td>
            <td rowspan="3">
                <div class="col-sm-3">
                    <div class="well"
                         style="width: 270px; height: 350px; margin: 10px">
                        <h4>Create vacation on me</h4>
                        <%@include file="../vacation/createVacationForm.jsp" %>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="col-sm-3">
                    <div class="well"
                         style="width: 270px; height: 130px; margin: 10px">
                        <h4>Create project</h4>
                        <form action="/project/createProject">
                            <button type="submit"
                                    class="btn btn-primary btn-md">
                                Create
                                new project
                            </button>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="col-sm-3">
                    <div class="well"
                         style="width: 270px; height: 130px; margin: 10px">
                        <h4>Create new user in system</h4>
                        <form action="/user/createUser">
                            <button type="submit"
                                    class="btn btn-primary btn-md">
                                Create
                                new user
                            </button>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty workingHoursStatisticList}">
                    <div class="well"
                         style="width: 620px; height: 500px; margin: 15px">
                        <h4 style="text-align: center;">User hours</h4>
                        <div id="container1"
                             style="width: 570px; height: 400px; margin: 0 auto"></div>
                    </div>
                </c:if>
            </td>
            <td colspan="2">
                <c:if test="${critical ne 0 || high ne 0 || normal ne 0 || low ne 0}">
                    <div class="well"
                         style="width: 620px; height: 500px; margin: 15px">
                        <h4 style="text-align: center;">Task statistic</h4>
                        <div id="container2"
                             style="width: 570px; height: 400px; margin: 0 auto"></div>
                    </div>
                </c:if>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
