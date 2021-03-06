package p455w0rdslib.util;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.FMLCommonHandler;
import p455w0rdslib.capabilities.CapabilityChunkLoader;

/**
 * @author p455w0rd
 *
 */
public class ChunkUtils {

	//private static final ChunkUtils INSTANCE = new ChunkUtils();

	private ChunkUtils() {
	}

	/**
	 * @param modInstance - And instance of your main mod class
	 */
	public static void register(Object modInstance) {
		ForgeChunkManager.setForcedChunkLoadingCallback(modInstance, Callback.getInstance());
	}

	public static ChunkPos getChunkPos(World world, BlockPos pos) {
		return world.getChunkFromBlockCoords(pos).getChunkCoordIntPair();
	}

	public static class Callback implements LoadingCallback {

		private static final Callback INSTANCE = new Callback();

		private Callback() {
		}

		/**
		 * Used as part of the Chunk Loader API for a simple Chunk Loading Callback<br>
		 * There is no real reason to <b>not</b> use this
		 */
		public static Callback getInstance() {
			return INSTANCE;
		}

		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world) {
			for (Ticket ticket : tickets) {
				int x = ticket.getModData().getInteger("xCoord");
				int y = ticket.getModData().getInteger("yCoord");
				int z = ticket.getModData().getInteger("zCoord");
				BlockPos pos = new BlockPos(x, y, z);
				TileEntity te = world.getTileEntity(pos);
				if (te != null && te.hasCapability(CapabilityChunkLoader.CAPABILITY_CHUNKLOADER_TE, null)) {
					TicketHandler handler = new TicketHandler();
					FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
						handler.load(world, pos, ticket);
					});
				}
			}
		}

	}

	public static class TicketHandler {

		private static final TicketHandler INSTANCE = new TicketHandler();

		private TicketHandler() {
		}

		public static TicketHandler getInstance() {
			return INSTANCE;
		}

		public Ticket getTicket(Object modInstnace, World world) {
			return ForgeChunkManager.requestTicket(modInstnace, world, ForgeChunkManager.Type.NORMAL);
		}

		public void load(World world, BlockPos pos, Ticket ticket) {
			if (world != null && !world.isRemote && pos != null) {
				if (!ForgeChunkManager.getPersistentChunksFor(world).containsKey(ChunkUtils.getChunkPos(world, pos))) {
					ticket.getModData().setInteger("xCoord", pos.getX());
					ticket.getModData().setInteger("yCoord", pos.getY());
					ticket.getModData().setInteger("zCoord", pos.getZ());
					ForgeChunkManager.forceChunk(ticket, ChunkUtils.getChunkPos(world, pos));
				}
			}
		}

		public void unload(World world, BlockPos pos, Ticket ticket) {
			if (world != null && !world.isRemote && pos != null) {
				if (ForgeChunkManager.getPersistentChunksFor(world).containsKey(ChunkUtils.getChunkPos(world, pos))) {
					ForgeChunkManager.unforceChunk(ticket, ChunkUtils.getChunkPos(world, pos));
					ForgeChunkManager.releaseTicket(ticket);
				}
			}
		}
	}

}
