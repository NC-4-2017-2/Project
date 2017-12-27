<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        p {
            color: red;
        }
    </style>
    <title>Trip</title>
</head>
<body>
<table>
    ${businessTrip}
    <%@include file="../errors/errorMap.jsp" %>
</table>
</body>
</html>
