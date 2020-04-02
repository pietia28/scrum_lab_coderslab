<%--
  Created by IntelliJ IDEA.
  User: airon
  Date: 31.03.2020
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Lista przepisów</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css?family=Charmonman:400,700|Open+Sans:400,600,700&amp;subset=latin-ext"
          rel="stylesheet">
    <link href='<c:url value="/css/style.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
</head>
<body>

<jsp:include page="../include/app-header.jsp"/>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">
        <jsp:include page="../include/leftmenu.jsp" />

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">USUWANIE DANYCH</h3>
                    </div>
                </div>
                <div>
                    <form class="padding-small text-center" action="${pageContext.servletContext.contextPath}" method="post">
                        <h4>Czy na pewno chcesz usunąć ${typeMsg}?</h4>
                        <c:if test="${not empty warning}">
                        <div class="container w-25"><div>
                                <div class="app-error"><c:out value="${warning}"></c:out></div>
                            <c:set  var="warning" value="" />
                            </div>
                        </c:if>
                        <input type="hidden" name="type" value="${param.type}" />
                        <input type="hidden" name="id" value="${param.id}" />
                        <button type="submit" value="true" name="submit" class="btn btn-danger rounded-0 text-light m-1">Usuń</button>
                        <button type="submit" value="false" name="submit" class="btn btn-success rounded-0 text-light m-1">Anuluj</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>



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
