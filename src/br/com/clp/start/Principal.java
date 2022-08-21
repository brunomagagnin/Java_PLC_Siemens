package br.com.clp.start;

import java.awt.EventQueue;

import javax.swing.UIManager;

import br.com.clp.gui.FramePrincipal;

public class Principal {

	public static void main(String[] args) {	
		//LOOK AND FEEL
		// "javax.swing.plaf.nimbus.NimbusLookAndFeel"
		//"com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
		//"javax.swing.plaf.metal.MetalLookAndFeel"
		//"com.sun.java.swing.plaf.motif.MotifLookAndFeel"
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					FramePrincipal principal = new FramePrincipal();
					principal.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
