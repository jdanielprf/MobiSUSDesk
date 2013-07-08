package br.ufma.lsd.mobileSUS.util;

public class Rota {
	public static double incremento =1;

	public static double latOrigem = -2.45;
	public static double logOrigem = -44.11;

	public static double latDestino = -2.49;
	public static double logDestino = -44.17;
	
	/*
	 * Parametros de latitude e longitude da origem e do detino Retorna tru
	 * quando a Unidade movel chegar no destino vcs vao receber como string
	 * trasforme em numero A funcção é cahamada a cada 0,5 segundos
	 */


	public static boolean rota(double latOrigem, double logOrigem,
			double latDestino, double logDestino) {
		System.out.print(">>>" + latOrigem + "," + logOrigem);
		System.out.println(">>>" + latDestino + "," + logDestino);
		if (Math.abs(Math.abs(latDestino) - Math.abs(latOrigem)) >= incremento) {
			if (latDestino < latOrigem) {
				latOrigem -= incremento;
				Rota.latOrigem =  latOrigem;
			} else {
				latOrigem += incremento;
				Rota.latOrigem = latOrigem;
			}
			return false;
		}else if (Math.abs(Math.abs(logDestino) - Math.abs(logOrigem)) >= incremento) {
			if (logDestino < logOrigem) {
				logOrigem -= incremento;
				Rota.logOrigem =  logOrigem;
			} else {
				logOrigem += incremento;
				Rota.logOrigem =  logOrigem;
			}
			return false;
		}
		return true;
	}

	public static boolean iniciar() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				while (!rota(latOrigem, logOrigem, latDestino, logDestino)); {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mudar();
				} 

			}

			
		}).start();

		return true;
	}

	public static void main(String[] args) {
		System.out.println("teste");
		iniciar();
	}
	
	public static void mudar() {
		
	}
}
