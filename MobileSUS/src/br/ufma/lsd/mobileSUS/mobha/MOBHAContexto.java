package br.ufma.lsd.mobileSUS.mobha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.swt.widgets.Display;

import br.ufma.lsd.mbhealth.client.monitoring.MobileDDSConnection;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformation;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.ContextInformationSubscribe;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.GenericInformation;
import br.ufma.lsd.mbhealthnet.communication.ddstopics.contextUserStatus;
import br.ufma.lsd.mbhealthnet.communication.pubsub.PubSubTopicListener;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.TelaPrincipal;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class MOBHAContexto {
	private static String idCentral = MOBHAUtil.central;
	private static String receberDe = "e1u2";

	private static MobileDDSConnection mobhaContext;
	private static HashMap<String, InterfaceContexto> lista = new HashMap<String, InterfaceContexto>();

	private static void receber(Object o) {
		System.out.println(">>>>>Contexto:" + o);
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
			
		} else if (o instanceof contextUserStatus) {
			contextUserStatus status = (contextUserStatus) o;
			Usuario usuario = TratarEventos.sessao.buscarUsuario(status.userName);
			System.out.println("usuario:"+status.userName);
			System.out.println("status:"+status.status);
			System.out.println("usuario:"+usuario);
			if(usuario!=null){
				if(!status.status.equals(usuario.getStatus())){
					usuario.setStatus(status.status);
					TratarEventos.sessao.salvar(usuario);
					
					
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							TelaPrincipal.window
									.carregarTabelaUsuarios(TratarEventos.sessao
											.getUsuarios());
						}
					});
				}
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
					settingsProperties(), idCentral);
			
			mobhaContext.registerSubTopicListener(new PubSubTopicListener() {

				@Override
				public void processTopic(Object o) {
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
		ContextInformationSubscribe inf = new ContextInformationSubscribe();
		inf.requestedUserName = de;
		inf.requestorUserName = idCentral;
		String[] information = { "this.location.latitude",
				"this.location.longitude" };
		inf.contextInformationInterest = information;
		inf.secure = false;
		
		try {
			lista.put(de, cal);
			mobhaContext.publishContextInformationsSubscribe(inf);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void registrar(String de) {

		// Solicitar permissão para receber as informações de Sandra
		ContextInformationSubscribe inf = new ContextInformationSubscribe();
		inf.requestedUserName = de;
		inf.requestorUserName = idCentral;
		String[] information = { "this.location.latitude",
				"this.location.longitude" };
		inf.contextInformationInterest = information;
		inf.secure = false;
		
		try {
			
			mobhaContext.publishContextInformationsSubscribe(inf);
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
		MOBHAContexto.registrar("e1u4", null);
	}

}
