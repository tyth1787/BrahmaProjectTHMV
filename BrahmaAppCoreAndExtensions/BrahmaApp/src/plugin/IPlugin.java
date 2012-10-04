package plugin;

import javax.swing.JPanel;

public interface IPlugin {
	
	public String getId();
	public void setId(String id);

	public void layout(JPanel panel);
	public void start();
	public void stop();
}
