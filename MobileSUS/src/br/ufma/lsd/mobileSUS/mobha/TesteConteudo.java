package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.ufma.lsd.mbhealthnet.android.mobha.content.MOBHAContentImpl;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentFile;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentListDirectory;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentMetaData;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentUploadRequest;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class TesteConteudo {
	private static String userCentral="e1u1";
	private static MOBHAContentImpl contService;
	
	public static void main(String[] args) {
		init();
		upload(userCentral,"sadfasdfasdf".getBytes());
		listarDiretorio();
		System.out.println("Fim");
	}
	public static void init() {
		System.out.println("!!!!!!!!!!!!");
		
		try {
		
			contService=MOBHAContentImpl.getInstance(settingsProperties(), userCentral);
		
			System.out.println("2");
			contService.registerSubTopicListener(new PubSubTopicListener() {			
				@Override
				public void processTopic(Object o) {
					System.out.println("processando...");
					System.out.println(o);
				}
			});
			System.out.println("3");
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
	public static void upload(String id,byte[] dados){
		System.out.println("upload");
		ContentUploadRequest cont=new ContentUploadRequest();
		cont.fromUserName=id;
		cont.contentFile=new ContentFile(dados);
		cont.contentMetaData=new ContentMetaData();
		cont.contentMetaData.contentName="teste2.txt";
		cont.contentMetaData.path="/home/gateway/e1";
		cont.contentMetaData.owner=userCentral;
		try{
			contService.publishUploadContent(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void listarDiretorio(){
		System.out.println("Listar diretorio");
		ContentListDirectory cont=new ContentListDirectory();
		cont.fromUserName=userCentral;
		cont.path="/home";
		cont.name="gateway";
	
		try{
			contService.listDirectory(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
