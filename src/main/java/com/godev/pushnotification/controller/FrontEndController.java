package com.godev.pushnotification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godev.pushnotification.service.StatusObserver;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *
 * @author G. Osorio
 *
 */
@RestController
@RequestMapping("/transaction")
@Slf4j
public class FrontEndController {

	@Autowired
	private StatusObserver statusObserver;

	@GetMapping(path = "/{id}/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getStatus(@PathVariable("id") Long id) {
		log.info("somebody wants to know the status of transaction: [{}]", id);
		return statusObserver.subscribe(id);
	}
}
