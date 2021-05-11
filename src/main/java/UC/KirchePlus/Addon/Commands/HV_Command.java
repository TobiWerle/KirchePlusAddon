package UC.KirchePlus.Addon.Commands;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import UC.KirchePlus.Addon.Events.Displayname;
import UC.KirchePlus.Addon.Utils.HV_User;
import UC.KirchePlus.Addon.Utils.PlayerCheck;
import UC.KirchePlus.Addon.Utils.TabellenMethoden;
import UC.KirchePlus.Addon.main.main;
import net.labymod.api.events.MessageSendEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class HV_Command {
	
	
	public HV_Command() {
		main.main.api.getEventManager().register(new MessageSendEvent() {

			@Override
			public boolean onSend(String arg) {
				String[] args = arg.split(" ");
				if(args[0].equalsIgnoreCase("/hv")) {
					if(args.length == 1) {
						try {
							TabellenMethoden.getHVList();
						} catch (IOException | GeneralSecurityException e1) {}
						Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.AQUA + "Die Hausverbote wurden synchronisiert!"));
					}
					
					if(args.length == 2) {
						if(args[1].equalsIgnoreCase("help")) {
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv " + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "synchronisiere die Hausverbote mit dem Client."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv list" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Gibt eine Liste mit allen Spielern aus, die Hausverbot haben."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv namecheck" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Überprüft ob es Fehler bei den eingetragenen Spielernamen gibt."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv info <User>" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Zeigt die Hausverbot-Infos zum Spieler."));
						}else
						if(args[1].equalsIgnoreCase("list")) {
							displayMessage(new TextComponentString(
									TextFormatting.DARK_BLUE + "[]"+
									TextFormatting.DARK_AQUA + "========"+
									TextFormatting.GRAY + "["+
									TextFormatting.AQUA + "Hausverbot"+
									TextFormatting.GRAY + "]"+
									TextFormatting.DARK_AQUA + "========"+
									TextFormatting.DARK_BLUE + "[]"));
							ArrayList<String> online = new ArrayList<String>();
							for(String name : Displayname.HVs.keySet()) {
								if(!isOnline(name)) {
									if(!TabellenMethoden.isDayOver(Displayname.HVs.get(name).getBis())) {
										Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - "+name));	
									}else {
										Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - "+ TextFormatting.RED +name));
									}
								}else online.add(name);
							}
							for(String name : online) {
								if(!TabellenMethoden.isDayOver(Displayname.HVs.get(name).getBis())) {
									Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.GREEN + " - " +name));
								}else {
									Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.GREEN + " - " +TextFormatting.RED +name));
								}
							}
							return true;
						}else
						if(args[1].equalsIgnoreCase("namecheck")){
							ArrayList<String> nameError = new ArrayList<>();
							for(String name : Displayname.HVs.keySet()) {
								if(!isOnline(name)) {
									if(PlayerCheck.checkName(name) == false) nameError.add(name);
								}
							}
							if(nameError.size() == 0) {
								Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.BLUE + " - Es wurden keine Spieler mit fehlerhaften Namen gefunden!"));
							}else Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - Spieler mit Fehler im Namen:"));
							for(String player : nameError){
								Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - "+ TextFormatting.RED +player));
							}
						}else
						if(args[1].equalsIgnoreCase("info")) {
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv info <User>" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Zeigt die Hausverbot-Infos zum Spieler."));
							return true;
						}else {
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv " + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "synchronisiere die Hausverbote mit dem Client."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv list" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Gibt eine Liste mit allen Spielern aus, die Hausverbot haben."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/hv info <User>" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Zeigt die Hausverbot-Infos zum Spieler."));
						}
					}
					if(args.length == 3) {
						if(args[1].equalsIgnoreCase("info")) {
							for(HV_User users : Displayname.HVs.values()) {
								if(args[2].equalsIgnoreCase(users.getName())) {
									displayMessage(new TextComponentString(
											TextFormatting.DARK_BLUE + "[]"+
											TextFormatting.DARK_AQUA + "========"+
											TextFormatting.GRAY + "["+
											TextFormatting.AQUA + "Hausverbot"+
											TextFormatting.GRAY + "]"+
											TextFormatting.DARK_AQUA + "========"+
											TextFormatting.DARK_BLUE + "[]"));
									displayMessage(new TextComponentString(""));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Wer: " + TextFormatting.RED + users.getName()));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Von: " + TextFormatting.RED + users.getVon()));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Grund: " + TextFormatting.RED + users.getGrund()));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Wann: " + TextFormatting.RED + users.getWann()));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Bis: " + TextFormatting.RED + users.getBis()));
									displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " -" + TextFormatting.GRAY +" Dauer: " + TextFormatting.RED + users.getDauer() + " Wochen"));
									displayMessage(new TextComponentString(""));
									return true;
								}
							}	
						}
					}	
				}
				return true;
			}
			
		});
	}




	private void displayMessage(TextComponentString text) {
		Minecraft.getMinecraft().player.sendMessage(text);
	}
	private boolean isOnline(String Playername) {
		if(Minecraft.getMinecraft().getConnection().getPlayerInfo(Playername) != null) {
			return true;
		}
		return false;
	}

}
