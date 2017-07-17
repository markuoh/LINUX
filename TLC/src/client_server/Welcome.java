package client_server;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class Welcome extends JPanel{
	JProgressBar pb;
	
	public Welcome() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pb = new JProgressBar();
		pb.setIndeterminate(true);
		this.add(pb);
		
		JFrame a = new JFrame("Welcome");
		a.setIconImage(Toolkit.getDefaultToolkit().getImage("///root/eclipse-workspace/TLC/src/client_server/kali.png"));
		a.setUndecorated(true);
		a.setSize(800, 500);
		a.setLocationRelativeTo(null);
		
		Container c = a.getContentPane();
		c.add(this);
		a.setVisible(true);
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		a.dispose();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("///root/eclipse-workspace/TLC/src/client_server/kali.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(bi, 0, 0, null);
	}
}
