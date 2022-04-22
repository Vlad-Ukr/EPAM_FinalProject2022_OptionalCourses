<!DOCTYPE html>
<html>
<head>
    <link href="frontend/securityPage.css" rel="stylesheet" type="text/css">
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="/jspf/taglib.jspf" %>
    <title>Error</title>
</head>
<body>
<div class="securityMessage">
    <h2><fmt:message key="security.header"/></h2>
    <h3><fmt:message key="security.message"/></h3>
    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
        <button class="logOutButton" name="pageName" type="submit" value="logOut"><fmt:message key="security.link"/></button>
    </form>
</div>
</body>
</html>