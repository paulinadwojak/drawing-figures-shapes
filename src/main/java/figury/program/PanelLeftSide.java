package figury.program;

import java.awt.Color;
import javax.swing.JPanel;

public class PanelLeftSide extends JPanel {

	private static final long serialVersionUID = 1L;
	Color color = new Color(26, 26, 26);

	public PanelLeftSide() {
		setLayout(null);
		setBackground(color);
		setBounds(15, 15, 200, 590);
	}

}
