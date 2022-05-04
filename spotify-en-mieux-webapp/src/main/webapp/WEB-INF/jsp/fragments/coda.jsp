
            </div>


            <!-- Queue section -->
            <div id="queue">
                <!-- section title -->
                <h2 class="title">Queue</h2>

                <!-- section content -->
                <div class="content list">
                    <div class="queue-drop-position"></div>


                </div>
            </div>


        </div>

        <!-- Controls area --------------------------------------------------->
        <div id="controls">
            <div class="song">
                <a href=""><img class="song-cover" src="" /></a>
                <div class="song-description">
                    <a href="" class="song-title"></a>
                    <a href="" class="song-artist"></a>
                </div>
                <i class="song-like fas fa-heart"></i>
            </div>
            <div id="controls-buttons">
                <button><i class="fas fa-random"></i></button>
                <button><i id="prevButton" class="fas fa-step-backward"></i></button>
                <button><i id="playPauseButton" class="fas fa-play-circle big"></i></button>
                <button><i id="nextButton" class="fas fa-step-forward"></i></button>
                <button><i class="fas fa-redo"></i></button>
            </div>
            <div id="controls-progress">
                <audio></audio>
                <input type="range" value="0"/>
            </div>
            <div id="controls-menu">
                <button id="toggle-queue-button"><i class="fas fa-list"></i></button>
                <button><i class="fas fa-cog"></i></button>
                <button><i id="controls-volume-icon" class="fas fa-fw fa-volume-up"></i></button>
                <input id="controls-volume" type="range" value="100" />
            </div>
        </div>

    </div>


    <div id="templates">

        <article class="song queue-item-template" data-src="" draggable="true">
            <p class="song-number"></p>
            <img class="song-cover" src="" draggable="false"/>
            <div class="song-description">
                <p href="" class="song-title"></p>
                <a href="" class="song-artist" draggable="false"></a>
            </div>
            <a href="" class="song-album" draggable="false">Album</a>
            <p class="song-duration">?:??</p>
            <button class="queue-play"><i class="fas fa-play-circle"></i></button>
            <button class="queue-remove"><i class="fas fa-times-circle"></i></button>
        </article>

    </div>


</body>

</html>