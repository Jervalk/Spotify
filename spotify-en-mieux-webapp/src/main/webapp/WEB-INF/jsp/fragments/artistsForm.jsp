

<script src="js/artistsForm.js"></script>


<label for="name">Name: </label>
<input id="name" name="name" type="text" value="${artist.name}" />
<br/>

<label for="picture">Picture: </label>
<input id="picture" name="picture" type="file" value="${artist.picture}" />
<br/>

<label for="type">Type: </label>
<input type="radio" id="type_person" name="type" ${(empty artist || artist['class'].simpleName == "Artist") ? "checked" : ""} value="PERSON"><label for="type_person">Person</label> 
<input type="radio" id="type_group" name="type" ${artist['class'].simpleName == "Group" ? "checked" : ""} value="GROUP"><label for="type_group">Group</label>
<br/>  

<label for="members">Members: </label>
<select id="members" name="members" multiple="multiple">
	<c:forEach items="${artists}" var="a">
		<option value="${a.id}" ${artist['class'].simpleName == "Group" && artist.members.contains(a) ? "selected" : ""}>${a.name}</option>
	</c:forEach>
</select>
<br/>  


<label for="groups">Groups: </label>
<select id="groups" name="groups" multiple="multiple">
	<c:forEach items="${groups}" var="g">
		<option value="${g.id}" ${artist.groups.contains(g) ? "selected" : ""}>${g.name}</option>
	</c:forEach>
</select>
<br/>  