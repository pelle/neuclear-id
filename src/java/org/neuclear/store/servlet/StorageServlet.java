/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 18, 2002
 * Time: 1:03:28 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.store.servlet;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.neuclear.id.builders.NamedObjectBuilder;
import org.neuclear.store.FileStore;
import org.neuclear.store.Store;
import org.neudist.utils.Utility;
import org.neudist.xml.soap.SOAPException;
import org.neudist.xml.soap.SOAPServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class StorageServlet extends SOAPServlet {
    public void init(ServletConfig config) throws ServletException {
        System.out.println("NEUDIST: Initialising StorageServlet");
        super.init(config);
        String storePath = config.getServletContext().getRealPath(Utility.denullString(config.getInitParameter("basedir"), "/WEB-INF/store"));
        System.out.println("NEUDIST: Setting up store at: " + storePath);
        store = new FileStore(storePath);

    }

    private Store getStore() {
        return store;
    }

    private Store store;

    protected Element handleSOAPRequest(Element request, String soapAction) throws SOAPException {
        try {
            store.receive(new NamedObjectBuilder(request));
        } catch (Exception e) {
            Element error = DocumentHelper.createElement("Error");
            error.setText(e.getMessage());
            return error;
        }
        return request;
    }

}
