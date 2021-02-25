package com.godev.pushnotification.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * 
 * @author G. Osorio
 *
 */
@Service
@Slf4j
public class StatusObserverInMemoryImpl implements StatusObserver {

	private static final long KEEP_ALIVE_DELAY = 10_000;

	private ConcurrentMap<Long, FluxSink<String>> pendingTransactions = new ConcurrentHashMap<>();

	@Override
	public Flux<String> subscribe(long id) {
		return Flux.<String>create(sink -> {
			log.info("waiting for webhook of transaction: [{}]", id);
			pendingTransactions.put(id, sink);

			sink.onDispose(() -> {
				log.info("transaction with id: [{}] is not longer observed", id);
				pendingTransactions.remove(id);
			});
		});
	}

	@Override
	public void publish(long id, String data) {
		log.info("a publish event for id: [{}] and data: [{}] was received", id, data);
		if (pendingTransactions.containsKey(id)) {
			pendingTransactions.get(id).next(data);
			pendingTransactions.get(id).complete();
		}
	}

	@PostConstruct
	private void keepAlive() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

		executorService.scheduleAtFixedRate(() -> {
			log.info("keep alive process");
			pendingTransactions.entrySet().forEach(e -> {
				e.getValue().next("KEEP_ALIVE_EVENT");
			});
		}, KEEP_ALIVE_DELAY, KEEP_ALIVE_DELAY, TimeUnit.MILLISECONDS);
	}
}
