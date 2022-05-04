

<label for="title">Title: </label>
<input id="title" name="title" type="text" value="${music.title}" />
<br/>
<c:forEach items="${errors.title}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="file">File: </label>
<input id="file" name="file" type="file" value="${music.file}" />
<br/>
<c:forEach items="${errors.file}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="artists">Artists: </label>
<select id="artists" name="artists" multiple="multiple">
	<c:forEach items="${artists}" var="a">
		<option value="${a.id}" ${music.artists.contains(a) ? "selected" : ""}>${a.name}</option>
	</c:forEach>
</select>
<br/>  
<c:forEach items="${errors.artists}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="albums">Albums: </label>
<select id="albums" name="albums" multiple="multiple">
	<c:forEach items="${albums}" var="a">
		<option value="${a.id}" ${music.albums.contains(a) ? "selected" : ""}>${a.name}</option>
	</c:forEach>
</select>
<br/>  
<c:forEach items="${errors.albums}" var="e">
	<p class="error">${e}</p>
</c:forEach>

<label for="styles">Styles: </label>
<select id="styles" name="styles" multiple="multiple">
	<c:forEach items="${styles}" var="a">
		<option value="${s.id}" ${music.styles.contains(s) ? "selected" : ""}>${s.name}</option>
	</c:forEach>
</select>
<br/>  
<c:forEach items="${errors.styles}" var="e">
	<p class="error">${e}</p>
</c:forEach>
