
<link rel="stylesheet" href="css/list.css">
<script src="js/list.js"></script>


<h2>Artists</h2>
<div class="list">
		<div class="item">
			<div class="item-name"><strong>Name</strong></div>
			<div class="item-type">Type</div>
			<div class="item-members">Members</div>
			<div class="item-actions">Actions</div>
		</div>
	<c:forEach var="artist" items="${artists}" varStatus="status">
		<div class="item">
			<div class="item-picture"><img alt="" src="files/${artist.picture}"/></div>
			<div class="item-name"><c:out value="${artist.name}"></c:out></div>
			<div class="item-type"><c:out value="${artist['class'].simpleName}"></c:out></div>
			<div class="item-members">
				<c:choose>
					<c:when test="${artist['class'].simpleName == 'Group'}">
						<c:forEach items="${artist.members}" var="m" varStatus="status">
							<c:out value="${m.name}"></c:out>${!status.last ? ', ' : '' }
						</c:forEach>
					</c:when>
					<c:otherwise>
						-
					</c:otherwise>
				</c:choose>
			</div>
			<div class="item-actions">
				<c:if test="${user.role == 'ROLE_ADMIN' or (artist.owner != null and artist.owner == user)}">
					<a href="artists/delete?id=${artist.id}" data-bs-container="body" data-bs-toggle="popover" data-bs-trigger="manual" data-bs-placement="left" data-bs-content="Click again to remove" data-bs-html="true"><i class="fas fa-user-times fa-fw"></i></a>
					<a href="artists/update?id=${artist.id}"><i class="fas fa-user-edit fa-fw"></i></a>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>

<c:if test="${user.role == 'ROLE_ADMIN' or user.role == 'ROLE_PRODUCER'}">
	<a href="artists/add"><i class="fas fa-plus"></i></a>
</c:if>

