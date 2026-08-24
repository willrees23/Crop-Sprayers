package com.github.willrees23.sprayer;

import com.github.willrees23.CropSprayersPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

// the physical representation of a crop sprayer in the world
public class CropSprayerVisual {

    // Ticks for one full turn. Higher = slower.
    private static final int ROTATION_PERIOD_TICKS = 160; // 8 seconds

    // Ticks for one full up-down-up bob.
    private static final int BOB_PERIOD_TICKS = 80; // 4 seconds

    // How far the crop travels above and below its resting height, in blocks.
    private static final double BOB_AMPLITUDE = 0.15;

    /*
     * Armour stand head poses are plain entity metadata - the client snaps to
     * each new value instead of interpolating, so this has to run every tick to
     * look smooth. A display entity would interpolate and could update at a
     * fifth of this rate, but armour stands work all the way back to 1.8.
     */
    private static final int UPDATE_INTERVAL_TICKS = 1;

    /*
     * Distance from the armour stand's own location up to where its head item
     * renders, so the crop floats at the centre of the sprayer block rather
     * than above it. Tuned for a small armour stand; adjust to taste in game.
     */
    private static final double HEAD_HEIGHT = 0.7;

    /*
     * Wraps where both the spin and the bob are back to their starting phase,
     * so neither jumps. Unlike a display entity this does not need doubling for
     * the quaternion period - a head pose of 2*PI and one of 0 render
     * identically, and nothing interpolates between them.
     */
    private static final int CYCLE_TICKS = lcm(ROTATION_PERIOD_TICKS, BOB_PERIOD_TICKS);

    private final CropSprayer sprayer;

    @Getter
    private ArmorStand stand;

    private Location baseLocation;
    private BukkitTask animationTask;
    private int elapsedTicks;

    public CropSprayerVisual(CropSprayer sprayer) {
        this.sprayer = sprayer;
    }

    public void spawn() {
        if (stand != null && !stand.isDead()) return;

        // centre of the sprayer block, dropped so the HEAD lands on that centre
        baseLocation = sprayer.getLocation().clone().add(0.5, 0.5 - HEAD_HEIGHT, 0.5);
        World world = baseLocation.getWorld();
        if (world == null) return;

        stand = world.spawn(baseLocation, ArmorStand.class, spawned -> {
            spawned.setVisible(false);
            spawned.setGravity(false);
            spawned.setInvulnerable(true);
            spawned.setSmall(true);
            spawned.setBasePlate(false);
            spawned.setArms(false);
            spawned.setMarker(true);   // no hitbox, so players cannot hit or collide with it
            spawned.setPersistent(false);

            EntityEquipment equipment = spawned.getEquipment();
            if (equipment != null) {
                // No setHelmetDropChance here - drop chances are Mob-only and an
                // ArmorStand is a LivingEntity but not a Mob, so it throws.
                // Nothing can drop the helmet anyway: the stand is invulnerable
                // and a marker, and remove() never drops equipment.
                equipment.setHelmet(new ItemStack(sprayer.getCrop().getDisplayItem()));
            }
        });

        elapsedTicks = 0;
        applyPose();

        CropSprayersPlugin plugin = CropSprayersPlugin.getInstance();
        animationTask = plugin.getServer().getScheduler()
                .runTaskTimer(plugin, this::tick, UPDATE_INTERVAL_TICKS, UPDATE_INTERVAL_TICKS);
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

    private void tick() {
        if (stand == null || stand.isDead()) {
            despawn();
            return;
        }

        elapsedTicks = (elapsedTicks + UPDATE_INTERVAL_TICKS) % CYCLE_TICKS;
        applyPose();
    }

    private void applyPose() {
        double angle = 2 * Math.PI * elapsedTicks / ROTATION_PERIOD_TICKS;
        double bob = BOB_AMPLITUDE * Math.sin(2 * Math.PI * elapsedTicks / BOB_PERIOD_TICKS);

        // spin: rotating the head pose turns the worn item with it
        stand.setHeadPose(new EulerAngle(0, angle, 0));

        // bob: the client smooths entity movement, so teleporting reads as glide
        stand.teleport(baseLocation.clone().add(0, bob, 0));
    }

    private static int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
