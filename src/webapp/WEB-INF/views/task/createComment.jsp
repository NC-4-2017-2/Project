<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create comment</title>
</head>
<body>
    <form action="/task/createComment/taskId/${taskId}/userId/${userId}/creationDate/${creationDate}" method="post">
        <td>Enter your comment:</td><br>
        <td><textarea rows="10" cols="45" name="bodyComment" value=${bodyComment}></textarea></td>
        <br>

        <td><button type="submit">Add comment</button></td>

        </tr>

</form>
</body>
</html>
