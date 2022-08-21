package com.clp.start;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.clp.gui.FrameStart;

public class Start {

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
					FrameStart start = new FrameStart();
					start.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
