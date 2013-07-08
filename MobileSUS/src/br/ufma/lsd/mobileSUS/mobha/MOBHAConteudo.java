package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.ufma.lsd.mbhealthnet.android.mobha.content.MOBHAContentImpl;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentDownloadRequest;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentDownloadResponse;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentFile;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentMetaData;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentSearchByMetaData;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContentUploadRequest;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.exception.DomainParticipantNotCreatedException;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class MOBHAConteudo {
	private static String userCentral=MOBHAUtil.central;
	private static MOBHAContentImpl contService;
	public static void main(String[] args) {
		init();
	//	upload(userCentral,"sadfasdfasdf".getBytes());
		download("e1u2", "13");
		
		listarDiretorio();
		System.out.println("Fim");
	}
	public static void init() {
		System.out.println("!!!!!!!!!!!!");
		if(contService!=null){
			return;
		}
		try {
		
			contService=MOBHAContentImpl.getInstance(settingsProperties(), userCentral);
		
			System.out.println("2");
			contService.registerSubTopicListener(new PubSubTopicListener() {			
				@Override
				public void processTopic(Object o) {
					System.out.println("processando...");
					System.out.println(o);
					if(o instanceof GenericInformation){
						GenericInformation g=(GenericInformation)o;
						System.out.println(g.message);
					}else if(o instanceof ContentDownloadResponse){
						ContentDownloadResponse resposta = (ContentDownloadResponse)o;
						salvarArquivo(resposta.contentFile.contentFile, TratarEventos.sessao.getDir() +"/temp/"+resposta.contentId+".png");	
					}
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
	
	public static void cancelar(){
		contService=null;
	}
	
	
	private static InputStream settingsProperties() throws FileNotFoundException {
		return new FileInputStream(new File("settings.properties"));
	}
	
	public static void registrar(LogicaProcessamento logica) {
	}
	
	public static void upload(String id,byte[] dados){
		System.out.println("upload");
		ContentUploadRequest cont=new ContentUploadRequest();
		cont.fromUserName=id;
		cont.contentFile=new ContentFile(dados);
		cont.contentMetaData=new ContentMetaData();
		cont.contentMetaData.contentName="teste2.txt";
		cont.contentMetaData.path="/home/gateway/e1/";
		cont.contentMetaData.owner=userCentral;
		cont.contentMetaData.mimeType="txt";
		try{
			contService.publishUploadContent(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void download(String id,String idContent){
		System.out.println("download");
		ContentDownloadRequest cont=new ContentDownloadRequest();
		cont.fromUserName=id;
		cont.contentId=idContent;
		
		try{
			contService.downloadContentFile(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void listarDiretorio(){
		//usar find metadados
		System.out.println("Listar diretorio");
		ContentSearchByMetaData cont=new ContentSearchByMetaData();
		
		String[] list=new String[2];
		list[0]="path";
		list[1]="name";
		
		String list2[]=new String[2];
		list2[0]="/home/gateway/e1/";
		list2[1]="teste2.txt";
		
		cont.fromUserName=userCentral;
		cont.metadaDataName=list;
		cont.metadaDataValue=list2;
		
	
		try{
			contService.searchContentByMetadata(cont);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void salvarArquivo(byte[] dados, String localizacao){
		try {
			File file=new File(localizacao).getParentFile();
			file.mkdirs();
			FileOutputStream f=new FileOutputStream(localizacao);
			f.write(dados);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
