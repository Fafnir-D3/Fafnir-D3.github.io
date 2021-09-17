<%-- 
    Document   : ContasAdmin
    Created on : 07/08/2021, 18:53:38
    Author     : buckl
--%>

<%/*
    Mostra todas as contas de todos os usuarios cadastrados no BD
*/%>
<%@page import="aplicacao.Conta"%>
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
            <%@include file="MenuAdmin.jsp" %>
        </div>
        
        <%ArrayList<Conta> contas= new ArrayList<>();
        contas=(ArrayList<Conta>)request.getAttribute("contas");
        %>
        
        <div class="container">
            <h1>Contas</h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Nome da Conta</th>
                    <th scope="col">Banco</th>
                    <th scope="col">Agência</th>
                    <th scope="col">Conta Corrente</th>
                  </tr>
                </thead>
                <tbody>
                    <%for(Conta aux:contas){%>
                    <tr>
                    <td><%=aux.getNome_conta()%></td>
                    <td><%=aux.getBanco()%></td>
                    <td><%=aux.getAgencia()%></td>
                    <td><%=aux.getConta_corrente()%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div> 
                
        </div>
                
    </body>
</html>
