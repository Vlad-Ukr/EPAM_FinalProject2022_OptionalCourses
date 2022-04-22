<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jspf/taglib.jspf" %>
<fmt:requestEncoding value="UTF-8"/>
<!DOCTYPE html>
<html>
<head>
    <title>Teacher</title>
    <link href="frontend/teacher.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="currentPage" value="teacher.jsp" scope="session"/>
<c:set var="pageRole" scope="request" value="teacher"/>
<tags:security>security</tags:security>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader"><fmt:message key="greetings.message"/>, ${sessionScope.userLogin}</h4>
    </div>
    <div class="bottomBar">
        <table>
            <td class="emptyTd">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                    <button class="homePage" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                            formmethod="get" name="pageName" type="submit" value="homePage"><fmt:message
                            key="student.page.home.page"/>
                    </button>
                    <button class="showCoursesButton" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                            formmethod="get" name="pageName" type="submit" value="showCourses"><fmt:message
                            key="teacher.page.journal"/>
                    </button>
                </form>
            </td>
            <td class="emptyTd">
                <form action="changeLocale.jsp" method="post">
                    <select class="changeLocaleSelect" name="locale">
                        <c:forEach items="${applicationScope.locales}" var="locale">
                            <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
                            <option value="${locale.key}" ${selected}>${locale.value}</option>
                        </c:forEach>
                    </select>
                    <input class="changeLocale" type="submit" value="<fmt:message key='set.language.button'/>">
                    <input type="hidden" name="pageName" value="${pageName}">
                </form>
            </td>

            <td class="emptyTd">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" onsubmit="return doSubmit()"
                      method="get">
                    <button class="logOutButton" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                            name="pageName" type="submit" value="logOut">
                        <fmt:message key="log.out.button"/>
                    </button>
                </form>
            </td>
        </table>
    </div>
</div>
</div>
<c:set var="mainPage" scope="request" value="instruction"/>
<c:set var="coursePage" scope="request" value="showCourses"/>
<c:set var="homePage" scope="request" value="showHome"/>
<c:set var="journalPage" scope="request" value="showJournal"/>
<c:choose>
<c:when test="${pageName==mainPage}">
    <div class="instruction">
        <h3><fmt:message key="welcome.message"/>, ${sessionScope.userLogin}!</h3>
        <text><fmt:message key="instruction.message"/></text>
    </div>
</c:when>
<c:when test="${pageName==homePage}">
<div class="homePageDiv">
    <table class="infoTable">
        <th class="studentInfoTh">
            <div class="Info">
                <h3>Email: ${sessionScope.teacher.login}</h3>
                <h3><fmt:message key="register.table.SNP"/>: ${sessionScope.teacher.fullName}</h3>
            </div>
        </th>

        <th class="infoTh">
            <div class="studentInfo">
                <h3><fmt:message key="teacher.page.your.courses"/></h3>
                <table>
                    <th><fmt:message key="add.course.table.name"/></th>
                    <th><fmt:message key="add.course.table.amount.of.student"/></th>
                    <th><fmt:message key="add.course.table.duration.in.hours"/></th>
                    <th><fmt:message key="add.course.table.topic"/></th>
                    <th><fmt:message key="course.table.status"/></th>
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
                                        <fmt:message key="teacher.page.start.course.button"/>
                                    </button>
                                </c:if>
                            </c:if>
                                <c:if test="${course.status=='В процессе'}">
                                    <button class="showCoursesButton" name="pageName" type="submit" value="showJournal">
                                        <fmt:message key="teacher.page.end.course.button"/>
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
        <h4><fmt:message key="teacher.page.choose.course.journal"/></h4>
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
        <text><fmt:message key="course.is.over.mark.exposed"/></text>
    </div>
    </c:if>
    </c:when>
    <c:when test="${pageName==journalPage}">
    <div class="selectCourseJournal">
        <form class="studentInfoForm" action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
            <table class="studentInfo">
                <caption><fmt:message key="teacher.page.students.on.course"/></caption>
                <c:choose>
                    <c:when test="${requestScope.course.status=='В процессе'}">
                        <c:if test="${ empty sessionScope.students}">
                            <td>
                                <h4><fmt:message key="teacher.page.empty.list"/></h4>
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
                                                <fmt:message key="teacher.page.submit.grades.button"/>
                                            </button>
                                        </td>
                                        <td>
                                            <button class="gradeButton" name="pageName" type="submit"
                                                    value="endCourse">

                                                <fmt:message key="teacher.page.end.course.button"/>
                                            </button>
                                            <input name="endCourseMarker" value="true" type="hidden">
                                            <input name="courseId" value="${requestScope.course.id}" type="hidden">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <button class="goBackButton" name="pageName" type="submit"
                                                    value="showCourses"><fmt:message key="teacher.page.go.back.button"/>
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
                                <h4><fmt:message key="teacher.page.empty.list"/></h4>
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
                            <button class="goBackButton" name="pageName" type="submit" value="showCourses"><fmt:message
                                    key="teacher.page.go.back.button"/>
                            </button>
                        </td>
                        <input name="userRole" value="teacher" type="hidden">
                    </c:when>
                    <c:when test="${requestScope.course.status=='Не начался'}">
                        <c:if test="${ empty sessionScope.students}">
                            <td>
                                <h4><fmt:message key="teacher.page.empty.list"/></h4>
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
                            <button class="goBackButton" name="pageName" type="submit" value="showCourses"><fmt:message
                                    key="teacher.page.go.back.button"/>
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
