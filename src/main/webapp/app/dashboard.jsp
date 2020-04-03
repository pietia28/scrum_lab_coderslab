<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
<jsp:include page="../include/app-header.jsp"/>
<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="../include/leftmenu.jsp"/>


        <div class="m-4 p-4 width-medium">
            <div class="dashboard-header m-4">
                <div class="dashboard-menu">
                    <div class="menu-item border-dashed">
                        <a href="/app/recipe/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="/app/plan/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj plan</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href="/app/recipe/plan/add">
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis do planu</span>
                        </a>
                    </div>
                </div>
                <div class="dashboard-alerts">
                    <div class="alert-item alert-info">
                        <i class="fas icon-circle fa-info-circle"></i>
                        <span class="font-weight-bold">Liczba przepisów: ${numberofrecipes}</span>
                    </div>
                    <div class="alert-item alert-light">
                        <i class="far icon-calendar fa-calendar-alt"></i>
                        <span class="font-weight-bold">Liczba planów: ${numberofplans}</span>
                    </div>
                </div>
            </div>
            <div class="m-4 p-4 border-dashed">
                <h2 class="dashboard-content-title">
                    <span>Ostatnio dodany plan z przepisami:</span> ${plan.name}
                </h2>
                <c:if test="${not empty planDetails}">
                <table class="table">
                    <thead>
                    <tr class="d-flex">
                        <th class="col-2">${planDetails.get(0).getDayName()}</th>
                        <th class="col-8"></th>
                        <th class="col-2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="d-flex">
                        <td class="col-2">${planDetails.get(0).getMealName()}</td>

                        <td class="col-8">${planDetails.get(0).recipe.getIngredients()}</td>

                        <td class="col-2"><a href="/app/recipe/details?id=${planDetails.get(0).recipe.getId()}"><button type="button" class="btn btn-primary rounded-0">Szczegóły</button></a></td>
                    </tr>
                    </tbody>
                </table>
                <c:forEach items="${planDetails}" var="planDetail" varStatus="theCount" begin ="1">
                <c:choose >
                <c:when test ="${planDetails.get(theCount.index).getDayName() == planDetails.get(theCount.index - 1).getDayName()}">
                <table class="table">
                    <tbody>
                <tr class="d-flex">
                    <td class="col-2">${planDetail.mealName}</td>

                    <td class="col-8">${planDetail.recipe.ingredients}</td>

                    <td class="col-2"><a href="/app/recipe/details?id=${planDetail.recipe.id}"><button type="button" class="btn btn-primary rounded-0">Szczegóły</button></a></td>
                </tr>
                </tbody>
                </table>
                </c:when>
                <c:otherwise>
                <table class="table">
                    <thead>
                    <tr class="d-flex">
                        <th class="col-2">${planDetail.dayName}</th>
                        <th class="col-8"></th>
                        <th class="col-2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="d-flex">
                        <td class="col-2">${planDetail.mealName}</td>

                        <td class="col-8">${planDetail.recipe.ingredients}</td>

                        <td class="col-2"><a href="/app/recipe/details?id=${planDetail.recipe.id}"><button type="button" class="btn btn-primary rounded-0">Szczegóły</button></a></td>
                    </tr>
                    </tbody>
                </table>
                </c:otherwise>
                </c:choose>
                </c:forEach>
                    </c:if>
            </div>
        </div>
    </div>
</section>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>