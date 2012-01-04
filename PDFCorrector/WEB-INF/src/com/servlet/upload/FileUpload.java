package com.servlet.upload;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

import com.pgrou.pdfcorrection.Main;

/**
 * Classe Servlet d'upload et de download de fichiers
 * 
 * @author Sébastien Bro
 * @version 1.0
 */

@WebServlet(name = "FileUpload", urlPatterns = { "/FileUpload" })
public class FileUpload extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MaxMemorySize = 1 * 1024 * 1024; // 1MB
	private static final int MaxRequestSize = 30 * 1024 * 1024; // 30MB
	private static final String correctionDir = "/Correction";
	private File corDir;
	private String htmlCorrection;
	private File tmpDir;

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
		processRequest(request, response);
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
		processRequest(request, response);
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
		corDir = new File(this.getServletContext().getRealPath("/")
				+ correctionDir);
		if (!corDir.isDirectory()) {
			corDir.mkdir();
		}
		
	}

	/**
	 * Traite une requète HTTP de type upload (avec correction) ou download de
	 * fichier
	 * 
	 * @param request
	 *            Requete HTTP
	 * @param request
	 *            Réponse HTTP
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");

		try {
			if (action.equals("upload")) {

				String realPath = this.getServletContext().getRealPath("/");
				//System.out.println("Real Path " + realPath);
				tmpDir = new File(realPath + "/" + correctionDir);

				String nbCol="";
				String mH="";
				String mB="";
				String fileName="";
				
				// Check that we have a file upload request
				boolean isMultipart = ServletFileUpload
						.isMultipartContent(request);

if (isMultipart){
            		
            		// Create a factory for disk-based file items
            		DiskFileItemFactory factory = new DiskFileItemFactory(MaxMemorySize,corDir);
            		
            		// Add a cleaning tracker
            		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(this.getServletContext());
                    factory.setFileCleaningTracker(fileCleaningTracker);

            		// Create a new file upload handler
            		ServletFileUpload upload = new ServletFileUpload(factory);
            		// Set overall request size constraint
            		upload.setSizeMax(MaxRequestSize);

            		try {
                		// Parse the request
	            		List items = upload.parseRequest(request);
	            		
	            		// Process the uploaded items
	            		Iterator iter = items.iterator();
	            		while (iter.hasNext()) {
	            		    FileItem item = (FileItem) iter.next();

	        				// Process a regular form field
	        				if ( item.isFormField( ) ) {
	        					String name = item.getFieldName( );
	        					String value = item.getString( );
	        					//System.out.println( "FORM:"+name+"/"+value);
	        					
	        					if (name.equals("nbcolonnes")){nbCol=value;}
	        					else{
	        						if (name.equals("entetehaute")){mH=value;}
	        						else{
	        							if (name.equals("entetebasse")){mB=value;}
	        						}
	        						
	        					}
	        					
	        					
	        				}
	        				// Process a file upload
	        				else
	        				{
	        					String fieldName = item.getFieldName( );
	        					fileName = item.getName( );
	        					String contentType = item.getContentType( );
	        					boolean isInMemory = item.isInMemory( );
	        					long sizeInBytes = item.getSize( );
	        					
	        					//System.out.println( "FILE:"+fileName+"/"+contentType+"/"+isInMemory+"/"+sizeInBytes);
	        					
	        					// Stocke le fichier uploade dans le dossier correction par défaut
	        					// Si taille < MaxMemorySize, ecriture directe
	        					// Si MaxMemorySize < taille , streaming
	        					
	        					boolean writeToFile = true;
	        					// Copie directe pour les petits fichiers, sinon streaming
	        					if (sizeInBytes>MaxMemorySize) writeToFile = false;
	         	        					
	          					if (writeToFile){ 
	        						// Ecriture directe
	        						//System.out.println( "Ecriture directe" );
	        						File uploadedFile = new File( corDir ,fileName );
	        						item.write( uploadedFile );
	        					} else { 
	        						// Streaming
	        						//System.out.println( "Streaming" );
	        						File uploadedFile = new File( corDir ,"rapport-"+fileName );
	        						InputStream sourceFile;
	        						try {
	        							sourceFile = item.getInputStream( );
	        							OutputStream destinationFile;
	        							try {
	        								destinationFile = new FileOutputStream( uploadedFile );
	        								byte buffer[] = new byte[512 * 1024];
	        								int nbLecture;
	        								while ( ( nbLecture = sourceFile.read( buffer ) ) != -1 )
	        								{
	        									destinationFile.write( buffer, 0, nbLecture );
	        								}
	        								sourceFile.close( );
	        							}
	        							catch ( FileNotFoundException e )
	        							{
	        								e.printStackTrace( );
	        							}
	        							catch ( IOException e )
	        							{
	        								e.printStackTrace( );
	        							}
	        							sourceFile.close();
	        						}
	        						catch ( IOException e )
	        						{
	        							e.printStackTrace( );
	        						}
	        					}
	
	        					response.sendRedirect("result.jsp");
	          					
	        				
	        				}
	        			}
	            		
	            		//Appel de la fonction Main
	            		
	            		Main.correction_final(tmpDir+"/"+fileName,nbCol,mH,mB);
	            		
	            		
	        		}
            		
            		
            		
            		
	        		catch ( FileUploadException e )
	        		{
	        			e.printStackTrace( );
	        		}
	        		catch ( Exception e )
	        		{
	        			e.printStackTrace( );
	        		}
            	}
            } else if (action.equals("download")) {
                
            	System.out.println("entrŽe dans le download");
            	
                String docName = request.getParameter("docName");
                String docSize = request.getParameter("docSize");
                File file = new File(corDir+"/"+docName);
            	FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[Integer.valueOf(docSize)];
                int offset = 0;
                int numRead = 0;
                while ((offset < buf.length)
                        && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0)) {

                    offset += numRead;

                }
                fis.close();
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + docName+"");
                ServletOutputStream outputStream =  response.getOutputStream();
                outputStream.write(buf);
                outputStream.flush();
                outputStream.close();
                
                response.sendRedirect("result.jsp");
            }
			
			
        } finally {
        }        
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
