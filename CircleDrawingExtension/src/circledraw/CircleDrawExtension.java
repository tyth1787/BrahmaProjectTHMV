package circledraw;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import plugin.Plugin;

public class CircleDrawExtension extends Plugin {
	public static final String PLUGIN_ID = "Circle Draw";

	private CirclePanel panel;

	public CircleDrawExtension() {
		super(PLUGIN_ID);
	}

	@Override
	public void layout(JPanel parentPanel) {
		parentPanel.setLayout(new BorderLayout());
		panel = new CirclePanel();
		parentPanel.add(panel);
	}

	@Override
	public void start() {
		// Not much to do here
	}

	@Override
	public void stop() {
		// Not much to do here
	}

	// For now we need to declare dummy main method
	// to include in manifest file
	public static void main(String[] args) {

	}

}
