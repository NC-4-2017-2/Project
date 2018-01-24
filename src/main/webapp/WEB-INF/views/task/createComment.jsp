<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create comment</title>
</head>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<body>
<jsp:include page="../fragments/header.jsp"></jsp:include>
    <form action="/task/createComment/taskId/${taskId}/userId/${userId}/creationDate/${creationDate}" method="post">
        <div class="col-lg-4">

           <div class="panel-body">
            <label for="bodyComment">Comment:</label>
            <textarea class="form-control" name="bodyComment" id="bodyComment"
                      style = "resize:none;" cols = "60" rows= "7" placeholder="Add your comment" value=${bodyComment}></textarea>
        </div>

            <%@include file="../errors/errorMap.jsp" %>
            <div class="form-group">
                <input onclick="window.history.go(-1); return false;"
                       type="button" class="btn btn-primary btn-md"
                       value="Back"/>
                <input type="submit" value="Create" class="btn btn-primary btn-md"/>
            </div>
        </div>

</form>
</body>
</html>
