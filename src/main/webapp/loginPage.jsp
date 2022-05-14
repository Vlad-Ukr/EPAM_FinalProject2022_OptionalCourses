<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jspf/taglib.jspf" %>
    <fmt:requestEncoding value="UTF-8"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Logging</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/frontend/loginPageCSS.css" >
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
    <button class="changeLocaleButton" value="eu" name="locale">Eu</button>
    <button class="changeLocaleButton" value="ru" name="locale">Ru</button>
    <button class="changeLocaleButton" value="ua" name="locale">Ua</button>
    <br>
    <input type="hidden" name="pageName" value="${pageName}">
</form>
<div title="We stand with Ukraine" id="standWithUA"></div>
<c:choose>
    <c:when test="${not empty loginError}">
        <div class="error">
            <text><fmt:message key="incorrect.password.or.login"/></text>
        </div>

    </c:when>

    <c:when test="${not empty sessionScope.registerMessage}">
        <div class="accesReg">
            <text><fmt:message key="register.message"/></text>
        </div>

    </c:when>

</c:choose>

</body>