/*
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Sep 18, 2002
 * Time: 1:03:28 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.neuclear.store.servlet;

import org.neuclear.commons.Utility;
import org.neuclear.receiver.ReceiverServlet;
import org.neuclear.store.FileStore;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public class StorageServlet extends ReceiverServlet {
    public void init(ServletConfig config) throws ServletException {
        System.out.println("NEUDIST: Initialising StorageServlet");
        super.init(config);
        storePath = config.getServletContext().getRealPath(Utility.denullString(config.getInitParameter("basedir"), "/WEB-INF/store"));
        System.out.println("NEUDIST: Setting up store at: " + storePath);
        setReceiver(new FileStore(storePath));

    }

    private String storePath;


}
