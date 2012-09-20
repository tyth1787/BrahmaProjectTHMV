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

	
	private DefaultListModel<String> listModel;
	private PluginView pluginView;

	// For holding registered plugin
	private HashMap<String, Plugin> idToPlugin;
	private Plugin currentPlugin;

	// Plugin manager
	PluginManager pluginManager;

	public PluginCore() {
		
		listModel = new DefaultListModel<String>();
		pluginView = new PluginView(listModel);
		idToPlugin = new HashMap<String, Plugin>();

		addListeners();

		startPluginManager();

	}

	// Start the plugin manager now that the core is ready
	private void startPluginManager() {
		try {
			this.pluginManager = new PluginManager(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread(this.pluginManager);
		thread.start();
	}

	private void addListeners() {
		// Add action listeners
		pluginView.getSideList().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						// If the list is still updating, return
						if (e.getValueIsAdjusting())
							return;

						// List has finalized selection, let's process further
						int index = pluginView.getSideList().getSelectedIndex();
						String id = listModel.elementAt(index);
						Plugin plugin = idToPlugin.get(id);

						if (plugin == null || plugin.equals(currentPlugin))
							return;

						// Stop previously running plugin
						if (currentPlugin != null)
							currentPlugin.stop();

						// The newly selected plugin is our current plugin
						currentPlugin = plugin;

						pluginView.loadPluginIntoView(plugin);
						
						currentPlugin.start();
						pluginView.setBottomLabelText(plugin.getId(), "is running!");
						
					}
				});
	}

	public void start() {
		setInvokeLater(true);
	}

	public void stop() {
		setInvokeLater(false);
	}

	private void setInvokeLater(final boolean start) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if (start) {
					pluginView.getFrame().pack();
				}
				pluginView.getFrame().setVisible(start);
			}
		});
	}

	public void addPlugin(Plugin plugin) {
		this.idToPlugin.put(plugin.getId(), plugin);
		this.listModel.addElement(plugin.getId());
		pluginView.setBottomLabelText(plugin.getId(), "plugin has been recently added!");
	}


	public void removePlugin(String id) {
		Plugin plugin = this.idToPlugin.remove(id);
		this.listModel.removeElement(id);

		// Stop the plugin if it is still running
		plugin.stop();
		pluginView.setBottomLabelText(plugin.getId(), "plugin has been recently removed!");
	}
}
