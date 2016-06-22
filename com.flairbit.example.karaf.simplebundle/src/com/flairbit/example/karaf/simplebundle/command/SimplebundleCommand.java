package com.flairbit.example.karaf.simplebundle.command;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.flairbit.example.karaf.simplebundle.api.Simplebundle;
import osgi.enroute.debug.api.Debug;

/**
 * This is the implementation. It registers the Simplebundle interface and calls it
 * through a Gogo command.
 * 
 */
@Component(service=SimplebundleCommand.class, property = { Debug.COMMAND_SCOPE + "=smplbnd",
		Debug.COMMAND_FUNCTION + "=smplbnd" }, name="com.flairbit.example.karaf.simplebundle.command")
public class SimplebundleCommand {
	private Simplebundle target;

	public void smplbnd(String message) {
		target.say(message);
	}

	@Reference
	void setSimplebundle(Simplebundle service) {
		this.target = service;
	}
}
