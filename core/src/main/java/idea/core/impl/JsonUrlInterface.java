/**
 * 
 */
package idea.core.impl;

import java.io.IOException;

/**
 * @author sriik
 *
 */
public interface JsonUrlInterface {

	String sendGET() throws IOException;
	void sendPOST() throws IOException;
}
