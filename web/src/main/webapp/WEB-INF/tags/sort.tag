<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="pageBean" type="com.es.phoneshop.web.controller.pages.ProductPageData" required="true" %>
<c:if test="${pageBean.sortFields.stream().anyMatch(sortField -> sortField eq field).get()}">
    <tags:sortLink field="${field}" order="ASC"/>
    <tags:sortLink field="${field}" order="DESC"/>
</c:if>