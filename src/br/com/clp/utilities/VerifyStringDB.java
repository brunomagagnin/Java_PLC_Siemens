package br.com.clp.utilities;

import java.util.ArrayList;
import java.util.List;

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

	// Booleanos para validação final
	private static boolean textLength = false;
	private static boolean isEmpty = false;
	private static boolean verifyFirstString[] = { false, false };
	private static boolean verifySecondString[] = { false, false };
	private static boolean verifyType = false;
	private static boolean verificaDigit = false;
	private static boolean verifyNumbers = false;

	// Para separação do texto
	private static boolean arrayLength = false;
	private static String stringText;
	private static char lastCharacter;
	private static List<String> listString = new ArrayList<String>();
	private static List<String> listNumbers = new ArrayList<String>();
	private static List<Character> listFirstChars = new ArrayList<Character>();
	private static List<Character> listSecondChars = new ArrayList<Character>();
	private static List<Character> listCharNumbers = new ArrayList<Character>();
	private static String stringBreacked[];

	public static boolean verifyText(String text) {
		clearLists();
		stringText = text;
		if (verifyArray()) {
			if (verifyLists()) {
				breakString();
			} else {
				System.out.println("Texto do campo errado!");
				return false;
			}
		} else {
			System.out.println("Texto do campo errado!");
			return false;
		}
		verifyChars();
		if (textLength && isEmpty && verifyFirstString[0] && verifyFirstString[1] && verifySecondString[0] && verifySecondString[1] && verifyType
				&& verifyNumbers) {
			return true;
		} else {
			System.out.println("Texto do campo errado!!");
			return false;
		}
	}

	// Limpa os boolenas de verificação e arrays
	private static void clearLists() {
		textLength = false;
		arrayLength = false;
		isEmpty = false;
		verifyFirstString[0] = false;
		verifyFirstString[1] = false;
		verifySecondString[0] = false;
		verifySecondString[1] = false;
		verifyType = false;
		verificaDigit = false;
		verifyNumbers = false;

		listString.clear();
		listNumbers.clear();
		listFirstChars.clear();
		listSecondChars.clear();
		listCharNumbers.clear();
	}
	
	// Verifica a string, separa String e verifica o array resultante da separação
	private static boolean verifyArray() {
		String textoUpper = stringText.toUpperCase();
		if (!stringText.isEmpty() && stringText.length() >= 7 && stringText.contains(".")) {
			stringBreacked = textoUpper.split("\\.");
			textLength = true;
			isEmpty = true;
		} else {
			textLength = false;
			isEmpty = false;
			return false;
		}
		if (stringBreacked.length == 2 || stringBreacked.length == 3) {
			arrayLength = true;
		} else {
			arrayLength = false;
			return false;
		}
		return true;
	}

	// Verifica se o ArrayList foi preenchido corretamente
	private static boolean verifyLists() {
		if (stringBreacked[0].substring(0, 2).isBlank() | stringBreacked[1].substring(0, 2).isBlank()
				| stringBreacked[1].substring(2, 3).isBlank() | stringBreacked[0].substring(2).isBlank()
				| stringBreacked[1].substring(3).isBlank()) {
			return false;
		} else {
			return true;
		}
	}

	private static void breakString() {
		// Separa textos quando endereço for tipo W e DW
		if (stringBreacked.length == 2) {
			listString.add(stringBreacked[0].substring(0, 2)); // primeiro texto
			listString.add(stringBreacked[1].substring(0, 2));// segundo texto
			listString.add(stringBreacked[1].substring(2, 3));// tipo de memória
			listNumbers.add(stringBreacked[0].substring(2)); // texto número do bloco de memória
			listNumbers.add(stringBreacked[1].substring(3)); // texto número do endereço da memória no bloco

		}
		if (stringBreacked.length == 3) {
			// DB1 . DBX0 . 0 separa textos quando endereço for X(bit)
			listString.add(stringBreacked[0].substring(0, 2)); // primeiro texto
			listString.add(stringBreacked[1].substring(0, 2));// segundo texto
			listString.add(stringBreacked[1].substring(2, 3));// tipo de memória
			listNumbers.add(stringBreacked[0].substring(2)); // texto número do bloco de memória
			listNumbers.add(stringBreacked[1].substring(3)); // texto número do endereço da memória no bloco
			listNumbers.add(stringBreacked[2]); // texto número do bit
		}

		// Coloca os Caracteres numa lista PRIMEIROS SEGUNDOS E ULTIMO
		for (int a = 0; a < listString.get(0).length(); a++) {
			listFirstChars.add(listString.get(0).charAt(a));
		}
		for (int a = 0; a < listString.get(1).length(); a++) {
			listSecondChars.add(listString.get(1).charAt(a));
		}
		lastCharacter = listString.get(2).charAt(0);

		// Coloca os n�meros numa lista
		for (int a = 0; a < listNumbers.get(0).length(); a++) {
			listCharNumbers.add(listNumbers.get(0).charAt(a));
		}
		for (int a = 0; a < listNumbers.get(1).length(); a++) {
			listCharNumbers.add(listNumbers.get(1).charAt(a));
		}

		if (stringBreacked.length == 3) {
			listCharNumbers.add(listNumbers.get(2).charAt(0));
		}
	}

	// Verifica os textos
	private static void verifyChars() {
		boolean typeX = false;
		for (int a = 0; a < templateDB.length; a++) {
			if (templateDB[a] == listFirstChars.get(a)) {
				verifyFirstString[a] = true;
			} else {
				verifyFirstString[a] = false;
			}
		}
		for (int a = 0; a < templateDB.length; a++) {
			if (templateDB[a] == listSecondChars.get(a)) {
				verifySecondString[a] = true;
			} else {
				verifySecondString[a] = false;
			}
		}
		// Verifica tipo. Se for X(bit)
		for (int a = 0; a < templateType.length; a++) {
			if (templateType[a] == lastCharacter && templateType[a] != 'X' && stringBreacked.length == 2) {
				verifyType = true;
				break;
			} else if (lastCharacter == 'X' && stringBreacked.length == 3) {
				if (Integer.parseInt(listNumbers.get(2)) < 8 && Integer.parseInt(listNumbers.get(2)) > -1) {
					verifyType = true;
					typeX = true;
					break;
				}
				verifyType = false;
				typeX = false;
				break;
			} else {
				typeX = false;
				verifyType = false;
			}
		}
		for (int a = 0; a < listCharNumbers.size(); a++) {
			if (Character.isDigit(listCharNumbers.get(a))) {
				verifyNumbers = true;
			} else {
				verifyNumbers = false;
				break;
			}
		}
		if (typeX) {
			if (listNumbers.size() == 3 && listNumbers.get(2).length() != 1) {
				verifyNumbers = false;
			}

		}
	}

}
