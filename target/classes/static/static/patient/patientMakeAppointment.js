function makeAppointment(element) {
    var cabinetId  = element.getAttribute("data-cabinet-id");

    window.location.href = "/makeAppointment/" + cabinetId;
}