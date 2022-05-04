

	<h2>Update music</h2>
	
	<form action="musics/update?id=${music.id}" method="post" enctype="multipart/form-data">
	
		<%@ include file="../fragments/musicsForm.jsp" %>
		
		<button type="submit">Update</button>
	</form>

