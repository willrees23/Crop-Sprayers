package com.github.willrees23.sprayer.visuals;

import com.github.willrees23.CropSprayersPlugin;
import com.github.willrees23.sprayer.CropSprayer;
import com.github.willrees23.util.CustomHeadUtil;
import com.github.willrees23.util.MathsUtil;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

// the floating crop head above a sprayer: an invisible armour stand wearing the
// crop's custom head, spun and bobbed on a repeating task
public class ArmorStandVisual {

    // Ticks for one full turn. Higher = slower
    private static final int ROTATION_PERIOD_TICKS = 160; // 8 seconds

    // Ticks for one full up-down-up bob
    private static final int BOB_PERIOD_TICKS = 80; // 4 seconds

    // How far the crop travels above and below its resting height, in blocks
    private static final double BOB_AMPLITUDE = 0.15;

    // Interval at which to update the animation
    private static final int UPDATE_INTERVAL_TICKS = 1;

    // Height of the armour stand's head, used to position the crop correctly
    private static final double HEAD_HEIGHT = 0.7;

    // number of ticks after which both the spin & bob are back to the start
    private static final int CYCLE_TICKS = MathsUtil.lcm(ROTATION_PERIOD_TICKS, BOB_PERIOD_TICKS);

    private final CropSprayer sprayer;

    @Getter
    private ArmorStand stand;

    private Location baseLocation;
    private BukkitTask animationTask;
    private int elapsedTicks;

    public ArmorStandVisual(CropSprayer sprayer) {
        this.sprayer = sprayer;
    }

    // starts the animation. the stand itself is created by the first tick that
    // finds its chunk loaded, which may be immediately or much later
    public void spawn() {
        if (animationTask != null) return;

        // centre of the sprayer block, dropped so the HEAD lands on that centre
        baseLocation = sprayer.getLocation().getBlock().getLocation().clone().add(0.5, 0, 0.5);
        if (baseLocation.getWorld() == null) return;

        elapsedTicks = 0;

        CropSprayersPlugin plugin = CropSprayersPlugin.getInstance();
        animationTask = plugin.getServer().getScheduler()
                .runTaskTimer(plugin, this::tick, 0L, UPDATE_INTERVAL_TICKS);
    }

    private void createStand() {
        World world = baseLocation.getWorld();
        if (world == null) return;

        stand = world.spawn(baseLocation, ArmorStand.class, spawned -> {
            spawned.setVisible(false);
            spawned.setGravity(false);
            spawned.setInvulnerable(true);
            spawned.setSmall(true);
            spawned.setBasePlate(false);
            spawned.setArms(false);
            spawned.setMarker(true);
            spawned.setPersistent(false);

            EntityEquipment equipment = spawned.getEquipment();
            if (equipment != null) {
                ItemStack item = CustomHeadUtil.createCustomSkull(sprayer.getCrop().getDisplayTexture());
                equipment.setHelmet(item);
            }
        });
    }

    public void despawn() {
        if (animationTask != null) {
            animationTask.cancel();
            animationTask = null;
        }
        if (stand != null) {
            stand.remove();
            stand = null;
        }
    }

    // where the head currently sits, for anything that wants to emit from it
    public Location getHeadLocation() {
        return stand == null ? null : stand.getLocation();
    }

    private void tick() {
        if (baseLocation == null) return;

        if (stand == null || stand.isDead()) {
            if (!isChunkLoaded()) return;

            createStand();
            if (stand == null) return;
        }

        elapsedTicks = (elapsedTicks + UPDATE_INTERVAL_TICKS) % CYCLE_TICKS;
        applyPose();
    }

    private boolean isChunkLoaded() {
        World world = baseLocation.getWorld();
        if (world == null) return false;

        return world.isChunkLoaded(baseLocation.getBlockX() >> 4, baseLocation.getBlockZ() >> 4);
    }

    private void applyPose() {
        double angle = 2 * Math.PI * elapsedTicks / ROTATION_PERIOD_TICKS;
        double bob = BOB_AMPLITUDE * Math.sin(2 * Math.PI * elapsedTicks / BOB_PERIOD_TICKS);

        // spin: rotating the head pose turns the worn item with it
        stand.setHeadPose(new EulerAngle(0, angle, 0));

        // bob: the client smooths entity movement, so teleporting reads as glide
        stand.teleport(baseLocation.clone().add(0, bob, 0));
    }
}
