<%-- 
    Document   : catergorias
    Created on : 05/08/2021, 22:26:37
    Author     : buckl
--%>

<%/*
    Mostra todas as categorias cadastradas no BD. Pensei em talvez fazer links
para os lançamentos específicos da mesma forma que fiz em contas, mas foquei no
que estava pedido no documento do trabalho.
*/%>
<%@page import="java.lang.String"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="css/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="css/mdb.min.css"/>
        <title>Home</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    
    <body>
      <!-- Background image -->
        <div class="bg-image" style=" background-image: url('img/background.jpg');  height: 100vh; overflow: auto;">
        
        <div class="container">
            <%@include file="Menu.jsp" %>
        </div>
        
        <%ArrayList<String> categorias= new ArrayList<>();
        categorias=(ArrayList<String>)request.getAttribute("categorias");
        %>
        
        <div class="container">
            <h1>Categorias</h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Descrição</th>
                  </tr>
                </thead>
                <tbody>
                  <%for(String aux:categorias){%>
                    <tr>
                    <td><%=aux%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
                
        </div>
        
    </body>
</html>
