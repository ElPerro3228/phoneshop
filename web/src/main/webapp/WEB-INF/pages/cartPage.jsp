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
    <sf:form method="post" modelAttribute="cartPageData">
<%--    <input type="hidden" name="_method" value="PUT">--%>
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
        <c:forEach var="phone" items="${phones}">
            <tr class="cart-item">
                <td>${phone.brand}</td>
                <td><a href="<c:url value="productDetails/${phone.id}"/>">${phone.model}</a></td>
                <td> <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/> </td>
                <div class="container">
                    <td>
                        <sf:input path="cartItems[${phone.id}]" value="${cartPageData.cartItems[phone.id]}"/>
                        <sf:errors path="cartItems[${phone.id}]" cssClass="error" cssStyle="color: red"/>
                        <%--<input name="cart-item-quantity" id="${entry.key.id}" value="${entry.value}">
                        <div class="error" style="color: red"></div>--%>
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
                <button name="order" type="button" class="btn btn-outline-success">Order</button>
            </div>
        </div>
    </div>
    </sf:form>
</tags:master>