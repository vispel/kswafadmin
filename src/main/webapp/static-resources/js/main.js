$(document).ready(function() {

    function getContext() {
        return $("body").data("context");
    };

    $('#datePickerDateFrom, #datePickerDateTo').datepicker( {
        format : 'yyyy-mm-dd',
        orientation: 'top left'
    });


    $("#userRulesTable").on("click", function(item){
        var id = parseInt($(item.target).parent("tr").attr('id'));
        window.location.href = getContext() + "admin/edit/signature/"+id;
    });

});


