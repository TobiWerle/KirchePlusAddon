package UC.KirchePlus.Addon.Utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import UC.KirchePlus.Addon.main.main;
import net.labymod.main.LabyMod;
import net.labymod.user.User;
import net.labymod.user.group.EnumGroupDisplayType;
import net.labymod.utils.ModColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class DisplayStringAbovePlayer {
	
	
	
	public static void draw(Entity entity, float ticks, boolean hv, boolean brot) {
    	try {
    		if(entity.getDistance(Minecraft.getMinecraft().player) >= 100.0D) {
    			return;
    		}
    		FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
    		double renderPosX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
            double renderPosY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
            double renderPosZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;
            
            double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks) - renderPosX;
            double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks)  + entity.height / 2.0f - renderPosY;
            double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks) - renderPosZ;
            
            User user = (entity instanceof EntityPlayer) ? LabyMod.getInstance().getUserManager().getUser(entity.getUniqueID()) : null;
            float maxNameTagHeight = (user == null || !(LabyMod.getSettings()).cosmetics) ? 0.0F : user.getMaxNameTagHeight();
            boolean displayRank = (user != null && user.getGroup() != null && user.getGroup().getDisplayType() == EnumGroupDisplayType.ABOVE_HEAD);
            yPos += maxNameTagHeight;
            
            
            float playerViewY = Minecraft.getMinecraft().getRenderManager().playerViewY;
            float playerViewX = Minecraft.getMinecraft().getRenderManager().playerViewX;
            boolean thirdPersonView = Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2;
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GL11.glPushMatrix();            
            GlStateManager.translate(xPos, yPos+1.7, zPos);
            GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate((float)(thirdPersonView ? -1 : 1) * playerViewX, 1.0F, 0.0F, 0.0F);
            
            
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            
            
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false); 
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_TEXTURE_2D);          
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            
            
            String HVPrefix = "";
            String BrotPrefix = "";
            
            
            
            if(brot == true) {
            	if(main.bread == true) {
            		BrotPrefix = ModColor.createColors(main.breadprefix);
            	}
            }
            if(hv == true) {
            	if(main.hv == true) {
            		HVPrefix = ModColor.createColors(main.hvPrefix);
            	}
            }
            
            if(!entity.isSneaking()) {
            	fontrenderer.drawString(HVPrefix+BrotPrefix, -fontrenderer.getStringWidth(HVPrefix+BrotPrefix) / 2, (float)yPos, Color.white.getRGB(), true);
            }
   
            
           
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
           
            
            
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
        } catch (Exception exception) {}
    }

}
