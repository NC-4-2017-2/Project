<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p><c:if test="${not empty errorMap}">
    <c:forEach items="${errorMap}" var="error">
        ${error.value}<br>
    </c:forEach>
</c:if></p>