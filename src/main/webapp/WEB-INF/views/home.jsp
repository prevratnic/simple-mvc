<%--
  Author: Ilya Varlamov aka privr@tnik
  Date: 23.07.13
  Time: 16:00
--%>
<jsp:useBean id="test" scope="request" type="java.lang.String" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <jsp:include page="menu.jsp" flush="true" />
    <h1>Home</h1>
    <p><%= test %></p>
</body>
</html>