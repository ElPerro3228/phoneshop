<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="productListPage" required="true" %>
<%@ attribute name="text" required="true" %>
<a class="page-link" href="<c:url value="/productList?query=${param.query}&field=${param.field}&order=${param.order}&page=${productListPage}"/>">${text}</a>