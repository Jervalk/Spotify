<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<base href="${pageContext.request.contextPath}/" />
    <title>Spotify++</title>
    
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
        integrity="sha512-iBBXm8fW90+nuLcSKlbmrPcLa0OT92xO1BIsZ+ywDWZCvqsWgccV3gFoRBv0z+8dLJgyAHIhR35VZc2oM/gI1w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="js/song.js"></script>
    <script src="js/player.js"></script>
    <script src="js/queue.js"></script>
    <script src="js/script.js"></script>
</head>

<body>

    <div id="container">

        <!-- Menu area -------------------------------------------------------->
        <div  id="menu">
            <div class="menu-title"><i class="fas fa-music"></i> Spotify++</div>
            <p><c:out value="${not empty user ? user.username : 'not connected'}"></c:out></p>
            <div class="menu-items">
                <a href="" class="menu-item selected"><i class="fas fa-home"></i> Home</a>
                <a href="" class="menu-item"><i class="fas fa-search"></i> Search</a>
                <c:if test="${user.role == 'ROLE_ADMIN'}">
                	<a href="users" class="menu-item"><i class="fas fa-users"></i> Users</a>
                </c:if>
                <a href="artists" class="menu-item"><i class="fas fa-users"></i> Artists</a>
                <a href="musics" class="menu-item"><i class="fas fa-music"></i> Musics</a>
                <hr style="width:80%;"/>
                <c:choose>
                	<c:when test="${empty user}">
		                <a href="signup" class="menu-item"><i class="fas fa-user-plus"></i> Sign up</a>
		                <a href="signin" class="menu-item"><i class="fas fa-sign-in-alt"></i> Sign in</a>
                	</c:when>
                	<c:otherwise>
                		<a href="signout" class="menu-item"><i class="fas fa-sign-out-alt"></i> Sign out</a>
                	</c:otherwise>
                </c:choose>
                <hr style="width:80%;"/>
                <a href="" class="menu-item"><i class="fas fa-compact-disc"></i> Your Albums</a>
                <a href="" class="menu-item"><i class="fas fa-music"></i> Your Musics</a>
                <a href="" class="menu-item"><i class="fas fa-book"></i> Your Playlists</a>
            </div>
        </div>

        <!-- Main area ------------------------------------------------------->
        <div id="main">

            <div id="content">