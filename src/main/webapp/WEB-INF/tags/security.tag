<%@ tag import="com.example.optionalcoursesfp.entity.User" %><%
    response.setHeader("Cache-Control","no-cache, no-store");
    User user =(User)session.getAttribute("user");
    if(user==null){
        response.sendRedirect("loginPage.jsp");
    }
%>