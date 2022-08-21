package br.com.clp.comunication;

import java.util.List;

import MOKA7.S7;
import br.com.clp.gui.TextField;
import br.com.clp.utilities.FilterStringDB;

public class DriverS7 extends ConnectionS7 {
	private byte[] buffer = new byte[65536];
	private final int AMOUNTBIT = 1;
	private final int AMOUNTWORD = 2;
	private final int AMOUNTDOUBLE = 4;

	public DriverS7(String IP) {
		super(IP);
	}

//Método ReadArea(Identificação de Área, Número do Bloco DB, Número do endereço na DB e Buffer)

	// *************BLOCO PARA LEITURA DE BITS***************
	// Método S7.GetAtBit(Buffer, int Pos, int Bit)
	public void getBit(List<TextField> listFieldValues, List<TextField> listFieldDB, int index) {
		client.ReadArea(S7.S7AreaDB, 
						FilterStringDB.getNumberDB(listFieldDB.get(index).getText()),
						FilterStringDB.getAdressMemory(listFieldDB.get(index).getText()), 
						this.AMOUNTWORD, 
						this.buffer);
		boolean bit = S7.GetBitAt(this.buffer, 0, FilterStringDB.getBitIfX(listFieldDB.get(index).getText()));
		listFieldValues.get(index).setText("" + bit);
	}

	// *********BLOCO PARA LEITURA DE WORDS**********
	// Método S7.GetWordAt(Buffer, posição inicial no Buffer)
	public void getWord(List<TextField> listFieldValues, List<TextField> listFieldDB, int index) {
		client.ReadArea(S7.S7AreaDB, 
						FilterStringDB.getNumberDB(listFieldDB.get(index).getText()),
						FilterStringDB.getAdressMemory(listFieldDB.get(index).getText()), 
						this.AMOUNTWORD, 
						this.buffer);
		Integer data = S7.GetWordAt(this.buffer, 0);
		listFieldValues.get(index).setText(data.toString());
	}

	// ********BLOCO PARA LEITURA DE DOUBLEWORDS*************
	// Método S7.GetDWordAT(Buffer, posição inicial do buffer)
	public void getDouble(List<TextField> listFieldValues, List<TextField> listFieldDB, int index) {
		client.ReadArea(S7.S7AreaDB, 
				FilterStringDB.getNumberDB(listFieldDB.get(index).getText()),
				FilterStringDB.getAdressMemory(listFieldDB.get(index).getText()),
				AMOUNTDOUBLE, 
				this.buffer);
		long data = S7.GetDWordAt(this.buffer, 0);
		listFieldValues.get(index).setText(Long.toString(data));
	}
}
