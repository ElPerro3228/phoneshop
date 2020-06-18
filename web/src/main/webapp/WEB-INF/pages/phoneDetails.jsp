<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Phone Details" miniCart="${miniCart}">
    <div class="container">
        <a href="<c:url value="/productList"/>">
            <button name="productList" type="button" class="btn btn-outline-success">Back to product list</button>
        </a>
        <div class="row">
            <div class="col-sm-6">
                <h4><c:out value="${phone.model}"/></h4>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                <p>${phone.description}</p>
                <table border="1px" class="table">
                    <tr>
                        <td>
                            <strong>Price:</strong> <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/> <br/>
                            <input id="${phone.id}" value="1">
                            <button name="add-to-cart" type="button" class="btn btn-outline-success" data-phone-id="${phone.id}">Add to</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col-sm-6">
                <h6>Display</h6>
                <table border="1px" class="table">
                    <tr>
                        <td>Size</td>
                        <td><c:out value="${phone.displaySizeInches} \""/></td>
                    </tr>
                    <tr>
                        <td>Resolution</td>
                        <td><c:out value="${phone.displayResolution}"/></td>
                    </tr>
                    <tr>
                        <td>Technology</td>
                        <td><c:out value="${phone.displayTechnology}"/></td>
                    </tr>
                    <tr>
                        <td>Pixel density</td>
                        <td><c:out value="${phone.pixelDensity}"/></td>
                    </tr>
                </table>
                <h6>Dimensions & weight</h6>
                <table border="1px" class="table">
                    <tr>
                        <td>Length</td>
                        <td><c:out value="${phone.lengthMm}mm"/></td>
                    </tr>
                    <tr>
                        <td>Width</td>
                        <td><c:out value="${phone.widthMm}mm"/></td>
                    </tr>
                    <tr>
                        <td>Color</td>
                        <td>
                            <c:forEach var="color" items="${phone.colors}">
                                <c:out value="${color.code}, "/>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>Weight</td>
                        <td><c:out value="${phone.weightGr}"/></td>
                    </tr>
                </table>
                <h6>Camera</h6>
                <table border="1px" class="table">
                    <tr>
                        <td>Front</td>
                        <td><c:out value="${phone.frontCameraMegapixels} megapixeles"/></td>
                    </tr>
                    <tr>
                        <td>Back</td>
                        <td><c:out value="${phone.backCameraMegapixels} megapixeles"/></td>
                    </tr>
                </table>
                <h6>Battery</h6>
                <table border="1px" class="table">
                    <tr>
                        <td>Talk time</td>
                        <td><c:out value="${phone.talkTimeHours} hours"/></td>
                    </tr>
                    <tr>
                        <td>Stand by time</td>
                        <td><c:out value="${phone.standByTimeHours} hours"/></td>
                    </tr>
                    <tr>
                        <td>Battery capacity</td>
                        <td><c:out value="${phone.batteryCapacityMah}mAh"/></td>
                    </tr>
                    <tr>
                        <td>Pixel density</td>
                        <td><c:out value="${phone.pixelDensity}"/></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</tags:master>