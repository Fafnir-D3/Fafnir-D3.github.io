<%-- 
    Document   : EditaLancamento
    Created on : 17/08/2021, 17:35:14
    Author     : buckl
--%>

<%/*
    Pagina auxiliar para editar lancamentos, ela recebe todos os parametros para 
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
        <form method="POST" action="EditaLancamento">
            <div class="form-group">
                <label for="nome_conta">Nome da Conta</label>
                <input type="text" class="form-control" required name="nome_conta" id="nomeregistro" value="<%=request.getParameter("nome_contaAntigo")%>">
            </div>
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <input type="text" class="form-control" required name="categoria" id="categoriaregistro" value="<%=request.getParameter("categoriaAntigo")%>">
            </div>
            <div class="form-group">
                <label for="valor">Valor</label>
                <input type="text" class="form-control valor" required name="valor" id="valorregistro" value="<%=request.getParameter("valorAntigo")%>">
            </div>
            <div class="form-group">
                <label for="operacao">Operação</label>
                <input type="text" class="form-control operacao" required name="operacao" id="operacaoregistro" value="<%=request.getParameter("operacaoAntigo")%>" >
            </div>
            <div class="form-group">
                <label for="data">Data</label>
                <input type="text" class="form-control data" required name="data" id="dataregistro" value="<%=request.getParameter("dataAntigo")%>" >
            </div>
            <div class="form-group">
                <label for="descricao">Descrição</label>
                <input type="text" class="form-control" name="descricao" id="descricaoregistro" value="<%=request.getParameter("descricaoAntigo")%>" >
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
            $('.data').mask('0000-00-00');
            $('.valor').mask('#0.00', {reverse: true});
            $('.operacao').mask('Z', {
                translation: {
                  'Z': {
                    pattern: /[C|D]/
                  }
                }
            });
        });
    </script>
            
    </body>
</html>
