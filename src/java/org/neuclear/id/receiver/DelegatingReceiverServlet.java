package org.neuclear.id.receiver;

import org.nanocontainer.servlet.ApplicationScopeObjectReference;
import org.nanocontainer.servlet.KeyConstants;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.ObjectReference;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/*
$Id: DelegatingReceiverServlet.java,v 1.1 2004/07/21 23:04:05 pelle Exp $
$Log: DelegatingReceiverServlet.java,v $
Revision 1.1  2004/07/21 23:04:05  pelle
Added DelegatingReceiver and friends. This was created to make it easier to create complex services in NeuClear.

*/

/**
 * User: pelleb
 * Date: Jul 21, 2004
 * Time: 11:11:23 AM
 */
public class DelegatingReceiverServlet extends ReceiverServlet implements KeyConstants {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        MutablePicoContainer container = getApplicationContainer(config.getServletContext());
        setReceiver((Receiver) container.getComponentInstance(Receiver.class));
    }

    private MutablePicoContainer getApplicationContainer(ServletContext context) {
        ObjectReference ref = new ApplicationScopeObjectReference(context, APPLICATION_CONTAINER);
        return (MutablePicoContainer) ref.get();
    }
}
