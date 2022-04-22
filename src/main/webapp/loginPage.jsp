<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jspf/taglib.jspf" %>
    <fmt:requestEncoding value="UTF-8"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Факультатив</title>
    <link href="frontend/loginPageCSS.css" rel="stylesheet" type="text/css">
    <script src="frontend/sendFormController.js"></script>
    <head/>
<body style="font-family:Arial, Verdana, sans-serif;color:#72157C">
<c:set var="currentPage" value="loginPage.jsp" scope="session"/>
<form class="loginForm" action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
      method="post">
    <h3 class="loginH3"><fmt:message key="login.header"/></h3>
    <div id="loginInput">
        <label> <fmt:message key="login.login.text"/>(email):</label>
        <input class="inputForm" type="text" id="login" name="user_login" required placeholder="epam@epam.com"
               title="Введите свой email согласно шаблону:'epam@epam.com'">
    </div>

    <div id="passwordInput">
        <label><fmt:message key="login.password.text"/>:</label>
        <input class="inputForm" type="password" id="password" name="user_password" required placeholder="********">
    </div>
    <table>
        <tr>
            <th>
                <button style="font-family:Arial, Verdana, sans-serif;color:#72157C" id="loginButton"
                        name="pageName"
                        value="loginUser" type="submit"><fmt:message key="login.entry.button"/>
                </button>
            </th>
            <th>
                <a id="registerLink" href="registerPage.jsp"><fmt:message key="login.register.link"/></a>
            </th>
        </tr>
    </table>
</form>
<form class="changeLocaleForm" action="changeLocale.jsp" method="post">
    <select class="changeLocaleSelect" name="locale">
        <c:forEach items="${applicationScope.locales}" var="locale">
            <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
            <option value="${locale.key}" ${selected}>${locale.value}</option>
        </c:forEach>
    </select>
    <br>
    <input class="changeLocale" type="submit" value="<fmt:message key='set.language.button'/>">
    <input type="hidden" name="pageName" value="${pageName}">

</form>

<c:choose>
    <c:when test="${not empty loginError}">
        <div class="error">
            <text><fmt:message key="incorrect.password.or.login"/></text>
        </div>

    </c:when>

    <c:when test="${ not empty registerMessage}">
        <div class="accesReg">
            <text><fmt:message key="register.message"/></text>
        </div>

    </c:when>

</c:choose>

</body>