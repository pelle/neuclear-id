package org.neuclear.id.receiver;

/*
$Id: HandlingReceiver.java,v 1.1 2004/07/21 23:04:05 pelle Exp $
$Log: HandlingReceiver.java,v $
Revision 1.1  2004/07/21 23:04:05  pelle
Added DelegatingReceiver and friends. This was created to make it easier to create complex services in NeuClear.

*/

/**
 * User: pelleb
 * Date: Jul 21, 2004
 * Time: 12:54:43 PM
 */
public interface HandlingReceiver extends Receiver {
    public String handlesTagName();
}
