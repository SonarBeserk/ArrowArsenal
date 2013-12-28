package me.sonarbeserk.arrowarsenal.yamls;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Data {
	
	private FileConfiguration data = null;
	private File dataFile = null;
	private JavaPlugin plugin = null;
	
	public Data(JavaPlugin plugin) {
		
		this.plugin = plugin;

		saveDefault();
	}

    /**
     * Reloads the data file
     */
	public void reload() {
		
		if(plugin.getConfig() == null) {
			
			plugin.reloadConfig();
		}

        saveDefault();

		if(dataFile == null) {
			
			dataFile = new File(plugin.getDataFolder(), "data.yml");
		}
		
		data = YamlConfiguration.loadConfiguration(dataFile);
		
		InputStream defConfigStream = plugin.getResource("data.yml");
		
		if(defConfigStream != null) {
			
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			data.setDefaults(defConfig);
		} else {
			
			plugin.getLogger().severe("Unable to load data.");
			return;
		}
	}
	
	private FileConfiguration getFileConfiguration() {
		
		if(data == null) {
			
			reload();
		}
		
		return data;
	}

    /**
     * Saves the default version of the data file if it was not found
     */
	private void saveDefault() {
		
		if(plugin.getConfig() == null) {
			
			plugin.reloadConfig();
		}
		
		if(dataFile == null) {
			
			dataFile = new File(plugin.getDataFolder() + "/" + "data.yml");
		}
		
		if(!dataFile.exists()) {
			
			plugin.saveResource("data.yml", false);
			return;
		}
	}

    /**
     * Saves the current state of the data file, also destroys any comments in the file
     */
	public void save() {
		
		if((data == null)||(dataFile == null)) {return;}
		
		try {
			
			getFileConfiguration().save(dataFile);
		} catch (IOException ex) {
			
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + dataFile, ex);
		}
	}

    /**
     * Sets the value at the path specified
     * @param path the path to set at
     * @param value the value to set
     */
    public void set(String path, Object value) {

        data.set(path, value);
    }

    /**
     * Gets a entry from the data file
     */
    public Object get(String path) {

        return getFileConfiguration().get(path);
    }
}
