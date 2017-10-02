/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "FormularioOculto", urlPatterns = {"/FormularioOculto"})
public class FormularioOculto extends HttpServlet {

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
            out.println("<title>Formulario Oculto</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"Css/formulariocss.css\" media=\"all\" />");
            out.println("</head>");
            out.println("<body>");
            /*
            Sera como nuestro metodo principal,todo se manejara desde aqui.
            Mensajes de error mostrados o llamadas a metodos.
             */
            errores(request);
            if (errorN || errorU || errorP || errorF) {

                if (request.getParameter("Volver") == null) {

                    pagError(request, response);

                } else {

                    if (errorN == true) {
                        out.println("<h1>Error en el nombre</h1>");
                    }
                    if (errorU == true) {
                        out.println("<h1>Error en el usuario</h1>");
                    }
                    if (errorP == true) {
                        out.println("<h1>Error en la password</h1>");
                    }
                    if (errorF == true) {
                        out.println("<h1>Error en la fecha introducida</h1>");
                    }
                    formulario(request, response);

                }
            } else {
                pagFin(request, response);

            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    /*
    Mostrara un mensaje de error y el boton para volver al formulario.
    Tambien guardara los campos rellenos mediante un formulario oculto en caso de que haya datos.
     */
    protected void pagError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Enumeration<String> parametros;
        try (PrintWriter out = response.getWriter()) {
            parametros = request.getParameterNames();
            out.println("<form id=\"formulario\" action=\"FormularioOculto\" method=\"post\">");
            out.println("<h1>Rellene correctamente los campos obligatorios</h1>");

            while (parametros.hasMoreElements()) {
                String elemento = parametros.nextElement();
                String valor = request.getParameter(elemento);

                out.println("<input type=\"hidden\" name=" + elemento + " value=" + valor + " >");

            }
            out.println("<input type=\"submit\" name=\"Volver\" value=\"Volver\" formaction=\"FormularioOculto\" />");
            out.println("</form>");
        }
    }

    /*
    Mostrara una pagina si los datos son correctos mostrando los datos que hemos introducidos y 
    un boton de vuelta al indice.
     */
    protected void pagFin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Enumeration<String> parametros;
            parametros = request.getParameterNames();
            int month = Integer.parseInt(request.getParameter("Mes"));
            String nombreMes = "";
            int pref = 0;
            out.println("<h2>Datos introducidos en la base de datos correctamente.</br>");
            while (parametros.hasMoreElements()) {
                String elemento = parametros.nextElement();
                String valor = request.getParameter(elemento);

                if (elemento.equalsIgnoreCase("Enviar")) {

                } else if (elemento.equalsIgnoreCase("Dia") || elemento.equalsIgnoreCase("Mes") || elemento.equalsIgnoreCase("Year")) {
                    if (elemento.equalsIgnoreCase("Mes")) {
                        if(month == 1){
                            nombreMes = "Enero";
                        }else if(month == 2){
                            nombreMes = "Febrero";
                        }else if (month == 3){
                            nombreMes = "Marzo";
                        }else if (month == 4){
                            nombreMes = "Abril";
                        }else if (month == 5){
                            nombreMes = "Mayo";
                        }else if (month == 6){
                            nombreMes = "Junio";
                        }else if (month == 7){
                            nombreMes = "Julio";
                        }else if (month == 8){
                            nombreMes = "Agosto";
                        }else if (month == 9){
                            nombreMes = "Septiembre";
                        }else if (month == 10){
                            nombreMes = "Octubre";
                        }else if (month == 11){
                            nombreMes = "Noviembre";
                        }else if (month == 12){
                            nombreMes = "Diciembre";
                        }
                        out.println("Fecha: " + request.getParameter("Dia") + " de " + nombreMes + " de " + request.getParameter("Year"));
                        out.println("</br>");

                    }

                } else if (elemento.equalsIgnoreCase("Deporte") || elemento.equalsIgnoreCase("Lectura") || elemento.equalsIgnoreCase("Cine") || elemento.equalsIgnoreCase("Viajes")) {
                    if (pref == 0) {
                        out.print("Preferencias:");
                        pref++;
                    }
                    String[] aficiones = request.getParameterValues(elemento);
                    for (int i = 0; i < aficiones.length; i++) {
                        out.print(aficiones[i] + " ");
                    }
                } else {
                    out.print(elemento + " : " + valor + "</br>");

                }

            }
            out.println("</br>");
            out.println("<a href=\"index.html\">Volver al indice</a>");

        }
    }

    /*
    Comprueba si existen errores y detecta que campo obligatorio es el erroneo.
     */
    boolean errorN = false;
    boolean errorU = false;
    boolean errorP = false;
    boolean errorF = false;

    protected void errores(HttpServletRequest request) {
        int year = Integer.parseInt(request.getParameter("Year"));
        int month = Integer.parseInt(request.getParameter("Mes"));
        int day = Integer.parseInt(request.getParameter("Dia"));
        errorN = false;
        errorU = false;
        errorP = false;
        errorF = false;
        if (request.getParameter("nombre") != null) {
            if ("".equals(request.getParameter("nombre"))) {
                errorN = true;

            } else {
                errorN = false;
            }
        }
        if (request.getParameter("usuario") != null) {
            if ("".equals(request.getParameter("usuario"))) {
                errorU = true;

            } else {
                errorU = false;
            }
        }
        if (request.getParameter("password") != null) {
            if ("".equals(request.getParameter("password"))) {
                errorP = true;

            } else {
                errorP = false;
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day == 31) {
                errorF = true;
            } else {
                errorF = false;
            }
        }
        if (month == 2) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                if (day <= 29) {
                    errorF = false;
                } else if (day <= 28) {
                    errorF = false;
                } else {
                    errorF = true;
                }
            } else if (day >= 29) {
                errorF = true;
            }

        }

    }

    /*
    Creamos el formulario dentro del servlet diferenciando si es un campo con datos rellenos anteriormente
    o es un campo libre de datos.
     */
    protected void formulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("<form id=\"formulario\" action=\"FormularioCompleto\" method=\"post\">");
        out.println("<fieldset>");
        out.println("<legend>Informacion personal</legend>");
        out.println("<label>*Nombre</label>");
        out.println("<input id=\"nombre\" name=\"nombre\" type=\"text\" value=\"" + request.getParameter("nombre") + "\" >");
        out.println("</br>");
        out.println("<label>Apellidos</label>");
        out.println("<input id=\"apellidos\" name=\"apellidos\" type=\"text\" value=\"" + request.getParameter("apellidos") + "\" >");
        out.println("</br>");
        out.println("<label>Sexo</label>");
        if (request.getParameter("Sexo") != null) {
            if ("Hombre".equals(request.getParameter("Sexo"))) {
                out.println("<input type=\"radio\" name=\"Sexo\" value=\"Hombre\" checked>Hombre");
                out.println("<input type=\"radio\" name=\"Sexo\" value=\"Mujer\" >Mujer");
            } else {
                out.println("<input type=\"radio\" name=\"Sexo\" value=\"Hombre\" >Hombre");
                out.println("<input type=\"radio\" name=\"Sexo\" value=\"Mujer\" checked >Mujer");
            }
            out.println("</br>");
        }
        out.println("<label>Fecha de nacimiento :</label>");
        out.println("<select name=\"Dia\">");
        if (request.getParameter("Dia") != null) {
            out.println("<option selected>" + request.getParameter("Dia") + "</option>");
        }
        for (int i = 1; i <= 31; i++) {
            out.println("<option>" + i + "</option>");
        }
        out.println("</select> ");
        out.println("<select name=\"Mes\">");
        if (request.getParameter("Mes") != null) {
            out.println("<option selected>" + request.getParameter("Mes") + "</option>");
        }
        for (int i = 1; i <= 12; i++) {
            out.println("<option>" + i + "</option>");
        }
        out.println("</select>  ");
        out.println("<select name=\"Year\">");
        if (request.getParameter("Year") != null) {
            out.println("<option selected>" + request.getParameter("Year") + "</option>");
        }
        for (int i = 1951; i <= 2016; i++) {
            out.println("<option>" + i + "</option>");
        }
        out.println("</select>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<legend>Datos de acceso</legend>");
        out.println("<label>*Usuario</label>");
        out.println("<input id=\"usuario\" name=\"usuario\" type=\"text\" value=\"" + request.getParameter("usuario") + "\" />");
        out.println("</br>");
        out.println("<label>*Password</label>");
        out.println("<input id=\"password\" name=\"password\" type=\"password\" value=\"" + request.getParameter("password") + "\" />");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<legend>Informacion general</legend>");
        out.println("<label>Preferencias</label>");
        out.println("</br>");

        if (request.getParameter("Deporte") != null) {
            out.println("<input type=\"checkbox\" name=\"Deporte\" value=\"Deporte\" checked>Deporte");
            out.println("</br>");
        } else {
            out.println("<input type=\"checkbox\" name=\"Deporte\" value=\"Deporte\">Deporte");
            out.println("</br>");
        }
        if (request.getParameter("Lectura") != null) {
            out.println("<input type=\"checkbox\" name=\"Lectura\" value=\"Lectura\" checked >Lectura");
            out.println("</br>");
        } else {
            out.println("<input type=\"checkbox\" name=\"Lectura\" value=\"Lectura\" >Lectura");
            out.println("</br>");
        }
        if (request.getParameter("Cine") != null) {
            out.println("<input type=\"checkbox\" name=\"Cine\" value=\"Cine\" checked>Cine");
            out.println("</br>");
        } else {
            out.println("<input type=\"checkbox\" name=\"Cine\" value=\"Cine\">Cine");
            out.println("</br>");
        }
        if (request.getParameter("Viajes") != null) {
            out.println("<input type=\"checkbox\" name=\"Viajes\" value=\"Viajes\" checked>Viajes");
            out.println("</br>");
        } else {
            out.println("<input type=\"checkbox\" name=\"Viajes\" value=\"Viajes\">Viajes");
            out.println("</br>");
        }

        out.println("</fieldset>");
        out.println("<input type=\"submit\" value=\"Enviar\"formaction=\"FormularioOculto\" />");
        out.println("<input type=\"submit\" value=\"Borrar\"formaction=\"html/formularioOculto.html\" />");
        out.println("</form>");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        processRequest(request, response);
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
    }// </editor-fold>

}
