package com.clp.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class FilterStringDB {
	private static List<String> stringList = new ArrayList<String>();
	private static String stringBreaked[];

	private static void breakStringDB(String string) {
		stringList.clear();
		String textoUpper = string.toUpperCase();
		stringBreaked = textoUpper.split("\\.");
		for (int a = 0; a < stringBreaked.length; a++) {
			stringList.add(stringBreaked[a]);
		}
	}

	// Retorna o número do bloco DB
	public static int getNumberDB(String string) {
		breakStringDB(string);
		return Integer.parseInt(stringList.get(0).substring(2));
	}

	// Retorna o endereço da memória no bloco DB
	public static int getAdressMemory(String string) {
		breakStringDB(string);
		return Integer.parseInt(stringList.get(1).substring(3));
	}

	// Retorna o tipo de memória(X, W e DW)
	public static char getTypeMemory(String string) {
		breakStringDB(string);
		return stringList.get(1).charAt(2);
	}

	// Retorna o numero do bit quando for do tipo boolean (X)
	public static int getBitIfX(String string) {
		breakStringDB(string);
		if (stringBreaked.length == 3) {
			return Integer.parseInt(stringList.get(2).substring(0));
		} else {
			// aqui deveria lançar uma exceção ou outra coisa menos retornar 0. fica para
			// próxima versão.
			return 0;
		}
	}

}
