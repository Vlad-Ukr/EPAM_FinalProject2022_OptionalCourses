
<!DOCTYPE html>
<html>
<head>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <fmt:requestEncoding value="UTF-8"/>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Факультатив</title>
    <link href="frontend/loginPageCSS.css" rel="stylesheet" type="text/css">
    <script src="frontend/sendFormController.js"></script>
    <head/>
<body style="font-family:Arial, Verdana, sans-serif;color:#72157C">
<form class="loginForm" action="/OptionalCoursesFP_war_exploded/dispatcher-servlet"  onsubmit="return doSubmit()" method="post">
    <h3 class="loginH3">Авторизация</h3>
    <div id="loginInput">
        <label> Логин(email):</label>
        <input class="inputForm" type="text" id="login" name="user_login" required placeholder="epam@epam.com"
               title="Введите свой email согласно шаблону:'epam@epam.com'">
    </div>

    <div id="passwordInput">
        <label>Пароль:</label>
        <input class="inputForm" type="password" id="password" name="user_password" required placeholder="********">
    </div>
    <table>
        <tr>
            <th>
                <button style="font-family:Arial, Verdana, sans-serif;color:#72157C" id="loginButton" name="pageName"
                        value="loginUser" type="submit">Войти
                </button>
            </th>
            <th>
                <a id="registerLink" href="registerPage.jsp">Регистрация</a>
            </th>
        </tr>
    </table>
</form>
<c:choose>
    <c:when test="${not empty loginError}">
        <div class="error">
                ${loginError}
        </div>

    </c:when>

    <c:when test="${ not empty registerMessage}">
        <div class="accesReg">
                ${registerMessage}
        </div>

    </c:when>

</c:choose>

</body>