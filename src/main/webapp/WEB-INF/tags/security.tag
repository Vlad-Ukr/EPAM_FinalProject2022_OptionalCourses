<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.userRole or sessionScope.userRole!=pageRole }">
    <%request.getRequestDispatcher("securityError.jsp").forward(request, response);%>
</c:if>