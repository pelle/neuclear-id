/*
 * $Id: ReceiverServlet.java,v 1.1 2003/09/19 14:41:49 pelle Exp $
 * $Log: ReceiverServlet.java,v $
 * Revision 1.1  2003/09/19 14:41:49  pelle
 * Initial revision
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
import org.neuclear.id.NamedObject;
import org.neuclear.id.NamedObjectFactory;
import org.neuclear.utils.NeudistException;
import org.neuclear.xml.soap.SOAPException;
import org.neuclear.xml.soap.SOAPServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public abstract class ReceiverServlet extends SOAPServlet {
     public void init(ServletConfig config) throws ServletException {
         super.init(config);
    }

     protected Element handleSOAPRequest(Element request, String soapAction) throws SOAPException {
        try {
            System.out.println("NEUDIST: Got Storage Request "+soapAction);
            System.out.println(request.asXML());
            NamedObject named=NamedObjectFactory.createNamedObject(request);
            receiver.receive(named);
            return OK;
        } catch (NeudistException e) {
            e.printStackTrace(System.out);
            throw new SOAPException(e);
        }
    }
    protected void setReceiver(Receiver receiver) {
        this.receiver=receiver;
    }

    protected Receiver getReceiver() {
        return receiver;
    }

    private Receiver receiver;
    private static final Element OK=DocumentHelper.createElement("Status");
    {
        OK.setText("OK");
    }
}
