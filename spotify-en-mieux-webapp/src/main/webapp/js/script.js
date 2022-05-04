

var PLAYER;
var QUEUE;


window.addEventListener("load", function () {

    PLAYER = new Player();
    QUEUE = new Queue(Array.from(this.document.querySelectorAll("#main section:first-of-type .song"))
        .map(e => songFromHtml(e)));

    QUEUE.bind(this.document);

    // this.document.querySelectorAll("#main section:first-of-type .song")
    //     .forEach(e => QUEUE.add(songFromHtml(e)));

});


