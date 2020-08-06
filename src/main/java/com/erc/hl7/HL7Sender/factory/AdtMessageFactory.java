package com.erc.hl7.HL7Sender.factory;

import java.io.IOException;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;

public class AdtMessageFactory {

	static int PORT_NUMBER = 3402;
	private static HapiContext context = new DefaultHapiContext();

	public static ADT_A01 createMessage(String messageType) throws HL7Exception, IOException {

		try {
			// This patterns enables you to build other message types

			ADT_A01 adtMessage = new AdtA01MessageBuilder().build();
			return adtMessage;
			/*
			 * System.out.println(adtMessage.toString()); // create a new MLLP client over
			 * the specified port Connection connection = context.newClient("localhost",
			 * PORT_NUMBER, false);
			 * 
			 * // The initiator which will be used to transmit our message Initiator
			 * initiator = connection.getInitiator();
			 * 
			 * // send the previously created HL7 message over the connection established
			 * Parser parser = context.getPipeParser();
			 * System.out.println("Sending message:" + "\n" + parser.encode(adtMessage));
			 * Message response; response = initiator.sendAndReceive(adtMessage);
			 * 
			 * String responseString = parser.encode(response);
			 * System.out.println("Received response:\n" + responseString);
			 */
		} catch (HL7Exception | IOException e) {

			// if other types of ADT messages are needed, then implement your builders here
		}
		throw new RuntimeException(
				String.format("%s message type is not supported yet. Extend this if you need to", messageType));

	}

	public static void main(String... strings) {
		try {
			AdtMessageFactory.createMessage("A01");
		} catch (HL7Exception | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}