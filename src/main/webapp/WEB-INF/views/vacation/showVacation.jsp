<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Vacation</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<div class="col-lg-4">
    <table class="table table-hover table-dark" border="3">
        <thead>
        <tr>
            <th scope="col" colspan="2" style="text-align: center;">Vacation
                <c:if test="${vacation.pmId ne vacation.lmId}">
                    <c:if test="${currentUser.userId eq vacation.pmId}">
                        <form action="/vacation/updateStatus/${vacation.vacationId}"
                              method="post">
                            <button type="submit" class="btn btn-primary btn-md"
                                    name="status" value="APPROVED">Approve
                            </button>
                            <button type="submit" class="btn btn-primary btn-md"
                                    name="status" value="DISAPPROVED">Disapprove
                            </button>
                        </form>
                        <br>
                    </c:if>
                    <c:if test="${currentUser.userId eq vacation.lmId}">
                        <form action="/vacation/updateStatus/${vacation.vacationId}"
                              method="post">
                            <button type="submit" class="btn btn-primary btn-md"
                                    name="status" value="APPROVED">Approve
                            </button>
                            <button type="submit" class="btn btn-primary btn-md"
                                    name="status" value="DISAPPROVED">Disapprove
                            </button>
                        </form>
                        <br>
                    </c:if>
                </c:if>
                <c:if test="${vacation.pmId eq vacation.lmId}">
                    <form action="/vacation/updateStatus/${vacation.vacationId}"
                          method="post">
                        <button type="submit" class="btn btn-primary btn-md" name="status"
                                value="APPROVED">Approve
                        </button>
                        <button type="submit" class="btn btn-primary btn-md" name="status"
                                value="DISAPPROVED">Disapprove
                        </button>
                    </form>
                </c:if>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th scope="row">User:</th>
            <td>${author.lastName} ${author.firstName}</td>
        </tr>
        <tr>
            <th scope="row">Project:</th>
            <td>${project.name}</td>
        </tr>
        <tr>
            <th scope="row">Start date:</th>
            <td>${vacation.startDate}</td>
        </tr>
        <tr>
            <th scope="row">End date:</th>
            <td>${vacation.endDate}</td>
        </tr>
        <tr>
            <th scope="row">Project manager:</th>
            <td>${projectManager.lastName} ${projectManager.firstName}</td>
        </tr>
        <tr>
            <th scope="row">Line manager:</th>
            <td>${lineManager.lastName} ${lineManager.firstName}</td>
        </tr>
        <tr>
            <th scope="row">Project manager status:</th>
            <td>${vacation.pmStatus}</td>
        </tr>
        <tr>
            <th scope="row">Line manager status:</th>
            <td>${vacation.lmStatus}</td>
        </tr>
        </tbody>
    </table>
    <c:choose>
        <c:when test="${vacation.lmStatus ne 'APPROVED' && vacation.pmStatus ne 'APPROVED' && vacation.userId eq currentUser.userId}">
            <form action="/vacation/updateAuthorVacation/${vacation.vacationId}">
                <input onclick="window.history.go(-1); return false;"
                       type="button" class="btn btn-primary btn-md"
                       value="Back"/>
                <button type="submit" class="btn btn-primary btn-md">Update
                </button>
            </form>
        </c:when>
        <c:otherwise>
            <div class="form-group">
                <input onclick="window.history.go(-1); return false;"
                       type="button" class="btn btn-primary btn-md"
                       value="Back"/>
            </div>
        </c:otherwise>
    </c:choose>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
