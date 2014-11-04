/**
 * 
 */
package com.gdbocom.util.communication;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author qm
 *
 */
public interface LoopPacket {
    public List parseLoopResponseBody(byte[] response)
            throws UnsupportedEncodingException ;

}
