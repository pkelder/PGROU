/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.22
 * Generated at: 2011-12-15 16:50:16 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.servlet.browse.Files;
import java.io.File;

public final class result_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write(" \n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("    \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>PDFCorrector - Resultat</title>\n");
      out.write("       \n");
      out.write("    </head>\n");
      out.write("    <script>\n");
      out.write("\n");
      out.write("function getParamValue(param,url)\n");
      out.write("{\n");
      out.write("\tvar u = url == undefined ? document.location.href : url;\n");
      out.write("\tvar reg = new RegExp('(\\\\?|&|^)'+param+'=(.*?)(&|$)');\n");
      out.write("\tmatches = u.match(reg);\n");
      out.write("\treturn matches[2] != undefined ? decodeURIComponent(matches[2]).replace(/\\+/g,' ') : '';\n");
      out.write("}\n");
      out.write("\n");
      out.write("function init() {\n");
      out.write("\n");
      out.write("//on teste si le fichier online_correction existe ou pas\n");
      out.write("bouton=getParamValue('bouton',document.location.href);\n");
      out.write("if (bouton=='false'){\n");
      out.write("document.getElementById(\"boutonJSP\").type = 'hidden';\n");
      out.write("}\n");
      out.write("}\n");
      out.write("\n");
      out.write("window.onload = init; \n");
      out.write("\n");
      out.write("</script>\n");
      out.write("    \n");
      out.write("    <body>\n");
      out.write("           \n");
      out.write("        <h1> Corrections </h1>\n");
      out.write("        <table width=\"100%\" cellspacing=\"0\" border=\"5\">\n");
      out.write("           <tr>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <th align=\"center\">Numero</th>\n");
      out.write("                <th align=\"center\">Nom</th>\n");
      out.write("                <th align=\"center\">Taille</th>\n");
      out.write("                <th align=\"center\">Telecharger</th>\n");
      out.write("            </tr>\n");
      out.write("\t  \t\t\n");
      out.write("\t  \t\t\n");
      out.write("\t  \t\t\n");
      out.write("\t  \t\t");
 Files listFiles = new Files(new File(this.getServletContext().getRealPath("/")+"/Correction"));
      out.write("\n");
      out.write("            ");
 File[] listTXT= listFiles.getFilesByType("txt"); 
      out.write("\n");
      out.write("            ");
 for (int i = 0; i < listTXT.length; i++) { 
      out.write("\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td align=\"center\">");
      out.print( i+1 );
      out.write("</td>\n");
      out.write("                <td align=\"center\">");
      out.print( listTXT[i].getName() );
      out.write("</td>\n");
      out.write("                <td align=\"center\">");
      out.print( listTXT[i].length() );
      out.write("</td>\n");
      out.write("\t\t\t\t<td align=\"center\">\n");
      out.write("                    <a href=\"");
      out.print(request.getContextPath());
      out.write("/FileUpload?action=download&docName=");
      out.print(listTXT[i].getName());
      out.write("&docSize=");
      out.print(listTXT[i].length());
      out.write("\" title=\"Download\"><img src=\"");
 this.getServletContext().getRealPath("/");
      out.write("/PDFCorrector/download.png\" alt=\"Download\" /></a>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("\t\t\t");
 } 
      out.write("\n");
      out.write("        </table>\n");
      out.write("        <br/>\n");
      out.write("        <h1> Rapports </h1>\n");
      out.write("        <table width=\"100%\" cellspacing=\"0\" border=\"5\">\n");
      out.write("           <tr>\n");
      out.write("            </tr>\n");
      out.write("            <tr>\n");
      out.write("                <th align=\"center\">Numero</th>\n");
      out.write("                <th align=\"center\">Nom</th>\n");
      out.write("                <th align=\"center\">Taille</th>\n");
      out.write("                <th align=\"center\">Telecharger</th>\n");
      out.write("            </tr>\n");
      out.write("\t  \t    ");
 File[] listPDF= listFiles.getFilesByType("pdf"); 
      out.write("\n");
      out.write("            ");
 for (int i = 0; i < listPDF.length; i++) { 
      out.write("\n");
      out.write("\t\t\t<tr>\n");
      out.write("\t\t\t\t<td align=\"center\">");
      out.print( i+1 );
      out.write("</td>\n");
      out.write("                <td align=\"center\">");
      out.print( listPDF[i].getName() );
      out.write("</td>\n");
      out.write("                <td align=\"center\">");
      out.print( listPDF[i].length() );
      out.write("</td>\n");
      out.write("\t\t\t\t<td align=\"center\">\n");
      out.write("                    <a href=\"");
      out.print(request.getContextPath());
      out.write("/FileUpload?action=download&docName=");
      out.print(listPDF[i].getName());
      out.write("&docSize=");
      out.print(listPDF[i].length()	);
      out.write("\" title=\"Download\"><img src=\"");
 this.getServletContext().getRealPath("/");
      out.write("/PDFCorrector/download.png\" alt=\"Download\" /></a>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("\t\t\t");
 } 
      out.write("\n");
      out.write("        </table>\n");
      out.write("\n");
      out.write("<br/>\n");
      out.write("<form action=\"");
      out.print(request.getContextPath());
      out.write("/online_correction.jsp\" method=\"get\" >\n");
      out.write("<input type=\"submit\" value=\"Corriger le fichier en ligne \" class=\"art-button\" id=\"boutonJSP\"/>\n");
      out.write("\t\t</form>\n");
      out.write("<br/>\n");
      out.write("\n");
      out.write("Si la list des errors ne s'affiche pas, rafraichir la page  <a href=\"javascript:location.reload(true)\">Ici</a>\n");
      out.write("\n");
      out.write("<br/>\n");
      out.write("<p>Pour corriger un nouveau fichier, c'est par <a href=\"");
      out.print(request.getContextPath());
      out.write("/\"> ici</a></p>\n");
      out.write("\n");
      out.write("\n");
      out.write("<br/>   \n");
      out.write("    </body>\n");
      out.write("\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
