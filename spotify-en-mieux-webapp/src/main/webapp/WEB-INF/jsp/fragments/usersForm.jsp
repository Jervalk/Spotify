
<label for="username">Username: </label>
<input id="username" name="username" type="text" value="${editedUser.username}" />
<br />
<c:forEach items="${errors.username}" var="e">
	<p class="error">${e}</p>
</c:forEach>
<label for="password">Password: </label>
<input id="password" name="password" type="password" value="${editedUser.password}" />
<br />
<c:forEach items="${errors.password}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="email">Email: </label>
<input id="email" name="email" type="email" value="${editedUser.email}" />
<br />
<c:forEach items="${errors.email}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="role">Role: </label>
<select id="role" name="role">
	<option value="ROLE_USER" ${editedUser.role == "ROLE_USER" ? "selected" : ""}>User</option>
	<option value="ROLE_PRODUCER" ${editedUser.role == "ROLE_PRODUCER" ? "selected" : ""}>Producer</option>
	<option value="ROLE_ADMIN" ${editedUser.role == "ROLE_ADMIN" ? "selected" : ""}>Admin</option>
</select>
<br />
<c:forEach items="${errors.role}" var="e">
	<p class="error">${e}</p>
</c:forEach>
