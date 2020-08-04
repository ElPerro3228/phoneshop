<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart Page">
    <sf:form method="post" modelAttribute="quickOrder">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="container">
            <c:forEach var="orderItem" items="${quickOrder.orderItems}" varStatus="status">
                <div class="row">
                    <div class="column-sm-6">
                        <sf:input path="orderItems[${status.index}].phoneId" value="${orderItem.phoneId}"/>
                        <sf:errors path="orderItems[${status.index}].phoneId" cssClass="error" cssStyle="color: red"/>
                    </div>
                    <div class="column-sm-6">
                        <sf:input path="orderItems[${status.index}].quantity" value="${orderItem.quantity}"/>
                        <sf:errors path="orderItems[${status.index}].quantity" cssClass="error" cssStyle="color: red"/>
                    </div>
                </div>
            </c:forEach>
            <input type="submit" value="Submit">
        </div>
    </sf:form>
</tags:master>