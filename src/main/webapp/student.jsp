<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student</title>
    <link href="frontend/student.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="pageRole" scope="request" value="student"/>
<tags:security>security</tags:security>
<tags:pagination>pagination</tags:pagination>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader">Добрый день,${sessionScope.userLogin}</h4>
    </div>
    <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
        <div class="bottomBar">
            <button class="homePage" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                    formmethod="get" name="pageName" type="submit" value="homePage">Личный кабинет
            </button>
            <button class="showCoursesButton" name="pageName" type="submit" value="showCoursesStudent">Все курсы
            </button>
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
<c:choose>
    <c:when test="${pageName==mainPage}">
        <div class="instruction">
            <h3>Добро пожаловать, ${sessionScope.userLogin}!</h3>
            <text>Для начала работы кликните на нужную вам кнопку, находящиеся в верхней панели управления</text>
        </div>
    </c:when>
    <c:when test="${pageName==coursePage}">
        <div class="workSpace">
            <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                <input name="studentPageMarker" value="true" type="hidden">
                <button class="showAllCoursesButton" name="pageName" type="submit" value="showCoursesStudent">Список
                    всех
                    курсов
                </button>
                <select class="selectBy" name="teacherSelect" size="1">
                    <option disabled>Выберите преподавателя</option>
                    <option value="0">Все учителя</option>
                    <c:forEach var="teacher" items="${sessionScope.teacherList}">
                        <option value="${teacher.id}">${teacher.fullName}</option>
                    </c:forEach>
                </select>
                <select class="selectBy" name="topicSelect" size="1">
                    <option disabled>Выберите тему</option>
                    <option value="allTopics">Все темы</option>
                    <c:forEach var="topic" items="${sessionScope.topicList}">
                        <option value="${topic}">${topic}</option>
                    </c:forEach>
                </select>

                <button class="confirmSelectButton" name="pageName" type="submit" value="selectCourses">Выбрать</button>

                <select class="sortSelect" name="sortSelect" size="1" required>
                    <option disabled>Выберите сортировку</option>
                    <option value="byNameA-Z">По алфавиту(а-я)</option>
                    <option value="byNameZ-A">По алфавиту(я-а)</option>
                    <option value="byDuration">По длительности</option>
                    <option value="byStudents">По кол. студентов</option>
                </select>
                <button class="sortButton" name="pageName" type="submit" value="sortCourse">Сортирвать</button>
            </form>
        </div>
        <c:choose>
            <c:when test="${not empty studentAlreadyRegistered}">
                <div class="errorMsg">
                        ${studentAlreadyRegistered}
                </div>
            </c:when>
            <c:when test="${not empty maxAmountOfRegistration}">
                <div class="errorMsg">
                        ${maxAmountOfRegistration}
                </div>
            </c:when>
            <c:when test="${not empty blockedStudent}">
                <div class="errorMsg">
                        ${blockedStudent}
                </div>
            </c:when>
            <c:when test="${not empty accessedRegistration}">
                <div class="accessMsg">
                        ${accessedRegistration}
                </div>
            </c:when>
            <c:when test="${not empty maxAmountOfStudent}">
                <div class="errorMsg">
                        ${maxAmountOfStudent}
                </div>
            </c:when>
            <c:when test="${not empty   endCourse}">
                <div class="errorMsg">
                        ${  endCourse}
                </div>
            </c:when>
            <c:when test="${not empty goingCourse}">
                <div class="errorMsg">
                        ${  goingCourse}
                </div>
            </c:when>
        </c:choose>
        <table class="courseInfoTable" id="table-id">
            <th class="courseTh">Название</th>
            <th class="courseTh">Длительность</th>
            <th class="courseTh">Количество студентов</th>
            <th class="courseTh">Макс. кол. мест</th>
            <th class="courseTh">Тема</th>
            <th class="courseTh">Перподаватель</th>
            <th class="courseTh">Статус</th>
            <c:forEach var="course" items="${requestScope.courseList}">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                      method="get">
                    <tr>
                        <td class="courseTableTd"><input class="dataInput" name="courseName"
                                                         value="${course.name}"
                                                         required readonly="readonly"></td>
                        <td class="courseTableTd"><input class="numberInput" name="courseDuration"
                                                         value="${course.duration}" required min="1"
                                                         readonly="readonly">
                        </td>
                        <td class="courseTableTd"><input class="numberInput" name="courseAmountOfStudent"
                                                         value="${course.amountOfStudent}" readonly="readonly">
                        </td>
                        <td class="courseTableTd"><input class="numberInput" name="courseMaxAmountOfStudent"
                                                         value="${course.maxAmountOfStudent}" required min="1"
                                                         readonly="readonly">
                        </td>
                        <td class="courseTableTd"><input class="dataInput" name="courseTopic"
                                                         value="${course.topic}"
                                                         required readonly="readonly"></td>
                        <td class="courseTableTd"><input class="dataInput" name="courseTeacherId"
                                                         value="${course.teacherFullName}"
                                                         readonly="readonly"></td>
                        <td class="courseTableTd"><input class="dataInput" id="statusInput" name="  endCourse"
                                                         value="${course.status}"
                                                         readonly="readonly"></td>
                        <td>
                            <input name="courseId" value="${course.id}" type="hidden">
                            <input name="courseStatus" value="${course.status}" type="hidden">
                            <button class="registerbutton" name="pageName" type="submit" value="registerOnCourse">
                                Записаться
                            </button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
        <div id="pageNavPosition" style="padding-top: 20px" align="center">
        </div>
        <script type="text/javascript">
            var pager = new Pager('table-id',5);
            pager.init();
            pager.showPageNav('pager', 'pageNavPosition');
            pager.showPage(1);
        </script>
        </div>
    </c:when>
    <c:when test="${pageName==homePage}">

        <div class="homePageDiv">
            <table class="infoTable">
                <th class="studentInfoTh">
                    <div class="Info">
                        <h3>Email: ${sessionScope.student.login}</h3>
                        <h3>ФИО: ${sessionScope.student.fullName}</h3>
                        <h3>Статус: ${sessionScope.student.status}</h3>
                    </div>
                </th>

                <th class="infoTh">
                    <div class="studentInfo">
                        <h3>Информация о ваших курсах</h3>
                        <table>
                            <th class="courseTh">Название</th>
                            <th class="courseTh">Длительность</th>
                            <th class="courseTh">Тема</th>
                            <th class="courseTh">Перподаватель</th>
                            <th class="courseTh">Статус</th>
                            <th class="courseTh">Оценка</th>
                            <c:forEach var="course" items="${requestScope.studentCourses}">
                                <c:if test="${course.status!='Закончен'}">
                                    <tr>
                                        <td class="courseTableTd"><input class="dataInput" name="courseName"
                                                                         value="${course.name}"
                                                                         required readonly="readonly"></td>
                                        <td class="courseTableTd"><input class="numberInput" name="courseDuration"
                                                                         value="${course.duration}" required min="1"
                                                                         readonly="readonly"></td>
                                        <td class="courseTableTd"><input class="dataInput" name="courseTopic"
                                                                         value="${course.topic}"
                                                                         required readonly="readonly"></td>
                                        <td class="courseTableTd"><input class="dataInput" name="courseTeacherId"
                                                                         value="${course.teacherFullName}"
                                                                         readonly="readonly"></td>
                                        <td class="courseTableTd"><input class="dataInput" id="status"
                                                                         name="courseStatus"
                                                                         value="${course.status}"
                                                                         readonly="readonly">
                                            <input name="courseId" value="${course.id}" type="hidden"></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            <c:forEach var="course" items="${requestScope.finishedStudentCourses}">
                            <tr>
                                <td class="courseTableTd"><input class="dataInput" name="courseName"
                                                                 value="${course.name}"
                                                                 required readonly="readonly"></td>
                                <td class="courseTableTd"><input class="numberInput" name="courseDuration"
                                                                 value="${course.duration}" required min="1"
                                                                 readonly="readonly"></td>
                                <td class="courseTableTd"><input class="dataInput" name="courseTopic"
                                                                 value="${course.topic}"
                                                                 required readonly="readonly"></td>
                                <td class="courseTableTd"><input class="dataInput" name="courseTeacherId"
                                                                 value="${course.teacherFullName}"
                                                                 readonly="readonly"></td>
                                <td class="courseTableTd"><input class="dataInput" id="status3" name="courseStatus"
                                                                 value="${course.status}"
                                                                 readonly="readonly">
                                <td><input class="markInput" name="mark"
                                           value="${course.mark}"
                                           type="number" readonly="readonly"></td>
                            <tr>
                                </c:forEach>
                        </table>
                    </div>
                </th>

            </table>

        </div>
    </c:when>
</c:choose>
</body>