package br.ufma.lsd.mobileSUS.telas;


public class RecarregarMapa implements Runnable{
	@Override
	public void run() {
		while(true){
		
			try {
				TelaPrincipal.window.carregarMapa();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	}
}
