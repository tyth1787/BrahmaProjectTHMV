package plugin;

import javax.swing.JPanel;

public abstract class Plugin implements IPlugin{
	private String id;

	public Plugin(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// Callback method
	public abstract void layout(JPanel panel);
	public abstract void start();
	public abstract void stop();
}
