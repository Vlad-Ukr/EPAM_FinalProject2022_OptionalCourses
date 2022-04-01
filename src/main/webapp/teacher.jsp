<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher</title>
    <link href="frontend/teacher.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="pageRole" scope="request" value="teacher"/>
<tags:security>security</tags:security>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader">Добрый день,${sessionScope.userLogin}</h4>
    </div>
    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
        <div class="bottomBar">
            <button class="homePage" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                    formmethod="get" name="pageName" type="submit" value="homePage">Личный кабинет
            </button>
            <button class="showCoursesButton" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                    formmethod="get" name="pageName" type="submit" value="showCourses">Журнал
            </button>
            <input name="userRole" value="teacher" type="hidden">
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
<c:set var="coursePage" scope="request" value="showCourses"/>
<c:set var="homePage" scope="request" value="showHome"/>
<c:set var="journalPage" scope="request" value="showJournal"/>
<c:choose>
<c:when test="${pageName==mainPage}">
    <div class="instruction">
        <h3>Добро пожаловать, ${sessionScope.userLogin}!</h3>
        <text>Для начала работы кликните на нужную вам кнопку, находящиеся в верхней панели управления</text>
    </div>
</c:when>
<c:when test="${pageName==homePage}">
<div class="homePageDiv">
    <table class="infoTable">
        <th class="studentInfoTh">
            <div class="Info">
                <h3>Email: ${sessionScope.teacher.login}</h3>
                <h3>ФИО: ${sessionScope.teacher.fullName}</h3>
            </div>
        </th>

        <th class="infoTh">
            <div class="studentInfo">
                <h3>Ваши курсы</h3>
                <table>
                    <th>Название</th>
                    <th>Кол. студентов</th>
                    <th>Длительность</th>
                    <th>Тема</th>
                    <c:forEach var="course" items="${requestScope.teacherCourses}">
                    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                        <tr>
                            <td class="courseTableTd"><input class="dataInput" name="courseName"
                                                             value="${course.name}"
                                                             required readonly="readonly"></td>
                            <td class="courseTableTd"><input class="numberInput" name="studentAmount"
                                                             value="${course.amountOfStudent}"
                                                             readonly="readonly"></td>
                            <td class="courseTableTd"><input class="numberInput" name="courseDuration"
                                                             value="${course.duration}" required min="1"
                                                             readonly="readonly"></td>
                            <td class="courseTableTd"><input class="dataInput" name="courseTopic"
                                                             value="${course.topic}"
                                                             required readonly="readonly"></td>
                            <td class="courseTableTd"><input class="dataInput" name="courseStatus"
                                                             value="${course.status}"
                                                             required readonly="readonly"></td>
                            <input name="courseId" value="${course.id}" type="hidden"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><c:if test="${course.status=='Не начался'}">
                                <c:if test="${course.amountOfStudent>0}">
                                    <button class="showCoursesButton" name="pageName" type="submit" value="startCourse">
                                        Начать курс
                                    </button>
                                </c:if>
                            </c:if>
                                <c:if test="${course.status=='В процессе'}">
                                    <button class="showCoursesButton" name="pageName" type="submit" value="showJournal">
                                        Закончить
                                    </button>
                                    <input name="userRole" value="teacher" type="hidden">
                                </c:if></td>
                        </tr>
                    </form>
                    </c:forEach>
            </div>
        </th>
    </table>
    </c:when>
    <c:when test="${pageName==coursePage}">
    <div class="workSpace">
        <h4>Выберете курс, которого хотите псомотреть журнал</h4>
        <c:forEach var="course" items="${requestScope.courseList}">
            <div class="courseSelectButton">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                    <button class="showCoursesButton" name="pageName" type="submit" value="showJournal">${course.name}
                    </button>
                    <input name="courseId" value="${course.id}" type="hidden"></td>
                    <input name="courseStatus" value="${course.status}" type="hidden"></td>
                </form>
            </div>
        </c:forEach>
    </div>
    <c:if test="${not empty endMessage}">
    <div class="endMessage">
            ${endMessage}
    </div>
    </c:if>
    </c:when>
    <c:when test="${pageName==journalPage}">
    <div class="selectCourseJournal">
        <form class="studentInfoForm" action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
            <table class="studentInfo">
                <caption>Студенты которые записаны на этом курсе</caption>
                <c:choose>
                    <c:when test="${requestScope.course.status=='В процессе'}">
                        <c:if test="${ empty sessionScope.students}">
                            <td>
                                <h4>Список пуст</h4>
                            </td>
                        </c:if>
                        <c:forEach var="student" items="${sessionScope.students}">
                            <tr>
                                <td><input class="dataInput" name="studentFullName" value="${student.fullName}"
                                           readonly="readonly"></td>
                                <td><input name="dataInput" value="${requestScope.course.id}"
                                           type="hidden"></td>
                                <c:choose>
                                    <c:when test="${student.firstCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="firstCourseMark${student.id}"
                                                   value="${student.firstCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                    <c:when test="${student.secondCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="secondCourseMark${student.id}"
                                                   value="${student.secondCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                    <c:when test="${student.thirdCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="thirdCourseMark${student.id}"
                                                   value="${student.thirdCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </c:forEach>

                        <td>
                            <c:if test="${ not empty sessionScope.students}">
                                <table>
                                    <tr>
                                        <td>
                                                <button class="gradeButton" name="pageName" type="submit"
                                                        value="gradingStudents">
                                                    Выставить оценки
                                                </button>
                                        </td>
                                        <td>
                                                <button class="gradeButton" name="pageName" type="submit"
                                                        value="endCourse">
                                                    Закончить курс
                                                </button>
                                                <input name="endCourseMarker" value="true" type="hidden">
                                            <input name="courseId" value="${requestScope.course.id}" type="hidden">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <button class="goBackButton" name="pageName" type="submit"
                                                    value="showCourses">Вернуться
                                            </button>
                                        </td>
                                    </tr>
                                </table>
                                <input name="courseTeacherName" value="${sessionScope.teacher.fullName}" type="hidden">
                            </c:if>

                        </td>
                        <input name="courseId" value="${requestScope.course.id}" type="hidden"></td>
                        <input name="courseName" value="${requestScope.course.name}" type="hidden">
                        <input name="courseTopic" value="${requestScope.course.topic}" type="hidden">
                        <input name="courseDuration" value="${requestScope.course.duration}" type="hidden">
                        <input name="userRole" value="teacher" type="hidden">
                    </c:when>
                    <c:when test="${requestScope.course.status=='Закончен'}">
                        <c:if test="${ empty sessionScope.finishedCourses}">
                            <td>
                                <h4>Список пуст</h4>
                            </td>
                        </c:if>
                        <c:forEach var="finishedCourses" items="${sessionScope.finishedCourses}">
                            <tr>
                                <td><input class="dataInput" name="studentFullName"
                                           value="${finishedCourses.studentFullName}"
                                           readonly="readonly"></td>
                                <td><input class="numberInput" name="courseMark"
                                           value="${finishedCourses.mark}"
                                           readonly="readonly" type="number" min="0" max="100"></td>

                            </tr>
                        </c:forEach>
                        <td>
                            <button class="goBackButton" name="pageName" type="submit" value="showCourses">Вернуться
                            </button>
                        </td>
                        <input name="userRole" value="teacher" type="hidden">
                    </c:when>
                    <c:when test="${requestScope.course.status=='Не начался'}">
                        <c:if test="${ empty sessionScope.students}">
                            <td>
                                <h4>Список пуст</h4>
                            </td>
                        </c:if>
                        <c:forEach var="student" items="${sessionScope.students}">
                            <tr>
                                <td><input class="dataInput" name="studentFullName" value="${student.fullName}"
                                           readonly="readonly"></td>
                                <td><input name="dataInput" value="${requestScope.course.id}"
                                           type="hidden"></td>
                                <c:choose>
                                    <c:when test="${student.firstCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="firstCourseMark${student.id}"
                                                   value="${student.firstCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                    <c:when test="${student.secondCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="secondCourseMark${student.id}"
                                                   value="${student.secondCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                    <c:when test="${student.thirdCourseId==requestScope.course.id}">
                                        <td><input class="numberInput" name="thirdCourseMark${student.id}"
                                                   value="${student.thirdCourseMark}"
                                                   type="number" min="0" max="100"></td>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        <td>
                            <button class="goBackButton" name="pageName" type="submit" value="showCourses">Вернуться
                            </button>
                        </td>
                        <input name="userRole" value="teacher" type="hidden">
                    </c:when>
                </c:choose>
            </table>
        </form>
    </div>
    </c:when>
    </c:choose>
</body>
