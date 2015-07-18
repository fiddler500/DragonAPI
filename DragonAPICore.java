/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.DragonAPI;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import net.minecraftforge.common.ForgeVersion;
import Reika.DragonAPI.Exception.MisuseException;
import Reika.DragonAPI.Instantiable.Event.Client.GameFinishedLoadingEvent;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.Java.ReikaObfuscationHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DragonAPICore {

	protected DragonAPICore() {throw new MisuseException("The class "+this.getClass()+" cannot be instantiated!");}

	protected static final Random rand = new Random();

	public static final String last_API_Version = "@MAJOR_VERSION@"+"@MINOR_VERSION@";

	public static boolean debugtest = false;

	private static boolean loaded;

	private static final String MINFORGE = "required-after:Forge@[10.13.0.1291,);"; //was 1205/1231
	public static final String dependencies = MINFORGE+"after:BuildCraft|Energy;after:IC2;after:ThermalExpansion;after:Thaumcraft;"+
			"after:powersuits;after:GalacticCraft;after:Mystcraft;after:UniversalElectricity;after:Forestry;after:MagicBees;"+
			"after:BinnieCore;after:Natura;after:TConstruct;after:ProjRed|Core;after:bluepower;after:Waila";

	public static final String FORUM_PAGE = "http://www.minecraftforum.net/topic/1969694-";
	public static final UUID Reika_UUID = UUID.fromString("e5248026-6874-4954-9a02-aa8910d08f31");

	public static URL getReikaForumPage() {
		try {
			return new URL(FORUM_PAGE);
		}
		catch (MalformedURLException e) {
			ReikaJavaLibrary.pConsole("The mod provided a malformed URL for its documentation site!");
			e.printStackTrace();
			return null;
		}
	}

	public static final boolean hasAllClasses() {
		return true;
	}

	public static File getMinecraftDirectory() {
		return (File)FMLInjectionData.data()[6];
	}

	public static String getMinecraftDirectoryString() {
		String s = getMinecraftDirectory().getAbsolutePath();
		if (s.endsWith("/.") || s.endsWith("\\.")) {
			s = s.substring(0, s.length()-2);
		}
		return s;
	}

	public static boolean isReikasComputer() {
		return true;
	}

	protected static Side getSide() {
		return FMLCommonHandler.instance().getEffectiveSide();
	}

	public static boolean isOnActualServer() {
		return getSide() == Side.SERVER && FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer();
	}

	public static boolean isSinglePlayer() {
		return getSide() == Side.SERVER && !FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer();
	}

	public static void debugPrint(Object o) {
		ReikaJavaLibrary.pConsole(o);
		if (!ReikaObfuscationHelper.isDeObfEnvironment())
			Thread.dumpStack();
	}

	public static class DragonAPILoadWatcher {

		public static final DragonAPILoadWatcher instance = new DragonAPILoadWatcher();

		private DragonAPILoadWatcher() {

		}

		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void load(GameFinishedLoadingEvent evt) {
			loaded = true;
		}

	}

	public static boolean hasGameLoaded() {
		return loaded;
	}
}
