<%@ page import="org.neudist.utils.Utility,
                 com.opensymphony.util.TextUtils,
                 org.neuclear.receiver.ReceiverServlet,
                 org.neuclear.id.SignedNamedObject,
                 org.neuclear.auth.AuthenticationTicket,
                 org.neuclear.id.NSTools,
                 org.neudist.utils.ServletTools
                 ,
                 org.neuclear.id.Identity,
                 org.neuclear.id.resolver.NSResolver"%>
 <%
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires",0);
    Cookie cookies[]=request.getCookies();
    Identity userns=null;
    boolean loggedin=false;
    HttpSession sess=request.getSession(true);
    if (!Utility.isEmpty(request.getParameter("logout"))) {
        sess.removeAttribute("NeuClearAuthTicket");

    }
    AuthenticationTicket ticket=(AuthenticationTicket)sess.getAttribute("NeuClearAuthTicket");

        if (ticket!=null){
            loggedin=true;
            userns=ticket.getSignatory();
        } else {
            loggedin=false;
            if (cookies!=null){
                for (int i=0;i<cookies.length;i++) {
                    if(cookies[i].getName().equals("identity")) {
                        userns=NSResolver.resolveIdentity(cookies[i].getValue());
                        i=cookies.length;
                    }
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
    <input name="identity" value="<%=(userns!=null)?userns.getName():""%>" type="text" size="30">
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