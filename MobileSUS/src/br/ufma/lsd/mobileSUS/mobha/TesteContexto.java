package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.lsd.ufma.mbhealth.server.MobileDDSConnection;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.Context;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformationSubscribe;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.exception.TopicNotRegisteredException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class TesteContexto {
	private static String userCentral="e01u01";

	private static MobileDDSConnection mobhaContext;

	public static void init(String idRecept) {
	
		try {
			mobhaContext = MobileDDSConnection.getInstance(settingsProperties(), userCentral);
			mobhaContext.registerSubTopicListener(new PubSubTopicListener() {
				
				@Override
				public void processTopic(Object o) {
					System.out.println(o);
				}
			});			
			ContextInformationSubscribe subscribeSandra=new ContextInformationSubscribe();
			subscribeSandra.requestedUserName = idRecept;
			subscribeSandra.requestorUserName = userCentral;
			String [] information = {"this.location.latitude"};
			subscribeSandra.contextInformationInterest = information;
			subscribeSandra.secure = false;
			try {
				mobhaContext.publishContextInformationsSubscribe(subscribeSandra);
			} catch (TopicNotRegisteredException e) {
				e.printStackTrace();
			} catch (DomainParticipantNotCreatedException e) {
				e.printStackTrace();
			}


		} catch ( DomainParticipantNotCreatedException | IOException e) {
			e.printStackTrace();
		}
	}
	private static InputStream settingsProperties() throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}
	
}
