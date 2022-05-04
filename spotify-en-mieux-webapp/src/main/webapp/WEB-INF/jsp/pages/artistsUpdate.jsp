

	<h2>Update artist</h2>
	
	<form action="artists/update?id=${artist.id}" method="post" enctype="multipart/form-data">
	
		<%@ include file="../fragments/artistsForm.jsp" %>
		
		<button type="submit">Update</button>
	</form>

