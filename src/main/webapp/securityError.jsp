<!DOCTYPE html>
<html>
<head>
    <link href="frontend/securityPage.css" rel="stylesheet" type="text/css">
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <title>Error</title>
</head>
<body>
<div class="securityMessage">
    <h2>Попытка входа без авторизации!</h2>
    <h3>Пожалуйста, нажмите на кнопку ниже и войдите в систему!</h3>
    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
        <button class="logOutButton" name="pageName" type="submit" value="logOut">Авторизация</button>
    </form>
</div>
</body>
</html>