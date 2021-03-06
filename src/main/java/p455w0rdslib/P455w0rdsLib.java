package p455w0rdslib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import p455w0rdslib.proxy.CommonProxy;

/**
 * @author p455w0rd
 *
 */
@Mod(modid = LibGlobals.MODID, name = LibGlobals.NAME, version = LibGlobals.VERSION, dependencies = LibGlobals.DEPENDENCIES, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.9.4,1.10.2]")
public class P455w0rdsLib {

	@SidedProxy(clientSide = LibGlobals.CLIENT_PROXY, serverSide = LibGlobals.SERVER_PROXY)
	public static CommonProxy PROXY;

	@Instance(LibGlobals.MODID)
	public static P455w0rdsLib INSTANCE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		INSTANCE = this;
		PROXY.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		PROXY.init(e);
	}

}
