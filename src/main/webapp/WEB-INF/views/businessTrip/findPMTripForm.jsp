<form action="/businessTrip/viewPMTrip">
        <label for="status">Choose status:</label>
        <div class="input-group">
            <select name="status" id="status" required>
                <option value="APPROVED">APPROVED</option>
                <option value="DISAPPROVED">DISAPPROVED</option>
                <option value="WAITING_FOR_APPROVAL">WAITING_FOR_APPROVAL
                </option>
            </select>
        </div>
        <br>
        <div class="form-group">
            <input type="submit" class="btn btn-primary btn-md" value="Find">
        </div>
</form>
