<!DOCTYPE html>
<html>
<head>
    <title>Admin</title>
    <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
    <fmt:requestEncoding value="UTF-8"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="frontend/adminPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/courseWorkPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/teacherWorkPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/studentWorkPage.css" rel="stylesheet" type="text/css">
    <script src="frontend/sendFormController.js"></script>
    <script src="frontend/confirmPassword.js"></script>
</head>
<body>
<c:set var="pageRole" scope="request" value="admin"/>
<tags:security>security</tags:security>
<tags:pagination>pagination</tags:pagination>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader">Добрый день,${sessionScope.userLogin}</h4>
    </div>
    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()" method="get">
        <div class="bottomBar">
            <button class="courseWorkButton" name="pageName" type="submit" value="showCourses">Работа с курсами
            </button>
            <button class="teacherWorkButton" name="pageName" type="submit" value="showTeachers">Работа с
                преподователями
            </button>
            <button class="studentWorkButton" name="pageName" type="submit" value="showStudents">Работа с студентами
            </button>
            <input name="userRole" value="admin" type="hidden"></td>
            <select class="languageSelect" size="1">
                <option disabled>Выберите язык</option>
                <option value="russian">Русский</option>
                <option value="ukrainian">Українська</option>
                <option value="english">English</option>
            </select>
            <button class="logOutButton" name="pageName" type="submit" value="logOut">Выйти</button>
        </div>
    </form>
</div>
</div>
<c:set var="mainPage" scope="request" value="instruction"/>
<c:set var="studentPage" scope="request" value="showStudents"/>
<c:set var="teacherPage" scope="request" value="showTeachers"/>
<c:set var="coursePage" scope="request" value="showCourses"/>
<c:choose>
    <c:when test="${pageName==mainPage}">
        <div class="instruction">
            <h3>Добро пожаловать, ${sessionScope.userLogin}!</h3>
            <text>Для начала работы кликните на нужную вам кнопку, находящиеся в верхней панели управления</text>
        </div>
    </c:when>
    <c:when test="${pageName==studentPage}">
        <div class="workSpace">
            <table class="workSpaceTable" id="table-id">
                <tr>
                    <th>Id</th>
                    <th>Логин</th>
                    <th>ФИО</th>
                    <th>Статус</th>
                    <th>Курсы</th>
                    <th>
                        <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                              method="get">
                            <select class="selectBy" name="courseSelect" size="1">
                                <option disabled>Выберите Курс</option>
                                <option value="allCourses">Все курсы</option>
                                <c:forEach var="course" items="${requestScope.courseList}">
                                    <option value="${course.id}">${course.name}</option>
                                </c:forEach>
                            </select>
                            <br>
                            <button class="confirmSelectButton" name="pageName" type="submit" value="selectStudents">
                                Выбрать
                            </button>
                        </form>
                    </th>
                </tr>
                <c:forEach var="student" items="${requestScope.studentList}">
                    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                          method="get">
                        <tr>
                            <td><input class="inputId" name="studentId" value="${student.id}" readonly="readonly"></td>
                            <td><input class="inputLogin" name="studentLogin" value="${student.login}"
                                       readonly="readonly"></td>
                            <td><input class="fullNameInput" name="studentFullName" value="${student.fullName}"
                                       readonly="readonly"></td>
                            <td><input class="inputStatus" name="studentStatusId" value="${student.status}"
                                       readonly="readonly">
                            </td>
                            <td>
                                <details>
                                    <summary>Записан на курсы</summary>
                                    <c:forEach var="course" items="${requestScope.courseList}">
                                        <c:if test="${student.firstCourseId==course.id||student.secondCourseId==course.id||student.thirdCourseId==course.id}">
                                            ${course.name}
                                            <br>
                                        </c:if>
                                    </c:forEach>
                                </details>
                            </td>
                            <td>
                                <button class="unBlockButton" name="pageName" type="submit" value="unBlock">
                                    Разблокировать
                                </button>
                            </td>
                            <td>
                                <button class="blockButton" name="pageName" type="submit" value="block">Заблокировать
                                </button>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </table>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
    <c:when test="${pageName==teacherPage}">
        <div class="workSpace">
            <table class="workSpaceTable">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                      method="post">

                    <td>
                        <div id="emailInput">
                            <label>Email:</label>
                            <input class="inputForm" type="email" id="email" name="user_email" required
                                   placeholder="epam@email.com"
                                   pattern="^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
                                   title="Введите свой email согласно шаблону:'epam@epam.com'">
                        </div>
                    </td>
                    <td>
                        <div id="fullNameInput">
                            <label>ФИО:</label>
                            <input class="inputForm" type="text" id="fullname" name="user_fullname" required
                                   placeholder="Фамилия Имя Отчество"
                                   pattern="^[А-ЯЁЇ][а-яёї]{1,}([-][А-ЯЁї][а-яёї]{1,})?\s[А-ЯЁЇ][а-яёї]{2,}\s[А-ЯЁЇ][а-яёї]{1,}$"
                                   title="Введите свой email согласно шаблону: 'Иванов Иван Иваныч'">
                        </div>
                    </td>
                    <tr>
                        <td>
                            <div id="passwordInput">
                                <label>Пароль:</label>
                                <input class="inputForm" type="password" id="password" name="user_password" required
                                       placeholder="********" minlength="8" onkeyup=check();
                                       pattern="(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}"
                                       title="Пароль должен содержать хотя бы одну цифру,
                                  специальный символ;
                                  заглавную латинскую букву;
                                  и должен состоять как минимум из 8 вышеупомянутых символов.">
                                <input class="inputForm" type="hidden" name="pageName" value="insertTeacher"/>
                            </div>
                        </td>
                        <td>
                            <div id="confirmPassword">
                                <label>Подтвердите пароль:</label>
                                <input class="inputForm" type="password" id="confirm_password" name="confirm_password"
                                       required
                                       placeholder="********"
                                       title="Повторите пароль" onkeyup=check();>
                            </div>
                            <span id='message'></span>
                        </td>
                        <td>
                            <button style="font-family:Arial, Verdana, sans-serif;color:#72157C"
                                    id="registerButton" type="submit" name="pageName" value="insertTeacher"
                                    onkeyup=check()>
                                Зарегестрировать
                            </button>
                        </td>
                </form>
            </table>
            <c:choose>
                <c:when test="${not empty registerMessage}">
                    <div class="accesReg">
                            ${registerMessage}
                    </div>

                </c:when>

            </c:choose>
            <c:choose>
                <c:when test="${ not empty deniedRegister}">
                    <div class="deniedRegisterM">
                            ${deniedRegister}
                    </div>

                </c:when>

            </c:choose>
            <table class="teacherInfoTable" id="table-id">
                <tr>
                    <th>Id</th>
                    <th>Логин</th>
                    <th>ФИО</th>
                    <th>Курсы</th>
                </tr>
                <c:forEach var="teacher" items="${requestScope.teacherList}">
                    <tr>
                        <td class="teacherTableTd"><input class="dataInput" id="inputTeacherId" name="teacherId"
                                                          value="${teacher.id}" readonly="readonly"></td>
                        <td class="teacherTableTd"><input class="dataInput" id="inputTeacherLogin" name="teacherLogin"
                                                          value="${teacher.login}" readonly="readonly"></td>
                        <td class="teacherTableTd"><input class="dataInput" id="inputTeacherFullName"
                                                          name="teacherFullName"
                                                          value="${teacher.fullName}" readonly="readonly"></td>
                        <td>
                            <details>
                                <summary>Закрепленные курсы</summary>
                                <c:forEach var="course" items="${requestScope.courseList}">
                                    <c:if test="${course.teacherId==teacher.id}">
                                        ${course.name}
                                        <br>
                                    </c:if>
                                </c:forEach>
                            </details>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
    <c:when test="${pageName==coursePage}">
        <div class="workSpace">
            <table class="workSpaceTable">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                      method="get">
                    <td>
                        <div id="nameInput">
                            <label>Название:</label>
                            <input class="inputForm" type="text" id="name" name="courseName" required>
                        </div>
                    <td>
                        <div id="durationInput">
                            <label>Длительность в часах:</label>
                            <input class="inputForm" type="number" id="duration" name="courseDuration" required
                                   value="1"
                                   min="1" required>
                        </div>
                    </td>
                    <td>
                        <div id="maxAmountInput">
                            <label>Количество студентов:</label>
                            <input class="inputForm" type="number" id="maxAmount" name="maxAmount" required value="1"
                                   min="1" required>
                            <input class="inputForm" type="hidden" name="pageName" value="insertCourse"/>
                        </div>
                    </td>
                    <td>
                        <div id="topicInput">
                            <label>Тема:</label>
                            <input class="inputForm" type="text" id="topic" name="topic" required>
                        </div>
                    </td>
                    </td>
                    <td>
                        <label>Преподаватель:</label>
                        <select class="teacherSelect" name="teacherSelect" size="1" required>
                            <option disabled>Выберите преподавателя</option>
                            <c:forEach var="teacher" items="${requestScope.teacherList}">
                                <option value="${teacher.id}">${teacher.fullName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button style="font-family:Arial, Verdana, sans-serif;color:#72157C"
                                id="addButton" type="submit" name="pageName" value="insertCourse">
                            Добавить
                        </button>
                    </td>
                </form>
            </table>
            <c:choose>
                <c:when test="${not empty registerMessage}">
                    <div class="accesReg">
                            ${registerMessage}
                    </div>

                </c:when>

            </c:choose>
            <c:choose>
                <c:when test="${ not empty deniedRegister}">
                    <div class="deniedRegisterM">
                            ${deniedRegister}
                    </div>

                </c:when>

            </c:choose>
            <table class="courseInfoTable" id="table-id">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                      method="get">
                    <tr class="courseTr">
                        <th class="courseTh">Id</th>
                        <th class="courseTh">Название</th>
                        <th class="courseTh">Длительность</th>
                        <th class="courseTh">Количество студентов</th>
                        <th class="courseTh">Макс. кол. мест</th>
                        <th class="courseTh">Тема</th>
                        <th class="courseTh">Перподаватель</th>
                        <th class="courseTh">Статус</th>
                        <td class="emptyTd">
                            <input name="studentPageMarker" value="false" type="hidden">
                            <select class="sortSelect" name="sortSelect" size="1" required>
                                <option disabled>Выберите сортировку</option>
                                <option value="byNameA-Z">По алфавиту(а-я)</option>
                                <option value="byNameZ-A">По алфавиту(я-а)</option>
                                <option value="byDuration">По длительности</option>
                                <option value="byStudents">По кол. студентов</option>
                            </select>
                            <button class="sortButton" name="pageName" type="submit" value="sortCourse">Сортирвать
                            </button>
                            <input name="userRole" value="admin" type="hidden">
                        </td>
                    </tr>
                    <tbody>
                    <c:forEach var="course" items="${requestScope.courseList}">
                        <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                              onsubmit="return doSubmit()"
                              method="get">
                            <tr class="courseInfoTr">
                                <td class="courseTableTd"><input class="numberInput" name="courseId"
                                                                 value="${course.id}"
                                                                 readonly="readonly"></td>
                                <td class="courseTableTd"><input class="dataInput" name="courseName"
                                                                 value="${course.name}"
                                                                 required></td>
                                <td class="courseTableTd"><input class="numberInput" name="courseDuration"
                                                                 value="${course.duration}" required min="1"></td>
                                <td class="courseTableTd"><input class="numberInput" name="courseAmountOfStudent"
                                                                 value="${course.amountOfStudent}"
                                                                 readonly="readonly">
                                </td>
                                <td class="courseTableTd"><input class="numberInput" name="courseMaxAmountOfStudent"
                                                                 value="${course.maxAmountOfStudent}" required
                                                                 min="1">
                                </td>
                                <td class="courseTableTd"><input class="dataInput" name="courseTopic"
                                                                 value="${course.topic}"
                                                                 required></td>
                                <td class="courseTableTd"><input class="dataInput" name="courseTeacherId"
                                                                 value="${course.teacherFullName}"
                                                                 readonly="readonly"></td>
                                <td class="courseTableTd"><input class="dataInput" id="statusInput"
                                                                 name="courseStatus"
                                                                 value="${course.status}"
                                                                 readonly="readonly"></td>
                            </tr>
                            <tr class="courseInfoTr">
                                <td class="emptyTd"></td>
                                <td class="emptyTd"></td>
                                <td class="emptyTd"></td>
                                <td class="emptyTd"></td>
                                <td class="emptyTd"></td>
                                <td class="emptyTd"></td>
                                <td>
                                    <button class="redactButton" name="pageName" type="submit" value="updateCourse">
                                        Редактировать
                                    </button>
                                </td>
                                <td>
                                    <button class="deleteButton" name="pageName" type="submit" value="deleteCourse">
                                        Удалить
                                    </button>
                                </td>
                            </tr>
                            <input name="userRole" value="admin" type="hidden"></td>
                        </form>
                    </c:forEach>
                    </tbody>
                </form>
            </table>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
</c:choose>
</body>
</html>
