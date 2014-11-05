/**
 * 
 */
package com.gdbocom.util.communication;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author qm
 *
 */
public interface LoopPacket {
	public Map parseLoopResponseBody(byte[] response, int loopOffset)
            throws UnsupportedEncodingException;

}
