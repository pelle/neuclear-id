package org.neuclear.source;

import org.neuclear.id.NamedObject;
import org.neudist.utils.NeudistException;
import org.neudist.utils.Utility;

/**
 * (C) 2003 Antilles Software Ventures SA
 * User: pelleb
 * Date: Feb 10, 2003
 * Time: 8:26:04 PM
 * $Id: Source.java,v 1.2 2003/09/22 19:24:02 pelle Exp $
 * $Log: Source.java,v $
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
 * Revision 1.3  2003/02/18 14:57:31  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 * Revision 1.2  2003/02/14 14:05:01  pelle
 * The New Source Classes work and NS resolution works as well.
 * I've renamed Target to TargetReference to prepare for the other main refactoring ahead. Implementation of
 * Senders.
 *
 * Revision 1.1  2003/02/14 05:10:13  pelle
 * New Source model is implemented.
 * It doesnt quite verify things correctly yet. I'm not yet sure why.
 * CommandLineSigner is simplified to make it easier to use.
 *
 */
public abstract class Source {
    abstract public NamedObject fetch(String endpoint, String name) throws NeudistException;

    public static synchronized Source getInstance() {
        if (instance == null) {
            Source mainSource = null;
            String name = System.getProperty("org.neuclear.source");
            if (Utility.isEmpty(name)) {
                name = DEFAULT;
            }
            try {
                mainSource = (Source) Class.forName(name).newInstance();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.
                try {
                    mainSource = (Source) Class.forName(DEFAULT).newInstance();
                } catch (Exception e1) {
                    e1.printStackTrace();  //To change body of catch statement use Options | File Templates.
                    System.err.println("Real big problems");
                }
            }
            instance = new CachedSource(mainSource);
        }

        return instance;
    }

    private static Source instance;

    private final static String DEFAULT = "org.neuclear.source.HttpSource";
}
