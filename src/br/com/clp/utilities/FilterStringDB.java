package br.com.clp.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class FilterStringDB {
	private static List<String> strList = new ArrayList<String>();
	private static String strTextSeparate[];

	private static void separarTextoDB(String string) {
		try {
			strList.clear();
			String textoUpper = string.toUpperCase();
			strTextSeparate = textoUpper.split("\\.");
			for (int a = 0; a < strTextSeparate.length; a++) {
				strList.add(strTextSeparate[a]);
			}
		} catch (Exception e) {
			System.out.println("Erro de acesso ao array  Tratatextos " + e.getMessage() + " - " + e.getCause());
		}
	}
	//Retorna o número do bloco DB
	public static int getNumberDB(String string) {
		separarTextoDB(string);
		return Integer.parseInt(strList.get(0).substring(2));
	}

	// Retorna o endereço da memória no bloco DB
	public static int getAdressMemory(String string) {
		separarTextoDB(string);
		return Integer.parseInt(strList.get(1).substring(3));
	}

	//Retorna o tipo de memória(X, W e DW)
	public static char getTypeMemory(String string) {
		separarTextoDB(string);
		return strList.get(1).charAt(2);
	}

	//Retorna o numero do bit quando for do tipo boolean (X)
	public static int getBitIfX(String string) {
		separarTextoDB(string);
		if (strTextSeparate.length == 3) {
			return Integer.parseInt(strList.get(2).substring(0));
		} else {
			// aqui deveria lançar uma exceção ou outra coisa menos retornar 0. fica para
			// próxima versão.
			return 0;
		}
	}

}
