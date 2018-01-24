<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All comments</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
    <script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>

</head>
    <body>
    <jsp:include page="../fragments/header.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3">
                <c:forEach items="${comments}" var="comment">

                    <div class="row">
                        <div class="col-sm-2">
                            <div class="thumbnail">
                                <img class="img-responsive user-photo"
                                     src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">
                            </div>
                        </div>
                        <div class="col-sm-10">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <strong> ${comment.getLastName()}  ${comment.getFirstName()} </strong>
                                    <span class="text-muted"> ${comment.getCreationDate()} </span>
                                </div>
                                <div class="panel-body">
                                        ${comment.getBodyComment()}
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <div class="form-group">
                    <input onclick="window.history.go(-1); return false;"
                           type="button" class="btn btn-primary btn-md"
                           value="Back"/>
                </div>

            </div>
        </div>
    </div>
        <%@include file="../errors/errorMap.jsp" %>
    </body>
</html>
