/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 14, 2002
 * Time: 1:13:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.contracts.nsauth;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.id.NameSpace;
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neuclear.id.signrequest.SignatureRequest;
import org.neuclear.time.TimeTools;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;

import java.security.PrivateKey;
import java.sql.Timestamp;
import java.util.Date;

/**
 * This Authentication Ticket is used by websites to authenticate a user.
 * It generates a unique Name in the users NameSpace, which the user then signs.
 */
public class AuthenticationTicket extends NamedObject {
    /**
     * <p>Used by a website to create an authentication ticket for validation.</p>
     * <p>Eg.:<br>
     * <tt>NamedObject ticket=new AuthenticationTicket("neu://test/bob","neu://site/neubay",36000,"http://neubay.com");</tt><br>
     * Would give you a namedobject containing the following xml:<br>
     * <pre>&lt;nsauth:AuthenticationTicket xmlns:nsauth="http://neuclear.org/neu/nsauth" xmlns:nsdl="http://neuclear.org/neu/nsdl" nsdl:name="/test/two/neu.testapp.-2o1qkqrvxyesyt7dae22ulvp56eju30zyys5t6nxjjie2gw3qq" nsauth:validto="20021002T084919848GMT+00:00" nsauth:href="http://localhost:8080/neudistframework/"&gt;
     * &lt;/nsauth:AuthenticationTicket&gt;
     *</pre>
     *
     * @param user The Users namespace eg: neu://test/bob
     * @param requester The requesters NameSpace eg. neu://site/neubay
     * @param validity The validity of the ticket in Milliseconds
     * @param siteurl URL for interactive signing service to send user to after signing.
     */
    private AuthenticationTicket(String user, String requester, long validity, String siteurl) throws NeudistException {
        super(createUniqueTicketName(user, requester), AuthenticationTicket.TAG_NAME, AuthenticationTicket.NS_NSAUTH);

        NamedObject userns = NamedObjectFactory.fetchNamedObject(user);

        if (userns == null || (!(userns instanceof NameSpace)) || (Utility.isEmpty(((NameSpace) userns).getSigner())))
            throw new NeudistException("The provided namespace: " + user + " doesnt exist or doesnt allow interactive signing");
        Element root = getElement();
        if (validity >= 0)
            root.addAttribute(DocumentHelper.createQName("validto", NS_NSAUTH), TimeTools.formatTimeStamp(new Timestamp(new Date().getTime() + validity)));
        if (!Utility.isEmpty(siteurl))
            root.addAttribute(DocumentHelper.createQName("href", NS_NSAUTH), siteurl);
    }

    /**
     * This constructor is used to create a UserAuthenticationTicket from an XML Element
     * @param elem AuthenticationTicket element
     * @throws NeudistException
     */
    public AuthenticationTicket(Element elem) throws NeudistException {
        super(elem);
    }

    public static SignatureRequest createAuthenticationRequest(String user, String requester, long validity, String siteurl, String targeturl, PrivateKey signer) throws NeudistException {
        AuthenticationTicket ticket = new AuthenticationTicket(user, requester, validity, siteurl);
        return SignatureRequest.createRequest(requester, targeturl, ticket, signer);

    }

    /**
     * This is just used to create a unique ticket for use by the ticket
     * @param userNameSpace
     * @param reqNameSpace
     * @return
     */
    private static String createUniqueTicketName(String userNameSpace, String reqNameSpace) {
        // Yeah, yeah there are better ways to do this
        String ms = new Long(new Date().getTime()).toString();
        byte ticketsrc[] = new byte[ms.length() + reqNameSpace.length()];
        System.arraycopy(ms.getBytes(), 0, ticketsrc, 0, ms.length());
        System.arraycopy(reqNameSpace.getBytes(), 0, ticketsrc, ms.length(), reqNameSpace.length());
        String ticket = CryptoTools.formatAsURLSafe(CryptoTools.digest256(ticketsrc));
        //Lets reuse ticketsrc for memory reasons
        int offset = ms.length() + 1;
        if (reqNameSpace.startsWith("neu://"))
            offset += 5;


        for (int i = offset; i < ticketsrc.length; i++) {
            if (ticketsrc[i] == (byte) '/')
                ticketsrc[i] = (byte) '.';
        }
/*
        byte ticketName[]=new byte[userNameSpace.length()+33]; // Create new Name byte array to hold userNameSpace a '/' and the generated ticket (size 32)
        System.arraycopy(userNameSpace.getBytes(),0,ticketName,0,userNameSpace.length());
        ticketName[userNameSpace.length()]=(byte)'/';
        System.arraycopy(ticket,0,ticketName,userNameSpace.length()+1,ticket.length);
*/
        return userNameSpace + '/' + new String(ticketsrc, offset, ticketsrc.length - offset) + '.' + ticket;
    }

    /**
     * Get the end time of the validity of the ticket
     * @return Timestamp object containing the end time of the ticket
     * @throws NeudistException
     */
    public Timestamp getValidTo() throws NeudistException {
        String ts = getElement().attributeValue(DocumentHelper.createQName("validto", NS_NSAUTH));
        return TimeTools.parseTimeStamp(ts);
    }

    /**
     * The Site URL of the site requesting authentication.
     * @return the URL or null if unavailable.
     */
    public String getSiteHref() {
        return getElement().attributeValue(DocumentHelper.createQName("href", NS_NSAUTH));
    }

    public String getTagName() {
        return TAG_NAME;
    }

    /**
     * @return the XML NameSpace object
     */
    public Namespace getNS() {
        return NS_NSAUTH;
    }
/*

    public Element explain() {
        ExplainNamedObject explanation=new ExplainNamedObject(this);
        explanation.addDescription("This will authenticate you to the site listed below. You should only sign it if you are willing to authenticate yourself to them.");
        explanation.addProperty("Site",getSiteHref(),"The Site that You are Authenticating to");
        try {
            explanation.addProperty("ValidTo",Utility.formatTimeStamp(getValidTo()),"How long is this ticket valid to");
        } catch (NeudistException e) {
            explanation.addProperty("ValidTo","Unspecified or Unreadable","How long is this ticket valid to. Warning, this is valid for ever. Do you really want that?");
        }

        return super.explain();
    }
*/

    private static final String TAG_NAME = "AuthenticationTicket";
    public static final String URI_NSAUTH = "http://neuclear.org/neu/nsauth";
    public static final Namespace NS_NSAUTH = DocumentHelper.createNamespace("nsauth", URI_NSAUTH);
}
