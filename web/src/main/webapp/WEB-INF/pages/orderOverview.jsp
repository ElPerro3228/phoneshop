<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order Overview Page" miniCart="${miniCart}">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">

            </div>
            <div class="col-sm-8">
                <h5 id="total-price">
                    Price: <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/>
                </h5>
            </div>
        </div>
        <a href="<c:url value="/productList"/>">
            <button name="productList" type="button" class="btn btn-outline-success">Back to product list</button>
        </a>
    </div>
    <div class="error" id="error-panel">
    </div>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                Status: <c:out value="${order.status}"/>
            </div>
        </div>
    </div>
        <table border="1px" class="table">
            <thead>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Price</td>
                <td>Quantity</td>
                <td>Action</td>
            </tr>
            </thead>
            <c:forEach var="orderItem" items="${order.orderItems}" varStatus="status">
                <tr class="cart-item">
                    <td>${orderItem.phone.brand}</td>
                    <td><a href="<c:url value="productDetails/${orderItem.phone.id}"/>">${orderItem.phone.model}</a></td>
                    <td> <fmt:formatNumber value="${orderItem.phone.price}" type="currency" currencySymbol="$"/> </td>
                    <td>
                        <c:out value="${orderItem.quantity}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    Subtotal price: <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/>
                </div>
                <div class="col-sm-4">
                    Delivery price: <fmt:formatNumber value="${order.deliveryPrice}" type="currency" currencySymbol="$"/>
                </div>
                <div class="col-sm-4">
                    Total price: <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    First name
                </div>
                <div class="col-sm-4">
                    <c:out value="${order.firstName}"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Last name
                </div>
                <div class="col-sm-4">
                    <c:out value="${order.lastName}"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Address
                </div>
                <div class="col-sm-4">
                    <c:out value="${order.deliveryAddress}"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Phone
                </div>
                <div class="col-sm-4">
                    <c:out value="${order.contactPhoneNo}"/>
                </div>
            </div>
        </div>
</tags:master>