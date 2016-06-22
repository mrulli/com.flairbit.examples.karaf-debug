package com.flairbit.example.karaf.simplebundle.provider;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.flairbit.example.karaf.simplebundle.api.Simplebundle;

/**
 * This is the implementation.It registers a Simplebundle service.
 */
@Component(immediate=true, name="com.flairbit.example.karaf.simplebundle")
public class SimplebundleImpl implements Simplebundle {
	private String name;

	@Activate
	void activate(Map<String, Object> map) {
		name = map.containsKey("name") ? (String) map.get("name") : "World";
		say("Hello");
	}

	@Deactivate
	void deactivate(Map<String, Object> map) {
		say("Goodbye");
	}

	@Override
	public boolean say(String message) {
		System.out.println(name + ":"  + message);
		return false;
	}

}
