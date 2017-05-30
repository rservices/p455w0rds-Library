package p455w0rdslib;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import p455w0rdslib.entity.EntitySittableBlock;

/**
 * @author p455w0rd
 *
 */
public class LibEntities {

	private static int entityId = 0;

	private static int nextID() {
		return entityId++;
	}

	public static void init() {
		EntityRegistry.registerModEntity(new ResourceLocation(LibGlobals.MODID, "sitting_entity"), EntitySittableBlock.class, "ArmorStand", nextID(), P455w0rdsLib.INSTANCE, 80, 3, false);
	}

}
