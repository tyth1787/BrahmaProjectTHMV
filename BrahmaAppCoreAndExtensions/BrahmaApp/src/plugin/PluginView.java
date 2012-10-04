package plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class PluginView {
	// Lets create the elements that we will need
	private JFrame frame;
	private JLabel bottomLabel;
	private JList sideList;
	private JPanel centerEnvelope;
	private JPanel contentPane;
	
	
	public PluginView(DefaultListModel<String> listModel) {
		frame = new JFrame("Pluggable Board Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = (JPanel) frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(700, 500));
		bottomLabel = new JLabel("No plugins registered yet!");
		
		sideList = new JList(listModel);
		sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sideList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(sideList);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		
		// Create center display area
		centerEnvelope = new JPanel(new BorderLayout());
		centerEnvelope
				.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		// Lets lay them out, contentPane by default has BorderLayout as its
		// layout manager
		contentPane.add(centerEnvelope, BorderLayout.CENTER);
		contentPane.add(scrollPane, BorderLayout.EAST);
		contentPane.add(bottomLabel, BorderLayout.SOUTH);
		
		
	}
	
	public void loadPluginIntoView(IPlugin plugin) {
		// Clear previous working area
		centerEnvelope.removeAll();

		// Create new working area
		JPanel centerPanel = new JPanel();
		centerEnvelope.add(centerPanel, BorderLayout.CENTER);

		// Ask plugin to layout the working area
		plugin.layout(centerPanel);
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	public JList getSideList() {
		return sideList;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public JLabel getBottomLabel() {
		return bottomLabel;
	}
	
	public void setBottomLabelText(String pluginId, String newText) {
		getBottomLabel().setText("The " + pluginId + " " + newText);
	}
}
