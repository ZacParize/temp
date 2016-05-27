package ru.mycompany;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pdv on 08.07.2015.
 */

public class SimpleChat {

    private ArrayList<String> messageList = new ArrayList<String>();
    private String chatServerTime = "00:00:01 01.01.0001";

    public SimpleChat() {

    }

    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    public String getChatServerTime() {
        return chatServerTime;
    }

    public void setChatServerTime(String chatServerTime) {
        this.chatServerTime = chatServerTime;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");*/
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("command") != null) {
            String command = (String) request.getParameter("command");
            if (command.equals("addMessageToChat")) {
                addMessageToChat(request, response);
            }
        }
    }

    private void addMessageToChat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nickId = "nick";
        String messageId = "message";
        if(request.getParameter(nickId) != null && request.getParameter(messageId) != null)
        {
            String nick = request.getParameter(nickId);
            String message = request.getParameter(messageId);
            this.setChatServerTime(new SimpleDateFormat("HH:mm:SS dd.MM.yyyy").format(new Date()));
            this.getMessageList().add("<b>" + nick + "</b><i>[" + this.getChatServerTime().substring(11) + "]</i>" + message + "<br>");
            if(this.getMessageList().size() > 50) this.getMessageList().remove(0);
            this.printMessages(response);
        }
    }

    private void printMessage(int index,HttpServletResponse response) throws IOException {
        if (index >= 0 && index < this.getMessageList().size()) response.getOutputStream().println(this.getMessageList().get(index));
    }

    protected void printMessages(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        for (int i = 0; i < this.getMessageList().size(); i++) this.printMessage(i,response);
    }

    protected void addMessageForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.getOutputStream().println("<form method=\"post\" action=\"/add_message_to_chat\" role=\"form\"><table><tr><td colspan=\"2\" style=\"color: red\">Form to send your message</td></tr><tr><td>Your nick:</td><td><input type=\"text\" name=\"nick\" /></td></tr><tr><td>Your message:</td><td><input type=\"text\" name=\"message\" /></td></tr><tr><td><input type=\"hidden\" name=\"command\" value=\"addMessageToChat\"/></td></tr><tr><td><input type=\"submit\" value=\"send\" /></td></tr></table></form>");
    }

}
