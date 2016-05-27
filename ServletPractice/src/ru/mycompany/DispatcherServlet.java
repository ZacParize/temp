package ru.mycompany;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Random;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int initialization_count = 0;
    private SimpleChat chat = new SimpleChat();
    private LockObject counter = new LockObject();
    private String ip;
    private String cookie;
    private String protocol;
    private String localAddr;
    private String remoteUser;
    private String queryString;
    private String remoteHost;
    private String authType;
    private String header;
    private String method;
    private String contentType;
    private String servletPath;
    private String requestURI;
    private String localPort;
    private String remotePort;
    private String serverPort;

    public DispatcherServlet() {
        DispatcherServlet.initialization_count++;
    }

    public LockObject getCounter() {
        return counter;
    }

    public void setCounter(LockObject counter) {
        this.counter = counter;
    }

    @Override
    public void init() throws ServletException
    {
        this.ip = new String("Ip: ");
        this.cookie = new String("Cookie: ");
        this.protocol = new String("Protocol: ");
        this.localAddr = new String("Local address: ");
        this.remoteUser = new String("Remote user: ");
        this.queryString = new String("Query string: ");
        this.remoteHost = new String("Remote host: ");
        this.authType = new String("Auth type: ");
        this.header = new String("Header: ");
        this.method = new String("Method: ");
        this.contentType = new String("Content type: ");
        this.servletPath = new String("Servlet path: ");
        this.requestURI = new String("Request URI: ");
        this.localPort = new String("Local port : ");
        this.remotePort = new String("Remote port : ");
        this.serverPort = new String("Server port : ");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        if (req.getAttribute("checkKey") == null)
            req.setAttribute("checkKey",new Random().nextInt(10000));
        if (this.getServletContext().getAttribute("checkKey") == null)
            this.getServletContext().setAttribute("checkKey",new Random().nextInt(10000));
        // Actual logic goes here.
        try {
            switch (req.getRequestURI()) {
                /*какая-никакая проверка на многопоточность*/
                case "/make_load": {
                    long limit = 900000000;
                    for (long idx = 0; idx < limit; idx++) {
                        if (idx == 100) {
                            resp.setContentType("text/html");
                            resp.setCharacterEncoding("UTF-8");
                            resp.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
                            PrintWriter out = resp.getWriter();
                            out.println("<h1>Value of locked object before:" + this.getCounter().getValue() + "</h1>");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (idx == (limit - 1)) {
                            Serializator.serialize(this.getCounter());
                            this.setCounter((LockObject)Serializator.deserialize());
                            System.out.println(this.getCounter().toString());
                            this.getCounter().setValue(new Random().nextInt(10000));
                            PrintWriter out = resp.getWriter();
                            out.println("<h1>Value of locked object after:" + this.getCounter().getValue() + "</h1>");
                        }
                    }
                    break;
                }
                case "/show_chat": {
                    this.chat.printMessages(resp);
                    this.chat.addMessageForm(resp);
                    break;
                }
                case "/add_message_to_chat": {
                    this.chat.processRequest(req, resp);
                    this.chat.addMessageForm(resp);
                    session.setAttribute("chat",this.chat);
                    break;
                }
                case "/test": {
                    Enumeration<String> parameterNames = req.getParameterNames();
                    while (parameterNames.hasMoreElements()) {
                        String paramName = parameterNames.nextElement();
                        String[] paramValues = req.getParameterValues(paramName);
                        for (int i = 0; i < paramValues.length; i++) {
                            String paramValue = paramValues[i];
                            System.out.println(paramName + "  :  " + paramValue);
                        }
                    }

                    break;
                }
                default: {
                    // Set response content type
                    resp.setContentType("text/html");
                    resp.setCharacterEncoding("UTF-8");
                    resp.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
                    PrintWriter out = resp.getWriter();
                    out.println("<h1>" + this.ip + req.getRemoteAddr() + "</h1>");
                    Cookie[] cookies = req.getCookies();
                    for (Cookie cookie : cookies) {
                        out.println("<h1>" + this.cookie + " domain: " + cookie.getDomain() + "</h1>");
                        out.println("<h1>" + this.cookie + " name: " + cookie.getName() + "</h1>");
                        out.println("<h1>" + this.cookie + " value: " + cookie.getValue() + "</h1>");
                    }
                    out.println("<h1>" + this.protocol + req.getProtocol() + "</h1>");
                    out.println("<h1>" + this.localAddr + req.getLocalAddr() + "</h1>");
                    out.println("<h1>" + this.remoteUser + req.getRemoteUser() + "</h1>");
                    out.println("<h1>" + this.queryString + req.getQueryString() + "</h1>");
                    out.println("<h1>" + this.remoteHost + req.getRemoteHost() + "</h1>");
                    out.println("<h1>" + this.authType + req.getAuthType() + "</h1>");
                    Enumeration headerNames = req.getHeaderNames();
                    while (headerNames.hasMoreElements()) {
                        String key = (String) headerNames.nextElement();
                        String value = req.getHeader(key);
                        out.println("<h1>" + this.header + " " + key + ": " + value  + "</h1>");
                    }
                    out.println("<h1>" + this.method + req.getMethod() + "</h1>");
                    out.println("<h1>" + this.contentType + req.getContentType() + "</h1>");
                    out.println("<h1>" + this.servletPath + req.getServletPath() + "</h1>");
                    out.println("<h1>" + this.requestURI + req.getRequestURI() + "</h1>");
                    out.println("<h1>" + this.localPort + req.getLocalPort() + "</h1>");
                    out.println("<h1>" + this.remotePort + req.getRemotePort() + "</h1>");
                    out.println("<h1>" + this.serverPort + req.getServerPort() + "</h1>");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        doGet(req,resp);
    }

    @Override
    public long getLastModified(HttpServletRequest req) {
        return 1;
    }

    @Override
    public void destroy()
    {
        // do nothing.
    }

}
