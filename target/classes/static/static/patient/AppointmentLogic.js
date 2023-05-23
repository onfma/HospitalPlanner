    function validateForm() {
        var currentDate = new Date();
        var selectedDate = new Date(document.getElementById("date").value);
        var selectedTime = new Date("2000-01-01 " + document.getElementById("time").value);
        var isScheduled = false;
        var isScheduledForSelectedDoctor = false;

        if (!isAfterCurrentDate()) {
            //document.getElementById("error").innerText = "Date is incorrect..";
            alert("Date is incorrect.");
        } else if (!isWorkDay()) {
            alert("We don't work on the weekends.");
        } else if (!isInCabinetSchedule()) {
            alert("Not in the Cabinet Schedule. ");
        } else if (!isInSelectedDoctorSchedule()) {
            alert("Not in the selected Doctor Schedule.");
            // schimbi doctorul pentru data si ora selectata
            // SAU
            // schimbi ora si data in urmatoarul slot disponibil pt doctorul selectat
        } else if (!isSlotAvailable(doctor)) {
            // schimbi ora si data in urmatoarul slot disponibil pt doctorul selectat
            // SAU
            // schimbi la doctorul acesta care liber slotul pt programare la data si ora selectata
        } else return true;

        return false;
    }


    function isAfterCurrentDate(){
        var currentDate = new Date();
        var selectedDate = new Date(document.getElementById("date").value);

        return selectedDate >= currentDate;
    }

    function isWorkDay(){
        var currentDate = new Date();
        var selectedDate = new Date(document.getElementById("date").value);

        return !(selectedDate.getDay() === 0 || selectedDate.getDay() === 6);
    }

    function isInCabinetSchedule(){
        var selectedDate = new Date(document.getElementById("date").value);
        var selectedTime = new Date("2000-01-01 " + document.getElementById("time").value);

        var scheduleList = document.getElementsByClassName("text_schedule");
        for (var i = 0; i < scheduleList.length; i += 3) {
            var dayOfWeek = scheduleList[i].innerText;
            var startTimeStr = scheduleList[i + 2].innerText.split(" - ")[0];
            var endTimeStr = scheduleList[i + 2].innerText.split(" - ")[1];

            var scheduleStartTime = new Date("2000-01-01 " + startTimeStr);
            var scheduleEndTime = new Date("2000-01-01 " + endTimeStr);

            if (
                selectedDate.getDay() === getDayOfWeekNumber(dayOfWeek) &&
                selectedTime >= scheduleStartTime &&
                selectedTime <= scheduleEndTime
            ) {
                return true;
            }
        }
        return false;
    }

    function isInSelectedDoctorSchedule(){
        var cabinetID = document.getElementById('cab').value;
        var selectedDate = new Date(document.getElementById("date").value);
        var selectedTime = new Date("2000-01-01 " + document.getElementById("time").value);
        var selectedCNP = document.getElementById('select_doctor').value;
        var DoctorSchedule = [];

        console.log(cabinetID);
        console.log(selectedCNP);


        // Fetch the doctor's schedule using the selected CNP
        fetch('/' + cabinetID + '/doctorSchedule/' + selectedCNP)
            .then(function(response) {
                if (!response.ok) {
                    throw new Error('Error retrieving doctor schedule');
                }
            })
            .then(function(schedule) {
                DoctorSchedule = schedule;
                console.log('Doctor schedule:', schedule);

                // Verifică structura răspunsului JSON
                console.log('Schedule entry example:', schedule[0]); // Afișează o înregistrare de exemplu din schedule

                return true;
            })
            .catch(function(error) {
                console.error('Error:', error);
                return false; // Prevent form submission
            });

        for (var i = 0; i < DoctorSchedule.length; i++) {
            var scheduleEntry = DoctorSchedule[i];

            // Check if the selected date matches the schedule entry's day of the week
            if (selectedDate.getDay() === getDayOfWeekNumber(scheduleEntry.dayOfWeek)) {
                var scheduleStartTime = new Date("2000-01-01 " + scheduleEntry.startTime);
                var scheduleEndTime = new Date("2000-01-01 " + scheduleEntry.endTime);

                // Check if the selected time falls within the schedule entry's start and end times
                if (selectedTime >= scheduleStartTime && selectedTime <= scheduleEndTime) {
                    return true;
                }
            }
        }
        return false;

        // for (var i = 0; i < DoctorSchedule.length; i++) {
        //     var schedule = DoctorSchedule[i];
        //     var id = schedule.id;
        //     var dayOfWeekk = schedule.dayOfWeek;
        //     var startTime = DoctorSchedule.startTime;
        //     var endTime = schedule.endTime;
        // }
    }


    function getDayOfWeekNumber(dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "monday":
                return 1;
            case "tuesday":
                return 2;
            case "wednesday":
                return 3;
            case "thursday":
                return 4;
            case "friday":
                return 5;
            case "saturday":
                return 6;
            case "sunday":
                return 0;
            default:
                return -1;
        }
    }