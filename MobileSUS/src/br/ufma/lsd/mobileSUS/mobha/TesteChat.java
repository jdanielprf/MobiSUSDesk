package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import br.ufma.lsd.mbhealthnet.android.mobha.chat.MOBHAChat;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.Chat;
import br.ufma.lsd.mbhealthnet.communication.exception.DataWriterNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.exception.InvalidTopicException;
import br.ufma.lsd.mbhealthnet.communication.exception.PublisherNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.exception.TopicNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.exception.TopicNotRegisteredException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class TesteChat {
	private static String userCentral = "Felipe";
	private static MOBHAChat contService;

	public static void init(String idRecept) {
		System.out.println("!!!!!!!!!!!!");

		try {

			contService = new MOBHAChat(userCentral, settingsProperties());
			contService.registerSubTopicListener(new PubSubTopicListener() {
				@Override
				public void processTopic(Object o) {
					System.out.println(o);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static InputStream settingsProperties()
			throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}

	public static void enviar(byte[] dados) {

		try {
			contService.publishChat(new Chat());
		} catch (TopicNotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PublisherNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TopicNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataWriterNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DomainParticipantNotCreatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
