package UC.KirchePlus.Addon.Commands;

import UC.KirchePlus.Addon.Utils.MarryFile;
import UC.KirchePlus.Addon.Utils.MarryFile.types;
import UC.KirchePlus.Addon.main.main;
import net.labymod.api.events.MessageSendEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MarryRP_Command {

	public MarryRP_Command() {
		main.main.api.getEventManager().register(new MessageSendEvent() {
			
			@Override
			public boolean onSend(String arg) {
				String[] args = arg.split(" ");
				///marryrp mm Spieler1 Spieler2
				if(args[0].equalsIgnoreCase("/marryrp")) {
					if(args.length != 4) {
						displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/marryrp <Type> <Name1> <Name2>" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Marry RP Text auf deinen Desktop."));
						displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: MM" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Mann-Mann RP Text"));
						displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: FF" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Frau-Frau RP Text"));
						displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: MF" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Mann-Frau RP Text. (Name1 ist immer Mann)"));
					}
					if(args.length == 4) {
						types type = types.none;
						if(args[1].toLowerCase().equals("mm")) type = types.MM;
						if(args[1].toLowerCase().equals("mf")) type = types.MF;
						if(args[1].toLowerCase().equals("ff")) type = types.FF;
						if(type == types.none) {
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.RED + "Fehler bei den angegebenen Type. Verfügbare Typen:"));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: MM" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Mann-Mann RP Text"));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: FF" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Frau-Frau RP Text"));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.GRAY + "Type: MF" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Erstelle ein Mann-Frau RP Text. (Name1 ist immer Mann)"));
							return true;
						}		
						String name1 = args[2];
						String name2 = args[3];
						
						MarryFile.createMarryRP(type, name1, name2);
						displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "Du hast erfolgreich ein MarryRP Text für "+ TextFormatting.GREEN + name1 
								+ TextFormatting.AQUA + " und " +TextFormatting.GREEN + name2 + TextFormatting.AQUA +" erstellt"));
					}
					return true;
				}
				return false;
			}
		});
	}
	
	private void displayMessage(TextComponentString text) {
		Minecraft.getMinecraft().player.sendMessage(text);
	}
}
