package com.servlet.upload;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;


/**
 * Classe Servlet d'upload et de download de fichiers
 * 
 * @author Sébastien Bro
 * @version 1.0
 */

@WebServlet(name = "FileUpload", urlPatterns = { "/FileUpload" })
public class WriteFileCorrection extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File tmpDir;
	private static final String correctionDir = "/Correction";


	/**
	 * Récupère les requètes de type GET
	 * 
	 * @param request
	 *            Requete HTTP
	 * @param request
	 *            Réponse HTTP
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * Récupère les requètes de type POST
	 * 
	 * @param request
	 *            Requete HTTP
	 * @param request
	 *            Réponse HTTP
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialise la configuration du servlet avec le chemin d'accès du dossier
	 * contenant les corrections
	 * 
	 * @param config
	 *            Configuration du servlet
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
	}

	/**
	 * Traite une requète HTTP de type upload (avec correction) ou download de
	 * fichier
	 * 
	 * @param request
	 *            Requete HTTP
	 * @param request
	 *            Réponse HTTP
	 * @throws FileUploadException 
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, FileUploadException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");

			if (action.equals("writecorrection")) {
				// Check that we have a file upload request
				boolean isMultipart = ServletFileUpload
						.isMultipartContent(request);

if (isMultipart){


            		try {
            			
            			// Create a factory for disk-based file items
                		DiskFileItemFactory factory = new DiskFileItemFactory();
                		
                		// Add a cleaning tracker
                		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(this.getServletContext());
                        factory.setFileCleaningTracker(fileCleaningTracker);
            			// Create a new file upload handler
                		ServletFileUpload writecorrection = new ServletFileUpload(factory);
                		// Parse the request
	            		List items = writecorrection.parseRequest(request);
	            		
	            		// Process the uploaded items
	            		Iterator iter = items.iterator();
	            		while (iter.hasNext()) {
	            		    FileItem item = (FileItem) iter.next();

	        				// Process a regular form field
	        				if ( item.isFormField( ) ) {
	        					String name = item.getFieldName( );
	        					String value = item.getString( );
	        					//System.out.println( "FORM:"+name+"/"+value);
	        					
	        					if (name.equals("correctedText")){
	        						
	        						//rŽcupŽration du bon chemin
	        						String realPath = this.getServletContext().getRealPath("/");
	        						tmpDir = new File(realPath + "/" + correctionDir);
	        						
	        						
	        						// crÂŽation du nouveau fichier
	        						
	        						File fichier = new File(tmpDir+"/corrected-text.txt");
	        						
	        						PrintWriter out = null;
	        						try {
	        							out = new PrintWriter(new FileWriter(fichier));
	        						} catch (IOException e) {
	        							e.printStackTrace();
	        						}
	        							out.write(value);
	        							out.println();
	        							out.close(); // Ferme le flux du fichier, sauvegardant ainsi les donnŽes
	        							
	        							
	        							File MyJSP=new File(realPath+"/online_correction.jsp");
	        							MyJSP.delete();
	        					}	
	        					
	            		
	        					
	        					
	        					
	        				}
	            		}
            		
	        				response.sendRedirect("result.jsp?bouton=false");
            		}
            		finally{}
            	
	       }  }  
    }


	/**
	 * Donne les informations concernant le servlet
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}

	/**
	 * Affiche la correction HTML
	 */
	// public String getHtmlCorrection(){
	// return this.htmlCorrection;
	// }

}
