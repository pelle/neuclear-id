/*
 * $Id: SigningServlet.java,v 1.18 2003/12/12 00:13:11 pelle Exp $
 * $Log: SigningServlet.java,v $
 * Revision 1.18  2003/12/12 00:13:11  pelle
 * This may actually work now. Need to put a few more test cases in to make sure.
 *
 * Revision 1.17  2003/12/11 23:57:29  pelle
 * Trying to test the ReceiverServlet with cactus. Still no luck. Need to return a ElementProxy of some sort.
 * Cleaned up some missing fluff in the ElementProxy interface. getTagName(), getQName() and getNameSpace() have been killed.
 *
 * Revision 1.16  2003/12/10 23:58:52  pelle
 * Did some cleaning up in the builders
 * Fixed some stuff in IdentityCreator
 * New maven goal to create executable jarapp
 * We are close to 0.8 final of ID, 0.11 final of XMLSIG and 0.5 of commons.
 * Will release shortly.
 *
 * Revision 1.15  2003/11/21 04:45:14  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.14  2003/11/19 23:33:59  pelle
 * Signers now can generatekeys via the generateKey() method.
 * Refactored the relationship between SignedNamedObject and NamedObjectBuilder a bit.
 * SignedNamedObject now contains the full xml which is returned with getEncoded()
 * This means that it is now possible to further receive on or process a SignedNamedObject, leaving
 * NamedObjectBuilder for its original purposes of purely generating new Contracts.
 * NamedObjectBuilder.sign() now returns a SignedNamedObject which is the prefered way of processing it.
 * Updated all major interfaces that used the old model to use the new model.
 *
 * Revision 1.13  2003/11/18 23:35:45  pelle
 * Payment Web Application is getting there.
 *
 * Revision 1.12  2003/11/18 00:01:55  pelle
 * The sample signing web application for logging in and out is now working.
 * There had been an issue in the canonicalizer when dealing with the embedded object of the SignatureRequest object.
 *
 * Revision 1.11  2003/11/15 01:58:16  pelle
 * More work all around on web applications.
 *
 * Revision 1.10  2003/11/13 23:26:42  pelle
 * The signing service and web authentication application is now almost working.
 *
 * Revision 1.9  2003/11/11 21:18:44  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.8  2003/11/05 23:40:21  pelle
 * A few minor fixes to make all the unit tests work
 * Also the start of getting SigningServlet and friends back working.
 *
 * Revision 1.7  2003/10/29 21:16:27  pelle
 * Refactored the whole signing process. Now we have an interface called Signer which is the old SignerStore.
 * To use it you pass a byte array and an alias. The sign method then returns the signature.
 * If a Signer needs a passphrase it uses a PassPhraseAgent to present a dialogue box, read it from a command line etc.
 * This new Signer pattern allows us to use secure signing hardware such as N-Cipher in the future for server applications as well
 * as SmartCards for end user applications.
 *
 * Revision 1.6  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.5  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
 * Revision 1.4  2003/09/24 23:56:49  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:29  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:40  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.17  2003/02/18 14:57:29  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.16  2003/02/18 00:06:15  pelle
 * Moved the Signer's into xml-sig
 *
 * Revision 1.15  2003/02/14 21:10:36  pelle
 * The email sender works. The LogSender and the SoapSender should work but havent been tested yet.
 * The SignedNamedObject has a new log() method that logs it's contents at it's parent Identity's logger.
 * The Identity object also has a new method receive() which allows one to receive a named object to the Identity's
 * default receiver.
 *
 * Revision 1.14  2003/02/10 22:30:15  pelle
 * Got rid of even further dependencies. In Particular OSCore
 *
 * Revision 1.13  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.12  2002/12/17 21:40:59  pelle
 * First part of refactoring of SignedNamedObject and SignedObject Interface/Class parings.
 *
 * Revision 1.11  2002/12/17 20:34:42  pelle
 * Lots of changes to core functionality.
 * First of all I've refactored most of the Resolving and verification code. I have a few more things to do
 * on it before I'm happy.
 * There is now a NSResolver class, which handles all the namespace resolution. I took most of the functionality
 * for this out of SignedNamedObject.
 * Then there is the veriifer, which verifies a given SignedNamedObject using the NSResolver.
 * This has simplified the SignedNamedObject classes drastically, leaving them as mainly data objects, which is what they
 * should be.
 * I have also gone around and tightened up security on many different classes, making clases and/or methods final where appropriate.
 * NSCache now operates using http://www.waterken.com's fantastic ADT collections library.
 * Something important has been added, which is a SignRequest named object. This signed object, embeds an unsigned
 * named object for signing by an end users' signing service.
 * Now were almost ready to start seriously implementing AssetIssuers and Transfers, which will be the most important
 * part of the framework.
 *
 * Revision 1.10  2002/10/10 21:29:31  pelle
 * Oops. XML-Signature's SignedInfo element I had coded as SignatureInfo
 * As I thought Canonicalisation doesnt seem to be standard.
 * Updated the SignedServlet to default to using ~/.neuclear/signers.ks
 *
 * Revision 1.9  2002/10/06 00:39:29  pelle
 * I have now expanded support for different types of Signers.
 * There is now a JCESigner which uses a JCE KeyStore for signing.
 * I have refactored the SigningServlet a bit, eliminating most of the demo code.
 * This has been moved into DemoSigningServlet.
 * I have expanded the CommandLineSigner, so it now also has an option for specifying a default signing service.
 * The default web application now contains two signers.
 * - The Demo one is still at /Signer
 * - There is a new one at /personal/Signer this uses the testkeys.ks for
 * signing anything under neu://test
 * Note neu://test now has a default interactive signer running on localhost.
 * So to play with this you must install the webapp on your own local machine.
 *
 * Revision 1.8  2002/10/02 21:03:45  pelle
 * Major Commit
 * I completely redid the namespace resolving code.
 * It now works correctly with the new store attribute of the namespace
 * And can correctly work out the location of a namespace file
 * by hierarchically signing it.
 * I have also included several top level namespaces and finalised
 * the root namespace.
 * In short all of the above means that we can theoretically call
 * Neubia live now. (Well on my first deployment anyway).
 * There is a new CommandLineSigner utility class which creates and signs
 * namespaces using standard java keystores.
 * I'm now working on updating the documentation, so other people
 * than me might have a chance at using it.
 *
 * Revision 1.7  2002/09/29 00:22:10  pelle
 * Several cosmetic changes.
 * First attempt at a new CommandLine tool for signing and creating namespace files.
 * This will be used by people to create requests for namespaces.
 *
 * Revision 1.6  2002/09/25 19:20:15  pelle
 * Added various new schemas and updated most of the existing ones.
 * Added explanation interface for explaining the purpose of a
 * SignedNamedObject to a user. We may want to use XSL instead.
 * Also made the signing webapp look a bit nicer.
 *
 * Revision 1.5  2002/09/23 15:09:18  pelle
 * Got the SimpleSigner working properly.
 * I couldn't get SealedObjects working with BouncyCastle's Symmetric keys.
 * Don't know what I was doing, so I reimplemented it. Encrypting
 * and decrypting it my self.
 *
 * Revision 1.4  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 19, 2002
 * Time: 5:18:18 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.signers.servlet;

import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.Utility;
import org.neuclear.commons.crypto.Base64;
import org.neuclear.commons.crypto.passphraseagents.PassPhraseAgent;
import org.neuclear.commons.crypto.signers.InvalidPassphraseException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.crypto.signers.TestCaseSigner;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.NSTools;
import org.neuclear.id.SignatureRequest;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.receiver.ReceiverServlet;
import org.neuclear.xml.xmlsec.XMLSecTools;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class SigningServlet extends ReceiverServlet implements PassPhraseAgent {
    public final void init(final ServletConfig config) throws ServletException {
        System.out.println("NEUDIST: Initialising SigningServlet");
        super.init(config);
        context = config.getServletContext();
        reqMap = new HashMap();
        try {
            System.out.println("NEUDIST: Initialising SigningServlet");
            title = Utility.denullString(config.getInitParameter("title").toString(), "NeuClear Signing Service");
            if (signer == null) {
                signer = new TestCaseSigner(this);
            }
            System.out.println("NEUDIST: Finished SigningServlet Init ");

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (NeuClearException e) {
            e.printStackTrace();
        }


    }


    protected static final Signer getSigner() {
        return signer;
    }

    protected final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        System.out.println("NEUDIST: doPost()");
        if (request.getContentType().equals("text/xml")) {
            System.out.println("NEUDIST: call SOAP Servlet");
            super.doPost(request, response);
            return;
        }
        reqMap.put(Thread.currentThread(), request);
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        ServletTools.printHeader(out, request, title);
        final String b64xml = request.getParameter("base64xml");
        final String endpoint = request.getParameter("endpoint");
        final NamedObjectBuilder named;
        SignatureRequest sigreq = null;
        boolean isSigned = false;
        try {
            if (!Utility.isEmpty(b64xml)) {
                sigreq = (SignatureRequest) VerifyingReader.getInstance().read(new ByteArrayInputStream(Base64.decode(b64xml)));
            } else {
                out.print("Nothing to Sign..");
                return;
            }

            if (sigreq != null) {

                named = sigreq.getUnsigned();
                if (!Utility.isEmpty(request.getParameter("sign"))) {
                    final String parent = NSTools.getSignatoryURI(named.getName());
                    out.println("Signing with " + parent + "...");
                    out.flush();
                    try {
                        context.log("SIGN: Signing with " + parent);
                        final SignedNamedObject signed = named.sign(signer);
                        isSigned = true;
                        out.println("Signed<br>");
                        out.println("<br>Verifying...");
                        out.flush();
                        out.println(signed.getName() + " Verified<br>");

                    } catch (InvalidNamedObjectException e) {
                        out.println("<br><font color=\"red\"><b>ERROR: Invalid Identity</b></font><br>");
                        //e.printStackTrace(out);
                        isSigned = false;
                    } catch (InvalidPassphraseException e) {
                        out.println("<br><font color=\"red\"><b>ERROR: Wrong Passphrase</b></font><br>");
                        isSigned = false;
                    } catch (NonExistingSignerException e) {
                        out.println("<br><font color=\"red\"><b>ERROR: We Aren't Able to Sign for that Identity</b></font><br>");
                        isSigned = false;
                    }

                }
                out.println("<table bgcolor=\"#708070\"><tr><td><h4 style=\"color: white\">");
                if (isSigned)
                    out.println("Signed Item");
                else
                    out.println("Item to Sign:");
                out.println("</h4>");
                out.println("<b>Requesting Site:</b><br/>");
                out.println(sigreq.getSignatory().getName());
                out.println("<br><b>Type:</b><br/>");
                out.println(named.getElement().getName());
                if (!Utility.isEmpty(sigreq.getDescription())) {
                    out.println("<br><b>Description:</b><br/>");
                    out.println(sigreq.getDescription());
                }


                out.println("</td></tr></table>");
                if (!isSigned) {
                    out.println("<table bgcolor=\"#D0FFD0\"><tr><td bgcolor=\"#026A32\"><h4 style=\"color: white\">Do you wish to sign this?</h4></td></tr>");
                    out.print("<tr><td><form action=\"");
                    out.print(request.getRequestURL());
                    out.print("\" method=\"POST\"><input name=\"base64xml\" value=\"");
                    out.print(b64xml);
                    out.print("\" type=\"hidden\">\n <input name=\"endpoint\" value=\"");
                    out.print(endpoint);
                    out.println("\" type=\"hidden\"/>\nPassphrase: <input name=\"passphrase\" type=\"password\" size=\"40\">");
                    out.println(" <input type=\"submit\" name=\"sign\" value=\"Sign\"></form></td></tr></table>");
                } else if (!Utility.isEmpty(endpoint)) {
                    out.print("<tr><td>Signed, returning to site...<form action=\"");
                    out.print(endpoint);
                    out.print("\" method=\"POST\"><input name=\"neuclear-request\" value=\"");
//                    context.log("Signing Servlet: ");
                    out.print(XMLSecTools.encodeElementBase64(named));
                    out.println("\" type=\"hidden\"/>");
//                    out.write("<input type=\"submit\">");
                    out.write("</form>\n");
                    out.write("<script language=\"javascript\">\n");
                    out.write("<!--\n   document.forms[0].submit();\n-->\n");
                    out.write("</script>\n");


                }
            }
        } catch (Exception e) {
            out.println("<br><font color=\"red\"><pre>");
            e.printStackTrace(out);
            out.println("</pre></font>");
        }
        out.println("<p align\"left\"><img src=\"images/neubia40x40.png\"><br><a href=\"http://www.neubia.com\"><i>&copy; 2002 Antilles Software Ventures SA</i></a></body></html>");
        reqMap.remove(Thread.currentThread()); //Super Important
    }

    protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html");
        System.out.println("NEUDIST: doGet()");
        final PrintWriter out = response.getWriter();
        ServletTools.printHeader(out, request, title);
        out.println("<form method=\"POST\" action=\"Signer\"><textarea name=\"xml\" cols=\"80\"rows=\"30\"></textarea><br><input type=\"submit\" name=\"submit\" value=\"Confirm\"></form>");
        out.println("</body></html>");

    }

    /**
     * Retrieve the PassPhrase for a given name/alias
     * 
     * @param name 
     * @return 
     */
    public final char[] getPassPhrase(final String name) {
        if (reqMap == null)
            return null;
        final HttpServletRequest request = (HttpServletRequest) reqMap.get(Thread.currentThread());
        if (request == null)
            return null;
        final String passphrase = request.getParameter("passphrase");
        if (passphrase == null)
            return null;
        return passphrase.toCharArray();
    }

/*
    public Element receiveNamedObject(SignedNamedObject obj, String soapAction) throws SOAPException {
        try {
            signObject(obj, "hello".toCharArray());// TODO How do we get the passphrase here? Popup request?
            return obj.getElement();
        } catch (InvalidNamedObjectException e) {
            throw new SOAPException(e);
        } catch (InvalidPassphraseException e) {
            throw new SOAPException(e);
        } catch (NonExistingSignerException e) {
            throw new SOAPException(e);
        } catch (NeuClearException e) {
            throw new SOAPException(e);
        }
    }

    protected static void signObject(SignedNamedObject obj, char passphrase[]) throws NeuClearException, InvalidNamedObjectException, InvalidPassphraseException, NonExistingSignerException {
        if (!obj.isSigned()) {
            try {
                String parentName = NSTools.getSignatoryURI(obj.getName());
                PrivateKey pk = signer.getKey(parentName, passphrase);
                if (pk == null)
                    throw new NonExistingSignerException("Signing Service doesn't contain Signing keys for: " + parentName);
                obj.sign(pk);
//                obj.store();
                obj.sendObject();
            } catch (IOException e) {
                throw new XMLSecurityException(e);
//             } catch (GeneralSecurityException e) {
//                throw new XMLSecurityException(e);
            }
        }


    }

*/
    protected javax.servlet.ServletContext context;
    private static Signer signer;
    private String id;
    private String title;
    private Map reqMap;
}
