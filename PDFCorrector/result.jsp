<%@ page import="com.servlet.browse.Files" %>
<%@ page import="java.io.File" %> 



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PDFCorrector - Resultat</title>
       
    </head>
    <script>

function getParamValue(param,url)
{
	var u = url == undefined ? document.location.href : url;
	var reg = new RegExp('(\\?|&|^)'+param+'=(.*?)(&|$)');
	matches = u.match(reg);
	return matches[2] != undefined ? decodeURIComponent(matches[2]).replace(/\+/g,' ') : '';
}

function init() {

//on teste si le fichier online_correction existe ou pas
bouton=getParamValue('bouton',document.location.href);
if (bouton=='false'){
document.getElementById("boutonJSP").type = 'hidden';
}
}

window.onload = init; 

</script>
    
    <body>
           
        <h1> Corrections </h1>
        <table width="100%" cellspacing="0" border="5">
           <tr>
            </tr>
            <tr>
                <th align="center">Numero</th>
                <th align="center">Nom</th>
                <th align="center">Taille</th>
                <th align="center">Telecharger</th>
            </tr>
	  		
	  		
	  		
	  		<% Files listFiles = new Files(new File(this.getServletContext().getRealPath("/")+"/Correction"));%>
            <% File[] listTXT= listFiles.getFilesByType("txt"); %>
            <% for (int i = 0; i < listTXT.length; i++) { %>
			<tr>
				<td align="center"><%= i+1 %></td>
                <td align="center"><%= listTXT[i].getName() %></td>
                <td align="center"><%= listTXT[i].length() %></td>
				<td align="center">
                    <a href="<%=request.getContextPath()%>/FileUpload?action=download&docName=<%=listTXT[i].getName()%>&docSize=<%=listTXT[i].length()%>" title="Download"><img src="<% this.getServletContext().getRealPath("/");%>/PDFCorrector/download.png" alt="Download" /></a>
                </td>
            </tr>
			<% } %>
        </table>
        <br/>
        <h1> Rapports </h1>
        <table width="100%" cellspacing="0" border="5">
           <tr>
            </tr>
            <tr>
                <th align="center">Numero</th>
                <th align="center">Nom</th>
                <th align="center">Taille</th>
                <th align="center">Telecharger</th>
            </tr>
	  	    <% File[] listPDF= listFiles.getFilesByType("pdf"); %>
            <% for (int i = 0; i < listPDF.length; i++) { %>
			<tr>
				<td align="center"><%= i+1 %></td>
                <td align="center"><%= listPDF[i].getName() %></td>
                <td align="center"><%= listPDF[i].length() %></td>
				<td align="center">
                    <a href="<%=request.getContextPath()%>/FileUpload?action=download&docName=<%=listPDF[i].getName()%>&docSize=<%=listPDF[i].length()	%>" title="Download"><img src="<% this.getServletContext().getRealPath("/");%>/PDFCorrector/download.png" alt="Download" /></a>
                </td>
            </tr>
			<% } %>
        </table>

<br/>
<form action="<%=request.getContextPath()%>/online_correction.jsp" method="get" >
<input type="submit" value="Corriger le fichier en ligne " class="art-button" id="boutonJSP"/>
		</form>
<br/>

Si la list des errors ne s'affiche pas, rafraichir la page  <a href="javascript:location.reload(true)">Ici</a>

<br/>
<p>Pour corriger un nouveau fichier, c'est par <a href="<%=request.getContextPath()%>/"> ici</a></p>


<br/>   
    </body>

</html>
