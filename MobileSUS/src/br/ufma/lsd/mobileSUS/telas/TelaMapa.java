package br.ufma.lsd.mobileSUS.telas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import br.ufma.lsd.mobileSUS.util.Utilidade;

public class TelaMapa {

	protected Shell shell;
	public static Label lblLAtLong;
	public String lat,log;
	private TelaChamado chamado;
	 BrowserFunction function;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaMapa window = new TelaMapa();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void posicao(String lat,String log) {
		this.lat=lat;
		this.log=log;
	}
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(TelaPrincipal.window.shell, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setSize(608, 437);
		shell.setText("MAPA");
		
		Browser browser = new Browser(shell, SWT.NONE);
	
		
		browser.setBounds(0, 0, 592, 358);
		browser.setText(Utilidade.lerArquivo("criarposicao.html"));
		function = new CustomFunction (browser, "theJavaFunction",this);
		
		Button btnSalvar = new Button(shell, SWT.NONE);
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				salvar();
			}
		});
		btnSalvar.setBounds(450, 364, 142, 35);
		btnSalvar.setText("Salvar");
		
		Label lblLocalizao = new Label(shell, SWT.NONE);
		lblLocalizao.setBounds(10, 369, 75, 15);
		lblLocalizao.setText("Localiza\u00E7\u00E3o:");
		
		lblLAtLong = new Label(shell, SWT.NONE);
		lblLAtLong.setBounds(91, 374, 331, 15);
		lblLAtLong.setText("New Label");
		
		if(lat!=null&&log!=null){
			String str="mudarPosicao("+lat+","+  log+");";
			browser.execute(str);
			System.out.println(str);
		}
	}
	
	private void salvar(){
		if (chamado!=null) {
			chamado.alterarPosicao(lat, log);
		}
		shell.dispose();
	}

	public TelaChamado getChamado() {
		return chamado;
	}

	public void setChamado(TelaChamado chamado) {
		this.chamado = chamado;
	}
	
	

	
	static class CustomFunction extends BrowserFunction {
		private TelaMapa map;
		CustomFunction (Browser browser, String name,TelaMapa map) {
			super (browser, name);
			this.map=map;
			
		}
		public Object function (Object[] arguments) {
			System.out.println ("theJavaFunction() called from javascript with args:"+arguments[0]+","+arguments[1]);
			lblLAtLong.setText("("+arguments[1]+","+arguments[0]+")");
			map.lat=""+arguments[0];
			map.log=""+arguments[1];
			return null;
		}
	}
	
}

