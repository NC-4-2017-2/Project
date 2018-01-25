<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin"/>
<sec:authorize access="hasRole('ROLE_PM')" var="isPM"/>
<sec:authorize access="hasRole('ROLE_LM')" var="isLM"/>
<sec:authorize access="hasRole('ROLE_SE')" var="isSE"/>
<div class="navbar navbar-default navbar-fixed-bottom">
    <div class="container">
        <c:if test="${isAdmin}">
            <p class="navbar-text pull-left"><strong>Admin Dashboard</strong>
            </p>
        </c:if>
        <c:if test="${isPM}">
            <p class="navbar-text pull-left"><strong>Project Manager
                Dashboard</strong></p>
        </c:if>
        <c:if test="${isLM}">
            <p class="navbar-text pull-left"><strong>Line Manager
                Dashboard</strong></p>
        </c:if>
        <c:if test="${isSE}">
            <p class="navbar-text pull-left"><strong>Software Engineer
                Dashboard</strong></p>
        </c:if>
        <p class="navbar-text pull-right">Designed by <a
                href="https://github.com/NC-4-2017-2/Project">NC_Group4</a></p>
    </div>
</div>
