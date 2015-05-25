package com.khonsu.enroute.events;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class EventBus {

	private static EventBus instance;
	private Map<Object, Set<Consumer>> consumerSet = new HashMap<>();

	EventBus() {}

	public static EventBus getInstance() {
		if (instance == null) {
			instance = new EventBus();
		}
		return instance;
	}

	public void announce(Object event) {
		announce(event, null);
	}

	public void announce(Object event, Object payload) {
		for (Consumer consumerReference : getListOfConsumersForEvent(event)) {
			safeInvoke(consumerReference, payload);
		}
	}

	private void safeInvoke(Consumer consumerReference, Object payload) {
		try {
			consumerReference.invoke(payload);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void unregister(Object event, Consumer consumer) {
		Set<Consumer> consumers = getListOfConsumersForEvent(event);
		consumers.remove(consumer);
		consumerSet.put(event, consumers);
	}

	public void register(Object event, Consumer consumer) {
		Set<Consumer> consumers = getListOfConsumersForEvent(event);
		consumers.add(consumer);
		consumerSet.put(event, consumers);
	}

	private Set<Consumer> getListOfConsumersForEvent(Object event) {
		Set<Consumer> consumers = consumerSet.get(event);
		if (consumers == null) {
			consumers = new LinkedHashSet<>();
		}
		return new LinkedHashSet<>(consumers);
	}

	public void clear() {
		consumerSet.clear();
	}

	public interface Consumer<PAYLOAD_TYPE> {
		void invoke(PAYLOAD_TYPE payload);
	}
}
