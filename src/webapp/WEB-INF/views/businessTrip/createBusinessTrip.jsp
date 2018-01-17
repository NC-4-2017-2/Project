<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Create business trip</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/businessTrip/createBusinessTrip/projectId/${projectId}/pmId/${pmId}"
      method="post">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="country">Choose country:</label>
            <div class="input-group">
                <select name="country" id="country" required>
                    <c:forEach items="${countryList}" var="country">
                        <option value="${country}">${country}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="startDate">Choose start date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="startDate"
                       name="startDate" placeholder="Enter start date"
                       required></div>
        </div>
        <div class="form-group">
            <label for="endDate">Choose end date:</label>
            <div class="input-group">
                <input type="date" class="form-control" id="endDate"
                       name="endDate" placeholder="Enter end date" required>
            </div>
        </div>
        <c:choose>
            <c:when test="${authorId eq pmId }">
                <input type="hidden" name="authorId" value="${pmId}"/>
                <div class="form-group">
                    <label for="user">Choose user:</label>
                    <div class="input-group">
                        <select name="user" id="user">
                            <c:forEach items="${userList}" var="user">
                                <option value="${user.userId}"
                                        name="user">${user.firstName} ${user.lastName} ${user.dateOfBirth}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="user" value="${authorId}"/>
                <input type="hidden" name="authorId" value="${authorId}"/>
            </c:otherwise>
        </c:choose>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md"
                   value="Create"/>
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
