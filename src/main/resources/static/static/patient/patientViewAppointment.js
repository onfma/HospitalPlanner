$(document).ready(function() {
  $('.dropdown-menu a').click(function(event) {
    event.preventDefault(); // Prevent the default behavior of following the href link
    var selectedOption = $(this).text();
    $(this).closest('.dropdown').find('.form-control').val(selectedOption);
  });
});

function cancelAppointment(button) {
  var appointmentId = button.getAttribute("data-appointment-id");

  axios.post("/myAppointments/cancelAppointment", { appointmentId: appointmentId })
    .then(function (response) {
      location.reload();
    })
    .catch(function (error) {
      console.error("Error canceling the appointment:", error);
    });
}

function viewReport(button) {
    var appointmentId = button.getAttribute("data-appointment-id");

    window.location.href = "/viewReport/" + appointmentId;
}


//function viewReport(button) {
//    var appointmentId = button.getAttribute("data-appointment-id");
//
//    axios.post("/viewReport", { appointmentId: appointmentId })
//        .then(function (response) {
//          location.reload();
//        })
//        .catch(function (error) {
//          console.error("Error generating the report:", error);
//        });
//}
