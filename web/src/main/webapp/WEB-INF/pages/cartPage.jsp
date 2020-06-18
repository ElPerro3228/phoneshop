<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart Page">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">

            </div>
            <div class="col-sm-8">
                <h5 id="total-price">
                    Price: <fmt:formatNumber value="${cartPrice}" type="currency" currencySymbol="$"/>
                </h5>
            </div>
        </div>
        <a href="<c:url value="/productList"/>">
            <button name="productList" type="button" class="btn btn-outline-success">Back to product list</button>
        </a>
    </div>
    <div class="error" id="error-panel">

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
        <c:forEach var="entry" items="${cartItems.entrySet()}">
            <tr class="cart-item">
                <td>${entry.key.brand}</td>
                <td><a href="<c:url value="productDetails/${entry.key.id}"/>">${entry.key.model}</a></td>
                <td> <fmt:formatNumber value="${entry.key.price}" type="currency" currencySymbol="$"/> </td>
                <div class="container">
                    <td>
                        <input name="cart-item-quantity" id="${entry.key.id}" value="${entry.value}">
                        <div class="error" style="color: red"></div>
                    </td>
                    <td>
                        <button name="delete-from-cart" type="button" class="btn btn-outline-success" data-phone-id="${entry.key.id}">Delete</button>
                    </td>
                </div>
            </tr>
        </c:forEach>
    </table>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <button name="update-cart" type="button" class="btn btn-outline-success">Update</button>
            </div>
            <div class="col-sm-6">
                <button name="order" type="button" class="btn btn-outline-success">Order</button>
            </div>
        </div>
    </div>
</tags:master>