<%-- 
    Document   : contas
    Created on : 05/08/2021, 22:29:12
    Author     : buckl
--%>

<%/*
    Mostra todas as contas do usuario logado, e permite editar, excluir e inserir
contas. Além disso gera links para cada conta que mostram todos os lançamentos 
apenas daquela conta.
*/%>
<%@page import="model.Conta"%>
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
                    <td><a href="MostrarLancamentos?conta=<%=aux.getId()%>"><%=aux.getNome_conta()%></a></td>
                    <td><%=aux.getBanco()%></td>
                    <td><%=aux.getAgencia()%></td>
                    <td><%=aux.getConta_corrente()%></td>
                    <td><a href="ExcluiConta?conta_corrente=<%=aux.getConta_corrente()%>" class="btn btn-primary">Excluir</a></td>
                    <td><a href="EditaConta.jsp?id=<%=aux.getId()%>&nomeAntigo=<%=aux.getNome_conta()%>&bancoAntigo=<%=aux.getBanco()%>&agenciaAntigo=<%=aux.getAgencia()%>&contaAntigo=<%=aux.getConta_corrente()%>" class="btn btn-primary">Editar</a></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div> 
                
        <div class="container">
            
            <h1>Cadastrar Nova Conta</h1>
            
            <button type="button" id="formButton" name="formButton" class="btn btn-primary">Mostrar</button>
            
            <form method="POST" action="InserirConta" id="form1" name="form1">
            <div class="form-group">
                <label for="nome_conta">Nome da Conta</label>
                <input type="text" class="form-control" required name="nome_conta" id="nomeregistro">
            </div>
            <div class="form-group">
                <label for="banco">Banco</label>
                <input type="text" class="form-control banco" required name="banco" id="bancoregistro">
            </div>
            <div class="form-group">
                <label for="agencia">Agência</label>
                <input type="text" class="form-control agencia" required name="agencia" id="agenciaregistro">
            </div>
            <div class="form-group">
                <label for="conta_corrente">Conta Corrente</label>
                <input type="text" class="form-control contaCorrente" required name="conta_corrente" id="contaregistro">
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
            
        </form>
        </div>
        
        </div>
                
        <script src="js/jquery-3.4.1.min.js"></script>
    
    <script type="text/javascript">
        $(document).ready(function() {
            $("#form1").hide();
            $("#formButton").click(function() {
                $("#form1").toggle();
            });
        });
    </script>
    
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
