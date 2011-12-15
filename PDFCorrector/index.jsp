<%@ page import="com.servlet.browse.Files" %>
<%@ page import="com.pgrou.pdfcorrection.Main" %>
<%@ page import="java.io.File" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PDFCorrector</title>
       
    </head>
    
    
    <body>
    
	<h1> Bienvenue sur PDFCorrector !</h1><br/>
    	<h2> Envoi du fichier</h2>
     	<form  id="frmUploadDoc" method="post" action="<%=request.getContextPath()%>/FileUpload?action=upload" enctype="multipart/form-data" name="frmUploadDoc"> 
            <table width="100%" cellspacing="0" class="tableList_1 t_space">
                <tr>
                
                    <input name="uploadDocFile" id="uploadDocFile" type="file" class="formTxtBox_1" style="width:450px; background:none;"/></br>
					<p>Nombre de colonnes : 
					<select name="nbcolonnes"> 
						<option value="1">1</option> 
						<option value="2">2</option> 
						<option value="3">3</option>
						<option value="4">4</option>
					</select></p>
					<p>Taille de l'entete haute : <input type="text" name="entetehaute" size="1" value="0"/> cm</p>
					<p>Taille de l'entete basse : <input type="text" name="entetebasse" size="1" value="0"/> cm</p>
                    <input type="submit" name="upload" id="btnUpload" value="Lancer la correction" />
                </tr>
            </table>
        </form>
        <br/>
          
       
  
</body>
</html>
