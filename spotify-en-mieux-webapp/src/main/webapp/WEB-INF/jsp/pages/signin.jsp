

	<h2>Signin</h2>
	
	<form action="signin" method="post">
	
		<label for="username">Username: </label>
		<input id="username" name="username" type="text" value="${username}" />
		<br />

		<label for="password">Password: </label>
		<input id="password" name="password" type="password" value="${password}" />
		<br />

		
		<c:forEach items="${errors}" var="e">
			<p class="error">${e}</p>
		</c:forEach>
		
		<button type="submit">Sign in !</button>
	</form>

