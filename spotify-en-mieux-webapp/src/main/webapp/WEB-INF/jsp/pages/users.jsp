
    <link rel="stylesheet" href="css/list.css">
    <script src="js/list.js"></script>

<!-- 
<h2>Users</h2>

<table>
	<tr>
		<th>Username</th>
		<th>Email</th>
		<th>Role</th>
		<th></th>
	</tr>
	<c:forEach items="${users}" var="user">
		<tr>
			<td>${user.username}</td>
			<td>${user.email}</td>
			<td>${user.role}</td>
			<td></td>
		</tr>
	</c:forEach>
</table>
 -->

<h2>Users</h2>
<div class="list">
		<div class="item">
			<div class="item-username"><strong>Username</strong></div>
			<div class="item-email">Email</div>
			<div class="item-role">Role</div>
			<div class="item-actions">Actions</div>
		</div>
	<c:forEach var="user" items="${users}" varStatus="status">
		<div class="item">
			<div class="item-username"><c:out value="${user.username}"></c:out></div>
			<div class="item-email"><c:out value="${user.email}"></c:out></div>
			<div class="item-role">${user.role}</div>
			<div class="item-actions">
				<a href="users/delete?id=${user.id}" data-bs-container="body" data-bs-toggle="popover" data-bs-trigger="manual" data-bs-placement="left" data-bs-content="Click again to remove" data-bs-html="true"><i class="fas fa-user-times fa-fw"></i></a>
				<a href="users/update?id=${user.id}"><i class="fas fa-user-edit fa-fw"></i></a>
			</div>
		</div>
	</c:forEach>
</div>

<a href="users/add"><i class="fas fa-plus"></i></a>

