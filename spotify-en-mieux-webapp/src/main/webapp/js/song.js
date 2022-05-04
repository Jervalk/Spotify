
function Song(title, artist, cover, source) {
    this.title = title;
    this.artist = artist;
    this.cover = cover;
    this.source = source;
};

function songFromHtml(element) {
    return new Song(
        element.querySelector(".song-title").textContent.trim(),
        element.querySelector(".song-artist").textContent.trim(),
        element.querySelector(".song-cover").getAttribute("src"),
        element.getAttribute("data-src"));
};

function songToHtml(song, element) {
    element.setAttribute("data-src", song.source);
    // element.querySelector(".song-number").innerHTML = this.songs.length;
    element.querySelector(".song-cover") && element.querySelector(".song-cover").setAttribute("src", song.cover);
    element.querySelector(".song-title") && (element.querySelector(".song-title").innerHTML = song.title);
    element.querySelector(".song-artist") && (element.querySelector(".song-artist").innerHTML = song.artist);
}