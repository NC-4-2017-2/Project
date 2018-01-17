<form action="/vacation/createVacation" method="post">
    <div class="form-group">
        <label for="startDate">Choose start date:</label>
        <div class="input-group">
            <input type="date" class="form-control" id="startDate"
                   name="startDate" placeholder="Enter start date" required>
        </div>
    </div>
    <div class="form-group">
        <label for="endDate">Choose end date:</label>
        <div class="input-group">
            <input type="date" class="form-control" id="endDate" name="endDate"
                   placeholder="Enter end date" required></div>
    </div>
    <div class="form-group">
        <input type="submit" class="btn btn-primary btn-md" value="Create"/>
    </div>
</form>