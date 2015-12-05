<%@ page import="ua.sumdu.greenberg.model.objects.Product" %>
<%@ page import="ua.sumdu.greenberg.model.objects.User" %>
<%@ page language="java" pageEncoding="UTF-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="author" content="Greenberg Dima">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="./css/style-product.css"/>
    <script src="./js/jquery-2.1.3.js"></script>
    <script src="./js/product.js"></script>
    <title>Buy it now a product</title>
    <!--[if lte IE 8]>
    <script type="text/javascript">alert("Your web browser is outdated Please upgrade your browser!");
        window.location.href="http://www.google.com/intl/ru/chrome"</script>
    <![endif]-->
</head>
<body>
<%@include file="../jspf/product-header.jspf" %>
    <div class="container-product">
        <h2>Credit Card Payment</h2>
        <h4>Enter your credit card details</h4>
        <% Product product = (Product) request.getAttribute("product");
            if (product != null){ %>
        <table align="center">
            <form>
                <tr>
                    <td></td>
                    <td>Price :</td>
                    <td><%=product.getBuyoutPrice()%> $</td>
                </tr>
                <tr>
                    <td><img src="./images/creditcards.png" width="150" height="30"></td>
                    <td>The owner cards :</td>
                    <td><input type="text" id="name" size="15" maxlength="30"
                               onkeyup="return checkOnHTML(this);" required autofocus></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Card type :</td>
                    <td><select>
                        <option>Visa</option>
                        <option>Master Card</option>
                    </select></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Card number :</td>
                    <td><input type="text" id="numberCard" size="15" maxlength="30"
                               onkeyup="return checkOnHTML(this);" required></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Verification code</td>
                    <td><input type="text" id="verNumberCard" size="15" maxlength="30"
                               onkeyup="return checkOnHTML(this);"required></td>
                </tr>
                <tr>
                    <td></td>
                    <td>Is valid until :</td>
                    <td><select>
                        <% for (int m = 1; m <= 12; m++) {%>
                        <option><%= m%></option>
                        <% } %>
                        </select>
                        <select>
                        <% for (int y = 2000; y <= 2030; y++) {%>
                            <option><%= y%></option>
                        <% } %>
                        </select>
                    </td>
                </tr>
            </form>
                <tr>
                    <td></td>
                    <td><button onclick='location.href="product?id=<%=product.getId()%>"'>Break</button></td>
                    <% if (user != null) {%>
                    <td><button onclick="realBuy(<%= product.getId()%>, <%= user.getId()%>);">Buy</button></td>
                    <% } %>
                </tr>
            <tr>
                <td><br></td>
                <td><br></td>
            </tr>
        </table>
        <% } %>
<%@include file="../jspf/user-footer.jspf"%>
    </div>

</body>
</html>
