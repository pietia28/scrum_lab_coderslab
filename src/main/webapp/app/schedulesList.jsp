<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/> "/>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">

</head>
<body>
<jsp:include page="../include/app-header.jsp"/>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="../include/leftmenu.jsp"/>
        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">LISTA PLANÓW</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="/app/plan/add" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Dodaj plan</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <table class="table border-bottom">
                        <thead>
                        <tr class="d-flex">
                            <th class="col-1">ID</th>
                            <th class="col-2">NAZWA</th>
                            <th class="col-7">OPIS</th>
                            <th class="col-2 center">AKCJE</th>
                        </tr>
                        </thead>
                        <tbody class="text-color-lighter">
                        <c:forEach items="${adminSchedules}" var="schedule" varStatus="scheduleNo">
                        <tr class="d-flex">
                            <td class="col-1">${scheduleNo.index +1}</td>
                            <td class="col-2">
                                ${schedule.getName()}
                            </td>
                            <td class="col-7">
                                ${schedule.getDescription()}
                            </td>
                            <td class="col-2 d-flex align-items-center justify-content-center flex-wrap"><a href="/app/plan/delete?id=${schedule.getId()}" class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
                                <a href="/app/plan/details?id=${schedule.getId()}" class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>
                                <a href="/app-edit-schedules.html" class="btn btn-warning rounded-0 text-light m-1">Edytuj</a>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
