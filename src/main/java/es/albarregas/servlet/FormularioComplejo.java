/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "FormularioComplejo", urlPatterns = {"/FormularioComplejo"})
public class FormularioComplejo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"Css/complejovisualizacion.css\" media=\"all\" />");
            out.println("<title>Formulario Complejo</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println("<h1> Datos del formulario </h1>");
            Map<String, String[]> map = request.getParameterMap();
            Set set = map.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String[]> entry
                        = (Entry<String, String[]>) it.next();
                String paramName = entry.getKey();
                 if (paramName.equalsIgnoreCase("enviar")) {
                  }else {
                out.println("<p>"+paramName +": ");
                String[] paramValues = entry.getValue();
                 
                      if (paramValues.length == 1) {
                    String paramValue = paramValues[0];
                    if (paramValue.length() == 0) {
                        out.println(""+"</p><br/>");
                    } else {
                        out.println("<b>"+paramValue+"</b></p><br/>");
                    }
                } else {
                    
                    for(int i = 0; i < paramValues.length; i++) {
                        out.println("<b>" + paramValues[i] + "</b></p>");
                    }
                   
                }              
            }
            }
            out.println("<br/>");
            out.println("</br> <a href='index.html' > Enlace a index </a>");
            out.println("</div>");
        }
    }

 
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Formulario sin datos </title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> No se ha encontrado el formulario </h1>");
        out.println("<p> Vuelva hacia atras y rellene el formulario</p>");
        out.println("<a href='index.html' > Enlace a index </a>");
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
