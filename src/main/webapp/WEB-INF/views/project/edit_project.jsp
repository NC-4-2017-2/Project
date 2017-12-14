<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head>
    <style type="text/css">
        div.project{
            align: center;
            text-align: center;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Project</title>
</head>
<body>
<div class="project">
    <form action="/project/edit={id}" method="post">
        <table border="0">
            <tr>
                <h2>Edit Project</h2></td>
            </tr>
            <tr>
                <td>Project Id:</td>
                <td><input type="text" name="projectId" size="70" readonly="readonly"
                           value=${projectId}></td>
            </tr>
            <tr>
                <td>Project Name:</td>
                <td><input type="text" name="name" size="70" readonly="readonly"
                           value=${projectName}></td>
            </tr>
            <tr>
                <td>StartDate (mm/dd/yyyy):</td>
                <td><input type="text" name="startDate" size="70" readonly="readonly"
                           value=${startDate}></td>
            </tr>
            <tr>
                <td>EndDate (mm/dd/yyyy):</td>
                <td><input type="text" name="endDate" size="70"
                           value=${endDate}></td>
            </tr>
            <tr>
                <td>Status:</td>
                <td> <select name="projectStatus" >
                    <option value="OPENED">OPENED</option>
                    <option value="CLOSED">CLOSED</option>
                </select></td>
            </tr>
            <tr>
                <td>Project Manager:</td>
                <td><input type="text" name="projectManagerId" size="70"
                           value=${pmId}></td>
            </tr>

            <tr>
                <td>Sprints:</td>
                <%--@elvariable id="modelSprint" type="main.com.netcracker.project.controllers.project.ProjectController"--%>
                <form:form modelAttribute="modelSprint" method="post">
                <c:forEach items="${modelSprint.sprints}" var="sprint" varStatus="status">
                    Sprint ${status.index} :<br>
                    <input type="text" name="sprints[${status.index}].name" value="${sprint.name}" readonly="readonly"></td><br>
                    <select name="sprints[${status.index}].sprintStatus" >
                        <option value="OPENED">OPENED</option>
                        <option value="CLOSED">CLOSED</option>
                    </select><br>
                    <br>
                </c:forEach>
            </tr>
            </form:form>

            <tr>
                <input type="submit" value="Edit"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>