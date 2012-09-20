package plugin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PluginCore {
	// GUI Widgets that we will need
	private JFrame frame;
	private JLabel bottomLabel;
	private DefaultListModel<String> listModel;
	
	// For holding registered plugin
	private HashMap<String, Plugin> idToPlugin;
	private Plugin currentPlugin;
	
	// Plugin manager
	PluginManager pluginManager;
	
	public PluginCore() {
		idToPlugin = new HashMap<String, Plugin>();
		
		// Lets create the elements that we will need
		frame = new JFrame("Pluggable Board Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(700, 500));
		bottomLabel = new JLabel("No plugins registered yet!");
		
		listModel = new DefaultListModel<String>();
		JList sideList = new JList(listModel);
		sideList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sideList.setLayoutOrientation(JList.VERTICAL);
		JScrollPane scrollPane = new JScrollPane(sideList);
		scrollPane.setPreferredSize(new Dimension(100, 50));
		
		// Create center display area
		JPanel centerEnvelope = new JPanel(new BorderLayout());
		centerEnvelope.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		
		// Lets lay them out, contentPane by default has BorderLayout as its layout manager
		contentPane.add(centerEnvelope, BorderLayout.CENTER);
		contentPane.add(scrollPane, BorderLayout.EAST);
		contentPane.add(bottomLabel, BorderLayout.SOUTH);
		
		addListeners(centerEnvelope, contentPane, sideList);
		
		startPluginManager();


	}
	
	// Start the plugin manager now that the core is ready
	private void startPluginManager() {
		try {
			this.pluginManager = new PluginManager(this);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this.pluginManager);
		thread.start();
	}
	
	private void addListeners(final JPanel centerEnvelope, final JPanel contentPane, final JList sideList) {
		// Add action listeners
		sideList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// If the list is still updating, return
				if(e.getValueIsAdjusting())
					return;
				
				// List has finalized selection, let's process further
				int index = sideList.getSelectedIndex();
				String id = listModel.elementAt(index);
				Plugin plugin = idToPlugin.get(id);
				
				if(plugin == null || plugin.equals(currentPlugin))
					return;
				
				// Stop previously running plugin
				if(currentPlugin != null)
					currentPlugin.stop();
				
				// The newly selected plugin is our current plugin
				currentPlugin = plugin;
				
				loadPlugin(plugin);
			}

			//separate visual components
			private void loadPlugin(Plugin plugin) {
				// Clear previous working area
				centerEnvelope.removeAll();
				
				// Create new working area
				JPanel centerPanel = new JPanel();
				centerEnvelope.add(centerPanel, BorderLayout.CENTER); 
				
				// Ask plugin to layout the working area
				currentPlugin.layout(centerPanel);
				contentPane.revalidate();
				contentPane.repaint();
				
				// Start the plugin
				currentPlugin.start();
				setBottomLabelText(plugin.getId(),"is running!");
			}
		});
	}
	
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	public void stop() {
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				frame.setVisible(false);
			}
		});
	}
	
	public void addPlugin(Plugin plugin) {
		this.idToPlugin.put(plugin.getId(), plugin);
		this.listModel.addElement(plugin.getId());
		setBottomLabelText(plugin.getId(),"plugin has been recently added!");
	}
	
	public void setBottomLabelText(String newText, String pluginId) {
		this.bottomLabel.setText("The " + pluginId + " " + newText);
	}
	
	public void removePlugin(String id) {
		Plugin plugin = this.idToPlugin.remove(id);
		this.listModel.removeElement(id);
		
		// Stop the plugin if it is still running
		plugin.stop();
		setBottomLabelText(plugin.getId(),"plugin has been recently removed!");
	}
}
