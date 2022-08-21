package br.com.clp.comunication;

import MOKA7.S7;
import MOKA7.S7Client;

public class ConnectionS7 {
	protected String ip;
	protected final int SLOT = 2;
	protected final int RACK = 0;
	protected boolean isConnected = false;
	protected S7Client client = new S7Client();

	public ConnectionS7(String ip) {
		this.ip = ip;
	}

	public void connect() {
		client.SetConnectionType(S7.S7_BASIC);
		client.ConnectTo(ip, RACK, SLOT);
		if (client.Connected) {
			this.isConnected = true;
		} else {
			this.isConnected = false;
		}
	}

	public void disconnect() {
		if (client.Connected) {
			client.Disconnect();
			this.isConnected = false;
		}
	}

	public boolean isConnected() {
		return this.isConnected;
	}

}