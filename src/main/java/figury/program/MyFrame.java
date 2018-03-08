package figury.program;

import java.awt.Color;
import java.util.jar.JarFile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import figury.Circle;
import figury.Shape;
import figury.Triangle;

public class MyFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	int buttonsDistance = 150;
	Color color = new Color(56, 56, 56);
	JButton buttonCircle, buttonTriangle, buttonLoadJar;
	JPanel panel1;
	JPanel panel2;
	Circle circle;
	Triangle trojkat;
	Shape shape;
	Random random = new Random();
	JarInputStream jarInputFile;
	Map<JButton, Shape> map = new HashMap<>();
	ArrayList<String> classes;
	List<Class> figures = new ArrayList<Class>();
	List<Class> loadedFigures = new ArrayList<Class>();
	List<String> listOfNewFigureNames = new ArrayList<>();
	List<String> listOfAllFigureNames = new ArrayList<>();
	String loadedFiguresName;
	File choosedFile = null;
	JFileChooser fileChooser = new JFileChooser();
	JarFile jarFile;


	public MyFrame() {

		setVisible(true);
		getContentPane().setBackground(color);
		getContentPane().setLayout(null);
		setSize(900, 700);
		setResizable(false);
		setTitle("lista 5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addPanels();
	}

	public void addPanels() {
		panel1 = new PanelLeftSide();
		panel2 = new PanelRightSide();
		add(panel1);
		add(panel2);
		addButtons();
	}

	public void addButtons() {
		buttonCircle = new JButton("Circle");
		buttonCircle.setBounds(25, 50, 150, 30);
		buttonCircle.addActionListener(this);
		panel1.add(buttonCircle);
		figures.add(Circle.class);
		map.put(buttonCircle, new Circle());

		buttonTriangle = new JButton("Triangle");
		buttonTriangle.setBounds(25, 100, 150, 30);
		buttonTriangle.addActionListener(this);
		panel1.add(buttonTriangle);
		figures.add(Triangle.class);
		map.put(buttonTriangle, new Triangle());

		buttonLoadJar = new JButton("Add new figures");
		buttonLoadJar.setBounds(35, 620, 150, 30);
		buttonLoadJar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					loadJarFile();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}

			}
		});

		add(buttonLoadJar);
	}
	

	public void loadJarFile() throws MalformedURLException {

		chooseAFileFromMenu();

		getJarAbsolutePath();
		
		Enumeration<JarEntry> e = jarFile.entries();
		URL[] urls = { new URL("jar:file:" + choosedFile.getAbsolutePath() + "!/") };
		URLClassLoader urlClassLoader = URLClassLoader.newInstance(urls);
		while (e.hasMoreElements()) {
			JarEntry jarEntry = e.nextElement();
			if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(".class")) {
				continue;
			}
			String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
			className = className.replace('/', '.');
		
			try {
				Class<?> loadedClass = urlClassLoader.loadClass(className);
				loadedFigures.add(loadedClass);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		}

		checkIfFigureExist();
	}

	public void chooseAFileFromMenu() {
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			choosedFile = fileChooser.getSelectedFile();
		}
	}
	public void getJarAbsolutePath() {
		
		try {
			jarFile = new JarFile(choosedFile.getAbsolutePath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void checkIfFigureExist() {

		for (int i = 0; i < loadedFigures.size(); i++) {
			String loadedFiguresName = loadedFigures.get(i).getName()
					.substring(loadedFigures.get(i).getName().lastIndexOf(".") + 1);
			boolean duplicate = false;
			for (Class<?> existingFigure : figures) {
				if (loadedFiguresName
						.equals(existingFigure.getName().substring(figures.get(i).getName().lastIndexOf(".") + 1))) {
					duplicate = true;
				}
			}

			for (String previousFigure : listOfNewFigureNames) {
				if (previousFigure.equals(loadedFiguresName)) {
					duplicate = true;
				}
			}

			if (duplicate) break;
		
			figures.add(loadedFigures.get(i));
			listOfNewFigureNames.add(loadedFiguresName);
			}

		for (int i = 0; i < listOfNewFigureNames.size(); i++) {
			try {
				shape = (Shape) figures.get(i + listOfNewFigureNames.size()).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			checkIfFigureImplementsShapeInterface(loadedFiguresName, i);
	
		}
	}
	public void checkIfFigureImplementsShapeInterface(String loadedFiguresName, int i) {
		if (Shape.class.isAssignableFrom(loadedFigures.get(i))) {
			createNewButton(listOfNewFigureNames.get(i), shape);

		} else
			System.err.println("The figure can not be added. The class " + loadedFigures.get(i).getName()
					+ " does not implement the Shape interface");
	}
	
	public void createNewButton(String buttonName, Shape shape) {
		System.out.println("dodajemy przycisk");
		JButton button = new JButton(buttonName);
		button.setBounds(25, buttonsDistance, 150, 30);
		button.addActionListener(this);

		panel1.add(button);
		buttonsDistance += 50;

		map.put(button, shape);
	}

	@Override
	public void actionPerformed(ActionEvent action) {

		Shape currentShape = map.get(action.getSource());
		currentShape.paintShape(getGraphics());

	}
}
