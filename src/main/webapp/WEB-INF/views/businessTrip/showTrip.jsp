<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Trip</title>
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
            <th scope="col" colspan="2" style="text-align: center;">Business
                trip
                <c:if test="${currentUser.jobTitle.name() eq 'PROJECT_MANAGER'}">
                    <form action="/businessTrip/updateTripStatus/${businessTrip.businessTripId}"
                          method="post">
                        <button type="submit" class="btn btn-primary btn-md"
                                name="status"
                                value="APPROVED">Approve
                        </button>
                        <button type="submit" class="btn btn-primary btn-md"
                                name="status"
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
            <td>${businessTripUser}</td>
        </tr>
        <tr>
            <th scope="row">Project manager:</th>
            <td>${businessTripPM}</td>
        </tr>
        <tr>
            <th scope="row">Author:</th>
            <td>${businessTripAuthor}</td>
        </tr>
        <tr>
            <th scope="row">Project name:</th>
            <td>${businessTripProject}</td>
        </tr>
        <tr>
            <th scope="row">Country:</th>
            <td>${businessTrip.country}</td>
        </tr>
        <tr>
            <th scope="row">Start date:</th>
            <td>${businessTrip.startDate}</td>
        </tr>
        <tr>
            <th scope="row">End date:</th>
            <td>${businessTrip.endDate}</td>
        </tr>
        <tr>
            <th scope="row">Status:</th>
            <td>${businessTrip.status}</td>
        </tr>
        </tbody>
    </table>
    <div class="form-group">
        <c:choose>
            <c:when test="${businessTrip.status ne 'APPROVED' or businessTrip.pmId eq currentUser.userId}">
                <form action="/businessTrip/updateBusinessTrip/${businessTrip.businessTripId}">
                    <input onclick="window.history.go(-1); return false;"
                           type="button" class="btn btn-primary btn-md"
                           value="Back"/>
                    <button type="submit" class="btn btn-primary btn-md"
                            value="Update">
                        Update
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <input onclick="window.history.go(-1); return false;"
                       type="button" class="btn btn-primary btn-md"
                       value="Back"/>
            </c:otherwise>
        </c:choose>
    </div>
    <%@include file="../errors/errorMap.jsp" %>
</div>
</body>
</html>
