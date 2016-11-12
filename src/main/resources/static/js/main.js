
function confirmDelete(id, text) {
    $('#itemToDelete').val(id);
    $('#confirmDeleteMessage').html(text);
    $('#deleteModal').modal();
};