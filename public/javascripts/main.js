$(function() {

    var iframeheight = $('.fb-comments iframe').height();

    //console.log("iframe", $('.fb-comments iframe'));
    //console.log("iframe height", iframeheight);

    var ifr = $('.fb-comments iframe');
    ifr.on('load', function() {
        console.log("iframe height", this.height());
    });

    /*
    window.setInterval(function() {
        var ifr = $('.fb-comments iframe');
        console.log('heeeight', ifr.height());
    }, 500);*/

    //parent.postMessage('iframeheight', iframeheight);

    var eventMethod = window.addEventListener ? "addEventListener" : "attachEvent";
    var eventer = window[eventMethod];
    var messageEvent = eventMethod == "attachEvent" ? "onmessage" : "message";

    // Listen to message from child window
    eventer(messageEvent,function(e) {
        console.log('remote received message!:  ',e.data);
        parent.postMessage(e.data, "*");
    },false);

})