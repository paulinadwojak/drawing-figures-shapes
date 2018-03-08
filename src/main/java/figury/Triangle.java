package figury;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;


public class Triangle  implements Shape{

	Random random = new Random();
	Polygon polygon;
	int x, y;

	public void paintShape(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		polygon = new Polygon();
		polygon.addPoint(x = random.nextInt(550) + 260, y = random.nextInt(520) + 55);
		polygon.addPoint(x = random.nextInt(550) + 260, y = random.nextInt(520) + 55);
		polygon.addPoint(x = random.nextInt(550) + 260, y = random.nextInt(520) + 55);

		g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		g2.drawPolygon(polygon);
		g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		g2.fillPolygon(polygon);

		System.out.println("Rysuję trójkąt");
	}
}
