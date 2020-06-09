<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<a href="<c:url value="/productList">
            <c:param name="query" value="${param.query}" />
            <c:param name="sortField" value="${field}" />
            <c:param name="sortOrder" value="${order}" />
        </c:url>">
    <i class="${order eq "ASC" ? "fas fa-arrow-up" : "fas fa-arrow-down"}"></i>
</a>