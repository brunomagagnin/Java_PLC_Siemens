package com.clp.utilities;

import java.util.ArrayList;
import java.util.List;

import com.clp.exceptions.VerifyException;

/* UM DIA EU VOU REFATORAR ESSE CÓDIGO KKKKKKKKKKKKK

 *******************************************************************************************************************
 ***************************   COMO FUNCIONA O ENDEREÇO DE MEMÓRIA NO CLP SIEMENS    *******************************
 DB1.DBW50  <<<<<<<   EXEMPLO PARA CONTEXTUALIZAR
 
+++++++++++NOME DO BLOCO++++++++++++++++
* DB1  >> NÚmero do bloco de dados. Pode ser DB1, DB2 DB10, DB1000 etc.
* DB são caracteres obrigatórios do nome do bloco seguido de um número que é o número correspondente do bloco.

++++++++++++++++  ENDERE�O DE MEMORIA DENTRO DO BLOCO  +++++++++++++++++++ 
* DBW50 >> Após o PONTO, vem o endereço interno da memória dentro do bloco. DB São caracteres obrigatórios seguido de uma letra e após 
* números.
* X W D    Letras correspondentes ao tipo de memória: X (boolean), W (word), D (double word)
* Após a letra vem o número correspondente ao endereço de m�moria
* 
* Caso seja do tipo X(bit) como no exemplo ao lado DB1.DBX5.6 após o X deverá ter um ou mais números que corresponde a área de 
* memória dentro do bloco, seguido de um ponto e de um número de 0 à 7 que corresponde ao endereço do bit de um byte de memória.  


 >>>>>>>>>  Exemplos de endereços de memória válidos  <<<<<<<<<<<<<<<<
DB1.DBX12.2 -  referenciando endereço de um bit
DB1.DBW52 - referenciando ao endereço de uma Word
DBW23.DBD650 - referenciando ao endereço de um DoubleWord
*/

public final class VerifyStringDB {
	private static final char templateDB[] = { 'D', 'B' };
	private static final char templateType[] = { 'X', 'W', 'D' };
	
	private static String stringText;
	private static char lastCharacter;
	private static List<String> listString = new ArrayList<String>();
	private static List<String> listNumbers = new ArrayList<String>();
	private static List<Character> firstLetters = new ArrayList<Character>();
	private static List<Character> secondLetters = new ArrayList<Character>();
	private static List<Character> CharDigitList = new ArrayList<Character>();
	private static String strTextSeparate[];

	public static void verifyText(String str) {
		stringText = str;
		clearVariables();
		breakString();
		verifyArrayLength();
		isBlankLists();
		breakStringsToChar();
		verifyChars();
	}

	// Limpa os boolenas de verificação e arrays
	private static void clearVariables() {
		listString.clear();
		listNumbers.clear();
		firstLetters.clear();
		secondLetters.clear();
		CharDigitList.clear();
	}

	// Verifica a string, separa String
	private static void breakString() {
		String textoUpper = stringText.toUpperCase();
		if (!stringText.isEmpty() && stringText.length() >= 7 && stringText.contains(".")) {
			strTextSeparate = textoUpper.split("\\.");
		} else {
			throw new VerifyException();
		}
	}

	private static void verifyArrayLength() {
		if (strTextSeparate.length < 2 || strTextSeparate.length > 3) {
			throw new VerifyException();
		}
	}

	// Verifica se o ArrayList foi preenchido corretamente
	private static void isBlankLists() {
		if (strTextSeparate[0].substring(0, 2).isBlank() ||
				strTextSeparate[1].substring(0, 2).isBlank() ||
				strTextSeparate[1].substring(2, 3).isBlank() ||
				strTextSeparate[0].substring(2).isBlank() ||
				strTextSeparate[1].substring(3).isBlank()) {

			throw new VerifyException();
		}
	}

	private static void breakStringsToChar() {
		// Separa textos quando endereco for tipo W e DW
		if (strTextSeparate.length == 2) {
			listString.add(strTextSeparate[0].substring(0, 2)); // primeiro texto
			listString.add(strTextSeparate[1].substring(0, 2));// segundo texto
			listString.add(strTextSeparate[1].substring(2, 3));// tipo de memoria
			listNumbers.add(strTextSeparate[0].substring(2)); // texto numero do bloco de memoria
			listNumbers.add(strTextSeparate[1].substring(3)); // texto numero do endereco da memoria no bloco
		}
		// DB1 . DBX0 . 0 separa textos quando endereco for X(bit)
		if (strTextSeparate.length == 3) {
			listString.add(strTextSeparate[0].substring(0, 2)); // primeiro texto
			listString.add(strTextSeparate[1].substring(0, 2));// segundo texto
			listString.add(strTextSeparate[1].substring(2, 3));// tipo de memoria
			listNumbers.add(strTextSeparate[0].substring(2)); // texto numero do bloco de memoria
			listNumbers.add(strTextSeparate[1].substring(3)); // texto numero do endereco da memoria no bloco
			listNumbers.add(strTextSeparate[2]); // texto número do bit
		}
		// Coloca os Caracteres numa lista PRIMEIROS SEGUNDOS E ULTIMO
		for (int a = 0; a < listString.get(0).length(); a++) {
			firstLetters.add(listString.get(0).charAt(a));
		}
		for (int a = 0; a < listString.get(1).length(); a++) {
			secondLetters.add(listString.get(1).charAt(a));
		}
		lastCharacter = listString.get(2).charAt(0);

		// Coloca os numeros numa lista
		for (int a = 0; a < listNumbers.get(0).length(); a++) {
			CharDigitList.add(listNumbers.get(0).charAt(a));
		}
		for (int a = 0; a < listNumbers.get(1).length(); a++) {
			CharDigitList.add(listNumbers.get(1).charAt(a));
		}

		if (strTextSeparate.length == 3) {
			CharDigitList.add(listNumbers.get(2).charAt(0));
		}
	}

	// Verifica os textos
	// charMemory[] = { 'D', 'B' };
	private static void verifyChars() {
		// verifica as primeiras letras
		for (int a = 0; a < templateDB.length; a++) {
			if (templateDB[a] != firstLetters.get(a)) {
				throw new VerifyException();
			}
		}
		// verifica segundas letras
		for (int a = 0; a < templateDB.length; a++) {
			if (templateDB[a] != secondLetters.get(a)) {
				throw new VerifyException();
			}
		}

		//Verifica o tipo de caracter
		if (lastCharacter == templateType[0] && strTextSeparate.length == 3) { // verifica tipo W ou D // D
			if (Integer.parseInt(listNumbers.get(2)) > 7 || Integer.parseInt(listNumbers.get(2)) < 0) {
				throw new VerifyException();
			}
		} else if (lastCharacter == templateType[1] && strTextSeparate.length == 2 || 
				   lastCharacter == templateType[2] &&strTextSeparate.length == 2) {
		} else {
			throw new VerifyException();
		}

		for (int a = 0; a < CharDigitList.size(); a++) {
			if (!Character.isDigit(CharDigitList.get(a))) {
				throw new VerifyException();
			}
		}
		// if (typeX) 
		if (listNumbers.size() == 3 && listNumbers.get(2).length() != 1) {
			throw new VerifyException();
		}
	}
}
