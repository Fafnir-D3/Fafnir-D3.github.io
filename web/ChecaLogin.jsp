<%//reuso em alguns .jsp para checar o login de paginas privadas.
    
    if(session!=null){//checa login
        
            if(session.getAttribute("usuario")==null){

                response.sendRedirect("Erro.jsp");}
    }else response.sendRedirect("Erro.jsp");%>
