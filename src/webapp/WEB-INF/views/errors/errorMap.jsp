<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMap}">
    <div class="alert alert-danger" role="alert">
        <span class="glyphicon glyphicon-exclamation-sign"
              aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </div>
</c:if>