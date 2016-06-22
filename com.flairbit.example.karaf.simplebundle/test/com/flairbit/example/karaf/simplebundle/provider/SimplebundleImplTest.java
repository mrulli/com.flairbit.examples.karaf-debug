package com.flairbit.example.karaf.simplebundle.provider;

import org.junit.Test;

import com.flairbit.example.karaf.simplebundle.api.Simplebundle;

/*
 * Example JUNit test case
 * 
 */

public class SimplebundleImplTest {

	/*
	 * Example test method
	 */

	@Test
	public void simple() {
		Simplebundle impl = new SimplebundleImpl();
		
		impl.say("Hello World");
	}

}
