/*
 * $Id: ReceiverServlet.java,v 1.3 2004/04/21 00:10:28 pelle Exp $
 * $Log: ReceiverServlet.java,v $
 * Revision 1.3  2004/04/21 00:10:28  pelle
 * Index page looks a bit nicer.
 *
 * Revision 1.2  2004/04/20 23:33:08  pelle
 * All unit tests (junit and cactus) work. The AssetControllerServlet is operational.
 *
 * Revision 1.1  2004/03/03 23:26:43  pelle
 * Updated various tests to use the AbstractObjectCreationTest
 *
 * Revision 1.16  2004/01/13 15:11:35  pelle
 * Now builds.
 * Now need to do unit tests
 *
 * Revision 1.15  2003/12/15 23:33:04  pelle
 * added ServletTools.getInitParam() which first tries the ServletConfig, then the context config.
 * All the web.xml's have been updated to support this. Also various further generalizations have been done throughout
 * for getServiceid(), getTitle(), getSigner()
 *
 * Revision 1.14  2003/12/14 20:53:04  pelle
 * Added ServletPassPhraseAgent which uses ThreadLocal to transfer the passphrase to the signer.
 * Added ServletSignerFactory, which builds Signers for use within servlets based on parameters in the Servlets
 * Init parameters in web.xml
 * Updated SQLContext to use ThreadLocal
 * Added jakarta cactus unit tests to neuclear-commons to test the 2 new features above.
 * Added use of the new features in neuclear-commons to the servilets within neuclear-id and added
 * configuration parameters in web.xml
 *
 * Revision 1.13  2003/12/12 15:12:50  pelle
 * The ReceiverServletTest now passes.
 * Add first stab at a SigningServletTest which currently doesnt pass.
 *
 * Revision 1.12  2003/12/12 00:13:11  pelle
 * This may actually work now. Need to put a few more test cases in to make sure.
 *
 * Revision 1.11  2003/12/08 19:32:32  pelle
 * Added support for the http scheme into ID. See http://neuclear.org/archives/000195.html
 *
 * Revision 1.10  2003/11/28 00:12:58  pelle
 * Getting the NeuClear web transactions working.
 *
 * Revision 1.9  2003/11/24 23:33:37  pelle
 * More Cactus unit testing going on.
 *
 * Revision 1.8  2003/11/22 00:23:47  pelle
 * All unit tests in commons, id and xmlsec now work.
 * AssetController now successfully processes payments in the unit test.
 * Payment Web App has working form that creates a TransferRequest presents it to the signer
 * and forwards it to AssetControlServlet. (Which throws an XML Parser Exception) I think the XMLReaderServlet is bust.
 *
 * Revision 1.7  2003/11/21 04:45:13  pelle
 * EncryptedFileStore now works. It uses the PBECipher with DES3 afair.
 * Otherwise You will Finaliate.
 * Anything that can be final has been made final throughout everyting. We've used IDEA's Inspector tool to find all instance of variables that could be final.
 * This should hopefully make everything more stable (and secure).
 *
 * Revision 1.6  2003/11/11 21:18:43  pelle
 * Further vital reshuffling.
 * org.neudist.crypto.* and org.neudist.utils.* have been moved to respective areas under org.neuclear.commons
 * org.neuclear.signers.* as well as org.neuclear.passphraseagents have been moved under org.neuclear.commons.crypto as well.
 * Did a bit of work on the Canonicalizer and changed a few other minor bits.
 *
 * Revision 1.5  2003/10/21 22:31:13  pelle
 * Renamed NeudistException to NeuClearException and moved it to org.neuclear.commons where it makes more sense.
 * Unhooked the XMLException in the xmlsig library from NeuClearException to make all of its exceptions an independent hierarchy.
 * Obviously had to perform many changes throughout the code to support these changes.
 *
 * Revision 1.4  2003/09/26 00:22:07  pelle
 * Cleanups and final changes to code for refactoring of the Verifier and Reader part.
 *
 * Revision 1.3  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.2  2003/09/22 19:24:02  pelle
 * More fixes throughout to problems caused by renaming.
 *
 * Revision 1.1.1.1  2003/09/19 14:41:49  pelle
 * First import into the neuclear project. This was originally under the SF neuclear
 * project. This marks a general major refactoring and renaming ahead.
 *
 * The new name for this code is NeuClear Identity and has the general package header of
 * org.neuclear.id
 * There are other areas within the current code which will be split out into other subprojects later on.
 * In particularly the signers will be completely seperated out as well as the contract types.
 *
 *
 * Revision 1.8  2003/02/18 14:57:20  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.7  2003/02/09 00:15:55  pelle
 * Fixed things so they now compile with r_0.7 of XMLSig
 *
 * Revision 1.6  2002/12/04 13:52:48  pelle
 * Biggest change is a fix to the Canonicalization Writer It should be a lot more compliant with C14N now.
 * I think the only thing left to do is to sort the element attributes.
 *
 * Revision 1.5  2002/10/02 21:03:45  pelle
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
 * Revision 1.4  2002/09/21 23:11:16  pelle
 * A bunch of clean ups. Got rid of as many hard coded URL's as I could.
 *
 * User: pelleb
 * Date: Sep 19, 2002
 * Time: 5:20:55 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.id.receiver;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.commons.NeuClearException;
import org.neuclear.commons.crypto.signers.ServletSignerFactory;
import org.neuclear.commons.crypto.signers.Signer;
import org.neuclear.commons.servlets.ServletTools;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.verifier.VerifyingReader;
import org.neuclear.xml.XMLException;
import org.neuclear.xml.soap.XMLInputStreamServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

public class ReceiverServlet extends XMLInputStreamServlet {
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        try {
            alias = ServletTools.getInitParam("serviceid", config);
            title = ServletTools.getInitParam("title", config);
            signer = createSigner(config);
        } catch (Exception e) {
            ctx.log("Error in ReceiverServlet: " + e.getLocalizedMessage());
            throw new ServletException(e);
        }
    }

    protected final void handleInputStream(final InputStream is, final HttpServletRequest request, final HttpServletResponse response) throws IOException, NeuClearException, XMLException {
        final boolean isXML = request.getContentType().equals("text/xml");
        if (isXML) {
            response.setContentType("text/xml");
        } else {
            response.setContentType("text/html");
        }
        final PrintWriter writer = response.getWriter();
        if (!isXML)
            writer.print("<html><head><title>ReceiverServler</title></head><body>");

        final SignedNamedObject obj = VerifyingReader.getInstance().read(is);
        if (obj == null)
            throw new NeuClearException("Missing Request");
        ctx.log("NeuClear: Got Request " + obj.getName());
        ctx.log(obj.getEncoded());
        SignedNamedObject receipt = receiver.receive(obj);
        if (isXML)
            writer.print(receipt.getEncoded());
        else {
            writer.print("<h1>Successful</h1><div style=\"font-size:small\">Receipt:</br>");
            writer.print(receipt.getName());
            writer.print("</div>");
            writer.println("<form><textarea rows=\"30\" cols=\"80\">");
            writer.println(receipt.getEncoded());
            writer.println("</textarea></form><hr><a href=\"");
            writer.println(ServletTools.getAbsoluteURL(request, "/"));
            writer.println("\">Go to Menu</a>");
        }

        writer.close();

    }

    protected final void setReceiver(final Receiver receiver) {
        this.receiver = receiver;
    }

    final Receiver getReceiver() {
        return receiver;
    }

    public Signer getSigner() {
        return signer;
    }      ;
    protected Signer createSigner(ServletConfig config) throws GeneralSecurityException, NeuClearException, IOException {
        return ServletSignerFactory.getInstance().createSigner(config);
    }

    public final String getTitle() {
        return title;
    }

    public final String getAlias() {
        return alias;
    }

    private Receiver receiver;
    private Signer signer;
    private static final Element OK = DocumentHelper.createElement("Status");
    private String alias;
    private String title;

    {
        OK.setText("OK");
    }
}
