"use strict";



class Player {

    constructor() {
        // attributes reprensenting player HTML DOM elements
        this.audio = document.querySelector("#controls-progress audio");
        this.ppb = document.querySelector("#playPauseButton");
        this.state = "paused";

        // event listener for click on play pause button
        this.ppb.addEventListener("click", () => this.onPlayPauseButtonClickEvent());
        // event listener for end of playing
        this.audio.addEventListener("ended", () => this.onAudioEndedEvent());
        // event listener for toggling queue view
        document.querySelector("#toggle-queue-button")
            .addEventListener("click", () => QUEUE.toggle());
        // event listener for prev button
        document.querySelector("#prevButton")
            .addEventListener("click", () => this.onPrevClickEvent());
        // event listener for next button
        document.querySelector("#nextButton")
            .addEventListener("click", () => QUEUE.next());
    }

    loadAndPlay(song) {
        this.load(song);
        this.play();
    }

    play() {
        // start playing
        this.audio.play();
        // updating state
        this.state = "playing";
        // updating play/pause button status
        this.ppb.classList.remove("fa-play-circle");
        this.ppb.classList.add("fa-pause-circle");
    }

    pause() {
        // pause playing
        this.audio.pause();
        // updating state
        this.state = "paused";
        // updating play/pause button status
        this.ppb.classList.add("fa-play-circle");
        this.ppb.classList.remove("fa-pause-circle");
    }

    load(song) {
        // loading cover, title and artist in controls-song elements
        songToHtml(song, document.querySelector("#controls .song"));
        // loading source in audio element
        this.audio.setAttribute("src", song.source);
        if (this.state == "playing")
            this.audio.play();
    }

    onPlayPauseButtonClickEvent() {
        if (this.state == "playing")
            this.pause();
        else
            this.play();
    }

    onAudioEndedEvent() {
        // update PPB button state
        this.ppb.classList.add("fa-play-circle");
        this.ppb.classList.remove("fa-pause-circle");
        // call QUEUE next method 
        QUEUE.next();
    }

    onPrevClickEvent() {
        if (this.audio.currentTime < 3 && QUEUE.hasPrev())
            QUEUE.prev();
        else
            this.audio.currentTime = 0;
    }

};