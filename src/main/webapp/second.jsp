<%--
  Created by : tha
  Date: 02/02/2023
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
    <title>Show all users</title>
    <style>
        table {
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid darkorange;
        }
        th, td, input {
            padding: 5px;
        }
        input {
            margin: 1px;
            border: 1px solid darkorange;
        }
    </style>
</head>
<body>
<h1>Users</h1>
<h3>Create a new user</h3>
<form action="${pageContext.request.contextPath}/backend" method="post">
    <input type="text" name="fname" placeholder="First name">
    <br/><input type="text" name="lname" placeholder="Last name">
    <br/><input type="text" name="password" placeholder="Password">
    <br/><input type="text" name="phone" placeholder="Phone">
    <br/><input type="text" name="address" placeholder="Address">
    <br/><input type="submit" value="Create">
</form>
<h3>Users</h3>
<table >
    <thead>
    <tr>
        <th>User ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Phone</th>
        <th>Address</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sessionScope.users}" var="user">
        <tr>
            <td><c:out value="${user.id}"/></td>
            <td><c:out value="${user.fname}"/></td>
            <td><c:out value="${user.lname}"/></td>
            <td><c:out value="${user.phone}"/></td>
            <td><c:out value="${user.address}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

