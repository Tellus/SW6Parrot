package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.Random;

/**
 * Server helper
 * @author Admin
 *
 */
public class ServerHelper {

	/**
	 * Get status
	 * @return Random
	 */
	public int getStatus() {
		Random r = new Random();
        int result = r.nextInt(3);
        
        return result;
	}
}
