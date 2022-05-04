"use strict";



function distance(e, y2) {
    let y1 = e.getBoundingClientRect().top + (e.getBoundingClientRect().top - e.getBoundingClientRect().bottom) / 2;
    return Math.abs(y1 - y2);
}


function onDragEnterOver(e) {
    if (e.dataTransfer.types.includes("queue-item") &&
        e.dataTransfer.effectAllowed == "move") {
        // authorizing drop
        e.dataTransfer.dropEffet = "move";
        e.preventDefault();
        // computing closer drop position
        let closer;
        for (let dp of e.currentTarget.querySelectorAll(".queue-drop-position")) {
            if (!closer || distance(closer, e.clientY) > distance(dp, e.clientY))
                closer = dp;
        }
        // remove .active from other drop position
        for (let dp of e.currentTarget.querySelectorAll(".queue-drop-position"))
            if (dp != closer)
                dp.classList.remove("active");
        // add .active to the closer drop position
        closer.classList.add("active");
    }
}

function onDragLeaveEvent(e) {
    for (let dp of e.currentTarget.querySelectorAll(".queue-drop-position"))
        dp.classList.remove("active");
}

function onDropEvent(e) {
    // find closer to mouse drop position
    let closer;
    for (let dp of e.currentTarget.querySelectorAll(".queue-drop-position")) {
        if (!closer || distance(closer, e.clientY) > distance(dp, e.clientY))
            closer = dp;
    }
    closer.classList.remove("active");
    // retrieve data to drop
    let data = e.dataTransfer.getData("queue-item");
    let dummy = document.createElement("div");
    dummy.innerHTML = data;
    // inserting into DOM
    e.currentTarget.insertBefore(closer.cloneNode(), closer);
    e.currentTarget.insertBefore(dummy.firstChild, closer);
}



class Queue {


    constructor(songs) {

        // queue properties
        this.songs = [];
        this.currentSong;

        // adding event listeners for drag & drop
        let queueContent = document.querySelector("#queue .content");
        queueContent.addEventListener("dragenter", onDragEnterOver);
        queueContent.addEventListener("dragover", onDragEnterOver);
        queueContent.addEventListener("dragleave", onDragLeaveEvent);
        queueContent.addEventListener("drop", onDropEvent);

        // loading songs received as arguments if any
        songs.forEach(s => {
            this.add(s);
        })
        // loading the first song in the player
        if (this.songs.length) {
            PLAYER.load(this.songs[0]);
            this.currentSong = 1;
        }

    }

    toggle() {
        let queue = document.getElementById("queue");
        let content = document.querySelector("#content");
        if (window.getComputedStyle(queue).display == "none") {
            queue.style.display = "block";
            content.style.display = "none";
        } else {
            queue.style.display = "none";
            content.style.display = "block";
        }
    }

    add(song) {
        // add object to array
        this.songs.push(song);
        // create elements (clone template)
        let element = document
            .querySelector("#templates .queue-item-template")
            .cloneNode(true);
        // fill elements with correct values
        songToHtml(song, element);
        // insert elements in DOM
        document.querySelector("#queue .content").appendChild(element);
        // inserting drop position
        let div = document.createElement("div");
        div.classList.add("queue-drop-position");
        document.querySelector("#queue .content").appendChild(div);
        // add event listeners for play button
        this.bind(element);
    }

    insert(song, pos) {
        // insert object into array
        this.songs.splice(pos, 0, song);
        // inserting HTML element
        document.querySelector("#queue article.song:nth-of-type("+pos+")")
            .insertAdjacentElement("afterend", this.createQueueItem(song, pos+1));
        // updating songs numbers
        Array.from(document.querySelectorAll("#queue article.song:nth-of-type("+(pos+1)+")"))
            .forEach(s => s.querySelector(".song-number").textContent--);
    }

    remove(element) {
        // TODO: stop playing if it's the current song
        // retrieving num
        let num = element.querySelector(".song-number").textContent;
        // remove from songs array
        this.songs.splice(num - 1, 1);
        // remove it from DOM
        element.parentNode.removeChild(element.nextElementSibling);
        element.parentNode.removeChild(element);
        // update number of following songs
        Array.from(document.querySelectorAll("#queue .song"))
            .filter(s => s.querySelector(".song-number").textContent > num)
            .forEach(s => s.querySelector(".song-number").textContent--);
    }


    play(element) {
        // retrieve song number and update currentSong
        this.currentSong = element.querySelector(".song-number").textContent;
        // call play from PLAYER
        PLAYER.play(this.songs[this.currentSong - 1]);
    }


    prev() {
        if (this.currentSong > 1)
            PLAYER.loadAndPlay(this.songs[--this.currentSong - 1]);
    }

    hasPrev() {
        return this.currentSong > 1;
    }

    next() {
        if (this.hasNext())
            PLAYER.loadAndPlay(this.songs[++this.currentSong - 1]);
        else
            this.reset();
    }

    hasNext() {
        return this.currentSong < this.songs.length;
    }

    insertAndPlay(song) {
        // inserting song in songs array (replacing curretnSong)
        this.songs.splice(this.currentSong - 1, 1, song);
        // retrieving old song element
        let element = Array.from(document.querySelectorAll("#queue .song"))
            .filter(s => s.querySelector(".song-number").textContent == this.currentSong)[0];
        // fill elements with correct values
        element.setAttribute("data-src", song.source);
        element.querySelector(".song-cover").setAttribute("src", song.cover);
        element.querySelector(".song-title").innerHTML = song.title;
        element.querySelector(".song-artist").innerHTML = song.artist;
        // call play from PLAYER
        PLAYER.loadAndPlay(this.songs[this.currentSong - 1]);
    }

    reset() {
        PLAYER.pause();
        PLAYER.load(this.songs[0]);
        this.currentSong = 1;
    }


    createQueueItem(song, num) {
        let dummy = document.createElement("div").innerHTML = 
                `<article class="song queue-item-template" data-src="${song.source}" draggable="true">
                    <p class="song-number">${num}</p>
                    <img class="song-cover" src="${song.cover}" draggable="false"/>
                    <div class="song-description">
                        <p href="" class="song-title">${song.title}</p>
                        <a href="" class="song-artist" draggable="false">${song.artist}</a>
                    </div>
                    <a href="" class="song-album" draggable="false">Album</a>
                    <p class="song-duration">?:??</p>
                    <button class="queue-play"><i class="fas fa-play-circle"></i></button>
                    <button class="queue-remove"><i class="fas fa-times-circle"></i></button>
                </article>`
        return dummy.firstChild();
    }


    bind(element) {
        // add event listeners for play button
        element.querySelectorAll(".queue-play").forEach(b => {
            b.addEventListener("click", e => {
                this.play(b.parentNode);
            });
        });
        // add event listeners for remove button
        element.querySelectorAll(".queue-remove").forEach(b => {
            b.addEventListener("click", e => {
                this.remove(b.parentNode);
            });
        });
        // add event listeners for add button
        element.querySelectorAll(".queue-add").forEach(b => {
            b.addEventListener("click", e => {
                this.add(songFromHtml(b.parentNode));
            });
        });
        // add event listeners for insert and play button
        element.querySelectorAll(".queue-insert-play").forEach(b => {
            b.addEventListener("click", e => {
                this.insertAndPlay(songFromHtml(b.parentNode));
            });
        });
        // add event listener for dragstart event
        element.querySelectorAll(".song").forEach(song => {
            song.addEventListener("dragstart", e => {
                // setting drag data
                e.dataTransfer.setData("queue-item", song.outerHTML);
                e.dataTransfer.setData("queue-item-num", song.querySelector(".song-num"));
                // setting drag effect
                e.dataTransfer.effectAllowed = "move";
            });
        });
    }


};