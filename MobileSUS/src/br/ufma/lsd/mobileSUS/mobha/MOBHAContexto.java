package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import br.lsd.ufma.mbhealth.server.MobileDDSConnection;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformationSubscribe;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformationSubscribeResponse;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class MOBHAContexto {
	private static String nome = MOBHAUtil.central;
	private static String receberDe = "e1u3";
	
	private static MobileDDSConnection mobhaContext;
	private static HashMap<String,InterfaceContexto> lista=new HashMap<String,InterfaceContexto>();

	private static void receber(Object o) {
		if (o instanceof GenericInformation) {
			GenericInformation g = (GenericInformation) o;
			System.out.println(g.message);
		}
		if(o instanceof ContextInformationSubscribeResponse){
			ContextInformationSubscribeResponse g = (ContextInformationSubscribeResponse) o;
			InterfaceContexto cal = lista.get(g.requestorUserName);
			cal.receberCoordenadas(g.contextInformationInterest[0], g.contextInformationInterest[1]);
			
		}
	}
	
	public static void init() {
		System.out.println("inicio");
		try {
			mobhaContext = MobileDDSConnection.getInstance(
					settingsProperties(),nome);

			mobhaContext.registerSubTopicListener(new PubSubTopicListener() {

				@Override
				public void processTopic(Object o) {
					System.out.println(o);
					receber(o);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fim");
	}

	public static void registrar(String de,InterfaceContexto cal) {

		// Solicitar permissão para receber as informações de Sandra
		ContextInformationSubscribe subscribeSandra = new ContextInformationSubscribe();
		subscribeSandra.requestedUserName = receberDe;
		subscribeSandra.requestorUserName =nome;
		String[] information = { "this.location.latitude" };
		subscribeSandra.contextInformationInterest = information;
		subscribeSandra.secure = false;
		try {
			mobhaContext.publishContextInformationsSubscribe(subscribeSandra);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static InputStream settingsProperties()
			throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}

	public static void main(String[] args) {
		MOBHAContexto.init();
		MOBHAContexto.registrar(receberDe, null);
	}

}
