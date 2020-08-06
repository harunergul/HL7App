package com.erc.hl7.HL7Sender.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erc.hl7.HL7Sender.HL7SendingResponse;
import com.erc.hl7.HL7Sender.factory.AdtMessageFactory;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.parser.Parser;

@RestController
public class HL7SenderController {

	private static final int PORT_NUMBER = 3402;// change this to whatever your port number is

	// In HAPI, almost all things revolve around a context object
	private static HapiContext context = new DefaultHapiContext();

	@GetMapping("/sendadt")
	public HL7SendingResponse greeting(@RequestParam(name = "name", defaultValue = "John") String name,
			@RequestParam(name = "surname", defaultValue = "Doe") String surname) {

		try {

			// create the HL7 message
			// this AdtMessageFactory class is not from HAPI but my own wrapper
			// check my GitHub page or see my earlier article for reference

			ADT_A01 adtMessage = AdtMessageFactory.createMessage("A01");
			// create a new MLLP client over the specified port
            Connection connection = context.newClient("192.168.1.211", PORT_NUMBER, false);

            // The initiator which will be used to transmit our message
            Initiator initiator = connection.getInitiator();

            // send the previously created HL7 message over the connection established
            Parser parser = context.getPipeParser();
            System.out.println("Sending message:" + "\n" + parser.encode(adtMessage));
            Message response = initiator.sendAndReceive(adtMessage);

            // display the message response received from the remote party
            String responseString = parser.encode(response);
            System.out.println("Received response:\n" + responseString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new HL7SendingResponse(name, surname);
	}

}
