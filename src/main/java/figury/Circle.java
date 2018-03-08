package figury;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Circle implements Shape {

	Random random = new Random();
	int x, y, figureSize, leftBorder;

	public void paintShape(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		x = random.nextInt(625) + 250;
		y = random.nextInt(590) + 40;

		figureSize = Math.min(200, Math.min(875 - x, 625 - y));

		g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		g2.drawOval(x, y, figureSize, figureSize);
		g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		g2.fillOval(x, y, figureSize, figureSize);

		System.out.println("Rysuję kółko");
	}

}
