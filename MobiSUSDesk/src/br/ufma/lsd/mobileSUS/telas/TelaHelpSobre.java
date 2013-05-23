package br.ufma.lsd.mobileSUS.telas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TelaHelpSobre {

	protected Shell shlSobre;
	private Text txtAluosjosDaniel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaHelpSobre window = new TelaHelpSobre();
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
		shlSobre.open();
		shlSobre.layout();
		
		while (!shlSobre.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSobre = new Shell(Display.getDefault(), SWT.CLOSE | SWT.TITLE | SWT.MIN ); 
		shlSobre.setSize(478, 322);
		shlSobre.setText("Sobre");
		shlSobre.setLayout(new FillLayout(SWT.HORIZONTAL));

		
		txtAluosjosDaniel = new Text(shlSobre, SWT.BORDER);
		txtAluosjosDaniel.setEnabled(false);
		txtAluosjosDaniel.setEditable(false);
		txtAluosjosDaniel.setText("Disciplina de Computação Movel\nAlunos:Jos\u00E9 Daniel, ...\nProjeto Final");

	}
}
