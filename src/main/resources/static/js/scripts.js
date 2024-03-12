/*!
* Start Bootstrap - Shop Homepage v5.0.6 (https://startbootstrap.com/template/shop-homepage)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-shop-homepage/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

    function fillColumn(columnName) {
        var firstField = document.querySelector('input[name$="' + columnName + '"]');
        var time = firstField.value;
        var timeFields = document.querySelectorAll('input[name$="' + columnName + '"]');
        timeFields.forEach(function(field) {
            field.value = time;
        });
    }
$(document).ready(function() {
    $('#userType').change(function() {
        if ($(this).val() == 'true') {
            $('#countryField').show();
            $('#addressCountry, #addressCity, #addressStreet, #addressPostalCode').prop('disabled', false);
        } else {
            $('#countryField').hide();
            $('#addressCountry, #addressCity, #addressStreet, #addressPostalCode').prop('disabled', true);
        }
    });
});
function readURL(input) {
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
      $('#preview').attr('src', e.target.result);
    };
    reader.readAsDataURL(input.files[0]);
  }
}

$("#photoUpload").change(function(){
    readURL(this);
});
var formControls = document.querySelectorAll('.form-control');

formControls.forEach(function(formControl) {
    formControl.addEventListener('input', function() {
        var label = this.parentNode.querySelector('label');
        if (this.value.trim() !== '') {
            label.style.display = 'none';
        } else {
            label.style.display = 'block';
        }
    });
});
var allChecked = false;
function toggleAll() {
    var checkboxes = document.getElementsByClassName('street-checkbox');
    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = !allChecked;
    }
    allChecked = !allChecked;
}
