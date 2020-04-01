<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <a href="/" class="navbar-brand main-logo">
            Zaplanuj <span>Jedzonko</span>
        </a>
        <ul class="nav nounderline text-uppercase">
            
           <c:choose>
               <c:when test="${authorised == null}">
            <li class="nav-item ml-4">
                <a class="nav-link color-header" href="/login">logowanie</a>
            </li>
            <li class="nav-item ml-4">
                <a class="nav-link color-header" href="/register">rejestracja</a>
            </li>
               </c:when>
               <c:when test="${authorised != null}">
                   <li class="nav-item ml-4">
                       <a class="nav-link color-header" href="/app/dashboard">zaplanuj posiłki</a>
                   </li>
               </c:when>
           </c:choose>
            <li class="nav-item ml-4">
                <a class="nav-link" href="/about">o aplikacji</a>
            </li>
            <li class="nav-item ml-4">
                <a class="nav-link disabled" href="/recipelistall">Przepisy</a>
            </li>
            <li class="nav-item ml-4">
                <a class="nav-link disabled" href="/contact">Kontakt</a>
            </li>
        </ul>
    </nav>
</header>