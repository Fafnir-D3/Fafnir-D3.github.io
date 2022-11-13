<%-- 
    Document   : index
    Created on : 05/08/2021, 22:17:21
    Author     : buckl
--%>

<%/*
Mostra os dados do usuario logado
*/%>
<%@page import="model.Usuario"%>
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
        
        <%Usuario aux=(Usuario)request.getAttribute("usuario");
            %>
        
        <div class="container">
            <h1>Meus Dados</h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">CPF</th>
                  </tr>
                </thead>
                <tbody>
                    <tr>
                    <td><%=aux.getNome()%></td>
                    <td><%=aux.getCpf()%></td>
                    </tr>
                </tbody>
            </table>
        </div>
            
            <div class="container">
            <a href="FazLogoff" class="btn btn-primary">Sair</a>
            </div>
            
        </div>
    </body>
</html>
