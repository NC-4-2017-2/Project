<form action="/workingDay/createWorkingDay" method="post">
    <table border="0">
        <tr>
            <td>Monday:</td>
            <td><input name="monday" type="time"></td>
            <c:choose>
                <c:when test="${MondayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${MondayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${MondayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${MondayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Tuesday:</td>
            <td><input name="tuesday" type="time"></td>
            <c:choose>
                <c:when test="${TuesdayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${TuesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${TuesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${TuesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Wednesday:</td>
            <td><input name="wednesday" type="time"></td>
            <c:choose>
                <c:when test="${WednesdayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${WednesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${WednesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${WednesdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Thursday:</td>
            <td><input name="thursday" type="time"></td>
            <c:choose>
                <c:when test="${ThursdayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${ThursdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${ThursdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${ThursdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Friday:</td>
            <td><input name="friday" type="time"></td>
            <c:choose>
                <c:when test="${FridayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${FridayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${FridayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${FridayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Saturday:</td>
            <td><input name="saturday" type="time"></td>
            <c:choose>
                <c:when test="${SaturdayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${SaturdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${SaturdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${SaturdayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <td>Sunday:</td>
            <td><input name="sunday" type="time"></td>
            <c:choose>
                <c:when test="${SundayTime.status.name() eq 'APPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-success" style="font-size:17pt;">
                                <abbr title="Approved">${SundayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${SundayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${SundayTime.workingHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
            </c:choose>
        </tr>
    </table>
    <br>
    <div class="form-group">
        <input type="submit" class="btn btn-primary btn-md" value="Create">
    </div>
</form>