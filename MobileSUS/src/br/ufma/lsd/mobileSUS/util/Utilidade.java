package br.ufma.lsd.mobileSUS.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utilidade {
	public static String lerArquivo(String file) {
		String str = "";
		try {
			Scanner s = new Scanner(new File(file));
			while (s.hasNext()) {
				str += s.nextLine();
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return str;
	}
}
