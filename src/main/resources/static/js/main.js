
function confirmDelete(id, text) {
    $('#itemToDelete').val(id);
    $('#confirmDeleteMessage').html(text);
    $('#deleteModal').modal();
};

window.setTimeout(function () {
    $(".alert-success").fadeTo(500, 0).slideUp(500, function () {
        $(this).remove();
    });
}, 3000);