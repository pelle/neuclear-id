package org.neuclear.id.verifier;

import org.neuclear.id.InvalidNameSpaceException;
import org.neuclear.id.NSTools;
import org.neuclear.id.NameSpace;
import org.neuclear.id.NamedObject;
import org.neuclear.id.resolver.NSResolver;
import org.neudist.crypto.CryptoTools;
import org.neudist.utils.NeudistException;
import org.neudist.xml.xmlsec.XMLSecurityException;

import java.security.PublicKey;

/**
 * Verifies that an object has the correct Sig
 */
public final class NSVerifier {
    private static final String NSROOTPKMOD = "AKbv1DrfQCj7fbcc/9U8mLHi9LzFGVw8ac9z26BN1+yeq9VG3wvW+OXjvUpQ9cD+dpwpFXeai9Hz DkFeJcT9Coi9A8Aj4nffWAlxJ/AVOIRCi1d4A/d9InhQ7UYYA5O7XBcwKneopYVa9zRDUoy0ZpVy t9Kj5i0Zw6oZsflAu4S4pIU+niYwwWrYmCuBEq9kecf7nSGiU0rHp1QNs7NYdhXCl2BMcSaz0AZt AF8YLlZYasviJkoxUFBB/Vjqa98xi7V7XIGsMbXWqUvJ8MW2N/CUdBz5aDlpBUwul8rqyq+03A0q 84AFJiUcudqVL7KhURXB8ZYy/hZb+YkEvE3IigU=";
    private static final String NSROOTPKEXP = "AQAB";
    private static PublicKey nsrootpk;

    /**
     * We first check if the name is syntactically correct.
     * Then we check if it is a toplevel NameSpace.
     * If it is we check if the objects signature matches the toplevel namespace
     * We check if the parent is valid
     * We check if this object is allowed within the parents namespace
     */
    public final static boolean isNameValid(NamedObject obj) throws NeudistException {
        if (!NSTools.isValidName(obj.getName()))
            return false;

        String parentName = NSTools.getParentNSURI(obj.getName());

        if (parentName.equals("neu://")) {
            boolean valid = obj.verifySignature(getRootPK());
//            if (valid && obj instanceof NameSpace)
//                NSResolver.NSCACHE.cache((NameSpace)obj);
            return valid;
        }

        // Allright we need to do this the hard way
        NameSpace parent = null;
        try {
            parent = NSResolver.resolveNameSpace(parentName);
        } catch (InvalidNameSpaceException e) {
            return false;
        }

        //Quick hack to allow adding of arbitrary root. If the parent doesnt exist we assume its as root.
        //if (parent==null&&getName().equals("neu://")) return true;

        boolean valid = (parent != null && parent.postAllowed(obj));
//        if (valid && obj instanceof NameSpace)
//            NSResolver.NSCACHE.cache((NameSpace)obj);
        return valid;
    }

    /**
     *  Returns the fixed Root PublicKey
     */
    public final static PublicKey getRootPK() throws XMLSecurityException {
        if (nsrootpk == null)
            nsrootpk = CryptoTools.createPK(NSROOTPKMOD, NSROOTPKEXP);
        return nsrootpk;
    }

}
