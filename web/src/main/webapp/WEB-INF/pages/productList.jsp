<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <p>
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
    </p>
    <p>Found <c:out value="${phones.size()}"/> phones.</p>
    <p>${pagesNumber}</p>
    <tags:pageNavigation currentPage="${currentPage}" pagesNumber="${pagesNumber}"/>
    <table border="1px" class="table">
        <thead>
        <tr>
            <td>Image</td>
            <td>Brand <tags:sortLink field="brand" order="ASC"/> <tags:sortLink field="brand" order="DESC"/></td>
            <td>Model <tags:sortLink field="model" order="ASC"/> <tags:sortLink field="model" order="DESC"/></td>
            <td>Colors</td>
            <td>Display size <tags:sortLink field="displaySizeInches" order="ASC"/> <tags:sortLink field="displaySizeInches" order="DESC"/></td>
            <td>Price <tags:sortLink field="price" order="ASC"/> <tags:sortLink field="price" order="DESC"/></td>
            <td>Quantity</td>
            <td>Action</td>
        </tr>
        </thead>
        <c:forEach var="phone" items="${phones}">
            <tr>
                <td>
                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                </td>
                <td>${phone.brand}</td>
                <td>${phone.model}</td>
                <td>
                    <c:forEach var="color" items="${phone.colors}">
                        ${color.code}
                    </c:forEach>
                </td>
                <td>${phone.displaySizeInches}"</td>
                <td>$ ${phone.price}</td>
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
    </p>
</tags:master>