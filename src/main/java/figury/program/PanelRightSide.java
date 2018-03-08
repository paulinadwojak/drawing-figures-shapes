package figury.program;

import java.awt.Color;

import javax.swing.JPanel;

public class PanelRightSide extends JPanel {

	private static final long serialVersionUID = 1L;
	Color color = new Color(26, 26, 26);

	public PanelRightSide() {
		setLayout(null);
		setBackground(color);
		setBounds(250, 15, 625, 590);
	}

}
