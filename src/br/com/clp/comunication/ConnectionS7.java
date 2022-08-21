package br.com.clp.comunication;

import MOKA7.S7;
import MOKA7.S7Client;

public class ConnectionS7 {
	protected String ip;
	protected final int SLOT = 2;
	protected final int RACK = 0;
	protected boolean conectado = false;
	protected S7Client client = new S7Client();

	public ConnectionS7(String ip) {
		this.ip = ip;
	}

	public void connect() {
		try {
			client.SetConnectionType(S7.S7_BASIC);
			client.ConnectTo(ip, RACK, SLOT);
			if (client.Connected) {
				this.conectado = true;
			} else {
				this.conectado = false;
			}
		} catch (Exception e) {
			System.out.println("Erro conectar: " + e.getMessage());
		}
	}

	public void disconnect() {
		if (client.Connected) {
			client.Disconnect();
			this.conectado = false;
		}
	}

	public boolean statusConnect() {
		return this.conectado;
	}

}
