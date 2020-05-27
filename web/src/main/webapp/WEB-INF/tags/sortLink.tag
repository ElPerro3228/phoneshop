<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<a href="productList?query=${param.query}&field=${field}&order=${order}">
    <i class="${order eq "ASC" ? "fas fa-arrow-up" : "fas fa-arrow-down"}"></i>
</a>