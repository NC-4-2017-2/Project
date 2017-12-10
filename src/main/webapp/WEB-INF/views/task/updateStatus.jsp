<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update status your task</title>
</head>
<body>
<div align="center">
    <form action="/task/updateStatus" method="post" name="/task/updateStatus"
          commandName=taskForm">
        <table border="0">
            <tr>
                <h2>Update status your task</h2></td>
            </tr>

            <tr>
                <td>Status:</td>
                <td>
                    <select id="update" name="update[]" size="2" style="width: 200px; height: 100px;" multiple="multiple">
                    <h2>Select necessary status</h2>
                    <option value="OPEN">OPEN</option>
                    <option selected value="CLOSED">CLOSED</option>
                    <option value="REOPEN">REOPEN</option>
                    <option value="READY FOR TESTING">READY FOR TESTING</option>
                  </select></p>
                    <p><input type="submit" value="Apply"></p></td>
            </tr>
            <tr>

        </table>
    </form>
</div>
</body>
</html>
