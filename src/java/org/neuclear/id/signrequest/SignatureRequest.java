/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 14, 2002
 * Time: 1:13:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id.signrequest;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.crypto.CryptoTools;
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neuclear.utils.NeudistException;
import org.neuclear.utils.Utility;

import java.security.PrivateKey;
import java.util.Date;

/**
 * The Signature Request is a signed request for forwarding to a signing server. Its simply a signed wrapper around an unsigned
 * Named Object.
 */
public class SignatureRequest extends NamedObject {
    /**
     * <p>Used by a website to create an authentication ticket for validation.</p>
     * <p>Eg.:<br>
     * <tt>NamedObject ticket=new AuthenticationTicket("neu://test/bob","neu://site/neubay",36000,"http://neubay.com");</tt><br>
     * Would give you a namedobject containing the following xml:<br>
     * <pre>&lt;nsauth:AuthenticationTicket xmlns:nsauth="http://neuclear.org/neu/nsauth" xmlns:nsdl="http://neuclear.org/neu/nsdl" nsdl:name="/test/two/neu.testapp.-2o1qkqrvxyesyt7dae22ulvp56eju30zyys5t6nxjjie2gw3qq" nsauth:validto="20021002T084919848GMT+00:00" nsauth:href="http://localhost:8080/neudistframework/"&gt;
     * &lt;/nsauth:AuthenticationTicket&gt;
     *</pre>
     *
     * @param reqNameSpace The requesters NameSpace eg. neu://site/neubay
     * @param target URL for interactive signing service to send user to after signing.
     * @param payload the NamedObject to request signing
     */
    private SignatureRequest(String reqNameSpace,String target,NamedObject payload) throws NeudistException {
        super(createUniqueTicketName(reqNameSpace,payload.getName()), SignatureRequest.TAG_NAME,SignatureRequest.NS_NSSIGREQ);
        addElement(payload);
        this.payload=payload;
        Element root=getElement();
        if (!Utility.isEmpty(target))
            root.addAttribute(DocumentHelper.createQName("target",NS_NSSIGREQ),target);
    }

    /**
     * This constructor is used to create a UserAuthenticationTicket from an XML Element
     * @param elem AuthenticationTicket element
     * @throws NeudistException
     */
    public SignatureRequest(Element elem) throws NeudistException {
        super(elem);
        Element payloadElement=(Element)elem.elements().get(0);
        if (payloadElement!=null)
               payload=NamedObjectFactory.createNamedObject(payloadElement);
    }

    public static SignatureRequest createRequest(String requester,String targeturl,NamedObject payload,PrivateKey signer) throws NeudistException {
        SignatureRequest req=new SignatureRequest(requester,targeturl,payload);
        req.sign(signer);
        return req;
    }
    /**
     * This is just used to create a unique ticket for use by the ticket
     * @param targetNameSpace
     * @param reqNameSpace
     * @return
     */
    private static String createUniqueTicketName(String targetNameSpace,String reqNameSpace) {
        // Yeah, yeah there are better ways to do this
        String ms=new Long(new Date().getTime()).toString();
        byte ticketsrc[]=new byte[ms.length()+reqNameSpace.length()];
        System.arraycopy(ms.getBytes(),0,ticketsrc,0,ms.length());
        System.arraycopy(reqNameSpace.getBytes(),0,ticketsrc,ms.length(),reqNameSpace.length());
        String ticket=CryptoTools.formatAsURLSafe(CryptoTools.digest256(ticketsrc));
        //Lets reuse ticketsrc for memory reasons
        int offset=ms.length()+1;
        if (reqNameSpace.startsWith("neu://"))
            offset+=5;


        for (int i=offset;i<ticketsrc.length;i++) {
            if (ticketsrc[i]==(byte)'/')
                ticketsrc[i]=(byte)'.';
        }
/*
        byte ticketName[]=new byte[userNameSpace.length()+33]; // Create new Name byte array to hold userNameSpace a '/' and the generated ticket (size 32)
        System.arraycopy(userNameSpace.getBytes(),0,ticketName,0,userNameSpace.length());
        ticketName[userNameSpace.length()]=(byte)'/';
        System.arraycopy(ticket,0,ticketName,userNameSpace.length()+1,ticket.length);
*/
        return targetNameSpace+'/'+new String(ticketsrc,offset,ticketsrc.length-offset)+'.'+ticket;
    }

    /**
     * The Site URL of the site requesting authentication.
     * @return the URL or null if unavailable.
     */
    public String getSiteHref() {
        return getElement().attributeValue(DocumentHelper.createQName("href",NS_NSSIGREQ));
    }
    public String getTagName() {
         return TAG_NAME;
     }

    /**
     * @return the XML NameSpace object
     */
    public Namespace getNS() {
        return NS_NSSIGREQ;
    }

    public NamedObject getPayload() {
        return payload;
    }
    private NamedObject payload;

    private  static final String TAG_NAME="SignatureRequest";
    public static final String URI_NSSIGREQ="http://neuclear.org/neu/nssigrequest";
    public static final Namespace NS_NSSIGREQ=DocumentHelper.createNamespace("nsauth",URI_NSSIGREQ);

}
