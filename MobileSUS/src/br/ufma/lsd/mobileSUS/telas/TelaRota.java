package br.ufma.lsd.mobileSUS.telas;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import br.ufma.lsd.mobileSUS.entidades.Chamado;
import br.ufma.lsd.mobileSUS.util.Rota;

public class TelaRota {

	protected Shell shlRota;
	private Text text;
	private Text text_1;
	private Text textPorta;
	private Chamado chamado;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaRota window = new TelaRota(new Chamado());
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TelaRota(Chamado c){
		chamado=c;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlRota.open();
		shlRota.layout();
		while (!shlRota.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlRota = new Shell();
		shlRota.setSize(370, 250);
		shlRota.setText("Rota");
		
		Label lblNewLabel = new Label(shlRota, SWT.NONE);
		lblNewLabel.setBounds(10, 13, 72, 15);
		lblNewLabel.setText("Lat Origem");
		
		text = new Text(shlRota, SWT.BORDER);
		text.setText("-44.162635803222656");
		text.setBounds(104, 10, 245, 21);
		
		Label lblNewLabel_1 = new Label(shlRota, SWT.NONE);
		lblNewLabel_1.setBounds(10, 53, 55, 15);
		lblNewLabel_1.setText("Log origem");
		
		text_1 = new Text(shlRota, SWT.BORDER);
		text_1.setText("-2.557591368675138 ");
		text_1.setBounds(104, 50, 245, 21);
		
		Button btnIr = new Button(shlRota, SWT.NONE);
		btnIr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				rota();
			}
		});
		btnIr.setBounds(274, 185, 75, 25);
		btnIr.setText("Ir");
		
		textPorta = new Text(shlRota, SWT.BORDER);
		textPorta.setText("5554");
		textPorta.setBounds(104, 102, 245, 21);
		
		Label lblPorta = new Label(shlRota, SWT.NONE);
		lblPorta.setBounds(10, 105, 72, 15);
		lblPorta.setText("Porta:");

	}
// -2.557591368675138  -44.162635803222656
	protected void rota() {
		double log=Float.parseFloat(text.getText());
		double  lat=Float.parseFloat(text_1.getText());
		new Rota(lat,log,Float.parseFloat(chamado.getLatitude()),Float.parseFloat(chamado.getLongitude())).iniciar(Integer.parseInt(textPorta.getText()));
		shlRota.dispose();
	}
}
