<%-- 
    Document   : AdicionaCategoria
    Created on : 07/08/2021, 17:43:54
    Author     : buckl
--%>

<%/*
    Mostra as categorias cadastradas no BD e permite excluir, editar, e inserir
novas categorias.
*/%>
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
                    <td><a href="ExcluiCategoria?categoria=<%=aux%>" class="btn btn-primary">Excluir</a></td>
                    <td><form method="POST" action="EditaCategoria" >
                            <input type="text" class="form-control" required name="editado" id="editado" placeholder="Novo Nome">
                        </td>
                        <input type="hidden" class="form-control" required name="antigo" id="antigo" value="<%=aux%>">
                        <td><button type="submit" class="btn btn-primary" >Enviar</button></td>
                        </form>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
        
        <div class="container">
            <h1>Nova Categoria</h1>
        <form method="POST" action="MostrarCategorias">
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <input type="text" class="form-control" required name="categoria" id="categoriaregistro">
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
            
        </form>
        </div>
                
        </div>
                
    </body>
</html>
