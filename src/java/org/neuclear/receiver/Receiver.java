package org.neuclear.receiver;

/**
 * Created by IntelliJ IDEA.
 * User: pelleb
 * Date: Oct 10, 2002
 * Time: 11:24:59 PM
 * To change this template use Options | File Templates.
 * $Id: Receiver.java,v 1.4 2003/09/24 23:56:48 pelle Exp $
 * $Log: Receiver.java,v $
 * Revision 1.4  2003/09/24 23:56:48  pelle
 * Refactoring nearly done. New model for creating signed objects.
 * With view for supporting the xmlpull api shortly for performance reasons.
 * Currently still uses dom4j but that has been refactored out that it
 * should now be very quick to implement a xmlpull implementation.
 *
 * A side benefit of this is that the API has been further simplified. I still have some work
 * todo with regards to cleaning up some of the outlying parts of the code.
 *
 * Revision 1.3  2003/09/23 19:16:28  pelle
 * Changed NameSpace to Identity.
 * To cause less confusion in the future.
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
 * Revision 1.3  2003/02/18 14:57:19  pelle
 * Finished Cleaning up Receivers and Stores.
 * Also updated nsdl.xsd xml schema with latest changes.
 * The whole API is now very simple.
 *
 */

import org.neuclear.id.InvalidIdentityException;
import org.neuclear.id.SignedNamedObject;
import org.neudist.utils.NeudistException;

/**
 * <p>The Receiver interface is the base interface for almost all applications based on the NeuDist Framework.
 * You simply implement this to process the different types of NamedObjects.</p>
 * <p>For a fully fledged application SubClass ReceiverServlet to handle an instance of your Receiver.</p>
 */
public interface Receiver {
    /**
     * Add your main transaction processing logic within this method.
     * Remember you must check the validity of the SignedNamedObject here. Until you do so
     * you can not trust it.
     * @param obj
     * @throws InvalidIdentityException
     * @throws NeudistException
     */
    void receive(SignedNamedObject obj) throws InvalidIdentityException, NeudistException;
}
