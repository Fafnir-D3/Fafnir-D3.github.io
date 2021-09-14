
<%/*
    Mostra todos os administradores cadastrados no BD, permite excluir, editar e
inserir administradores.
*/%>
<%@page import="aplicacao.Usuario"%>
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
        </div >
        
        <%ArrayList<Usuario> usuarios=(ArrayList<Usuario>)request.getAttribute("administradores");
            %>
        
        <div class="container">
            <h1>Administradores</h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">CPF</th>
                  </tr>
                </thead>
                <tbody>
                    <%for(Usuario aux:usuarios){
                    String link="ExcluiAdministrador?cpf="+aux.getCpf();
                    %>
                    <tr>
                    <td><%=aux.getNome()%></td>
                    <td><%=aux.getCpf()%></td>
                    <td><a href="<%=link%>" class="btn btn-primary">Excluir</a></td>
                    <td><a href="EditaAdmin.jsp?cpfAntigo=<%=aux.getCpf()%>&nomeAntigo=<%=aux.getNome()%>&suspensoAntigo=<%=aux.getSuspenso()%>&senhaAntigo=<%=aux.getSenha()%>" class="btn btn-primary">Editar</a></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
                
        <div class="container">
            
            <h1>Cadastrar Novo Administrador</h1>
        
            <button type="button" id="formButton" name="formButton" class="btn btn-primary">Mostrar</button>
            
            <form method="POST" action="InserirAdministrador" id="form1" name="form1">
            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" class="form-control" required name="nome" id="nomeregistro">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" class="form-control cpf" required name="cpf" id="cpfregistro">
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="text" class="form-control senha" required name="senha" id="senharegistro">
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" required name="suspenso" value="A" id="suspensoregistro">
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
            
        </form>
        </div>
                
        <div class="container" style="margin-top: 40px; margin-bottom: 40px;"> 
            <a href="FazLogoff" class="btn btn-primary">Sair</a>
        </div>
    
        </div>
                
        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/jquery.mask.min.js"></script>
        <script type="text/javascript">
        $(document).ready(function() {
            $('.cpf').mask('000.000.000-00',{reverse:true});
            $('.senha').mask('000');
        });
        </script>
                
        <script type="text/javascript">
        $(document).ready(function() {
            $("#form1").hide();
            $("#formButton").click(function() {
                $("#form1").toggle();
            });
        });
    </script>
        
    </body>
    
</html>
