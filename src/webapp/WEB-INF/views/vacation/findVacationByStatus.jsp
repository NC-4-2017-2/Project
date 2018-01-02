<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Find vacation</title>
</head>
<body>
<form action="/vacation/viewVacation">
    Choose PM status:<select name="pmStatus" required>
    <option value="APPROVED">APPROVED</option>
    <option value="DISAPPROVED">DISAPPROVED</option>
    <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
</select><br>
    Choose LM status:<select name="lmStatus" required>
        <option value="APPROVED">APPROVED</option>
        <option value="DISAPPROVED">DISAPPROVED</option>
        <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL</option>
    </select><br>
    <%@include file="../errors/errorMap.jsp" %>
    <input type="submit" value="Find">
</form>
</body>
</html>
