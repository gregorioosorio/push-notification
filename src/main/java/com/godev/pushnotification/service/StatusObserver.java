package com.godev.pushnotification.service;

import reactor.core.publisher.Flux;

/**
 * 
 * @author G. Osorio
 *
 */
public interface StatusObserver {

	/**
	 * Subscribe a transaction we are waiting the web-hook
	 * 
	 * @param id
	 * @return the transaction status
	 */
	Flux<String> subscribe(long id);

	/**
	 * Publish when the transaction web-hook is received
	 * 
	 * @param id
	 * @param data
	 */
	void publish(long id, String data);

}
