<%--
  Created by IntelliJ IDEA.
  User: shut2204
  Date: 31.07.2022
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
    <meta charset="utf-8">
    <title>Payments</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file="/css/style1.css"%>
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
</head>

<body>
<nav>
    <input type="checkbox" id="check">
    <label for="check" class="checkbtn">
        <i class="fas fa-bars"></i>
    </label>
    <label class="logo">Payments</label>
    <ul>
        <li><a href="index.jsp">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Services</a></li>
        <li><a class="active" href="controller?command=logout">Logout</a></li>
    </ul>
</nav>

<section>
    <span class="large">Your Cards:</span>

    <div class="parent">
        <c:if test="${sessionScope.get('cards') != null }">
            <c:forEach var="card" items="${sessionScope.get('cards')}">
                <div class="card">
                    <h3 class="bank">Payments</h3>
                    <img src="img/chip.png" class="chip" />
                    <img src="img/contactless-indicator.png" class="indicator" />
                    <h3 class="number">${card.getIdcard()}</h3>
                    <h5 class="card-holder">
                        <span>card type</span><br />
                            ${card.getName_card()}
                    </h5>
                    <h5 class="valid"><span>Balance</span><br />${card.getBalance()}</h5>
                    <img src="img/visa.png" class="logo1" />
                </div>
            </c:forEach>
        </c:if>

        <div class="card">
            <button id="myBtn" class="addNewCardClass">+ Add new Card</button>
            <div class="popup" id="mypopup">
                <div class="popup-content">
                    <span class="close">&times;</span>
                    <form class="form" action="controller" method="post">
                        <p class="par" >Choose type</p>
                        <select class="sel" name="type" required="required">
                            <option value="">Choose...</option>
                            <option value="1">Personal</option>
                            <option value="2">Special</option>
                        </select>
                        <div class="Button">
                            <input class="button-29" role="button" type="submit" value="Submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>


    </div>
</section>

<script>
    let popup = document.getElementById('mypopup'),
        popupToggle = document.getElementById('myBtn'),
        popupClose = document.querySelector('.close');

    popupToggle.onclick = function () {
        popupToggle.style.display = "none";
        popup.style.display = "block";
    };

    popupClose.onclick = function () {
        popup.style.display = "none";
        popupToggle.style.display = "block";
    };

    window.onclick = function (e) {
        if(e.target == popup){
            popup.style.display = "none";
            popupToggle.style.display = "block";
        }
    }
</script>
</body>
</html>
