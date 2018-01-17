<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMap}">
    <div class="alert alert-danger col-lg-6 form-group">
        Error:
        <c:forEach items="${errorMap}" var="error">
            ${error.value}<br>
        </c:forEach>
    </div>
</c:if>