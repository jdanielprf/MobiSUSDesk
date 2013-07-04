package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import br.lsd.ufma.mbhealth.server.MobileDDSConnection;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformation;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformationSubscribe;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class MOBHAContexto {
	private static String nome = MOBHAUtil.central;
	private static String receberDe = "e1u2";

	private static MobileDDSConnection mobhaContext;
	private static HashMap<String, InterfaceContexto> lista = new HashMap<String, InterfaceContexto>();

	private static void receber(Object o) {

		if (o instanceof ContextInformation) {
			ContextInformation g = (ContextInformation) o;
			System.out.println(g.ownerUserName);
			for (int i = 0; i < g.informationNames.length; i++) {
				System.out.print((g.informationNames[i]+":"+g.informationValues[i]));
			}
			System.out.println();
			InterfaceContexto cal = lista.get(g.ownerUserName);
			if (cal != null){
				cal.receberCoordenadas(g.informationValues[3],
						g.informationValues[1]);
			}else{
				System.out.println("nao encontrado");
			}
		} else if (o instanceof GenericInformation) {
			GenericInformation g = (GenericInformation) o;
			System.out.println(g.message);
		}
	}

	public static void init() {
		System.out.println("inicio");
		try {
			mobhaContext = MobileDDSConnection.getInstance(
					settingsProperties(), nome);

			mobhaContext.registerSubTopicListener(new PubSubTopicListener() {

				@Override
				public void processTopic(Object o) {
					System.out.println(">>>>>>>>>>" + o);
					receber(o);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fim");
	}

	public static void registrar(String de, InterfaceContexto cal) {

		// Solicitar permissão para receber as informações de Sandra
		ContextInformationSubscribe subscribeSandra = new ContextInformationSubscribe();
		subscribeSandra.requestedUserName = receberDe;
		subscribeSandra.requestorUserName = nome;
		String[] information = { "this.location.latitude",
				"this.location.longitude" };
		subscribeSandra.contextInformationInterest = information;
		subscribeSandra.secure = false;
		
		try {
			lista.put(de, cal);
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
