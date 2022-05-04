"use strict";



class Player {

    constructor() {
        // attributes reprensenting player HTML DOM elements
        this.audio = document.querySelector("#controls-progress audio");
        this.progress = document.querySelector("#controls-progress input");
        this.volume = document.querySelector("#controls-volume");
        this.volumeIcon = document.querySelector("#controls-volume-icon");
        this.ppb = document.querySelector("#playPauseButton");
        this.state = "paused";

        // event listener for click on play pause button
        this.ppb.addEventListener("click", () => this.onPlayPauseButtonClickEvent());
        // event listener for end of playing
        this.audio.addEventListener("ended", () => this.onAudioEndedEvent());
        // event listener for updating progress bar when playing
        this.audio.addEventListener("timeupdate", (e) => {
        	this.progress.value = this.audio.currentTime;
        });
        // event listener for updating progress bar when playing
        this.audio.addEventListener("durationchange", (e) => {
	        this.progress.min = 0;
	        this.progress.max = this.audio.duration;
        });
        // event listener for updating playing when progress bar value is changed
        this.progress.addEventListener("change", (e) => {
        	this.audio.currentTime = this.progress.value;
        });
        // event listener for updating volume bar when audio volume changes
        this.audio.addEventListener("volumechange", (e) => {
        	this.volume.value = this.audio.volume*100;
        });
        // event listener for updating audio volume when volume bar changes
        this.volume.addEventListener("input", (e) => {
        	this.audio.volume = this.volume.value/100;
        	this.volumeIcon.classList.remove("fa-volume-up");
        	this.volumeIcon.classList.remove("fa-volume");
        	this.volumeIcon.classList.remove("fa-volume-down");
        	this.volumeIcon.classList.remove("fa-volume-off");
        	if (this.audio.volume == 0)
        		this.volumeIcon.classList.add("fa-volume-off");
        	else if (this.audio.volume < .3)
        		this.volumeIcon.classList.add("fa-volume-down");
        	else if (this.audio.volume < .6)
        		this.volumeIcon.classList.add("fa-volume");
        	else
        		this.volumeIcon.classList.add("fa-volume-up");
        });
        
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
        this.progress.value = 0;
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