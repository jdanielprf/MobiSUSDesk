package br.ufma.lsd.mobileSUS.telas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TelaConfig {

	protected Shell shlConfiguraes;
	private Text textEndereco;
	private Text textPorta;
	private Text textID;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TelaConfig window = new TelaConfig();
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
		shlConfiguraes.open();
		shlConfiguraes.layout();
		while (!shlConfiguraes.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlConfiguraes =  new Shell(TelaPrincipal.window.shell, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shlConfiguraes.setSize(398, 238);
		shlConfiguraes.setText("Configura\u00E7\u00F5es");

		Label lblNewLabel = new Label(shlConfiguraes, SWT.NONE);
		lblNewLabel.setBounds(0, 24, 55, 15);
		lblNewLabel.setText("Endere\u00E7o:");

		textEndereco = new Text(shlConfiguraes, SWT.BORDER);
		textEndereco.setBounds(75, 24, 302, 21);

		Label lblNewLabel_1 = new Label(shlConfiguraes, SWT.NONE);
		lblNewLabel_1.setBounds(0, 66, 55, 15);
		lblNewLabel_1.setText("Porta:");

		textPorta = new Text(shlConfiguraes, SWT.BORDER);
		textPorta.setBounds(75, 66, 302, 21);

		Label lblNewLabel_2 = new Label(shlConfiguraes, SWT.NONE);
		lblNewLabel_2.setBounds(0, 114, 55, 15);
		lblNewLabel_2.setText("ID:");

		textID = new Text(shlConfiguraes, SWT.BORDER);
		textID.setBounds(75, 114, 302, 21);

		Button btnNewButton = new Button(shlConfiguraes, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				salvar();
				JOptionPane.showMessageDialog(null, "Salvo!");
				shlConfiguraes.dispose();
			}
		});

		btnNewButton.setBounds(302, 163, 75, 25);
		btnNewButton.setText("Salvar");
		 
		ler();
		 
	}

	protected void salvar() {
		System.out.println("Salvando...");
		try {
			FileWriter escrever = new FileWriter("settings.properties");
			String dado=textEndereco.getText()+"\n";
			escrever.write(dado.toCharArray());
			dado=textPorta.getText()+"\n";
			escrever.write(dado.toCharArray());
			escrever.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		try {
			FileWriter escrever = new FileWriter("id.txt");
			String dado=textID.getText()+"\n";
			escrever.write(dado.toCharArray());
			escrever.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	protected void ler() {
		try {
			Scanner inp = new Scanner(new FileInputStream("settings.properties"));
			if(inp.hasNextLine()){
				textEndereco.setText(inp.nextLine());
			}
			if(inp.hasNextLine()){
				textPorta.setText(inp.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		///////////////////////////////////////////
		try {
			Scanner inp = new Scanner(new FileInputStream("id.txt"));
			if(inp.hasNextLine()){
				textID.setText(inp.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
