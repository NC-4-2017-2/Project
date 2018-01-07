<form action="/project/createProject" method="get">
    <tr>
        <td>Count of sprints:</td>
        <td><input type="number" name="countSprints"></td>
    </tr>
    <br>
    <tr>
        <td>Count of workers:</td>
        <td><input type="number" name="countWorkers"></td>
    </tr>
    <br>
    <%@include file="../errors/errorMap.jsp" %>
    <tr>
        <input type="submit" value="Next"/></td>
    </tr>
</form>
