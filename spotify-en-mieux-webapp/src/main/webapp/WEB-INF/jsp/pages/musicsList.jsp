
<link rel="stylesheet" href="css/list.css">
<script src="js/list.js"></script>


<h2>Musics</h2>
<div class="list">
		<div class="item">
			<div class="item-image"><strong>Image</strong></div>
			<div class="item-title"><strong>Title</strong></div>
			<div class="item-artists"><strong>Artists</strong></div>
			<div class="item-albums"><strong>Album</strong></div>
			<div class="item-styles">Styles</div>
			<div class="item-duration">Duration</div>
			<div class="item-actions">Actions</div>
		</div>
	<c:forEach var="music" items="${musics}" varStatus="status">
		<div class="item song" data-src="files/${music.file}">
			<div class="item-image"><img class="song-cover" src="files/${not empty music.albums ? music.albums.toArray()[0].cover : music.artists.toArray()[0].picture}" /></div>
			<div class="item-title song-title"><c:out value="${music.title}"></c:out></div>
			<div class="item-artists song-artist">
				<c:forEach var="a" items="${music.artists}" varStatus="status">
					<a href="artists/${a.id}"><c:out value="${a.name}"></c:out>${not status.last ? ', ' : ''}</a>
				</c:forEach>
			</div>
			<div class="item-albums">
				<c:forEach var="a" items="${music.albums}" varStatus="status">
					<a href="albums/${a.id}"><c:out value="${a.name}"></c:out>${not status.last ? ', ' : ''}</a>
				</c:forEach>
			</div>
			<div class="item-styles">
				<c:forEach var="s" items="${music.styles}" varStatus="status">
					<a href="styles/${s.id}"><c:out value="${s.name}"></c:out>${not status.last ? ', ' : ''}</a>
				</c:forEach>
			</div>
			<div class="item-duration">${music.duration}</div>
			<div class="item-actions">
				<button class="item-action-play queue-insert-play"><i class="fas fa-play fa-fw"></i></button>
				<button class="item-action-play queue-add"><i class="fas fa-plus fa-fw"></i></button>
				<c:if test="${user.role == 'ROLE_ADMIN' or (music.owner != null and music.owner == user)}">
					<a href="musics/delete?id=${music.id}" data-bs-container="body" data-bs-toggle="popover" data-bs-trigger="manual" data-bs-placement="left" data-bs-content="Click again to remove"><i class="fas fa-times fa-fw"></i></a>
					<a href="musics/update?id=${music.id}"><i class="fas fa-edit fa-fw"></i></a>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>

<c:if test="${user.role == 'ROLE_ADMIN' or user.role == 'ROLE_PRODUCER'}">
	<a href="musics/add"><i class="fas fa-plus"></i></a>
</c:if>

