
<%@ include file="/jspf/taglib.jspf" %>
<!DOCTYPE html>
<html>
<head>
    <title>Optional Courses</title>
    <link href="frontend/student.css" rel="stylesheet" type="text/css">
</head>
<body>
<c:set var="currentPage" value="student.jsp" scope="session"/>
<c:set var="pageRole" scope="request" value="student"/>
<tags:pagination>pagination</tags:pagination>
<div class="header">
    <div class="topBar">
        <h4 class="greetingsHeader"><fmt:message key="greetings.message"/>, ${sessionScope.userLogin}</h4>
    </div>
    <div class="bottomBar">
        <table id="topBarTable">
            <td class="emptyTd">
                <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                    <button class="homePage" formaction="/OptionalCoursesFP_war_exploded/dispatcher-servlet"
                            formmethod="get" name="pageName" type="submit" value="homePage"><fmt:message
                            key="student.page.home.page"/>
                    </button>
                    <button class="showCoursesButton" name="pageName" type="submit" value="showCoursesStudent">
                        <fmt:message key="student.page.all.courses"/>
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
<c:choose>
    <c:when test="${pageName==mainPage}">
        <div class="instruction">
            <h3><fmt:message key="welcome.message"/>, ${sessionScope.userLogin}!</h3>
            <text><fmt:message key="instruction.message"/></text>
        </div>
    </c:when>
    <c:when test="${pageName==coursePage}">
        <div class="workSpace">
            <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="get">
                <input name="studentPageMarker" value="true" type="hidden">
                <button class="showAllCoursesButton" name="pageName" type="submit" value="showCoursesStudent">
                    <fmt:message key="student.page.list.of.courses"/>
                </button>
                <select class="selectBy" name="teacherSelect" size="1">
                    <option disabled><fmt:message key="course.table.select.teacher"/></option>
                    <option value="0"><fmt:message key="student.page.all.teachers"/></option>
                    <c:forEach var="teacher" items="${sessionScope.teacherList}">
                        <option value="${teacher.id}">${teacher.fullName}</option>
                    </c:forEach>
                </select>
                <select class="selectBy" name="topicSelect" size="1">
                    <option disabled><fmt:message key="student.page.select.topic"/></option>
                    <option value="allTopics"><fmt:message key="student.page.all.topics"/></option>
                    <c:forEach var="topic" items="${sessionScope.topicList}">
                        <option value="${topic}">${topic}</option>
                    </c:forEach>
                </select>

                <button class="confirmSelectButton" name="pageName" type="submit" value="selectCourses"><fmt:message
                        key="student.table.select.button"/></button>

                <select class="sortSelect" name="sortSelect" size="1" required>
                    <option disabled><fmt:message key="course.table.select.sort"/></option>
                    <option value="byNameA-Z"><fmt:message key="course.table.select.sort.[a-z]"/></option>
                    <option value="byNameZ-A"><fmt:message key="course.table.select.sort.[z-a]"/></option>
                    <option value="byDuration"><fmt:message key="course.table.select.sort.duration"/></option>
                    <option value="byStudents"><fmt:message key="course.table.select.sort.amount.of.student"/></option>
                </select>
                <button class="sortButton" name="pageName" type="submit" value="sortCourse"><fmt:message
                        key="course.table.sort.button"/></button>
            </form>
        </div>
        <c:choose>
            <c:when test="${not empty studentAlreadyRegistered}">
                <div class="errorMsg">
                    <text><fmt:message key="already.registered"/></text>
                </div>
            </c:when>
            <c:when test="${not empty maxAmountOfRegistration}">
                <div class="errorMsg">
                    <text><fmt:message key="already.registered.3.times"/></text>
                </div>
            </c:when>
            <c:when test="${not empty blockedStudent}">
                <div class="errorMsg">
                    <text><fmt:message key="block.message"/></text>
                </div>
            </c:when>
            <c:when test="${not empty accessedRegistration}">
                <div class="accessMsg">
                    <text><fmt:message key="success.sign.on"/></text>
                </div>
            </c:when>
            <c:when test="${not empty maxAmountOfStudent}">
                <div class="errorMsg">
                    <text><fmt:message key="no.more.places.on.course"/></text>
                </div>
            </c:when>
            <c:when test="${not empty   endCourse}">
                <div class="errorMsg">
                    <text><fmt:message key="course.is.over"/></text>
                </div>
            </c:when>
            <c:when test="${not empty goingCourse}">
                <div class="errorMsg">
                    <text><fmt:message key="closed.enrollment"/></text>
                </div>
            </c:when>
        </c:choose>

        <c:if test="${requestScope.courseList.size()==0}">
            <fmt:message key="empty.all.course.list"/>
        </c:if>
        <c:if test="${requestScope.courseList.size()!=0}">
        <table class="courseInfoTable" id="table-id">
            <th class="courseTh"><fmt:message key="add.course.table.name"/></th>
            <th class="courseTh"><fmt:message key="add.course.table.duration.in.hours"/></th>
            <th class="courseTh"><fmt:message key="add.course.table.amount.of.student"/></th>
            <th class="courseTh"><fmt:message key="course.table.max.amount"/></th>
            <th class="courseTh"><fmt:message key="add.course.table.topic"/></th>
            <th class="courseTh"><fmt:message key="add.course.table.teacher"/></th>
            <th class="courseTh"><fmt:message key="course.table.status"/></th>
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
                        <td class="courseTableTd"><c:choose>
                            <c:when test="${course.status=='Not started'}">
                                <input class="statusInput"
                                       name="courseStatusView"
                                       value="<fmt:message key="not.started.status"/>"
                                       readonly="readonly">
                            </c:when>
                            <c:when test="${course.status=='In process'}">
                            <input class="statusInput"
                                   name="courseStatusView"
                                   value="<fmt:message key="in.process.status"/>"
                                   readonly="readonly"></td>
                        </c:when>
                        <c:when test="${course.status=='Ended'}">
                            <input class="statusInput"
                                   name="courseStatusView"
                                   value="<fmt:message key="ended.status"/>"
                                   readonly="readonly">
                        </c:when>
                        </c:choose></td>
                        <td>
                            <input name="courseId" value="${course.id}" type="hidden">
                            <input name="courseStatus" value="${course.status}" type="hidden">
                            <button class="registerbutton" name="pageName" type="submit" value="registerOnCourse">
                                <fmt:message key="student.page.sign.up"/>
                            </button>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
        </c:if>
        <div id="pageNavPosition" style="padding-top: 20px" align="center">
        </div>
        <script type="text/javascript">
            var pager = new Pager('table-id', 5);
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
                        <img src="data:image/jpeg;base64,${sessionScope.base64Encoded}" alt="avatar"
                             width="140" height="140"/>
                        <c:choose>
                            <c:when test="${ not empty deniedMessage}">
                                <div class="deniedRegisterM">
                                    <input class="photoMessage" type="text"  value='<fmt:message key="load.error"/>' readonly >
                                </div>
                            </c:when>
                        </c:choose>
                        <form action="/OptionalCoursesFP_war_exploded/dispatcher-servlet" method="post" enctype="multipart/form-data">
                            <input type="file" name="loadAvatar" id="headImg">
                            <style>#headImg::before {  content:'<fmt:message key="select.avatar.button"/>';}</style>
                            <button class="loadAvatar" name="pageName" type="submit" value="loadAvatar"><fmt:message key="load.button"/></button>
                        </form>
                        <h3>Email:${sessionScope.student.login}</h3>
                        <h3><fmt:message key="register.table.SNP"/>: ${sessionScope.student.fullName}
                         </h3>
                        <h3><fmt:message key="register.phone.number"/>: ${sessionScope.user.phoneNumber}</h3>
                       <c:choose>
                           <c:when test="${sessionScope.student.status == 'Unblocked'}">
                               <h3><fmt:message key="course.table.status"/>:<fmt:message key="unblocked.status"/></h3>
                           </c:when>
                           <c:when test="${sessionScope.student.status == 'Blocked'}">
                               <h3><fmt:message key="course.table.status"/>:<fmt:message key="blocked.status"/></h3>
                           </c:when>
                       </c:choose>
                    </div>
                </th>

                <th class="infoTh">
                    <div class="studentInfo">
                        <h3><fmt:message key="student.home.page.course.info"/></h3>
                        <c:if test="${requestScope.studentCourses.size()==0 && requestScope.finishedStudentCourses.size()==0}">
                            <h4><fmt:message key="empty.student.course.list"/></h4>
                        </c:if>
                        <c:if test="${requestScope.studentCourses.size()!=0 || requestScope.finishedStudentCourses.size()!=0}">
                        <table>
                            <th class="courseTh"><fmt:message key="add.course.table.name"/></th>
                            <th class="courseTh"><fmt:message key="add.course.table.duration.in.hours"/></th>
                            <th class="courseTh"><fmt:message key="add.course.table.topic"/></th>
                            <th class="courseTh"><fmt:message key="add.course.table.teacher"/></th>
                            <th class="courseTh"><fmt:message key="course.table.status"/></th>
                            <th class="courseTh"><fmt:message key="student.home.page.mark"/></th>
                            <c:forEach var="course" items="${requestScope.studentCourses}">
                                <c:if test="${course.status!='Ended'}">
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
                                        <td class="courseTableTd"><c:choose>
                                            <c:when test="${course.status=='Not started'}">
                                                <input class="statusInput"
                                                       name="courseStatusView"
                                                       value="<fmt:message key="not.started.status"/>"
                                                       readonly="readonly">
                                            </c:when>
                                            <c:when test="${course.status=='In process'}">
                                            <input class="statusInput"
                                                   name="courseStatusView"
                                                   value="<fmt:message key="in.process.status"/>"
                                                   readonly="readonly"></td>
                                        </c:when>
                                        <c:when test="${course.status=='Ended'}">
                                            <input class="statusInput"
                                                   name="courseStatusView"
                                                   value="<fmt:message key="ended.status"/>"
                                                   readonly="readonly">
                                        </c:when>
                                        </c:choose>
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
                                <td class="courseTableTd"><c:choose>
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
                                </c:choose></td>
                                <td><input class="markInput" name="mark"
                                           value="${course.mark}"
                                           type="number" readonly="readonly"></td>
                            </tr>
                                </c:forEach>
                        </table>
                        </c:if>
                    </div>
                </th>

            </table>

        </div>
    </c:when>
</c:choose>
<div title="We stand with Ukraine" id="standWithUA"></div>
</body>