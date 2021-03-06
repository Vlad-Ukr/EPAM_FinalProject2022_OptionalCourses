<%@ include file="/jspf/taglib.jspf" %>

<%-- set the locale --%>
<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<%-- goto back to the settings--%>
<c:set var="pageName" value="${requestScope.pageName}" scope="request"/>
<%
    request.setAttribute("pageName", "instruction");
    request.getRequestDispatcher((String) request.getSession().getAttribute("currentPage")).forward(request, response);%>

