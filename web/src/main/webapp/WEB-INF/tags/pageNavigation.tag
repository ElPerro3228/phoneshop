<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="currentPage" required="true" %>
<%@ attribute name="pagesNumber" required="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <tags:pageLink productListPage="${currentPage - 1}" text="<<"/>
            </li>
        </c:if>

        <li class="page-item"><tags:pageLink productListPage="${currentPage}" text="${currentPage}"/></li>
        <c:if test="${currentPage + 1 <= pagesNumber}">
            <li class="page-item"><tags:pageLink productListPage="${currentPage + 1}" text="${currentPage + 1}"/></li>
        </c:if>
        <c:if test="${currentPage + 2 <= pagesNumber}">
            <li class="page-item"><tags:pageLink productListPage="${currentPage + 2}" text="${currentPage + 2}"/></li>
        </c:if>

        <c:if test="${currentPage + 1 < pagesNumber}">
            <li class="page-item">
                <tags:pageLink productListPage="${currentPage + 1}" text=">>"/>
            </li>
        </c:if>
    </ul>
</nav>