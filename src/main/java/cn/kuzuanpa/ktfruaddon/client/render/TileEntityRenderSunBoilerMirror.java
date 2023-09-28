/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.client.render;

import cn.kuzuanpa.ktfruaddon.tile.parts.SunBoilerMirror;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;

public class TileEntityRenderSunBoilerMirror extends TileEntitySpecialRenderer {
    IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("ktfruaddon:models/sunboiler/mirror.obj"));
    public float rotateVertical =0;
    public float rotateHorizontal =0;
    ResourceLocation texture = new ResourceLocation("ktfruaddon:textures/model/sunboiler/mirror.png");

    private static int bodyList;

    public TileEntityRenderSunBoilerMirror() {
        GL11.glNewList(bodyList = GL11.glGenLists(1), GL11.GL_COMPILE);
        model.renderOnly("base");
        GL11.glEndList();
    }


    @Override
    public void renderTileEntityAt(TileEntity til, double x,
                                   double y, double z, float f) {
        if (! (til instanceof SunBoilerMirror)) return;
        SunBoilerMirror tile = (SunBoilerMirror)til;
        ChunkCoordinates targetTilePos = tile.targetSunBoilerPos;
        GL11.glPushMatrix();
        targetTilePos=new ChunkCoordinates(-425,117,671);
        //Initial setup
        int bright = tile.getWorldObj().getLightBrightnessForSkyBlocks(tile.xCoord, tile.yCoord + 1, tile.zCoord,0);
        int brightX = bright % 65536;
        int brightY = bright / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);

        try {


        long Ti=tile.getWorldObj().getWorldTime();
        double Xn=0,Yn=0,Zn=0,T=24000;
        while (Ti>24000) Ti-=24000;

        int X2A= targetTilePos.posX-tile.xCoord, Y2A= targetTilePos.posY-tile.yCoord, Z2A= targetTilePos.posZ-tile.zCoord;
        double L=Math.sqrt(X2A*X2A+Y2A*Y2A+Z2A*Z2A);
        double X2=X2A/L, Y2=Y2A/L, Z2=Z2A/L;
        double alpha=((4*Ti-T)/(4*T))*6.28,theta=0,phi=0;
        double X1=Math.cos(alpha),Y1=Math.sin(alpha);
        X1=X1==0?0.000001:X1;
        Y1=Y1==0?0.0000001:Y1;
        X2=X2==0?0.0000013:X2;
        Y2=Y2==0?0.0000012:Y2;
        Z2=Z2==0?0.0000011:Z2;
        Xn= (((Y1*X2)-(X1*Y2))/(Y1*Z2))+(((X1*Y2-Y1*X2)/Y1*Z2)+(Z2/(X1-X2)))/((Y1-Y2)/(X1-X2))+(X1/Y1);
        Yn=((((X1*Y2)-(Y1*X2))/(Y1*Z2))+(Z2/(X1-X2)))/(((Y1-Y2)/(X1-X2))+(X1/Y1));
        Zn=1;
        if(Yn<0) L=-L;
        Xn=Xn/L;
        Yn=Yn/L;
        Zn=Zn/L;
        phi=Math.acos(Yn);
            phi=phi/3.1415*180;
        theta=Math.asin(Zn/Math.sqrt(1-Yn*Yn));
            if(Xn<0) theta=-3.1415-theta;

            theta=theta/3.1415*180;
        rotateVertical= (float) phi;
        rotateHorizontal= (float) theta;
            FMLLog.log(Level.FATAL,"A"+Xn+Yn+Zn+"/"+L+"/"+Ti+"/"+alpha+"/"+theta+"/"+phi+"/"+rotateHorizontal+"/"+rotateVertical);
        }catch (Throwable t){t.printStackTrace();}
        //Rotate and move the model into position
        GL11.glTranslated(x, y, z );
        GL11.glTranslatef(0.5f, 0, 0.5f);
        ForgeDirection front = VALID_DIRECTIONS[tile.mFacing];
        GL11.glRotatef((front.offsetX == 1 ? 180 : 0) + front.offsetZ*90f, 0, 1, 0);
            bindTexture(texture);
            model.renderPart("base");

        GL11.glPushMatrix();
            if (rotateVertical >70) rotateVertical =70;
            if (rotateVertical <0) rotateVertical =0;

            GL11.glRotatef(-rotateHorizontal, 0, 1, 0);

            GL11.glTranslatef(0, 0.99f, 0);
            GL11.glRotatef(rotateVertical, 0, 0, 1);
            GL11.glTranslatef(0, -1.02f, 0);
            model.renderOnly("mirror");
        GL11.glPopMatrix();
        GL11.glPushMatrix();
            GL11.glRotatef(-rotateHorizontal, 0, 1, 0);
            model.renderOnly("rotate");

            int color;


        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glPopMatrix();
    }
}
