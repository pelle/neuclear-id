<%@page import="org.neuclear.commons.Utility,
                 com.opensymphony.util.TextUtils,
                 org.neuclear.auth.AuthenticationTicket,
                 java.util.Date,
                 org.neuclear.xml.xmlsec.XMLSecTools,

                org.neuclear.commons.servlets.ServletTools,
                org.neuclear.id.Identity,

                org.neuclear.commons.NeuClearException,
                org.neuclear.id.builders.SignatureRequestBuilder,
                org.neuclear.id.builders.AuthenticationTicketBuilder,
                org.neuclear.id.resolver.NSResolver"%>
<%
    try{
       response.setHeader("Pragma","no-cache");
        response.setDateHeader("Expires",0);
        String siteurl=ServletTools.getAbsoluteURL(request,"/");
        String userns=request.getParameter("identity");
        if (Utility.isEmpty(userns)) {
            response.sendError(500,"No Identity");
            response.flushBuffer();
            return;
        }
        Cookie usercookie=new Cookie("identity",userns);
        //usercookie.setSecure(true);
        usercookie.setMaxAge(2592000);
        response.addCookie(usercookie);
        AuthenticationTicketBuilder authreq=new AuthenticationTicketBuilder(userns,"neu://test",request.getRequestURI());
        SignatureRequestBuilder sigreq=new SignatureRequestBuilder("neu://test",userns,authreq,"Login to Site");
        request.getSession(true).setAttribute("auth",userns);

%>
 <html>
<head><title>
NeuDist Login
</title></head>
<body>
<h3>contacting signing service...</h3>
<form action="<%=NSResolver.resolveIdentity(userns).getSigner()%>)" method="POST">
    <input name="base64xml" value="<%=XMLSecTools.encodeElementBase64(sigreq.getElement())%>" type="hidden">
    <input name="endpoint" value="<%=siteurl%>" type="hidden"/>
</form>
<script language="javascript">
<!--
   document.forms[0].submit();
-->
</script>
<%} catch (Exception e) {
    %>
<html><head><title>Error: <%=e.getMessage()%></title></head><body>
<h4>Problem:</h4>
<pre><%=e.getMessage()%>
<% e.printStackTrace(System.out);%>
</pre>
    <%
    return;
}
%>

</body>
</html>