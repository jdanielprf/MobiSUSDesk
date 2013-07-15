package br.ufma.lsd.mobileSUS.telas;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import br.ufma.lsd.mobileSUS.mobha.MOBHAConteudo;
import br.ufma.lsd.mobileSUS.mobha.MOBHAUtil;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;

public class TelaArquivos {

	protected Shell shlArquivos;
	private List list;
	private String dir;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */

	public TelaArquivos(String dir,String id) {
		this.dir = dir;
		MOBHAConteudo.listarDiretorio(dir,id);
		open();
		
	}

	public static void main(String[] args) {
		try {
			new TelaArquivos(
					"C:\\Users\\Public\\Pictures\\Sample Pictures\\","");

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
		carrrgarLista();

		shlArquivos.open();
		shlArquivos.layout();
		while (!shlArquivos.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlArquivos =  new Shell(TelaPrincipal.window.shell, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shlArquivos.setSize(361, 359);
		shlArquivos.setText("Arquivos");

		list = new List(shlArquivos, SWT.BORDER);
		list.setBounds(0, 0, 355, 306);
		
		Button btnNewButton = new Button(shlArquivos, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				carrrgarLista();
			}
		});
		btnNewButton.setBounds(0, 306, 355, 25);
		btnNewButton.setText("Atualizar");

		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				if (list.getSelectionIndex() >= 0) {
					String str = list.getItem(list.getSelectionIndex());
					try {
						java.awt.Desktop.getDesktop().open(new File(dir + str));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,
								"Não foi possivel abrir o arquivo");
						e.printStackTrace();
					}
				}
			}
		});
	}

	protected void carrrgarLista() {

		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (int i = 0; i < listOfFiles.length; i++) {
				list.add(listOfFiles[i].getName());
			}
		}
	}

	protected static boolean checkArquivos(String diretorio) {
		File folder = new File(diretorio);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles == null) {
			return false;

		}
		return true;
	}

	public boolean fechado() {
		if (shlArquivos != null)
			return shlArquivos.isDisposed();
		else
			return false;
	}

	public void focus() {
		if (shlArquivos != null)
			shlArquivos.forceActive();
	}
}
