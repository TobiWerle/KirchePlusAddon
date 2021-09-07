package UC.KirchePlus.Addon.Events;

import java.util.ArrayList;
import java.util.HashMap;

import UC.KirchePlus.Addon.Utils.HV_User;
import UC.KirchePlus.Addon.Utils.TabellenMethoden;
import UC.KirchePlus.Addon.main.main;
import net.labymod.api.events.RenderEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class Displayname {
	
	public static ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
	
	public static HashMap<String, HV_User> HVs = new HashMap<String, HV_User>();

	
	public Displayname() {
		main.main.api.getEventManager().register(new RenderEntityEvent() {
			@Override
			public void onRender(Entity e, double f1, double f2, double f3, float f4) {
				if(e instanceof EntityPlayer) {
					boolean brot = false;
					boolean hv = false;
					if(HVs.containsKey(e.getName())) {
						if(!TabellenMethoden.isDayOver(HVs.get(e.getName()).getBis())) {
							hv = true;
						}
					}
				}
			}
		});
		
	}
	private static boolean isMasked(EntityPlayer ep){
		if(ScorePlayerTeam.formatPlayerName(ep.getTeam(), ep.getName()).contains("§k")){
			return true;
		}
		return false;
	}
	
}
