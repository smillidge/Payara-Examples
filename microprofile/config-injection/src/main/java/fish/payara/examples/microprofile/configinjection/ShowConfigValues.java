/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.examples.microprofile.configinjection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author steve
 */
@WebServlet(name = "ShowConfigValues", urlPatterns = {"/ShowConfigValues"})
public class ShowConfigValues extends HttpServlet {
    
    // Example showing injection of a default property
    @Inject
    @ConfigProperty(name = "default.property", defaultValue = "Default Value")
    String defaultProperty;
    
    // Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "file.property")
    String fileProperty;

    // Example showing reading a config property from the META-INF/microprofile-config.properties file
    @Inject
    @ConfigProperty(name = "application.property")
    String applicationProperty;

    
    // Example config property that uses a default converter to LocalDate
    @Inject
    @ConfigProperty(name = "date.property")
    LocalDate date;

    // Example injection the standard environment variable home
    @Inject
    @ConfigProperty(name = "HOME", defaultValue = "HOME environment variable not set")
    String home;
    
    // Example injection of the standard System Property java.home
    @Inject
    @ConfigProperty(name = "java.home", defaultValue = "java.home environment variable not set")
    String javaHome;
    
    @Inject
    TestBean bean;
    
    @Inject
    Config config;
    
    @Inject
    @ConfigProperty(name = "pojo.value")
    TestPojo pojo;
      
    @Inject
    @ConfigProperty(name = "list")
    List<String> injectedList;


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
            out.println("<title>Servlet Demonstrating use of the Microprofile Config API for Injection of Configuration Values</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Demonstrating use of the Microprofile Config API for Injection of Configuration Values</h1>");
            out.println("<p>Configuration values shown here can be overriden in the administration console in Domain, Instance and Application Properties</p>");
            out.println("<p>You can also override values by setting system properties or environment variables</p>");
            out.println("<h2>Properties Injected into a Servlet these will only change on redeploy</h2>");            
            out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
            out.format("<tr><td>%s</td><td>%s</td></tr>", "default.property",defaultProperty);
            out.format("<tr><td>%s</td><td>%s</td></tr>", "file.property",fileProperty);
            out.format("<tr><td>%s</td><td>%s</td></tr>", "date.property",date);
            out.format("<tr><td>%s</td><td>%s</td></tr>", "HOME",home);
            out.format("<tr><td>%s</td><td>%s</td></tr>", "java.home",javaHome);
            out.format("<tr><td>%s</td><td>%s</td></tr>", "application.property",applicationProperty);
            out.println("</table>");
            out.println("<h2>Properties Injected into a Request Scoped Bean these will change if you override them in Payara</h2>");            
            out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");
            out.format("<tr><td>%s</td><td>%s</td></tr>", "default.property",bean.getDefaultProperty());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "file.property",bean.getFileProperty());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "date.property",bean.getDate());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "HOME",bean.getHome());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "java.home",bean.getJavaHome());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "application.property",bean.getApplicationProperty());
            out.format("<tr><td>%s</td><td>%s</td></tr>", "Test Pojo",pojo);
            out.println("</table>"); 
            
            out.println("<h2>Standard Payara properties which can be obtained via the config api</h2>");            
            out.println("<table><tr><th>Property Name</th><th>Property Value</th></tr>");            
            out.println("<tr><td>payara.instance.http.port</td><td>" + config.getValue("payara.instance.http.port", Integer.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.http.address</td><td>" + config.getValue("payara.instance.http.address", InetAddress.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.https.port</td><td>" + config.getValue("payara.instance.https.port", Integer.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.https.address</td><td>" + config.getValue("payara.instance.https.address", InetAddress.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.root</td><td>" + config.getValue("payara.instance.root", String.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.type</td><td>" + config.getValue("payara.instance.type", String.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.name</td><td>" + config.getValue("payara.instance.name", String.class) + "</td></tr>");
            out.println("<tr><td>payara.domain.name</td><td>" + config.getValue("payara.domain.name", String.class) + "</td></tr>");
            out.println("<tr><td>payara.domain.installroot</td><td>" + config.getValue("payara.domain.installroot", String.class) + "</td></tr>");
            out.println("<tr><td>payara.config.dir</td><td>" + config.getValue("payara.config.dir", String.class) + "</td></tr>");
            out.println("<tr><td>payara.instance.config.name</td><td>" + config.getValue("payara.instance.config.name", String.class) + "</td></tr>");
            out.println("<tr><td>payara.admin.port</td><td>" + config.getValue("payara.admin.port", Integer.class) + "</td></tr>");
            out.println("</table>");
            
            out.println("<h2>SInjected list using a custom converter</h2>");            
            out.println("<table><tr><th>Element Number</th><th>Value</th></tr>");            
            int elementCount = 0;
            for (String string : injectedList) {
               out.println("<tr><td>"+ elementCount++ +"</td><td>" + string + "</td></tr>");                
            }
            out.println("</table>");
            
            out.println("</body>");
            out.println("</html>");
        }
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
