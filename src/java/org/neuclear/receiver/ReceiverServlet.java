/*
 * $Id: ReceiverServlet.java,v 1.3 2003/09/24 23:56:48 pelle Exp $
 * $Log: ReceiverServlet.java,v $
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
 * First import into the neuclear project. This was originally under the SF neudist
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
package org.neuclear.receiver;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.id.SignedNamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neudist.utils.NeudistException;
import org.neudist.xml.soap.SOAPException;
import org.neudist.xml.soap.SOAPServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public abstract class ReceiverServlet extends SOAPServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected Element handleSOAPRequest(Element request, String soapAction) throws SOAPException {
        try {
            System.out.println("NEUDIST: Got Storage Request " + soapAction);
            System.out.println(request.asXML());
            SignedNamedObject named = NamedObjectFactory.createNamedObject(request);
            receiver.receive(named);
            return OK;
        } catch (NeudistException e) {
            e.printStackTrace(System.out);
            throw new SOAPException(e);
        }
    }

    protected void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    protected Receiver getReceiver() {
        return receiver;
    }

    private Receiver receiver;
    private static final Element OK = DocumentHelper.createElement("Status");

    {
        OK.setText("OK");
    }
}
