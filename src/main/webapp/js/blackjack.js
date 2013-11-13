$(document).ready(function(){
    $("#start").click(function(){
        start();
    });

    $("#hit").click(function(){
        hit();
    });

    $("#stay").click(function(){
       stay();
    });
});

function start() {

    $("#cetera").animate({"height":"400px"}, 'slow');
    $("#cetera .panel-body").slideDown('slow');

    $.get('start', {}, function(json){
        var error = json.error;
        var cards = json.cards;
        if (!error) {
            clearExistingCards();
            $.each(cards, function(key, value){
                appendCard("cetera", "up", value.suit, value.value);
            });
            appendCard("host", "down", "", "");
            appendCard("host", "down", "", "");

            refreshScore("host", json.hostScore);
            refreshScore("cetera", json.ceteraScore);

            $("#start").attr("disabled", "disabled");
            $("#hit").removeAttr("disabled", "disabled");
            $("#stay").removeAttr("disabled", "disabled");
            $("#cetera-code").css("font-size", "80%");
            $("#cetera-code").css("color", "");
            $("#host-code").css("font-size", "80%");
            $("#host-code").css("color", "");
            $("#cetera-code").text("Player");
            $("#host-code").text("Host");
        }
    });
}

function hit() {
    $.get('hit', {}, function(json){
        var error = json.error;
        if(!error) {
            var player = json.playerCode;
            var totalValue = json.value;
            var value = json.card.value;
            var suit = json.card.suit;
            var gameStatus = json.gameStatus;
            var winner = json.winnerCode;
            appendCard(player, "up", suit, value);
            refreshScore(player, totalValue);

            if (gameStatus == 'Ready') {
                endWithWinner(winner);
            }
        }
    });
}

function endWithWinner(winner) {
    setUIEndStatus();
    setWinner(winner);
}

function setUIEndStatus() {
    $("#start").removeAttr("disabled");
    $("#hit").attr("disabled", "disabled");
    $("#stay").attr("disabled", "disabled");
}

function setWinner(winner) {
    var dom = "#" + winner + "-code";
    var player = $(dom).text();
    $(dom).animate({fontSize: "50pt"},
        'slow',
        function(){
            $(dom).css("color", "red");
            $(dom).animate({
                fontSize: "30pt"
            }, 'slow', function(){
                $(dom).text(player + " Wins");
            });
        }
    );
}

function clearExistingCards() {
    $("#host .panel-body").empty();
    $("#cetera .panel-body").empty();
}

function refreshScore(player, score){
    $("#" + player + "-score").text(score);
    $("#" + player + "-score").fadeOut('slow').fadeIn('slow');
}

function appendCard(player, face, suit, value) {
    if (face == "up") {
        $("#" + player + " .panel-body").append('<div class="card' + suit + value + '"></div>');
        $(".card" + suit + value ).css("float", "left");
        $(".card" + suit + value ).css("height", "240px");
        $(".card" + suit + value ).css("width", "170px");
        $(".card" + suit + value ).css("background-image", "url(/BlackJack/images/cards/"+ suit +"/" + value +".jpg)");
    } else {
        $("#" + player + " .panel-body").append('<div class="card"></div>');
        $(".card" + suit + value ).css("float", "left");
        $(".card" + suit + value ).css("height", "240px");
        $(".card" + suit + value ).css("width", "170px");
        $(".card" + suit + value ).css("background-image", "url(/BlackJack/images/stick_small.jpg)");
    }
    $(".card" + suit + value + ":last").fadeOut('slow').fadeIn('slow');
}

function stay() {
    $.get('stay', {}, function(json){
        var error = json.error;
        var winner = json.winnerCode;
        if (!error) {
            if(winner == null || winner == "" || winner == undefined) {
                switchPlayer();
            } else if (winner == "even") {
                endWithEven();
            } else {
                endWithWinner(winner)
            }
        }
    });
}

function endWithEven() {
    setUIEndStatus();
    var dom = "#cetera-code, #host-code";
        $(dom).animate({fontSize: "50pt"},
            'slow',
            function(){
                $(this).css("color", "green");
                $(this).animate({
                    fontSize: "30pt"
                }, 'slow', function(){
                    $(this).text($(this).text() + " -Even");
                });
            }
    );
}

function switchPlayer() {
    $("#cetera .panel-body").slideUp('slow');
    $("#cetera").animate({"height":"20px"}, 'slow');
}