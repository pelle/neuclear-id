/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 14, 2002
 * Time: 1:13:38 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id.auth;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.time.TimeTools;
import org.neuclear.id.*;
import org.neuclear.xml.xmlsec.XMLSecurityException;

import java.sql.Timestamp;
import java.text.ParseException;

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
     */
    private AuthenticationTicket(final SignedNamedCore core, final String requester, final Timestamp validto, final String siteurl)  {
        super(core);
        this.validTo = validto.getTime();
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
        return new Timestamp(validTo);
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
        public final SignedNamedObject read(final SignedNamedCore core, final Element elem) throws InvalidNamedObjectException {
            final QName qelem=DocumentHelper.createQName(TAG_NAME,NS_NSAUTH);
            InvalidNamedObjectException.assertElementQName(core,elem,qelem);
            final QName qreq = DocumentHelper.createQName("requester", NS_NSAUTH);
            final String requester = InvalidNamedObjectException.assertAttributeQName(core,elem,qreq);
            final QName qsite = DocumentHelper.createQName("sitehref", NS_NSAUTH);
            final String sitehref = InvalidNamedObjectException.assertAttributeQName(core,elem,qsite);
            try {
                final QName qtime = DocumentHelper.createQName("validto", NS_NSAUTH);
                final Timestamp validto = TimeTools.parseTimeStamp(InvalidNamedObjectException.assertAttributeQName(core,elem,qtime));
                return new AuthenticationTicket(core, requester, validto, sitehref);
            } catch (ParseException e) {
                throw new InvalidNamedObjectException(core.getName(),e.getLocalizedMessage());
            }
        }

    }

    private final String requester;
    private final String siteurl;
    private final long validTo;
    public static final String TAG_NAME = "AuthenticationTicket";
    public static final String URI_NSAUTH = "http://neuclear.org/neu/auth";
    public static final Namespace NS_NSAUTH = DocumentHelper.createNamespace("auth", URI_NSAUTH);
}
