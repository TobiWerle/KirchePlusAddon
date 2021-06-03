package UC.KirchePlus.Addon.main;

import java.util.List;

import UC.KirchePlus.Addon.Commands.Brot_Command;
import UC.KirchePlus.Addon.Commands.HV_Command;
import UC.KirchePlus.Addon.Commands.MarryRP_Command;
import UC.KirchePlus.Addon.Events.Displayname;
import UC.KirchePlus.Addon.Utils.MarryFile;
import UC.KirchePlus.Addon.Utils.TabellenMethoden;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.BooleanElement;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

public class main extends LabyModAddon{

	public static main main;
    
    public static boolean hv = false;
    public static boolean bread = false;
    
    public static String hvPrefix = "&8[&cHV&8]";
    public static String breadprefix = "&8[&2X&8]";
    
	@Override
	public void onEnable() {
		main = this;
		//Load
		new Displayname();
		MarryFile.load();
		TabellenMethoden.init();
		try {
			TabellenMethoden.getBrotList();
			TabellenMethoden.getHVList();
		} catch (Exception e) {}
		
		//Commands
		new MarryRP_Command();
		new Brot_Command();
		new HV_Command();
	}

	@Override
	public void loadConfig() {
		if(!getConfig().has("hv.Enabled")) {
			getConfig().addProperty("hv.Enabled", true);
			getConfig().addProperty("hv.Prefix", "&8[&cHV&8]");
			saveConfig();
		}
		if(!getConfig().has("bread.Enabled")) {
			getConfig().addProperty("bread.Enabled", true);
			getConfig().addProperty("bread.Prefix", "&8[&2X&8]");
			saveConfig();
		}
		hv = getConfig().get("hv.Enabled").getAsBoolean();
		bread = getConfig().get("bread.Enabled").getAsBoolean();
		hvPrefix = getConfig().get("hv.Prefix").getAsString();
		breadprefix = getConfig().get("bread.Prefix").getAsString();
		
		saveConfig();
	}
	
	
	@Override
	protected void fillSettings(List<SettingsElement> element) {
		element.add(new HeaderElement(ModColor.cl('c') + "HV Settings"));
		element.add(new BooleanElement("Hausverbot Enabled", new ControlElement.IconData(Material.BARRIER), new Consumer<Boolean>() {
			
			@Override
			public void accept(Boolean bool) {
				hv = bool;
				getConfig().addProperty("hv.Enabled", bool);
				saveConfig();
			}
		}, hv));
		
		element.add(new StringElement("Hausverbot Prefix", new ControlElement.IconData(Material.BARRIER), hvPrefix, new Consumer<String>() {

			@Override
			public void accept(String s) {
				hvPrefix = s;
				getConfig().addProperty("hv.Prefix", s);
				saveConfig();
			}
		}));
		
		element.add(new HeaderElement(ModColor.cl('2') + "Brotlist Settings"));
		
		element.add(new BooleanElement("Brotlist Enabled", new ControlElement.IconData(Material.BREAD), new Consumer<Boolean>() {
			
			@Override
			public void accept(Boolean bool) {
				hv = bool;
				getConfig().addProperty("bread.Enabled", bool);
				saveConfig();
			}
		}, hv));
		
		element.add(new StringElement("Brotlist Prefix", new ControlElement.IconData(Material.BREAD), breadprefix, new Consumer<String>() {

			@Override
			public void accept(String s) {
				hvPrefix = s;
				getConfig().addProperty("bread.Prefix", s);
				saveConfig();
			}
		}));
		
	}
	
}
