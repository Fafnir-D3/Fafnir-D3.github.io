<%-- 
    Document   : lancamentos
    Created on : 05/08/2021, 22:29:59
    Author     : buckl
--%>

<%/*
Mostra todos os Lancamentos cadastrados no BD
*/%>
<%@page import="aplicacao.Lancamento"%>
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
        
        <%ArrayList<Lancamento> lancamentos= new ArrayList<>();
        lancamentos=(ArrayList<Lancamento>)request.getAttribute("lancamentos");
        %>
        
        <div class="container">
            <h1>Lançamentos</h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Nome da Conta</th>
                    <th scope="col">Categoria</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Operação</th>
                    <th scope="col">Data</th>
                    <th scope="col">Descrição</th>
                  </tr>
                </thead>
                <tbody>
                  <%for(Lancamento aux:lancamentos){%>
                    <tr>
                    <td><%=aux.getNome_conta()%></td>
                    <td><%=aux.getCategoria()%></td>
                    <td><%=aux.getValor() %></td>
                    <% String color;
                    if(aux.getOperacao().equals("D"))color="red";
                    else color="green";%>
                    <td style="color:<%=color%>"><%=aux.getOperacao()%></td>
                    <td><%=aux.getData()%></td>
                    <td><%=aux.getDescricao()%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
                
        </div>
                
    </body>
</html>