package events;

import com.khonsu.enroute.events.EventBus;

import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class EventBusTests {
	private String event = "event";
	private String otherEvent = "other event";
	private int eventsReceived;
	private EventBus eventBus = EventBus.getInstance();

	@After
	public void tearDown() {
		EventBus.getInstance().clear();
	}

	@Test
	public void testThatConsumersAreInformedOfEventAsExpected() {
		FakeConsumer consumer = new FakeConsumer();
		eventBus.register(event, consumer);

		eventBus.announce(event);

		assertTrue(consumer.eventReceived);
	}

	@Test
	public void testThatConsumersAreInformedOfEventWithAssociatedPayloadAsExpected() {
		Object payload = new Object();

		FakeConsumerExpectingPayload consumer = new FakeConsumerExpectingPayload();
		eventBus.register(event, consumer);

		eventBus.announce(event, payload);

		assertSame(payload, consumer.payloadReceived);
	}

	@Test
	public void testThatUnsubscribedConsumerAreNotInformedOfEventAsExpected() {
		FakeConsumer consumer = new FakeConsumer();
		eventBus.register(event, consumer);
		eventBus.unregister(event, consumer);

		eventBus.announce(event);

		assertFalse(consumer.eventReceived);
	}

	@Test
	public void testThatMultipleConsumersAreInformedOfEventAsExpected() {
		FakeConsumer consumer1 = new FakeConsumer();
		FakeConsumer consumer2 = new FakeConsumer();
		eventBus.register(event, consumer1);
		eventBus.register(event, consumer2);

		eventBus.announce(event);

		assertTrue(consumer1.eventReceived);
		assertTrue(consumer2.eventReceived);
	}

	@Test
	public void testThatIfOneOfTwoConsumersIsUnSubscribing_RemainingConsumerIsInformedOfEventAsExpected() {
		FakeConsumer consumer1 = new FakeConsumer();
		FakeConsumer consumer2 = new FakeConsumer();
		eventBus.register(event, consumer1);
		eventBus.register(event, consumer2);
		eventBus.unregister(event, consumer2);

		eventBus.announce(event);

		assertTrue(consumer1.eventReceived);
		assertFalse(consumer2.eventReceived);
	}

	@Test
	public void testThatWhenTwoDifferentEventTypesAreRegistered_AndOneIsAnnounced_theCorrectConsumerIsNotified() {
		FakeConsumer consumer1 = new FakeConsumer();
		FakeConsumer consumer2 = new FakeConsumer();

		eventBus.register(event, consumer1);
		eventBus.register(otherEvent, consumer2);

		eventBus.announce(event);

		assertTrue(consumer1.eventReceived);
		assertFalse(consumer2.eventReceived);
	}

	@Test
	public void testThatWhenEventIsAnnouncedWithNoSubscribers_ThenNoConsumerIsInformed() {
		FakeConsumer consumer = new FakeConsumer();

		eventBus.register(otherEvent, consumer);
		eventBus.announce(event);

		assertFalse(consumer.eventReceived);
	}

	@Test
	public void theOneWhereTheConsumerDeRegistersWhenTheEventIsReceived() {
		EventBus.Consumer consumerOne = new EventBus.Consumer() {
			@Override
			public void invoke(Object payload) {
				eventBus.unregister(event,this);
			}
		};
		FakeConsumer consumerTwo = new FakeConsumer();
		eventBus.register(event, consumerOne);
		eventBus.register(event, consumerTwo);

		eventBus.announce(event);

		assertTrue(consumerTwo.eventReceived);

	}

	@Test
	public void testThatWhenTheFirstOfMulipleConsumersThrowsAnError_TheRemainingConsumersAreStillNotified() {
		EventBus.Consumer consumerOne = new EventBus.Consumer() {
			@Override
			public void invoke(Object payload) {
				throw new RuntimeException();
			}
		};
		FakeConsumer consumerTwo = new FakeConsumer();
		eventBus.register(event, consumerOne);
		eventBus.register(event, consumerTwo);

		eventBus.announce(event);

		assertTrue(consumerTwo.eventReceived);
	}

	@Test
	public void testThatWhenEventBusIsTornDown_ConsumersAreNotInformedOfEvents() {
		FakeConsumer consumer1 = new FakeConsumer();
		FakeConsumer consumer2 = new FakeConsumer();
		eventBus.register(event, consumer1);
		eventBus.register(event, consumer2);

		eventBus.clear();
		eventBus.announce(event);

		assertFalse(consumer1.eventReceived);
		assertFalse(consumer2.eventReceived);
	}

	private static class FakeConsumer implements EventBus.Consumer {
		boolean eventReceived;

		@Override
		public void invoke(Object optionalPayload) {
			eventReceived = true;
		}
	}

	private class FakeConsumerExpectingPayload implements EventBus.Consumer<Object> {
		Object payloadReceived;
		@Override
		public void invoke(Object payload) {
			payloadReceived = payload;
		}
	}
}