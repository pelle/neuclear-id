/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 14, 2002
 * Time: 1:13:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.auth;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.Identity;
import org.neuclear.id.NamedObjectReader;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.SignedNamedCore;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.sql.Timestamp;

/**
 * This Authentication Ticket is used by websites to authenticate a user.
 * It generates a unique Name in the users Identity, which the user then signs.
 */
public final class AuthenticationTicket extends SignedNamedObject {
    /**
     * <p>Used by a website to create an authentication ticket for validation.</p>
     * <p>Eg.:<br>
     * <tt>SignedNamedObject ticket=new AuthenticationTicket("neu://test/bob","neu://site/neubay",36000,"http://neubay.com");</tt><br>
     * Would give you a namedobject containing the following xml:<br>
     * <pre>&lt;auth:AuthenticationTicket xmlns:auth="http://neuclear.org/neu/auth" xmlns:nsdl="http://neuclear.org/neu/nsdl" nsdl:name="/test/two/neu.testapp.-2o1qkqrvxyesyt7dae22ulvp56eju30zyys5t6nxjjie2gw3qq" auth:validto="20021002T084919848GMT+00:00" auth:href="http://localhost:8080/neuclearframework/"&gt;
     * &lt;/auth:AuthenticationTicket&gt;
     * </pre>
     * 
     * @param core
     * @param requester
     * @param validto   
     * @param siteurl   
     * @throws NeuClearException 
     */
    private AuthenticationTicket(SignedNamedCore core, String requester, Timestamp validto, String siteurl) throws NeuClearException {
        super(core);
        this.validTo = validto;
        this.siteurl = siteurl;
        this.requester = requester;

    }


    /**
     * Get the end time of the validity of the ticket
     * 
     * @return Timestamp object containing the end time of the ticket
     * @throws NeuClearException 
     */
    public final Timestamp getValidTo() throws NeuClearException {
        return validTo;
    }

    /**
     * The Site URL of the site requesting authentication.
     * 
     * @return the URL or null if unavailable.
     */
    public final String getSiteHref() {
        return siteurl;
    }

    public final static class Reader implements NamedObjectReader {
        /**
         * Read object from Element and fill in its details
         * 
         * @param elem 
         * @return 
         */
        public final SignedNamedObject read(SignedNamedCore core, Element elem) throws NeuClearException, XMLSecurityException {
            String requester = elem.attributeValue(DocumentHelper.createQName("requester", NS_NSAUTH));
            String sitehref = elem.attributeValue(DocumentHelper.createQName("sitehref", NS_NSAUTH));
            Timestamp validto = TimeTools.parseTimeStamp(elem.attributeValue(DocumentHelper.createQName("validto", NS_NSAUTH)));

            return new AuthenticationTicket(core, requester, validto, sitehref);
        }

    }

    private final String requester;
    private final String siteurl;
    private final Timestamp validTo;
    public static final String TAG_NAME = "AuthenticationTicket";
    public static final String URI_NSAUTH = "http://neuclear.org/neu/auth";
    public static final Namespace NS_NSAUTH = DocumentHelper.createNamespace("auth", URI_NSAUTH);
}
