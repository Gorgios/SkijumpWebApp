

$(document).ready(function(){
   $('#myModal').on('click', function(event){

       $('#updt').modal();
    });
});
$(document).ready(function(){
    $('#changeRole').on('click', function(event){

        $('#crole').modal();
    });
});

function showDiv(id) {
    var x = document.getElementById(id);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
$(document).ready(function(){
$('.message a').click(function() {
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
});