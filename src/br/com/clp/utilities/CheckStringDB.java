package br.com.clp.utilities;

import java.util.ArrayList;
import java.util.List;

/* UM DIA EU VOU REFATORAR ESSE CÓDIGO KKKKKKKKKKKKK

 *******************************************************************************************************************
 ***************************   COMO FUNCIONA O ENDEREÇO DE MEMÓRIA NO CLP SIEMENS    *******************************
 DB1.DBW50  <<<<<<<   EXEMPLO PARA CONTEXTUALIZAR
 
+++++++++++NOME DO BLOCO++++++++++++++++
* DB1  >> Número do bloco de dados. Pode ser DB1, DB2 DB10, DB1000 etc.
* DB são caracteres obrigatórios do nome do bloco seguido de um número que é o número correspondente do bloco.

++++++++++++++++  ENDEREÇO DE MEMORIA DENTRO DO BLOCO  +++++++++++++++++++ 
* DBW50 >> Após o PONTO, vem o endereço interno da memória dentro do bloco. DB São caracteres obrigatórios seguido de uma letra e após 
* números.
* X W D    Letras correspondentes ao tipo de memória: X (boolean), W (word), D (double word)
* Após a letra vem o número correspondente ao endereço de mémoria
* 
* Caso seja do tipo X(bit) como no exemplo ao lado DB1.DBX5.6 após o X deverá ter um ou mais números que corresponde a área de 
* memória dentro do bloco, seguido de um ponto e de um número de 0 à 7 que corresponde ao endereço do bit de um byte de memória.  


 >>>>>>>>>  Exemplos de endereços de memória válidos  <<<<<<<<<<<<<<<<
DB1.DBX12.2 -  referenciando endereço de um bit
DB1.DBW52 - referenciando ao endereço de uma Word
DBW23.DBD650 - referenciando ao endereço de um DoubleWord
*/

public final class CheckStringDB {
	private static final char charMemory[] = { 'D', 'B' };
	private static final char charType[] = { 'X', 'W', 'D' };

	// Booleanos para validação final
	private static boolean textLength = false;
	private static boolean isEmpty = false;
	private static boolean verifyStr1[] = { false, false };
	private static boolean verifyStr2[] = { false, false };
	private static boolean verifyType = false;
	private static boolean verificaDigito = false;
	private static boolean verifyNumbers = false;

	// Para separação do texto
	private static boolean arrayLength = false;
	private static String stringTexto;
	private static char lastCharacter;
	private static List<String> strText = new ArrayList<String>();
	private static List<String> numbersList = new ArrayList<String>();
	private static List<Character> firstChars = new ArrayList<Character>();
	private static List<Character> secondChars = new ArrayList<Character>();
	private static List<Character> CharNumberList = new ArrayList<Character>();
	private static String strTextSeparate[];

	// db.dbw.1dd

	public static boolean verifyText(String texto) {
		clearCheck();
		stringTexto = texto;

		if (checkArray()) {
			if (checkList()) {
				separeText();
			} else {
				System.out.println("Texto do campo errado!");
				return false;
			}
		} else {
			System.out.println("Texto do campo errado!");
			return false;
		}

		checkTexto();

		if (textLength && isEmpty && verifyStr1[0] && verifyStr1[1] && verifyStr2[0] && verifyStr2[1] && verifyType
				&& verifyNumbers) {
			return true;
		} else {
			System.out.println("Texto do campo errado!!");
			return false;
		}
	}

	// Limpa os boolenas de verificação e arrays
	private static void clearCheck() {
		textLength = false;
		arrayLength = false;
		isEmpty = false;
		verifyStr1[0] = false;
		verifyStr1[1] = false;
		verifyStr2[0] = false;
		verifyStr2[1] = false;
		verifyType = false;
		verificaDigito = false;
		verifyNumbers = false;

		strText.clear();
		numbersList.clear();
		firstChars.clear();
		secondChars.clear();
		CharNumberList.clear();
	}
	// DB1.DBW0
	// DB1.DBX0.0

	// Verifica a string, separa String e verifica o array resultante da separação
	private static boolean checkArray() {
		String textoUpper = stringTexto.toUpperCase();

		if (!stringTexto.isEmpty() && stringTexto.length() >= 7 && stringTexto.contains(".")) {
			strTextSeparate = textoUpper.split("\\.");
			textLength = true;
			isEmpty = true;
		} else {
			textLength = false;
			isEmpty = false;
			return false;
		}
		if (strTextSeparate.length == 2 || strTextSeparate.length == 3) {
			arrayLength = true;
		} else {
			arrayLength = false;
			return false;
		}
		return true;
	}

	// Verifica se o ArrayList foi preenchido corretamente
	private static boolean checkList() {
		if (strTextSeparate[0].substring(0, 2).isBlank() | strTextSeparate[1].substring(0, 2).isBlank()
				| strTextSeparate[1].substring(2, 3).isBlank() | strTextSeparate[0].substring(2).isBlank()
				| strTextSeparate[1].substring(3).isBlank()) {
			return false;
		} else {
			return true;
		}
	}

	private static void separeText() {
		try {
			// Separa textos quando endereço for tipo W e DW
			if (strTextSeparate.length == 2) {
				strText.add(strTextSeparate[0].substring(0, 2)); // primeiro texto
				strText.add(strTextSeparate[1].substring(0, 2));// segundo texto
				strText.add(strTextSeparate[1].substring(2, 3));// tipo de memória
				numbersList.add(strTextSeparate[0].substring(2)); // texto número do bloco de memória
				numbersList.add(strTextSeparate[1].substring(3)); // texto número do endereço da memória no bloco

			}
			if (strTextSeparate.length == 3) {

				// DB1 . DBX0 . 0 separa textos quando endereço for X(bit)
				strText.add(strTextSeparate[0].substring(0, 2)); // primeiro texto
				strText.add(strTextSeparate[1].substring(0, 2));// segundo texto
				strText.add(strTextSeparate[1].substring(2, 3));// tipo de mémória
				numbersList.add(strTextSeparate[0].substring(2)); // texto número do bloco de memória
				numbersList.add(strTextSeparate[1].substring(3)); // texto número do endereço da memória no bloco
				numbersList.add(strTextSeparate[2]); // texto número do bit
			}

			// Coloca os Caracteres numa lista PRIMEIROS SEGUNDOS E ULTIMO
			for (int a = 0; a < strText.get(0).length(); a++) {
				firstChars.add(strText.get(0).charAt(a));
			}
			for (int a = 0; a < strText.get(1).length(); a++) {
				secondChars.add(strText.get(1).charAt(a));
			}
			lastCharacter = strText.get(2).charAt(0);

			// Coloca os números numa lista
			for (int a = 0; a < numbersList.get(0).length(); a++) {
				CharNumberList.add(numbersList.get(0).charAt(a));
			}
			for (int a = 0; a < numbersList.get(1).length(); a++) {
				CharNumberList.add(numbersList.get(1).charAt(a));
			}

			if (strTextSeparate.length == 3) {
				CharNumberList.add(numbersList.get(2).charAt(0));
			}
		} catch (Exception e) {
			System.out.println("Erro de acesso ao array CheckString" + e.getMessage() + e.getLocalizedMessage());
		}
	}

	// Verifica os textos
	private static void checkTexto() {
		boolean typeX = false;
		try {
			for (int a = 0; a < charMemory.length; a++) {
				if (charMemory[a] == firstChars.get(a)) {
					verifyStr1[a] = true;
				} else {
					verifyStr1[a] = false;
				}
			}

			for (int a = 0; a < charMemory.length; a++) {
				if (charMemory[a] == secondChars.get(a)) {
					verifyStr2[a] = true;
				} else {
					verifyStr2[a] = false;
				}
			}
			// Verifica tipo. Se for X(bit)
			for (int a = 0; a < charType.length; a++) {
				if (charType[a] == lastCharacter && charType[a] != 'X' && strTextSeparate.length == 2) {
					verifyType = true;
					break;
				} else if (lastCharacter == 'X' && strTextSeparate.length == 3) {
					if (Integer.parseInt(numbersList.get(2)) < 8 && Integer.parseInt(numbersList.get(2)) > -1) {
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

			for (int a = 0; a < CharNumberList.size(); a++) {
				if (Character.isDigit(CharNumberList.get(a))) {
					verifyNumbers = true;
				} else {
					verifyNumbers = false;
					break;
				}
			}

			if (typeX) {
				if (numbersList.size() == 3 && numbersList.get(2).length() != 1) {
					verifyNumbers = false;
				}

			}

		} catch (Exception e) {
			System.out.println("Erro ao acessar o array no CheckEndereço");
		}
	}

}
