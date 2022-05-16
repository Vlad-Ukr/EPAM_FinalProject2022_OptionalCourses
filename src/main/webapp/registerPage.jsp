<!DOCTYPE html>
<html>
<head>
    <%@ include file="/jspf/taglib.jspf" %>
        <%@ page contentType="text/html; charset=UTF-8" %>
        <fmt:requestEncoding value="UTF-8"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Registration</title>
    <link href="frontend/registerPageCSS.css" rel="stylesheet" type="text/css">
    <script src="frontend/confirmPassword/confirmPassword.js"></script>
    <head/>
<body style="font-family:Arial, Verdana, sans-serif;color:#72157C">
<c:set var="currentPage" value="registerPage.jsp" scope="session"/>
<form class="registerForm" action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
      method="post">
    <h3 class="registerH3"><fmt:message key="login.register.link"/></h3>

    <div id="emailInput">
        <label>Email:</label>
        <input class="inputForm" type="email" id="email" name="user_email" required placeholder="epam@email.com"
               pattern="^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
               title="Введите свой email согласно шаблону:'epam@epam.com'">
    </div>
    <div id="fullNameInput">
        <label><fmt:message key="register.table.SNP"/>:</label>
        <input class="inputForm" type="text" id="fullname" name="user_fullname" required
               placeholder="Иванов Иван Иваныч"
               pattern="^[А-ЯЁЇ][а-яёї]{1,}([-][А-ЯЁї][а-яёї]{1,})?\s[А-ЯЁЇ][а-яёї]{2,}\s[А-ЯЁЇ][а-яёї]{1,}$"
               title="Введите свой email согласно шаблону: 'Иванов Иван Иваныч'">
    </div>
    <div id="phoneNumber">
        <label><fmt:message key="register.phone.number"/>:</label>
        <input class="inputForm" type="text" id="phone_number" name="phone_number" required placeholder="+380*********"
               pattern="\+\d{12}"
               title="Введите свой номер телефона согласно шаблону: '+380111111111'">
    </div>

    <div id="passwordInput">
        <label><fmt:message key="login.password.text"/>:</label>
        <input class="inputForm" type="password" id="password" name="user_password" required placeholder="********"
               minlength="8" onkeyup='check();'
               required
               pattern="(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}"
               title="Пароль должен содержать хотя бы одну цифру,
                                  специальный символ;
                                  заглавную латинскую букву;
                                  и должен состоять как минимум из 8 вышеупомянутых символов.">
        <input class="inputForm" type="hidden" name="pageName" value="insertUser"/>
    </div>
    <div id="confirmPassword">
        <label><fmt:message key="register.table.repeat.table"/>:</label>
        <input class="inputForm" type="password" id="confirm_password" name="confirm_password" required
               placeholder="********"
               title="Повторите пароль" onkeyup='check();'>
    </div>
    <span id='message'></span>
    <table>
        <tr>
            <th>
                <button style="font-family:Arial, Verdana, sans-serif;color:#72157C"
                        id="registerButton" type="submit" name="pageName" value="insertUser" onkeyup='check();'>
                    <fmt:message key="login.register.link"/>
                </button>
            </th>
            <th>
                <a id="loginLink" href="loginPage.jsp"><fmt:message key="security.link"/></a>
            </th>
        </tr>
    </table>
</form>
<c:choose>
    <c:when test="${not empty deniedRegister}">
        <div class="deniedRegisterM">
            <text><fmt:message key="user.already.exists"/></text>
        </div>

    </c:when>
</c:choose>
<div title="We stand with Ukraine" id="standWithUA"></div>
</body>