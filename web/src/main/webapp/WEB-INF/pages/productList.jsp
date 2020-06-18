<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <form>
    <div class="container">
        <div class="row">
            <div class="col">
                <input name="query" value="<c:out value="${param.query}"/>"/>
            </div>
            <div class="col">
                <button class="btn btn-outline-success">Search</button>
            </div>
        </div>
    </div>
    </form>
    <p>Found <c:out value="${pageBean.phones.size()}"/> phones.</p>
    <p><c:out value="${pageBean.pagesNumber}"/></p>
    <tags:pageNavigation currentPage="${pageBean.currentPage}" pagesNumber="${pageBean.pagesNumber}"/>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Image</td>
            <td>Brand <tags:sort field="brand" pageBean="${pageBean}"/></td>
            <td>Model <tags:sort field="model" pageBean="${pageBean}"/></td>
            <td>Colors</td>
            <td>Display size <tags:sort field="displaySizeInches" pageBean="${pageBean}"/></td>
            <td>Price <tags:sort field="price" pageBean="${pageBean}"/></td>
            <td>Quantity</td>
            <td>Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${pageBean.phones}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </td>
                <td>${phone.brand}</td>
                <td><a href="<c:url value="productDetails/${phone.id}"/>">${phone.model}</a></td>
                <td>
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code}
                    </c:forEach>
                </td>
                <td>${phone.displaySizeInches}"</td>
                <td> <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/> </td>
                <div class="container">
                    <td><input id="${phone.id}" value="1"></td>
                    <td>
                        <button name="add-to-cart" type="button" class="btn btn-outline-success" data-phone-id="${phone.id}">Add to</button>
                        <div class="error" style="color: red"></div>
                    </td>
                </div>
            </tr>
        </c:forEach>
    </table>
</tags:master>