<%-- 
    Document   : Balancete
    Created on : 18/08/2021, 22:51:58
    Author     : buckl
--%>

<%/*
    Faz o balancete dos lancamentos de uma conta em um mes especifico. Usei 
porcentagens baseadas no crédito total, e usei porcentagens negativas para 
representar os débitos, de forma que os negativos podem exceder 100%.
    Além disso, também alerta se naquele mês específico a conta está em negativo
*/%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.util.Locale"%>
<%@page import="model.Lancamento"%>
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
        
        <%ArrayList<Lancamento> lancamentos= new ArrayList<>();
        lancamentos=(ArrayList<Lancamento>)request.getAttribute("lancamentos");
        double contador1=0;
        double contador2=0;
        Locale currentLocale = Locale.getDefault();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00",otherSymbols);
        %>
        
        <div class="container">
            <%String temp=request.getParameter("data");
            String[] split=temp.split("-");
            temp=(split[1]+"/"+split[0]);%>
            <h1>Balancete do Mês : <%=temp %></h1>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Categoria</th>
                    <th scope="col">Crédito</th>
                    <th scope="col">Débito</th>
                    <th scope="col">Percentual</th>
                  </tr>
                </thead>
                <tbody>
                    
                    <%for(Lancamento aux:lancamentos){ //somando os creditos e debitos
                  if(aux.getOperacao().equals("C")){contador1+=aux.getValor();}
                  else{contador2-=aux.getValor();}}%>
                    
                  <%for(Lancamento aux:lancamentos){%>
                    <tr>
                    <td><%=aux.getCategoria()%></td>
                    <%double valor1=0,valor2=0;
                        if(aux.getOperacao().equals("D")){valor2=aux.getValor();}
                        else{valor1=aux.getValor();}%>
                    <td><%=df.format(valor1)%></td>
                    <td><%=df.format(valor2)%></td>
                    <td><%=(int)((100*(valor1-valor2))/contador1)%>%</td>
                    </tr>
                    <%}%>
                    <tr>
                        <td>TOTAL</td>
                        <td>R$ <%=df.format(contador1)%></td>
                        <td>R$ <%=df.format(contador2)%></td>
                        <td><%=(int)((100*contador2)/contador1)%>%</td>
                    </tr>
                </tbody>
            </table>
        </div>
                
        <div class="container alert alert-danger" id="alert1" name="alert1" role="alert">
            A conta está em negativo nesse mês!
        </div>
                    
        <div class="container">
            <h1>Saldo final : R$ <%=df.format((contador1+contador2))%></h1>
        </div>
        
        </div>
        
    <script src="js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            if(<%=(contador1+contador2)%>>=0)$("#alert1").hide();
        });
    </script>
        
    </body>
</html>
