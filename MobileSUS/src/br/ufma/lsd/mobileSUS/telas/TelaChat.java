package br.ufma.lsd.mobileSUS.telas;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.ufma.lsd.mobileSUS.entidades.Msg;
import br.ufma.lsd.mobileSUS.entidades.Usuario;
import br.ufma.lsd.mobileSUS.mobha.Processamento;
import br.ufma.lsd.mobileSUS.telas.help.TratarEventos;

public class TelaChat {

	protected Shell shlMensagem;
	private Text text;
	private Usuario usuario;
	private StyledText styledText;

	public static void main(String[] args) {
		new TelaChat().open(new Usuario("TEste "));
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open(Usuario u) {
		if (u == null)
			return;
		usuario = u;

		Display display = Display.getDefault();
		createContents();
		shlMensagem.open();
		shlMensagem.layout();
		while (!shlMensagem.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMensagem = new Shell();
		shlMensagem.setSize(400, 383);
		shlMensagem.setText("Mensagem");
		shlMensagem.setLayout(new GridLayout(4, false));
		
				Label lblNomeDestinatario = new Label(shlMensagem, SWT.NONE);
				lblNomeDestinatario.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
						false, false, 4, 1));
				lblNomeDestinatario.setText(usuario.getNome());
				
						ScrolledComposite scrolledComposite = new ScrolledComposite(
								shlMensagem, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
						scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
								true, 4, 1));
						scrolledComposite.setExpandHorizontal(true);
						scrolledComposite.setExpandVertical(true);
						
								styledText = new StyledText(scrolledComposite, SWT.BORDER);
								styledText.setEditable(false);
								styledText.setText("");
								scrolledComposite.setContent(styledText);
								scrolledComposite.setMinSize(styledText.computeSize(SWT.DEFAULT,
										SWT.DEFAULT));

		text = new Text(shlMensagem, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		text.setFocus();

		Button btnNewButton = new Button(shlMensagem, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				enviarMensagem();
			}
		});
		btnNewButton.setText("Enviar");
		carregarTodasMgs();

		shlMensagem.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				ControllerTelasAbertas.fecharMgs(usuario);
			}
		});
	}

	public void carregarTodasMgs() {
		ArrayList<Msg> lista = TratarEventos.buscarMgs(usuario);
		System.out.println("<>>" + lista);
		if (lista != null) {
			for (Iterator<Msg> iterator = lista.iterator(); iterator.hasNext();) {
				Msg msg = (Msg) iterator.next();
				mostarMensagem(msg);

			}
		}
	}

	private void mostarMensagem(Msg msg) {
		String u = "Eu";
		if (msg.getDestino() == null) {
			u = msg.getDestino().getNome();
		}
		if (!styledText.getText().equals("")) {
			styledText.setText(styledText.getText() + "\n" + u + ":\n"
					+ msg.getMsg());
		} else {
			styledText.setText(u + ":\n" + msg.getMsg());
		}
	}

	private void enviarMensagem() {
		String mensagem = text.getText();
		text.setText("");
		text.setFocus();

		Msg msg = new Msg();
		msg.setMsg(mensagem);
		msg.setDestino(usuario);
		TratarEventos.sessao.addMsgEnviada(msg);
		mostarMensagem(msg);
		
		Processamento.get().enviarMsgChat(usuario.getId(), msg.getMsg());
		
	}

	public void fechar() {
		shlMensagem.dispose();
	}

	public void focus() {
		shlMensagem.forceActive();
	}
}
