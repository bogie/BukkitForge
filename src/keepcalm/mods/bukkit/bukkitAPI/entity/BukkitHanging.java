package keepcalm.mods.bukkit.bukkitAPI.entity;

import keepcalm.mods.bukkit.bukkitAPI.BukkitServer;
import net.minecraft.entity.EntityHanging;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

public class BukkitHanging extends BukkitEntity implements Hanging {
    public BukkitHanging(BukkitServer server, EntityHanging entity) {
        super(server, entity);
    }

    public BlockFace getAttachedFace() {
        return getFacing().getOppositeFace();
    }

    public void setFacingDirection(BlockFace face) {
        setFacingDirection(face, false);
    }

    public boolean setFacingDirection(BlockFace face, boolean force) {
        Block block = getLocation().getBlock().getRelative(getAttachedFace()).getRelative(face.getOppositeFace()).getRelative(getFacing());
        EntityHanging hanging = getHandle();
        int x = hanging.xPosition, y = hanging.yPosition, z = hanging.zPosition, dir = hanging.hangingDirection;
        hanging.xPosition = block.getX();
        hanging.yPosition = block.getY();
        hanging.zPosition = block.getZ();
        switch (face) {
            case SOUTH:
            default:
                getHandle().setDirection(0);
                break;
            case WEST:
                getHandle().setDirection(1);
                break;
            case NORTH:
                getHandle().setDirection(2);
                break;
            case EAST:
                getHandle().setDirection(3);
                break;
        }
        if (!force && !hanging.isEntityAlive()) {
            // Revert since it doesn't fit
            hanging.xPosition = x;
            hanging.yPosition = y;
            hanging.zPosition = z;
            hanging.setDirection(dir);
            return false;
        }
        return true;
    }

    public BlockFace getFacing() {
        switch (this.getHandle().hangingDirection) {
            case 0:
            default:
                return BlockFace.SOUTH;
            case 1:
                return BlockFace.WEST;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.EAST;
        }
    }

    @Override
    public EntityHanging getHandle() {
        return (EntityHanging) entity;
    }

    @Override
    public String toString() {
        return "BukkitHanging";
    }

    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
