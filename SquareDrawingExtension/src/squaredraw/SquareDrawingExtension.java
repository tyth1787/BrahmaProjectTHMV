package squaredraw;
import java.awt.BorderLayout;

import javax.swing.JPanel;

import plugin.Plugin;

public class SquareDrawingExtension extends Plugin {
	public static final String PLUGIN_ID = "Square Draw";

	private SquarePanel panel;

	public SquareDrawingExtension() {
		super(PLUGIN_ID);
	}

	@Override
	public void layout(JPanel parentPanel) {
		parentPanel.setLayout(new BorderLayout());
		panel = new SquarePanel();
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
