<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart Page" miniCart="${miniCart}">
    <div class="container">
        <a href="<c:url value="/productList"/>">
            <button name="productList" type="button" class="btn btn-outline-success">Back to product list</button>
        </a>
    </div>
    <div class="error" id="error-panel">
    </div>
    <div>
        <h4>Orders</h4>
    </div>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Order ID</td>
            <td>Customer</td>
            <td>Phone</td>
            <td>Address</td>
            <td>Total price</td>
            <td>Status</td>
        </tr>
        </thead>
        <c:forEach var="order" items="${orders}">
            <tr class="order">
                <td><a href="<c:url value="/admin/orders/${order.id}"/>">${order.id}</a></td>
                <td><c:out value="${order.firstName} ${order.lastName}"/> </td>
                <td><c:out value="${order.contactPhoneNo}"/></td>
                <td><c:out value="${order.deliveryAddress}"/></td>
                <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/></td>
                <td><c:out value="${order.status}"/></td>
            </tr>
        </c:forEach>
    </table>
</tags:master>