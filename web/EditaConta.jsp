<%-- 
    Document   : EditaConta
    Created on : 17/08/2021, 17:34:54
    Author     : buckl
--%>

<%/*
    Pagina auxiliar para editar contas, ela recebe todos os parametros para 
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
        <form method="POST" action="EditaConta">
            <div class="form-group">
                <label for="nome_conta">Nome da Conta</label>
                <input type="text" class="form-control" required name="nome_conta" id="nomeregistro" value="<%=request.getParameter("nomeAntigo")%>">
            </div>
            <div class="form-group">
                <label for="banco">Banco</label>
                <input type="text" class="form-contro banco" required name="banco" id="bancoregistro" value="<%=request.getParameter("bancoAntigo")%>">
            </div>
            <div class="form-group">
                <label for="agencia">Agência</label>
                <input type="text" class="form-control agencia" required name="agencia" id="agenciaregistro" value="<%=request.getParameter("agenciaAntigo")%>">
            </div>
            <div class="form-group">
                <label for="conta_corrente">Conta Corrente</label>
                <input type="text" class="form-control contaCorrente" required name="conta_corrente" id="contaregistro" value="<%=request.getParameter("contaAntigo")%>" >
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
            $('.agencia').mask('00000');
            $('.banco').mask('000');
            $('.contaCorrente').mask('0000-0');
        });
    </script>
            
    </body>
</html>
