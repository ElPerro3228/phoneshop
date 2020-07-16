<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Order Page" miniCart="${miniCart}">
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
    <sf:form method="post" modelAttribute="order">
        <div style="color: red">
            <sf:errors path="orderItems"/>
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
            <c:forEach var="cartItem" items="${cartDTO.cartItems.entrySet()}" varStatus="status">
                <tr class="cart-item">
                    <td>${cartItem.key.brand}</td>
                    <td><a href="<c:url value="productDetails/${cartItem.key.id}"/>">${cartItem.key.model}</a></td>
                    <td> <fmt:formatNumber value="${cartItem.key.price}" type="currency" currencySymbol="$"/> </td>
                    <td>
                        <c:out value="${cartItem.value}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    Subtotal price: <fmt:formatNumber value="${cartDTO.cartPrice}" type="currency" currencySymbol="$"/>
                    <sf:input path="subtotal" value="${cartDTO.cartPrice}" type="hidden"/>
                </div>
                <div class="col-sm-4">
                    Delivery price: <fmt:formatNumber value="${cartDTO.deliveryPrice}" type="currency" currencySymbol="$"/>
                    <sf:input path="deliveryPrice" value="${cartDTO.deliveryPrice}" type="hidden"/>
                </div>
                <div class="col-sm-4">
                    Total price: <fmt:formatNumber value="${cartDTO.totalPrice}" type="currency" currencySymbol="$"/>
                    <sf:input path="totalPrice" value="${cartDTO.totalPrice}" type="hidden"/>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    First name
                </div>
                <div class="col-sm-4">
                    <sf:input path="firstName"/> <br/>
                    <div style="color: red">
                        <sf:errors path="firstName"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Last name
                </div>
                <div class="col-sm-4">
                    <sf:input path="lastName"/>
                    <div style="color: red">
                        <sf:errors path="lastName"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Address
                </div>
                <div class="col-sm-4">
                    <sf:input path="deliveryAddress"/>
                    <div style="color: red">
                        <sf:errors path="deliveryAddress"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2">
                    Phone
                </div>
                <div class="col-sm-4">
                    <sf:input path="contactPhoneNo"/>
                    <div style="color: red">
                        <sf:errors path="contactPhoneNo"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <button name="order" type="submit" class="btn btn-outline-success">Order</button>
                </div>
            </div>
        </div>
    </sf:form>
</tags:master>