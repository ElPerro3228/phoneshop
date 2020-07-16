<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart Page" miniCart="${miniCart}">
    <div class="container">
        <div class="row">
            <div class="col-sm-4">

            </div>
            <div class="col-sm-8">
                <h5 id="total-price">
                    Price: <fmt:formatNumber value="${cartDTO.cartPrice}" type="currency" currencySymbol="$"/>
                </h5>
            </div>
        </div>
        <a href="<c:url value="/productList"/>">
            <button name="productList" type="button" class="btn btn-outline-success">Back to product list</button>
        </a>
    </div>
    <div class="error" id="error-panel">

    </div>
    <sf:form method="post" modelAttribute="cartDTO">
    <sf:input path="cartPrice" value="${cartDTO.cartPrice}" type="hidden"/>
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
        <c:forEach var="phone" items="${cartDTO.cartItems.keySet()}" varStatus="status">
            <tr class="cart-item">
                <td>${phone.brand}</td>
                <td><a href="<c:url value="productDetails/${phone.id}"/>">${phone.model}</a></td>
                <td> <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/> </td>
                <div class="container">
                    <td>
                        <sf:input path="cartItems[${phone.id}]" value="${cartDTO.cartItems[phone.id]}"/>
                        <sf:errors path="cartItems[${phone.id}]" cssClass="error" cssStyle="color: red"/>
                    </td>
                    <td>
                        <button name="delete-from-cart" type="button" class="btn btn-outline-success" data-phone-id="${phone.id}">Delete</button>
                    </td>
                </div>
            </tr>
        </c:forEach>
    </table>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <button name="update" type="submit" class="btn btn-outline-success">Update</button>
            </div>
            <div class="col-sm-6">
                <a href="<c:url value="/order"/>" >
                    <button name="order" type="button" class="btn btn-outline-success">Order</button>
                </a>
            </div>
        </div>
    </div>
    </sf:form>
</tags:master>