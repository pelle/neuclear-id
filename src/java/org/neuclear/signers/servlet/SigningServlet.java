/*
 * $Id: SigningServlet.java,v 1.29 2004/02/18 00:14:33 pelle Exp $
 * $Log: SigningServlet.java,v $
 * Revision 1.29  2004/02/18 00:14:33  pelle
 * Many, many clean ups. I've readded Targets in a new method.
 * Gotten rid of NamedObjectBuilder and revamped Identity and Resolvers
 *
 * Revision 1.28  2004/01/13 15:11:35  pelle
 * Now builds.
 * Now need to do unit tests
 *
 * Revision 1.27  2003/12/19 18:03:35  pelle
 * Revamped a lot of exception handling throughout the framework, it has been simplified in most places:
 * - For most cases the main exception to worry about now is InvalidNamedObjectException.
 * - Most lowerlevel exception that cant be handled meaningful are now wrapped in the LowLevelException, a
 *   runtime exception.
 * - Source and Store patterns each now have their own exceptions that generalizes the various physical
 *   exceptions that can happen in that area.
 *
 * Revision 1.26  2003/12/19 00:31:31  pelle
 * Lots of usability changes through out all the passphrase agents and end user tools.
 *
 * Revision 1.25  2003/12/16 23:44:10  pelle
 * End of work day clean up
 *
 * Revision 1.24  2003/12/16 23:17:07  pelle
 * Work done on the SigningServlet. The two phase web model is now only an option.
 * Allowing much quicker signing, using the GuiDialogueAgent.
 * The screen has also been cleaned up and displays the xml to be signed.
 * The GuiDialogueAgent now optionally remembers passphrases and has a checkbox to support this.
 * The PassPhraseAgent's now have a UserCancellationException, which allows the agent to tell the application if the user specifically
 * cancels the signing process.
 *
 * Revision 1.23  2003/12/15 23:33:05  pelle
 * added ServletTools.getInitParam() which first tries the ServletConfig, then the context config.
 * All the web.xml's have been updated to support this. Also various further generalizations have been done throughout
 * for getServiceid(), getTitle(), getSigner()
 *
 * Revision 1.22  2003/12/15 14:38:30  pelle
 * Added EnsureHostRequestFilter to commons, to only allow requests from a particular IP
 * Added a method to optionally show the passphrase box in the SigningServlet. As the default SigningServlet
 * is intended to be used with a gui passphrase agent, we dont want to display it.
 * The DemoSigningServlet does display the dialogue.
 * Added the new neuclear-signer package, which is a standalone web signer using Jetty. The project runs
 * when built with "maven javaapp". More testing needs to be done as well as a startup wizard.
 *
 * Revision 1.21  2003/12/14 20:53:04  pelle
 * Added ServletPassPhraseAgent which uses ThreadLocal to transfer the passphrase to the signer.
 * Added ServletSignerFactory, which builds Signers for use within servlets based on parameters in the Servlets
 * Init parameters in web.xml
 * Updated SQLContext to use ThreadLocal
 * Added jakarta cactus unit tests to neuclear-commons to test the 2 new features above.
 * Added use of the new features in neuclear-commons to the servilets within neuclear-id and added
 * configuration parameters in web.xml
 *
 * Revision 1.20  2003/12/12 19:28:03  pelle
 * All the Cactus tests now for signing servlet.
 * Added working AuthenticationFilterTest
 * Returned original functionality to DemoSigningServlet.
 * This is set up to use the test keys stored in neuclear-commons.
 * SigningServlet should now work for general use. It uses the default
 * keystore. Will add configurability later. It also uses the GUIDialogAgent.
 *
 * Revision 1.19  2003/12/12 15:12:50  pelle
 * The ReceiverServletTest now passes.
 * Add first stab at a SigningServletTest which currently doesnt pass.
 *
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
import org.neuclear.commons.crypto.passphraseagents.UserCancellationException;
import org.neuclear.commons.crypto.signers.NonExistingSignerException;
import org.neuclear.commons.crypto.signers.ServletSignerFactory;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.InvalidNamedObjectException;
import org.neuclear.id.SignatureRequest;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.builders.Builder;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.soap.XMLInputStreamServlet;
import org.neuclear.xml.xmlsec.XMLSecTools;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SigningServlet extends XMLInputStreamServlet {
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        context = config.getServletContext();
        context.log("NEUCLEAR: Initialising SigningServlet");
        try {
            title = ServletTools.getInitParam("title",config);
            signer = createSigner(config);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (NeuClearException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected Signer createSigner(ServletConfig config) throws GeneralSecurityException, NeuClearException, IOException {
        return ServletSignerFactory.getInstance().createSigner(config);
    }

    protected final Signer getSigner() {
        return signer;
    }


    protected void handleInputStream(InputStream is, HttpServletRequest request, HttpServletResponse response) throws IOException, NeuClearException, XMLException {
        SignatureRequest sigreq = (SignatureRequest) VerifyingReader.getInstance().read(is);
        if (sigreq == null) {
            throw new NeuClearException("nothing to sign");
        }
        if (!signer.canSignFor(sigreq.getUserid()))
            throw new NonExistingSignerException(sigreq.getUserid());

        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("text/html");
        final PrintWriter out = response.getWriter();
        ServletTools.printHeader(out, request, getTitle());
        final String endpoint = request.getParameter("endpoint");
        final Builder named = sigreq.getUnsigned();
        final String username = sigreq.getUserid();
        boolean isSigned = false;
        if (!signer.canSignFor(username)) {
             out.println("<h3>Can not Sign for:");
            out.println(username);
            out.println("</h3>");
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
        out.println("</td></tr><tr><td style=\"background:lightgrey;color:black\"><tt>");
        Matcher matcher=xmlescape.matcher(named.asXML());
        out.println(matcher.replaceAll("&lt;"));

        out.println("</td></tr></table>");
        if (isReadyToSign(request)) {

            out.println("<div id=\"log\" style=\"background:#003;color:#EEE\"><tt><ul><li>Signing with " + username + "...</li>");
            out.flush();
            try {
                isSigned = sign(named,username, out);

            } catch (InvalidNamedObjectException e) {
//                System.out.println("<br><font color=\"red\"><b>ERROR: Invalid Identity</b></font><br>");
                out.println("<li><font color=\"red\"><b>ERROR: Invalid Identity</b></font></li>");
                isSigned = false;
            } catch (UserCancellationException e) {
//                System.out.println("<br><font color=\"red\"><b>ERROR: User Cancellation</b></font><br>");
                out.println("<li><font color=\"red\"><b>You Cancelled</b></font></li>");
                out.println("<li onclick=\"history.back(2)\"><b>Click to Go back</b></li>");
                isSigned = false;
            } catch (NonExistingSignerException e) {
//                System.out.println("<br><font color=\"red\"><b>ERROR: We Aren't Able to Sign for that Identity</b></font><br>");
                out.println("<li><font color=\"red\"><b>ERROR: We Aren't Able to Sign for that Identity</b></font></li>");
                isSigned = false;
            }
            out.println("</ul></tt></div>");

        }
        if (!isSigned) {
            printSecondStageForm(request, out, sigreq, endpoint);
        } else if (!Utility.isEmpty(endpoint)) {
            out.print("<tr><td>Returning to site: ");
            out.print(endpoint);
            out.print("<form action=\"");
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

    private boolean sign(final Builder named, String username,final PrintWriter out) throws NeuClearException, XMLException {
        boolean isSigned;
        context.log("SIGN: Signing with " + username);
        final SignedNamedObject signed = named.convert(username,signer);
        isSigned = true;
        out.println("<li>Signed</li>");
        out.println("<li>" + signed.getName() + " Verified</li>");
        out.flush();
//        } catch (InvalidPassphraseException e) {
//            out.println("<li><font color=\"red\"><b>ERROR: Wrong Passphrase</b></font></li>");
//            out.flush();
//            isSigned = sign(named,out);
//        }
        return isSigned;
    }

    protected void printSecondStageForm(HttpServletRequest request, final PrintWriter out, SignatureRequest sigreq, final String endpoint) {
        //out.println("<table><tr><td ><h4>Do you wish to sign this?</h4></td></tr></table>");
    }

    /**
     * Return True when ready to sign.
     * Multirequest signers, need to verify that the correct request parameters are available.
     * @param request
     * @return
     */
    protected boolean isReadyToSign(HttpServletRequest request) {
        return true;
    }

    protected String getTitle() {
        return title;
    }



    protected javax.servlet.ServletContext context;
    private Signer signer;
    private String title;
    static private Pattern xmlescape=Pattern.compile("(\\<)");
}
