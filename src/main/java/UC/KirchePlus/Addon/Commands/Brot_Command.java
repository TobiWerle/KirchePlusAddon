package UC.KirchePlus.Addon.Commands;



import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import UC.KirchePlus.Addon.Events.Displayname;
import UC.KirchePlus.Addon.Utils.Brot_User;
import UC.KirchePlus.Addon.Utils.PlayerCheck;
import UC.KirchePlus.Addon.Utils.TabellenMethoden;
import UC.KirchePlus.Addon.main.main;
import net.labymod.api.events.MessageSendEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Brot_Command {
	
	public Brot_Command() {
		main.main.api.getEventManager().register(new MessageSendEvent() {

			@Override
			public boolean onSend(String arg) {
				String[] args = arg.split(" ");
				
				if(args[0].equalsIgnoreCase("/brot")) {
					if(args.length == 1) {
						Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.GRAY + " Die Brotliste wird synchronisiert! Dies könnte paar Sekunden dauern!"));
						Thread thread = new Thread(){
							@Override
							public void run() {
								try {
									TabellenMethoden.getBrotList();
								} catch (IOException | GeneralSecurityException e1) {}
								Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.AQUA + "Die Brotliste wurde synchronisiert!"));
							}
						};
						thread.start();
					}
					
					if(args.length == 2) {
						if(args[1].equalsIgnoreCase("help")) {
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/brot " + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "synchronisiere die Brotliste mit dem Client."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/brot list" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Gibt eine Liste mit allen Spielern aus, die Brot bekommen haben."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/brot namecheck" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Überprüft ob es Fehler bei den eingetragenen Spielernamen gibt."));
							displayMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - " + TextFormatting.AQUA + "/brot info <User>" + TextFormatting.DARK_GRAY + "-> " + TextFormatting.GRAY + "Zeigt die Brot-Infos zum Spieler."));
						}else
						if(args[1].equalsIgnoreCase("list")) {
							displayMessage(new TextComponentString(
									TextFormatting.DARK_BLUE + "[]"+
									TextFormatting.GREEN + "========"+
									TextFormatting.GRAY + "["+
									TextFormatting.GOLD + "Brotliste"+
									TextFormatting.GRAY + "]"+
									TextFormatting.GREEN + "========"+
									TextFormatting.DARK_BLUE + "[]"));
							
							ArrayList<String> online = new ArrayList<String>();
					
							for(String name : Displayname.BrotUser.keySet()) {
								if(!isOnline(name)) {
									Brot_User user = Displayname.BrotUser.get(name);
									String color = " §a";
									if(!TabellenMethoden.isSameDay(user.getDatum())) color = " §c";
									Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.DARK_GRAY + " - "+name + color + user.getDatum()));
								}else online.add(name);
							}
							for(String name : online) {
								Brot_User user = Displayname.BrotUser.get(name);
								String color = " §a";
								if(!TabellenMethoden.isSameDay(user.getDatum())) color = " §c";
								Minecraft.getMinecraft().player.sendMessage(new TextComponentString(color+ " - "+name + color + user.getDatum()));
							}
						}else
						if(args[1].equalsIgnoreCase("namecheck")) {
							Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.GRAY + " NameCheck wird ausgeführt! Dies könnte paar Sekunden dauern!"));
							Thread thread = new Thread() {
								@Override
								public void run() {
									ArrayList<String> nameError = new ArrayList<>();
									for(String name : Displayname.BrotUser.keySet()) {
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
								}
							};
							thread.start();
						}
					}
					if(args.length == 3) {
						if(args[1].equalsIgnoreCase("info")) {
							for(Brot_User users : Displayname.BrotUser.values()) {
								if(args[2].equalsIgnoreCase(users.getEmpfänger())) {
									displayMessage(new TextComponentString(
											TextFormatting.DARK_BLUE + "[]"+
											TextFormatting.GREEN + "========"+
											TextFormatting.GRAY + "["+
											TextFormatting.GOLD + "Brotliste"+
											TextFormatting.GRAY + "]"+
											TextFormatting.GREEN + "========"+
											TextFormatting.DARK_BLUE + "[]"));
									displayMessage(new TextComponentString(""));
									displayMessage(new TextComponentString(TextFormatting.DARK_AQUA + " -" + TextFormatting.GOLD +" Empfänger: " + TextFormatting.GREEN + users.getEmpfänger()));
									displayMessage(new TextComponentString(TextFormatting.DARK_AQUA + " -" + TextFormatting.GOLD +" Member: " + TextFormatting.GREEN + users.getMember()));
									String color = "§a";
									if(!TabellenMethoden.isSameDay(users.getDatum())) color = " §c";
									displayMessage(new TextComponentString(TextFormatting.DARK_AQUA + " -" + TextFormatting.GOLD +" Datum: " + color + users.getDatum()));
									displayMessage(new TextComponentString(""));
									return true;
								}
							}	
						}
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
	private boolean isOnline(String Playername) {
		if(Minecraft.getMinecraft().getConnection().getPlayerInfo(Playername) != null) {
			return true;
		}
		return false;
	}
}
