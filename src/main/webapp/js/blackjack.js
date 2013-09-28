$(document).ready(function(){
    $("#start").click(function(){
        start();
    });

    $("#hit").click(function(){
        hit();
    });
});

function start() {
    $(".card").remove();
}

function hit() {
    $("#cards").append('<div class="card"></div>');
    $.get('start', {}, function(){
        alert('got it.');
    });
}