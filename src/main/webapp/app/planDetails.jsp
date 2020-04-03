<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css"
          integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>

<body>
<jsp:include page="../include/app-header.jsp"/>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="../include/leftmenu.jsp"/>

        <div class="m-4 p-3 width-medium ">
            <div class="dashboard-content border-dashed p-3 m-4">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">SZCZEGÓŁY PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="/app/recipe/list" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Powrót</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <div class="schedules-content-header">
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Nazwa planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">${plan.getName()}</p>
                            </div>
                        </div>
                        <div class="form-group row">
                                <span class="col-sm-2 label-size col-form-label">
                                    Opis planu
                                </span>
                            <div class="col-sm-10">
                                <p class="schedules-text">
                                    ${plan.getDescription()}
                                </p>
                            </div>
                        </div>
                    </div>
                    <%------------------------------------------------------------------------------------------------%>
                    <c:if test="${not empty planDetailsList}">

                    <table class="table">
                        <thead>
                        <tr class="d-flex">
                            <th class="col-2">${planDetailsList.get(0).getDayName()}</th>
                            <th class="col-7"></th>
                            <th class="col-1"></th>
                            <th class="col-2"></th>
                        </tr>
                        </thead>
                        <tbody class="text-color-lighter">
                        <tr class="d-flex">
                            <td class="col-2">${planDetailsList.get(0).getMealName()}</td>
                            <td class="col-7">${planDetailsList.get(0).getRecipe().getName()}</td>
                            <td class="col-1 center">
                                <a href="/app/plan/delete-recipe?id=${planDetailsList.get(0).getId()}&recipeId=${planDetailsList.get(0).getRecipe().getId()}&planId=${planDetailsList.get(0).getPlan().getId()}"
                                   class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                            </td>
                            <td class="col-2 center">
                                <a href="/app/recipe/details?id=${planDetailsList.get(0).getRecipe().getId()}"
                                   class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                            </td>
                        </tr>

                        <c:forEach items="${planDetailsList}" var="planDetails" varStatus="theCount" begin="1">

                            <%--sprawdzamy czy dzień tygodnia jest taki sam jak wcześniej--%>
                        <c:choose>
                        <c:when test="${planDetailsList.get(theCount.index).getDayName() == planDetailsList.get(theCount.index - 1).getDayName()}">

                            <%--ten sam dzień tygodnia--%>
                            <tr class="d-flex">
                                <td class="col-2">${planDetails.getMealName()}</td>
                                <td class="col-7">${planDetails.getRecipe().getName()}</td>
                                <td class="col-1 center">
                                    <a href="/app/plan/delete-recipe?id=${planDetailsList.get(theCount.index).getId()}&recipeId=${planDetailsList.get(theCount.index).getRecipe().getId()}&planId=${planDetailsList.get(0).getPlan().getId()}"
                                       class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                </td>
                                <td class="col-2 center">
                                    <a href="/app/recipe/details?id=${planDetailsList.get(theCount.index).getRecipe().getId()}"
                                       class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>

                            <%--następny dzień tygodnia--%>
                        </tbody>
                    </table>
                    <table class="table">
                        <thead>
                        <tr class="d-flex">
                            <th class="col-2">${planDetails.getDayName()}</th>
                            <th class="col-7"></th>
                            <th class="col-1"></th>
                            <th class="col-2"></th>
                        </tr>
                        </thead>
                        <tbody class="text-color-lighter">
                        <tr class="d-flex">
                            <td class="col-2">${planDetails.getMealName()}</td>
                            <td class="col-7">${planDetails.getRecipe().getName()}</td>
                            <td class="col-1 center">
                                <a href="/app/plan/delete-recipe?id=${planDetailsList.get(theCount.index).getId()}&recipeId=${planDetails.getRecipe().getId()}&planId=${planDetailsList.get(0).getPlan().getId()}"
                                   class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                            </td>
                            <td class="col-2 center">
                                <a href="/app/recipe/details?id=${planDetails.getRecipe().getId()}"
                                   class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                            </td>
                        </tr>

                        </c:otherwise>
                        </c:choose>
                        </c:forEach>

                        </c:if>

                        </tbody>
                    </table>
                    <%------------------------------------------------------------------------------------------------%>
                </div>
            </div>
        </div>
    </div>
</section>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>

