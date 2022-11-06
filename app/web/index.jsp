<%-- 
    Document   : index
    Created on : 05/08/2021, 22:17:21
    Author     : buckl
--%>

<%@page import="model.UsuariosDAO"%>
<%@page import="model.AdministradoresDAO"%>
<%/*

*/%>
<%@page import="aplicacao.Usuario"%>
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
            
            <img src='img/banco.png' class="rounded mx-auto d-block" style="margin-top: 40px" />
            
        <div class="container-md d-flex justify-content-center" style=" justify-content: center;
    align-items: center; padding: 20px;">
            <div class="row text-center"><h1>Login</h1>
        <form method="POST" action="ValidaLogin">
            <div class="form-group d-flex justify-content-center">
                <label for="cpf">CPF</label>
                <input type="text" class="form-control d-flex cpf" data-inputmask="'alias': 'cpf'" required name="cpf" id="cpflogin">
            </div>
            <div class="form-group d-flex">
                <label for="senha">Senha</label>
                <input type="password" class="form-control d-flex senha" required name="senha" id="senhalogin">
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
            
        </form>
                </div>
        </div>
        
        <!– Facilita o logoff para testes, ou caso a sessão persista ao fechar o browser–>
        
        <div class="text-center"> 
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
        
    </body>
</html>
