<%@page import="org.neudist.utils.Utility,
                 com.opensymphony.util.TextUtils,
                 org.neuclear.contracts.nsauth.AuthenticationTicket,
                 java.util.Date,
                 org.neudist.xml.xmlsec.XMLSecTools,

                org.neudist.utils.ServletTools,
                org.neuclear.id.Identity,

                org.neuclear.commons.NeuClearException"%>
<%
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
//    SignatureRequest auth=null;
    try {
        // TODO: This needs a signer and a targeturl
  /*      auth = AuthenticationTicket.createAuthenticationRequest(
                    userns,
                    "neu://neu/testapp",
                    360000, // Valid for about an hour
                    siteurl,
                    siteurl,
                    null
            );
  */      // For this simple example we will fetch the object from the store later on.
//        auth.addTarget(new TargetReference(auth,ServletTools.getAbsoluteURL(request,"/Store"),"store"));
        request.getSession(true).setAttribute("nsauth",auth.getPayload().getName());
    } catch (NeuClearException e) {
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
 <html>
<head><title>
NeuDist Login
</title></head>
<body>
<h3>contacting signing service...</h3>
<form action="<%=((Identity)NamedObjectFactory.fetchNamedObject(userns)).getSigner()%>" method="POST">
    <input name="base64xml" value="<%=XMLSecTools.encodeElementBase64(auth.getElement())%>" type="hidden">
    <input name="endpoint" value="<%=siteurl%>" type="hidden"/>
</form>
<script language="javascript">
<!--
   document.forms[0].submit();
-->
</script>


</body>
</html>