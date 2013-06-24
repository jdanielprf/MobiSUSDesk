package br.ufma.lsd.mobileSUS.telas;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.ufma.lsd.mobileSUS.entidades.Chamados;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class TelaChamado {

	protected Shell shlChamado;
	private Text textLat;
	private Text textLong;
	private Text textDescricao;
	private Chamados chamado;
	private DateTime dateTime;
	private Text textID;
	private Combo cmbStatus;
	private Combo cmbResponsavel;
	protected TelaArquivos tetlaArquivos;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public TelaChamado(Chamados c) {
		chamado = c;
	}

	public TelaChamado() {
	}

	public static void main(String[] args) {
		try {
			TelaChamado window = new TelaChamado();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlChamado.open();
		shlChamado.layout();
		while (!shlChamado.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlChamado = new Shell();
		shlChamado.setMinimumSize(new Point(452, 392));
		shlChamado.setSize(452, 392);
		shlChamado.setText("Chamado");
		shlChamado.setLayout(new GridLayout(7, false));

		Label lblId = new Label(shlChamado, SWT.NONE);
		lblId.setText("ID:");
		new Label(shlChamado, SWT.NONE);

		textID = new Text(shlChamado, SWT.BORDER);
		textID.setEnabled(false);
		textID.setEditable(false);
		textID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);

		Label lblDescrio = new Label(shlChamado, SWT.NONE);
		lblDescrio.setText("Descri\u00E7\u00E3o:");
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);
		new Label(shlChamado, SWT.NONE);

		textDescricao = new Text(shlChamado, SWT.MULTI);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, true, 7,
				1);
		gridData.minimumHeight = 100;
		textDescricao.setLayoutData(gridData);
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;

		Label lblLatitude = new Label(shlChamado, SWT.NONE);
		lblLatitude.setText("Latitude:");
		new Label(shlChamado, SWT.NONE);

		textLat = new Text(shlChamado, SWT.BORDER);
		new Label(shlChamado, SWT.NONE);

		Label lblLongitude = new Label(shlChamado, SWT.NONE);
		lblLongitude.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		lblLongitude.setText("Longitude:");

		textLong = new Text(shlChamado, SWT.BORDER);

		Button button = new Button(shlChamado, SWT.NONE);
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				TelaMapa map = new TelaMapa();
				map.setChamado(TelaChamado.this);
				if (chamado != null)
					map.posicao(chamado.getLatitude(), chamado.getLongitude());
				map.open();
			}
		});
		button.setText("?");

		Label lblResponsavel = new Label(shlChamado, SWT.NONE);
		lblResponsavel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));
		lblResponsavel.setText("Responsavel:");

		cmbResponsavel = new Combo(shlChamado, SWT.NONE);
		cmbResponsavel.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

				controleDeChamados();
			}
		});
		cmbResponsavel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 4, 1));

		Button btnAlocarUnidadeMovel = new Button(shlChamado, SWT.NONE);
		btnAlocarUnidadeMovel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
				false, false, 1, 1));
		btnAlocarUnidadeMovel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAlocarUnidadeMovel.setText("?");

		Label lblSituao = new Label(shlChamado, SWT.NONE);
		lblSituao.setText("Situa\u00E7\u00E3o:");
		new Label(shlChamado, SWT.NONE);

		cmbStatus = new Combo(shlChamado, SWT.NONE);
		cmbStatus.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				controleDeChamados();
			}
		});
		cmbStatus
				.setItems(new String[] { "Criado", "Em andamento", "Cocluido" });
		cmbStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				5, 1));

		Label lblData = new Label(shlChamado, SWT.NONE);
		lblData.setText("Data:");
		new Label(shlChamado, SWT.NONE);

		dateTime = new DateTime(shlChamado, SWT.BORDER);
		dateTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				5, 1));

		Button btnCancelar = new Button(shlChamado, SWT.NONE);
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				shlChamado.dispose();
			}
		});
		btnCancelar.setText("Cancelar");
		new Label(shlChamado, SWT.NONE);

		Button btnRemover = new Button(shlChamado, SWT.NONE);
		btnRemover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				removerChamado();
			}
		});
		btnRemover.setText("Remover");
		new Label(shlChamado, SWT.NONE);
		
		Button btnArquivos = new Button(shlChamado, SWT.NONE);
		btnArquivos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				String diretorio=TratarEventos.sessao.getDir()+chamado.getId()+"/";
				if(!TelaArquivos.checkArquivos(diretorio)){
					JOptionPane.showMessageDialog(null,
							"Não existe nenhum arquivo para este chamado");
				}else if(tetlaArquivos==null){
					tetlaArquivos=new TelaArquivos(diretorio);
				}else if(tetlaArquivos.fechado()){
					tetlaArquivos=new TelaArquivos(diretorio);
				}else{
					tetlaArquivos.focus();
				}
			}
		});
		btnArquivos.setText("Arquivos");

		Button btnNewButton = new Button(shlChamado, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				salvarChamado();
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));

		btnNewButton.setText("Salvar");

		shlChamado.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				ControllerTelasAbertas.fecharChamado(chamado);
				shlChamado.close();
				System.out.println("fechar");
			}
		});

		carregarListaUsuarios();
		carregar();
		if (chamado != null) {
			btnRemover.setVisible(true);
		} else {
			btnRemover.setVisible(false);
		}
	}

	private void controleDeChamados() {
		System.out.println("controle de chamados");
		System.out.println(cmbResponsavel.getSelectionIndex());
		System.out.println(cmbStatus.getSelectionIndex());
		if (cmbResponsavel.getSelectionIndex() > 0
				&& cmbStatus.getSelectionIndex() == -1) {
			cmbStatus.select(1);
		} else if (cmbStatus.getSelectionIndex() > 0
				&& cmbResponsavel.getSelectionIndex() <= 0) {

			cmbStatus.select(0);

		} 
	}

	protected void removerChamado() {
		if (chamado != null) {
			if (TratarEventos.sessao.removeEvent(chamado)) {
				ControllerTelasAbertas.fecharChamado(chamado);
				return;
			}

		}

		MessageBox messagebox = new MessageBox(shlChamado, SWT.OK
				| SWT.ICON_ERROR);
		messagebox.setText("Erro!");
		messagebox.setMessage("Não foi possivel remover o chamado");
		messagebox.open();
	}

	public void alterarPosicao(String lat, String longitude) {
		textLat.setText(lat);
		textLong.setText(longitude);
	}

	public void carregarListaUsuarios() {
		cmbResponsavel.removeAll();
		cmbResponsavel.add("");
		ArrayList<Usuario> lista = TratarEventos.sessao.getUsuarios();
		for (Iterator<Usuario> iterator = lista.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			cmbResponsavel.add(usuario.toString());
		}
	}

	public void salvarChamado() {
		if (chamado == null) {
			chamado = new Chamados();
		}
		chamado.setDescricao(textDescricao.getText());
		chamado.setLatitude(textLat.getText());
		chamado.setLongitude(textLong.getText());

		Date data = new Date();
		data.setDate(dateTime.getDay());
		data.setMonth(dateTime.getMonth());
		data.setYear(dateTime.getYear());

		chamado.setData(data);

		if (chamado.getId() == null) {
			chamado.setId("" + new Random().nextInt(Integer.MAX_VALUE));
			TratarEventos.addChamado(chamado);
		}

		if (cmbStatus.getSelectionIndex() >= 0) {
			String status = cmbStatus.getItem(cmbStatus.getSelectionIndex());
			chamado.setStatus(status);
		}
		checkStatus();
		shlChamado.dispose();
		TelaPrincipal.window.carregarDados();
	}

	public String getStatus() {
		if (cmbStatus.getSelectionIndex() >= 0) {
			String status = cmbStatus.getItem(cmbStatus.getSelectionIndex());
			if (status.equals("Criado")) {
				return Chamados.STATUS_ABERTO;
			} else if (status.equals("Em andamento")) {
				return Chamados.STATUS_EM_ATENDIMENTO;
			} else if (status.equals("Cocluido")) {
				return Chamados.STATUS_FECHADO;
			}
		}
		return Chamados.STATUS_INDETERMINADO;
	}

	public void carregar() {
		if (chamado == null) {
			return;
		}
		textDescricao.setText("" + chamado.getDescricao());
		textLat.setText("" + chamado.getLatitude());
		textLong.setText("" + chamado.getLongitude());
		textID.setText("" + chamado.getId());
		Date data = chamado.getData();
		if (data != null) {
			dateTime.setDay(data.getDay());
			dateTime.setMonth(data.getMonth());
			dateTime.setYear(data.getYear());
		}

		if (chamado.getStatus() != null) {
			String[] itens = cmbStatus.getItems();
			for (int i = 0; i < itens.length; i++) {
				if (itens[i].equals(chamado.getStatus())) {
					cmbStatus.select(i);
					break;
				}
			}
		}
		System.out.println("==>" + chamado.getResponsavel());
		if (chamado.getResponsavel() != null) {
			String[] itens = cmbResponsavel.getItems();

			for (int i = 0; i < itens.length; i++) {
				if (itens[i].equals(chamado.getResponsavel().toString())) {
					cmbResponsavel.select(i);
					System.out.println("FOI!!!!!!");
					break;
				}
			}
		}
	}

	public void fechar() {
		shlChamado.dispose();
	}

	public void focus() {
		if (shlChamado != null && !shlChamado.isDisposed())
			shlChamado.forceActive();
	}

	public void checkStatus() {
		if (chamado.getStatus().equals(Chamados.STATUS_EM_ATENDIMENTO)
				&& getStatus().equals(Chamados.STATUS_FECHADO)) {
			TratarEventos.terminarAtendimentoChamado(chamado);
		} else if (getStatus().equals(Chamados.STATUS_EM_ATENDIMENTO)) {
			Usuario u = null;
			if (cmbResponsavel.getSelectionIndex() >= 0) {
				String userName = cmbResponsavel.getItem(cmbResponsavel
						.getSelectionIndex());
				u = TratarEventos.buscarUsuario(userName);
			}
			if (u != null) {

				if (TratarEventos.iniciarAtendimentoChamado(u, chamado)) {
					return;
				}
			}
			MessageBox messagebox = new MessageBox(shlChamado, SWT.OK
					| SWT.ICON_ERROR);
			messagebox.setText("Erro!");
			messagebox
					.setMessage("Não foi possivel alocar a unidade movel ao chamado");
			messagebox.open();
		}
		System.out.println(getStatus());
	}
}
