<%@ page import="org.neudist.utils.Utility,
                 com.opensymphony.util.TextUtils,
                 org.neuclear.receiver.ReceiverServlet,
                 org.neuclear.id.NamedObject,
                 org.neuclear.contracts.nsauth.AuthenticationTicket,
                 org.neuclear.id.NSTools,
                 org.neudist.utils.ServletTools,
                 org.neuclear.id.NamedObjectFactory"%>
 <%
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
    Cookie cookies[]=request.getCookies();
    String userns=null;
    boolean loggedin=false;
    HttpSession sess=request.getSession(true);
    if (!Utility.isEmpty(request.getParameter("logout"))) {
        sess.removeAttribute("NSAuthTicket");
        sess.removeAttribute("nsauth");

    }
    AuthenticationTicket ticket=(AuthenticationTicket)sess.getAttribute("NSAuthTicket");
    String ticketname=(String)sess.getAttribute("nsauth");
        if (ticket==null&&!Utility.isEmpty(ticketname)) {
            NamedObject named=NamedObjectFactory.fetchNamedObject(ticketname);
            if (named!=null){
                if (named instanceof AuthenticationTicket) {
                    ticket=(AuthenticationTicket)named;
                    sess.setAttribute("NSAuthTicket",named);
                } else {
                    out.println("Found ticket but it isn't an AuthenticationTicket it is a: "+named.getClass().toString());
                }
            }
        }

//        if (ticket!=null) {
//            out.println("found ticket: ");
//            out.println(ticket.getName());
//        }

        if (ticket!=null){
            if (ticket.isNameValid()) {
                loggedin=true;
                userns=NSTools.getParentNSURI(ticket.getName());
            }  else {
                out.println("Ticket ");
                out.println(ticket.getName());
                out.println(" is invalid");
                sess.removeAttribute("NSAuthTicket");
                sess.removeAttribute("nsauth");
            }

        }

        if (cookies!=null){
            for (int i=0;i<cookies.length;i++) {
                if(cookies[i].getName().equals("namespace")) {
                    userns=cookies[i].getValue();
                    i=cookies.length;
                }
            }
        }
%>
<html>
<head><title>
NeuDist Sample Web App
</title></head>
<body>
<h1>NeuDist Sample Web App</h1>
<%
    if(!loggedin){
%>
<form action="login.jsp" method="POST">
<table bgcolor="#FFFFE0"><tr><td valign="top">
    <input name="namespace" value="<%=Utility.denullString(userns)%>" type="text" size="30">
    </td><td valign="top">
    <input type="submit" name="submit" value="Login">
    </td>
</tr>
<tr><td colspan="2" bgcolor="#F0F0FF">
Try logging in using the example neu's below:<br>
<pre>
/test/one
/test/two
</pre>
Use the passphrase: "<tt>password</tt>" when asked.
</td></tr></table>
</form>
<%
    } else {
        %>
        <%=userns%> is Logged In<br><hr>
        <a href="<%=ServletTools.getAbsoluteURL(request,"/")%>?logout=1">Log Out</a>
        <%
    }
%>


</body>
</html>