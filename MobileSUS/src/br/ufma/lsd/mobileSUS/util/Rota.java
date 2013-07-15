package br.ufma.lsd.mobileSUS.util;

import java.net.Socket;
import java.util.Scanner;

public class Rota {
	public double incremento = 0.005;

	public double latOrigem = -2.45;
	public double logOrigem = -44.11;

	public double latDestino = -2.49;
	public double logDestino = -44.37;

	private Thread tr;

	private boolean parar=false;

	public static void main(String[] args) {

		System.out.println("teste");
		new Rota(-2.45, -44.11, -2.49, -44.27).iniciar(123);

	}

	/*
	 * Parametros de latitude e longitude da origem e do detino Retorna tru
	 * quando a Unidade movel chegar no destino vcs vao receber como string
	 * trasforme em numero A funcção é cahamada a cada 0,5 segundos
	 */

	public Rota(double latOrigem, double logOrigem, double latDestino,
			double logDestino) {
		this.latOrigem = latOrigem;
		this.logOrigem = logOrigem;
		this.latDestino = latDestino;
		this.logDestino = logDestino;
	}

	public boolean rota(double latOrigem, double logOrigem, double latDestino,
			double logDestino) {
		System.out.print(">>>" + latOrigem + "," + logOrigem);
		System.out.println(">>>" + latDestino + "," + logDestino);
		if (Math.abs(Math.abs(latDestino) - Math.abs(latOrigem)) >= incremento) {
			if (latDestino < latOrigem) {
				latOrigem -= incremento;
				this.latOrigem = latOrigem;
			} else {
				latOrigem += incremento;
				this.latOrigem = latOrigem;
			}
			return false;
		} else if (Math.abs(Math.abs(logDestino) - Math.abs(logOrigem)) >= incremento) {
			if (logDestino < logOrigem) {
				logOrigem -= incremento;
				this.logOrigem = logOrigem;
			} else {
				logOrigem += incremento;
				this.logOrigem = logOrigem;
			}
			return false;
		}
		return true;
	}

	public boolean iniciar(final int porta) {
		System.out.println("inicio rota");
		tr=new Thread(new Runnable() {
			@Override
			public void run() {

				while (!rota(latOrigem, logOrigem, latDestino, logDestino)&&!parar) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mudar(porta);
				}

			}

		});
		tr.start();
		System.out.println("Fim rota");
		return true;
	}

	public void mudar(int porta) {
		try {
			String str = String.format("geo fix %.5f %.5f \n", logOrigem,latOrigem
					) .replace(',', '.');

			System.out.println(str);
			Socket soc = new Socket("localhost", porta);
		

			Scanner scan = new Scanner(soc.getInputStream());

			System.out.println(scan.nextLine());
			soc.getOutputStream().write(str.getBytes());
			System.out.println(scan.nextLine());
			System.out.println(scan.nextLine());
			scan.close();

			soc.close();
		} catch (Exception e) {
			e.printStackTrace();
			parar=true;
		}
	}
}
