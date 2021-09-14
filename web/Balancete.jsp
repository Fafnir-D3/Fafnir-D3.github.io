<%-- 
    Document   : Balancete
    Created on : 18/08/2021, 22:51:58
    Author     : buckl
--%>

<%/*
    Pagina auxiliar para gerar balancetes de meses especificos.
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
            <%@include file="Menu.jsp" %>
        </div>
        
        <div class="container">
            <h1>Balancete</h1>
            <h1 style="margin-top: 40px">Escolha a conta e mês</h1>
            <form method="POST" action="MostrarBalancete" id="form1" name="form1">
            <div class="form-group">
                <label for="conta">Conta</label>
                <input type="text" class="form-control" required name="conta" id="contaregistro">
            </div>
            <div class="form-group">
                <label for="data">Data</label>
                <input type="text" class="form-control data" required name="data" id="dataregistro">
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
        });
    </script>
        
    </body>
</html>
