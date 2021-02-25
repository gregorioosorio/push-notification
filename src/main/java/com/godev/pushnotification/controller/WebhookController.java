package com.godev.pushnotification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godev.pushnotification.service.StatusObserver;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author G. Osorio
 *
 */
@RestController
@RequestMapping("/webhook")
@Slf4j
public class WebhookController {

	@Autowired
	private StatusObserver statusObserver;

	@PostMapping(path = "/{id}/status")
	public void pushStatus(@PathVariable("id") Long id) {
		log.info("webhook received for transaction id: [{}]", id);

		statusObserver.publish(id, "READY_EVENT");
	}
}
