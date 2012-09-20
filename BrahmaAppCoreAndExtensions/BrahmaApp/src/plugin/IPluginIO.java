package plugin;

import java.nio.file.Path;

public interface IPluginIO {
	void loadBundle(Path bundlePath) throws Exception;
	void unloadBundle(Path bundlePath);
}
