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
                                <abbr title="Approved">${MondayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${MondayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${MondayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${MondayTimeHours}</abbr>
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
                                <abbr title="Approved">${TuesdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${TuesdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${TuesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${TuesdayTimeHours}</abbr>
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
                                <abbr title="Approved">${WednesdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${WednesdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${WednesdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${WednesdayTimeHours}</abbr>
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
                                <abbr title="Approved">${ThursdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${ThursdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${ThursdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${ThursdayTimeHours}</abbr>
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
                                <abbr title="Approved">${FridayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${FridayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${FridayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${FridayTimeHours}</abbr>
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
                                <abbr title="Approved">${SaturdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${SaturdayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SaturdayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${SaturdayTimeHours}</abbr>
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
                                <abbr title="Approved">${SundayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'DISAPPROVED'}">
                    <td>
                        <div class="container">
                            <div class="text-danger" style="font-size:17pt;">
                                <abbr title="Disapproved">${SundayTimeHours}</abbr>
                            </div>
                        </div>
                    </td>
                </c:when>
                <c:when test="${SundayTime.status.name() eq 'WAITING_FOR_APPROVAL'}">
                    <td>
                        <div class="container">
                            <div class="text-info" style="font-size:17pt;">
                                <abbr title="Waiting for approval">${SundayTimeHours}</abbr>
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