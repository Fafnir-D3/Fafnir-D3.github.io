<%-- 
    Document   : lancamentos
    Created on : 05/08/2021, 22:29:59
    Author     : buckl
--%>

<%/*
    Essa página é usada em 2 situações, para visualizar todos os lançamentos do
usuario logado, ou para visualizar apenas os lançamentos de uma certa conta.
Para ver apenas de uma conta é necessário chegar nessa página vindo de um link
em "Contas.jsp". E para funcionar corretamente eu uso alguns scripts.
    Além disso, aqui também é mostrado se a conta está em negativo e é possível 
editar, excluir e inserir lançamentos.
*/%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Formatter"%>
<%@page import="model.Lancamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.Math"%>
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
        
        <%ArrayList<Lancamento> lancamentos= new ArrayList<>();
        lancamentos=(ArrayList<Lancamento>)request.getAttribute("lancamentos");
        int contas=(int)request.getAttribute("contas");
        double contador=0;
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00",otherSymbols);
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
                  <%for(Lancamento aux:lancamentos){
                  if(aux.getOperacao().equals("C")){contador+=aux.getValor();}
                  else{contador-=aux.getValor();}
                  %>
                    <tr>
                    <td><%=aux.getNome_conta()%></td>
                    <td><%=aux.getCategoria()%></td>
                    <td><%=df.format(aux.getValor()) %></td>
                    <%String cor;
                        if(aux.getOperacao().equals("D")){cor="red";}
                    else{cor="green";}%>
                    <td style="color:<%=cor%>"><%=aux.getOperacao()%></td>
                    <td><%=aux.getData()%></td>
                    <td><%=aux.getDescricao()%></td>
                    <td><a href="ExcluiLancamento?id=<%=aux.getId()%>" class="btn btn-primary">Excluir</a></td>
                    <td><a href="EditaLancamento.jsp?id=<%=aux.getId()%>&nome_contaAntigo=<%=aux.getNome_conta()%>&categoriaAntigo=<%=aux.getCategoria()%>&valorAntigo=<%=df.format(aux.getValor()) %>&operacaoAntigo=<%=aux.getOperacao()%>&dataAntigo=<%=aux.getData()%>&descricaoAntigo=<%=aux.getDescricao()%>" class="btn btn-primary">Editar</a></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
        </div>
                
                
        <%String aux="";
            if (contador>=0 || contas==0){aux="d-none";}%>
        <div class="<%=aux%> container alert alert-danger" id="alert1" name="alert1" role="alert">
            A conta está em negativo!
        </div>
        
        <%if(contas==0){aux="d-none";}else{aux="";}%> 
        <div class="<%=aux%>  container" id="saldo" name="saldo">
            <h1>Saldo atual : R$ <%=df.format(contador)%></h1>
        </div>
                
        <div class="container">
            
            <h1>Cadastrar Novo Lançamento</h1>
            
            <button type="button" id="formButton" name="formButton" class="btn btn-primary">Mostrar</button>
            
            <form method="POST" action="InserirLancamento" id="form1" name="form1">
            <div class="form-group">
                <label for="nome_conta">Nome da Conta</label>
                <input type="text" class="form-control" required name="nome_conta" id="nomeregistro">
            </div>
            <div class="form-group">
                <label for="categoria">Categoria</label>
                <input type="text" class="form-control" required name="categoria" id="categoriaregistro">
            </div>
            <div class="form-group">
                <label for="valor">Valor</label>
                <input type="text" class="form-control valor" required name="valor" id="valorregistro">
            </div>
            <div class="form-group">
                <label for="operacao">Operação</label>
                <input type="text" class="form-control operacao" required name="operacao" placeholder="C/D" id="operacaoregistro">
            </div>
            <div class="form-group">
                <label for="data">Data</label>
                <input type="text" class="form-control data" required name="data" id="dataregistro">
            </div>
            <div class="form-group">
                <label for="descricao">Descrição</label>
                <input type="text" class="form-control" name="descricao" id="descricaoregistro">
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