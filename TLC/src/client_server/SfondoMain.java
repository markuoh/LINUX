package client_server;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SfondoMain extends JPanel{
	
	public SfondoMain() {
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("///root/eclipse-workspace/TLC/src/client_server/world.png"));			
		} catch(Exception e) {
			e.printStackTrace();
		}
		g.drawImage(bi, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
