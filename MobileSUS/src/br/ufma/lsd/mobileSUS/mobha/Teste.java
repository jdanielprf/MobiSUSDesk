package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.ufma.lsd.mbhealthnet.android.mobha.content.MOBHAContentImpl;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class Teste {
	private static String user="teste";
	private static MOBHAContentImpl cont;
	public static void main(String[] args) {
		System.out.println("!!!!!!!!!!!!");
		try {
			settingsProperties();
		} catch (FileNotFoundException e1) {
			System.out.println("Erro!");
			e1.printStackTrace();
		}
		
		try {
		
			cont=MOBHAContentImpl.getInstance(settingsProperties(), user);
			cont.registerSubTopicListener(new PubSubTopicListener() {
				
				@Override
				public void processTopic(Object o) {
					System.out.println(o);
				}
			});
			
		}catch (FileNotFoundException e) {
			System.out.println("arquivo nao encontrado");
			e.printStackTrace();
		}catch (DomainParticipantNotCreatedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private static InputStream settingsProperties() throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}
}
