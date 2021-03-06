package com.dmd.physical_iot.physical;

import com.dmd.iot.parking_iot.common.ParkingSpaceEvents;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.Callable;
import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.OCCUPY;
import static com.dmd.iot.parking_iot.common.ParkingSpaceEvents.VACATE;

/**
 * A Spring Boot parking Internet of Things (IoT) physical-device application.
 *
 * An Internet-of-Things (IoT) application, that automates parking space availability in a parking lot, so that drivers can
 * quickly locate the nearest available space, in order to save time and fuel, and that also helps parking lot owners and attendants,
 * monitor and manage their lots.
 */

@SpringBootApplication
	public class PhysicalApplication {

	/**
	 * Java application entry point.
	 * @param args An array of application input arguments. Not used.
	 */
		public static void main(String[] args) {

			// This file has a lot of "dangling" javadoc: comments that look like javadoc, but aren't above the definition of an instance var/method. It's fine to comment code that isn't Javadoc-commentable, but don't use the two-star comments that look like Javadoc unless you're actually writing Javadoc.
			/**
			 * static helper that used to run spring application using default setting
			 */
			SpringApplication.run(PhysicalApplication.class, args);
			System.out.println("<--DMD--> GPIO Trigger ... started.");

			/**
			 * @class GpioFactory  provies a static method to create a new GPIO controller instance.
			 *
			 */
			final GpioController gpio = GpioFactory.getInstance();

			// The code in here is EXTREMELY non-DRY. You've copy/pasted something six times for these six buttons.
			// I'd much prefer if you wrote out the Callable class separately, and made instances of it for each of your buttons.
			// Or, you could even put that code in a loop, for each button in an array of buttons you created.
			// Either way, this file could be SIGNIFICANTLY shorter & easier to read.
			/**
			 * Creates a decorator interface to describe digital digital input pin using GPIO controller interface. Provisions input pin with pin and name parameter with internal pull down resistor enabled.
			 * @param pin pin
			 * @param String name
			 */
			final GpioPinDigitalInput myButton1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00, "R1-1",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "R1-2",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "R1-3",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "R2-2",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton5 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "R2-4",
					PinPullResistance.PULL_DOWN);
			final GpioPinDigitalInput myButton6 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, "R2-6",
					PinPullResistance.PULL_DOWN);



			/**
			 * Creates a gpio callback trigger internally when the state of the decorator interface changes.
			 * @param invokable GpioCallbackTrigger method
			 * @param Pinstate state
			 * @param Callable<void> callback
			 */
			myButton1.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton1.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton1.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient

							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));


			/**
			 * Creates a gpio callback trigger internally when the state of the decorator interface changes.
			 * @param invokable GpioCallbackTrigger method
			 * @param Pinstate state
			 * @param Callable<void> callback
			 */
			myButton2.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton2.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton2.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));

			myButton3.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton3.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton3.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient

							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));

			myButton4.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton4.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton4.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));

			myButton5.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton5.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton5.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));

			myButton6.addTrigger(new GpioCallbackTrigger(new Callable<Void>() {
				public Void call() throws Exception {

					/**
					 * check if status of decorator interface is currently low
					 * if true, current space is enum VACANT
					 * else, current space is enum OCCUPIED
					 */
					Boolean status =  myButton6.getState().isLow();
					ParkingSpaceEvents sendStatus = (status) ? VACATE : OCCUPY;

					/**
					 * get decorator interface's name.
					 */
					String spotName = myButton6.getName();
					System.out.println("GPIO TRIGGER CALLBACK  " + spotName + " " + status + " " + sendStatus);

					/**
					 * Spring framework utility
					 * implementation of MultiValueMap that wraps a LinkedHashMap
					 * creates enum object handled by web client request
					 */
					LinkedMultiValueMap paramsMap = new LinkedMultiValueMap();
					paramsMap.add("parkingLotName", "Parking Lot One");
					paramsMap.add("parkingSpaceName", spotName);
					paramsMap.add("parkingSpaceEvent", sendStatus.toString());

					/**
					 * Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
					 * @static_method create() create a new WebCLient with Reactor Netty by default
					 * @instance_method put() Start building an HTTP PUT request
					 * @param paramsMap reference to the spec type
					 *
					 */
					WebClient.RequestHeadersSpec requestSpec = WebClient
							.create("http://parking.my-dog-spot.com")
							.put()
							.uri("/space-map/update")
							.body(BodyInserters.fromMultipartData(paramsMap));

					/**
					 * Perform the HTTP request and retrieve the response body
					 * @bodyToMono Extracts the body to a Mono
					 *
					 */
					String responseSpec = requestSpec.retrieve()
							.bodyToMono(String.class)
							.block();
					System.out.println(responseSpec + " " + spotName + " " + status + " " + sendStatus);

					return null;
				}
			}));



			/**
			 * static block for dynamic linking use of wiring pi
			 */
		}

		static {
			System.setProperty("pi4j.linking", "dynamic");
		}



	}
