<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find vacation by status</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<form action="/vacation/viewVacation">
    <div class="col-lg-6">
        <div class="form-group">
            <label for="pmStatus">Choose PM status:</label>
            <div class="input-group">
                <select name="pmStatus" id="pmStatus" required>
                    <option value="APPROVED">APPROVED</option>
                    <option value="DISAPPROVED">DISAPPROVED</option>
                    <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL
                    </option>
                </select>
            </div>
            <br>
            <label for="lmStatus">Choose LM status:</label>
            <div class="input-group">
                <select name="lmStatus" id="lmStatus" required>
                    <option value="APPROVED">APPROVED</option>
                    <option value="DISAPPROVED">DISAPPROVED</option>
                    <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL
                    </option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
        <%@include file="../errors/errorMap.jsp" %>
    </div>
</form>
</body>
</html>
