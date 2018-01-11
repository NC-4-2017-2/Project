<table class="table table-hover table-dark" border="3">
    <thead>
    <tr>
        <th scope="col" colspan="2" style="text-align: center;">Project
            info:
        </th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">Name:</th>
        <td>${project.name}</td>
    </tr>
    <tr>
        <th scope="row">Start date:</th>
        <td>${project.startDate}</td>
    </tr>
    <tr>
        <th scope="row">End date:</th>
        <td>${project.endDate}</td>
    </tr>
    <tr>
        <th scope="row">Project manager:</th>
        <td>${projectManager.lastName} ${projectManager.firstName}</td>
    </tr>
    <tr>
        <th scope="row">Project status:</th>
        <td>${project.projectStatus}</td>
    </tr>
    </tbody>
</table>
<br>