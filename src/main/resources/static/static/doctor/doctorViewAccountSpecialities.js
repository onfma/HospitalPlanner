  $(document).ready(function() {
    $('.dropdown-menu a').click(function(event) {
      event.preventDefault(); // Prevent the default behavior of following the href link
      var selectedOption = $(this).text();
      $(this).closest('.dropdown').find('.form-control').val(selectedOption);
    });
  });

  $(document).ready(function() {
    $('input').on('input', function() {
      var formValid = false;

      $('input').each(function() {
        if ($(this).val().trim() !== '') {
          formValid = true;
          return false;
        }
      });

      if (formValid) {
        $('#submitButtonAccount').removeAttr('disabled');
      } else {
        $('#submitButtonAccount').attr('disabled', 'disabled');
      }
    });
  });

function addSpeciality(element) {
    var cabinetID = element.getAttribute("cabinet-id");

    window.location.href = "/doctorViewAccount/addSpeciality/" + cabinetID;
}