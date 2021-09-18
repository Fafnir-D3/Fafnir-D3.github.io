<%-- 
    Document   : EditaFormulario
    Created on : 07/08/2021, 05:49:07
    Author     : buckl
--%>

<%/*
    Pagina auxiliar para editar usuarios, ela recebe todos os parametros para 
auto-completar os dados, permitindo editar multiplos campos ao mesmo tempo. 
*/%>
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
    
    <%@include file="ChecaLogin.jsp" %>
    
    <body>
      <!-- Background image -->
      <div class="bg-image" style=" background-image: url('img/background.jpg');  height: 100vh; overflow: auto;">
          
        <div class="container">
            <h1>Editar </h1>
        <form method="POST" action="EditaUsuario">
            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" class="form-control" required name="nome" id="nomeregistro" value="<%=request.getParameter("nomeAntigo")%>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" class="form-control cpf" required name="cpf" id="cpfregistro" value="<%=request.getParameter("cpfAntigo")%>">
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="text" class="form-control senha" required name="senha" id="senharegistro" value="<%=request.getParameter("senhaAntigo")%>">
            </div>
            <div class="form-group">
                <label for="suspenso">Suspenso</label>
                <input type="text" class="form-control suspenso" required name="suspenso" id="suspensoregistro" value="<%=request.getParameter("suspensoAntigo")%>">
            </div>
            <div class="form-group">
                <input type="hidden" class="form-control" required name="id" value="<%=request.getParameter("id")%>" id="idregistro">
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
            
        </form>
        </div>
        
      </div>
            
            <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/jquery.mask.min.js"></script>
        <script type="text/javascript">
        $(document).ready(function() {
            $('.cpf').mask('000.000.000-00',{reverse:true});
            $('.senha').mask('000');
            $('.suspenso').mask('Z', {
            translation: {
                'Z': {
                    pattern: /[S|N]/
                } 
            }
            });
        });
    </script>
    
    </body>
</html>
