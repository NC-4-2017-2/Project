<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="principal" property="principal.username"/>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">ERP System</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="/">Home</a></li>
            <li><a href="#">Page 1</a></li>
            <li><a href="#">Page 2</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="/user/showUser/${principal}"><span class="glyphicon glyphicon-user"></span>${principal}</a></li>
            <li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span>Log out</a></li>
        </ul>
    </div>
</nav>