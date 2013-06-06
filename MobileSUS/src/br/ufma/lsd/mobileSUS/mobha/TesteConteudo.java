package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.ufma.lsd.mbhealthnet.android.mobha.content.MOBHAContentImpl;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentDownloadRequest;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentFile;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentMetaData;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentUploadRequest;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;

public class TesteConteudo {
	private static String user="teste";
	private static MOBHAContentImpl contService;
	public static void main(String[] args) {
		System.out.println("!!!!!!!!!!!!");
		
		try {
		
			contService=MOBHAContentImpl.getInstance(settingsProperties(), user);
			contService.registerSubTopicListener(new PubSubTopicListener() {
				
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
		upload();
		
	}
	private static InputStream settingsProperties() throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}
	private static void upload(){
		ContentUploadRequest cont=new ContentUploadRequest();
		cont.fromUserName="Felipe";
		cont.contentFile=new ContentFile();
		cont.contentMetaData=new ContentMetaData();
		cont.contentMetaData.contentName="teste2.txt";
		cont.contentMetaData.path="/home/felipe";
		cont.contentMetaData.owner="Felipe";
		try{
			contService.publishUploadContent(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void download(){
		ContentDownloadRequest cont=new ContentDownloadRequest();
		cont.fromUserName="Felipe";
		cont.contentId="teste2.txt";
		try{
			contService.downloadContentFile(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static void informacao(){

	}
}
