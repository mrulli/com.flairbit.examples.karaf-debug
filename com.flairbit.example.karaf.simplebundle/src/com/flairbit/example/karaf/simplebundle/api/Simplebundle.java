package com.flairbit.example.karaf.simplebundle.api;

import org.osgi.annotation.versioning.ProviderType;

/**
 * This is an example enroute bundle that has a component that implements a
 * simple API. 
 */

@ProviderType
public interface Simplebundle {
	
	/**
	 * The interface is a minimal method.
	 * 
	 * @param message the message to say
	 * @return true if the message could be spoken
	 */
	boolean say(String message);

}
