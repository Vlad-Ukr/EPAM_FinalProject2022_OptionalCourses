<%@ include file="/jspf/taglib.jspf" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<fmt:requestEncoding value="UTF-8"/>
<tags:pagination>pagination</tags:pagination>
<tags:security>security</tags:security>
<!DOCTYPE html>
<html>
<head>
    <title>Optional Courses</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="frontend/adminPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/courseWorkPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/teacherWorkPage.css" rel="stylesheet" type="text/css">
    <link href="frontend/studentWorkPage.css" rel="stylesheet" type="text/css">
    <script src="frontend/confirmPassword/confirmPassword.js" type="text/javascript"></script>
</head>
<body>
<c:set var="currentPage" value="admin.jsp" scope="session"/>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader"><fmt:message key="greetings.message"/> ,${sessionScope.user.login}</h4>
    </div>
    <div class="bottomBar">
        <table id="topBarTable">
            <td class="emptyTd">
                <form action="${pageContext.request.contextPath}/dispatcher-servlet"
                      method="get">
                    <button class="courseWorkButton" name="pageName" type="submit" value="showCourses"><fmt:message
                            key="work.with.courses.button"/>
                    </button>
                    <button class="teacherWorkButton" name="pageName" type="submit" value="showTeachers">
                        <fmt:message key="work.with.teachers.button"/>
                    </button>
                    <button class="studentWorkButton" name="pageName" type="submit" value="showStudents">
                        <fmt:message key="work.with.students.button"/>
                    </button>
                    <input name="userRole" value="admin" type="hidden">
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
                <form action="${pageContext.request.contextPath}/dispatcher-servlet"
                      method="post">
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
<c:set var="studentPage" scope="request" value="showStudents"/>
<c:set var="teacherPage" scope="request" value="showTeachers"/>
<c:set var="coursePage" scope="request" value="showCourses"/>
<c:choose>
    <c:when test="${pageName==mainPage}">
        <div class="instruction">
            <h3><fmt:message key="welcome.message"/> , ${sessionScope.userLogin}!</h3>
            <text><fmt:message key="instruction.message"/></text>
        </div>
    </c:when>
    <c:when test="${pageName==studentPage}">
        <div class="workSpace">
            <c:if test="${requestScope.studentList.size()==0}">
             <h4 class="emptyListMessage"><fmt:message key="empty.student.course.list.admin.page"/></h4>
            </c:if>
            <c:if test="${requestScope.studentList.size()!=0}">
            <table class="workSpaceTable" id="table-id">
                <tr>
                    <th>Id</th>
                    <th><fmt:message key="teacher.table.login"/></th>
                    <th><fmt:message key="register.table.SNP"/></th>
                    <th><fmt:message key="course.table.status"/></th>
                    <th><fmt:message key="teacher.table.courses"/></th>
                    <th>
                        <form action="${pageContext.request.contextPath}/dispatcher-servlet"
                              method="get">
                            <select class="selectBy" name="courseSelect" size="1">
                                <option disabled><fmt:message key="student.table.select"/></option>
                                <option value="allCourses"><fmt:message key="course.table.select.select"/></option>
                                <c:forEach var="course" items="${requestScope.courseList}">
                                    <option value="${course.id}">${course.name}</option>
                                </c:forEach>
                            </select>
                            <br>
                            <button class="confirmSelectButton" name="pageName" type="submit" value="selectStudents">
                                <fmt:message key="student.table.select.button"/>
                            </button>
                        </form>
                    </th>
                </tr>
                <c:forEach var="student" items="${requestScope.studentList}">
                    <form action="${pageContext.request.contextPath}/dispatcher-servlet"
                          method="get">
                        <tr>
                            <td><input class="inputId" name="studentId" value="${student.id}" readonly="readonly"></td>
                            <td><input class="inputLogin" name="studentLogin" value="${student.login}"
                                       readonly="readonly"></td>
                            <td><input class="fullNameInput" name="studentFullName" value="${student.fullName}"
                                       readonly="readonly"></td>
                            <td>
                                <c:choose>
                                    <c:when test="${student.status == 'Unblocked'}">
                                        <input class="inputStatus" name="studentStatusId"
                                               value="<fmt:message key="unblocked.status"/>"
                                               readonly="readonly">
                                    </c:when>
                                    <c:when test="${student.status == 'Blocked'}">
                                        <input class="inputStatus" name="studentStatusId"
                                               value="<fmt:message key="blocked.status"/>"
                                               readonly="readonly">
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <details>
                                    <summary><fmt:message key="student.table.fixed.courses"/></summary>
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
                                    <fmt:message key="student.table.unblock.button"/>
                                </button>
                            </td>
                            <td>
                                <button class="blockButton" name="pageName" type="submit" value="block">
                                    <fmt:message key="student.table.block.button"/>
                                </button>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </table>
            </c:if>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
    <c:when test="${pageName==teacherPage}">
        <div class="workSpace">
            <table class="workSpaceTable">
                <form action="${pageContext.request.contextPath}/dispatcher-servlet" method="post">
                    <td>
                        <div id="emailInput">
                            <label>Email:</label>
                            <input class="inputForm" type="email" id="email" name="user_email" required
                                   placeholder="epam@email.com"
                                   pattern="/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/g"
                                   title="Введите свой email согласно шаблону:'epam@epam.com'">
                        </div>
                    </td>
                    <td>
                        <div id="fullNameInput">
                            <label><fmt:message key="register.table.SNP"/>:</label>
                            <input class="inputForm" type="text" id="fullname" name="user_fullname" required
                                   placeholder="Фамилия Имя Отчество"
                                   pattern="^[А-ЯЁЇA-Z][а-яёїa-z]{1,}([-][А-ЯЁїA-Z][а-яёїa-z]{1,})?\s[А-ЯЁЇA-Z][а-яёїa-z]{1,}\s[А-ЯЁЇA-Z][а-яёїa-z]{1,}$"
                                   title="Введите свой email согласно шаблону: 'Иванов Иван Иваныч'">
                        </div>
                    </td>
                    <td>
                        <div id="phoneNumber">
                            <label><fmt:message key="register.phone.number"/>:</label>
                            <input class="inputForm" type="text" id="phone_number" name="phone_number" required
                                   placeholder="+380*********"
                                   pattern="\+\d{12}"
                                   title="Введите свой номер телефона согласно шаблону: '+380111111111'">
                        </div>
                    </td>
                    <tr>
                        <td>
                            <div id="passwordInput">
                                <label><fmt:message key="register.table.password"/>:</label>
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
                                <label><fmt:message key="register.table.repeat.table"/>:</label>
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
                                <fmt:message key="register.table.register.button"/>
                            </button>
                        </td>
                </form>
            </table>
            <c:choose>
                <c:when test="${not empty successMessage}">
                    <div class="accesReg">
                        <text><fmt:message key="course.success.message"/></text>
                    </div>

                </c:when>

            </c:choose>
            <c:choose>
                <c:when test="${ not empty deniedMessage}">
                    <div class="deniedRegisterM">
                        <text><fmt:message key="user.already.exists"/></text>
                    </div>

                </c:when>

            </c:choose>
            <c:if test="${sessionScope.teacherList.size()==0}">
                <h4 class="emptyListMessage"><fmt:message key="empty.teacher.course.list.admin.page"/></h4>
            </c:if>
            <c:if test="${sessionScope.teacherList.size()!=0}">
            <table class="teacherInfoTable" id="table-id">
                <tr>
                    <th>Id</th>
                    <th><fmt:message key="teacher.table.login"/></th>
                    <th><fmt:message key="register.table.SNP"/></th>
                    <th><fmt:message key="teacher.table.courses"/></th>
                </tr>
                <c:forEach var="teacher" items="${sessionScope.teacherList}">
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
                                <summary><fmt:message key="teacher.table.fixed.courses"/></summary>
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
            </c:if>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
    <c:when test="${pageName==coursePage}">
        <div class="workSpace">
            <table class="workSpaceTable">
                <form action="${pageContext.request.contextPath}/dispatcher-servlet" method="post">
                    <td>
                        <div id="nameInput">
                            <label><fmt:message key="add.course.table.name"/>:</label>
                            <input class="inputForm" type="text" id="name" name="courseName" required>
                        </div>
                    <td>
                        <div id="durationInput">
                            <label><fmt:message key="add.course.table.duration.in.hours"/>:</label>
                            <input class="inputForm" type="number" id="duration" name="courseDuration"
                                   value="1"
                                   min="1" required>
                        </div>
                    </td>
                    <td>
                        <div id="maxAmountInput">
                            <label><fmt:message key="course.table.max.amount"/>:</label>
                            <input class="inputForm" type="number" id="maxAmount" name="maxAmount"  value="1"
                                   min="1" required>
                            <input class="inputForm" type="hidden" name="pageName" value="insertCourse"/>
                        </div>
                    </td>
                    <td>
                        <div id="topicInput">
                            <label><fmt:message key="add.course.table.topic"/>:</label>
                            <input class="inputForm" type="text" id="topic" name="topic" required>
                        </div>
                    </td>
                    </td>
                    <td>
                        <label><fmt:message key="add.course.table.teacher"/>:</label>
                        <select class="teacherSelect" name="teacherSelect" size="1" required>
                            <option disabled><fmt:message key="course.table.select.teacher"/></option>
                            <c:forEach var="teacher" items="${requestScope.teacherList}">
                                <option value="${teacher.id}">${teacher.fullName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button style="font-family:Arial, Verdana, sans-serif;color:#72157C"
                                id="addButton" type="submit" name="pageName" value="insertCourse">
                            <fmt:message key="add.course.table.add.button"/>
                        </button>
                    </td>
                </form>
            </table>
            <c:choose>
                <c:when test="${not empty successMessage}">
                    <div class="accesReg">
                        <text><fmt:message key="course.success.message"/></text>
                    </div>

                </c:when>

            </c:choose>
            <c:choose>
                <c:when test="${ not empty deniedMessage}">
                    <div class="deniedRegisterM">
                        <text><fmt:message key="same.course.name"/></text>
                    </div>

                </c:when>
            </c:choose>
            <c:if test="${requestScope.courseList.size()==0}">
                <h4 class="emptyListMessage"><fmt:message key="empty.course.course.list.admin.page"/></h4>
            </c:if>
            <c:if test="${requestScope.courseList.size()!=0}">
            <table class="courseInfoTable" id="table-id">
                <form action="${pageContext.request.contextPath}/dispatcher-servlet"
                      method="get">
                    <tr class="courseTr">
                        <th class="courseTh">Id</th>
                        <th class="courseTh"><fmt:message key="add.course.table.name"/></th>
                        <th class="courseTh"><fmt:message key="add.course.table.duration.in.hours"/></th>
                        <th class="courseTh"><fmt:message key="add.course.table.amount.of.student"/></th>
                        <th class="courseTh"><fmt:message key="course.table.max.amount"/></th>
                        <th class="courseTh"><fmt:message key="add.course.table.topic"/></th>
                        <th class="courseTh"><fmt:message key="add.course.table.teacher"/></th>
                        <th class="courseTh"><fmt:message key="course.table.status"/></th>
                        <td class="emptyTd">
                            <input name="studentPageMarker" value="false" type="hidden">
                            <select class="sortSelect" name="sortSelect" size="1" required>
                                <option disabled><fmt:message key="course.table.select.sort"/></option>
                                <option value="byNameA-Z"><fmt:message key="course.table.select.sort.[a-z]"/></option>
                                <option value="byNameZ-A"><fmt:message key="course.table.select.sort.[z-a]"/></option>
                                <option value="byDuration"><fmt:message
                                        key="course.table.select.sort.duration"/></option>
                                <option value="byStudents"><fmt:message
                                        key="course.table.select.sort.amount.of.student"/></option>
                            </select>
                            <button class="sortButton" name="pageName" type="submit" value="sortCourse"><fmt:message
                                    key="course.table.sort.button"/>
                            </button>
                            <input name="userRole" value="admin" type="hidden">
                        </td>
                    </tr>
                    <tbody>
                    <c:forEach var="course" items="${requestScope.courseList}">
                        <form action="${pageContext.request.contextPath}/dispatcher-servlet" method="post">
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
                                <td class="courseTableTd">
                                    <c:choose>
                                    <c:when test="${course.status=='Not started'}">
                                        <input class="statusInput"
                                               name="courseStatus"
                                               value="<fmt:message key="not.started.status"/>"
                                               readonly="readonly">
                                    </c:when>
                                    <c:when test="${course.status=='In process'}">
                                    <input class="statusInput"
                                           name="courseStatus"
                                           value="<fmt:message key="in.process.status"/>"
                                           readonly="readonly"></td>
                                </c:when>
                                <c:when test="${course.status=='Ended'}">
                                    <input class="statusInput"
                                           name="courseStatus"
                                           value="<fmt:message key="ended.status"/>"
                                           readonly="readonly">
                                </c:when>
                                </c:choose>
                                </td>
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
                                        <fmt:message key="course.table.redact.button"/>
                                    </button>
                                </td>
                                <td>
                                    <button class="deleteButton" name="pageName" type="submit" value="deleteCourse">
                                        <fmt:message key="course.table.delete.button"/>
                                    </button>
                                </td>
                            </tr>
                            <input name="userRole" value="admin" type="hidden"></td>
                        </form>
                    </c:forEach>
                    </tbody>
                </form>
            </table>
            </c:if>
            <tags:pager>pager</tags:pager>
        </div>
    </c:when>
</c:choose>
<div title="We stand with Ukraine" id="standWithUA"></div>
</body>
</html>
