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
    <p>
        Found <c:out value="${phones.size()}"/> phones.
    <table border="1px">
        <thead>
        <tr>
            <td>Image</td>
            <td>Brand <tags:sortLink field="BRAND" order="ASC"/> <tags:sortLink field="BRAND" order="DESC"/></td>
            <td>Model <tags:sortLink field="MODEL" order="ASC"/> <tags:sortLink field="MODEL" order="DESC"/></td>
            <td>Colors</td>
            <td>Display size <tags:sortLink field="DISPLAY_SIZE_INCHES" order="ASC"/> <tags:sortLink field="DISPLAY_SIZE_INCHES" order="DESC"/></td>
            <td>Price <tags:sortLink field="PRICE" order="ASC"/> <tags:sortLink field="PRICE" order="DESC"/></td>
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
                <td><input name="quantity" value="1"></td>
                <td><button type="button" class="btn btn-outline-success">Add to</button></td>
            </tr>
        </c:forEach>
    </table>
    </p>
</tags:master>