package com.warring.library.particles;

import com.warring.library.WarringPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Particle
{
    private ParticleEffect particleName;
    private Location loc;
    private float[] data;
    private int amount;
    private Player player;

    public Particle(Player player, Location loc, ParticleEffect particleName, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        this.player = player;
        this.loc = loc.add(new Vector(0.5, 0.5, 0.5));
        this.particleName = particleName;
        (this.data = new float[4])[0] = offsetX;
        this.data[1] = offsetY;
        this.data[2] = offsetZ;
        this.data[3] = speed;
        this.amount = amount;
    }

    public static void create(Player player, Location loc, ParticleEffect particleName, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean repeating) {
        Particle particle = new Particle(player, loc, particleName, offsetX, offsetY, offsetZ, speed, amount);
        if (repeating) {
            particle.start();
        }
        else {
            particle.display();
        }
    }

    private void start() {
        new BukkitRunnable() {
            public void run() {
                try {
                    if (Particle.this.loc.getWorld().getPlayers().isEmpty()) {
                        return;
                    }
                    if (Particle.this.player.getLocation().distance(Particle.this.loc) <= 16.0) {
                        Particle.this.particleName.display(Particle.this.data[0], Particle.this.data[1], Particle.this.data[2], Particle.this.data[3], Particle.this.amount, Particle.this.loc, Particle.this.player);
                    }
                }
                catch (Exception ex) {}
            }
        }.runTaskTimerAsynchronously(WarringPlugin.getInstance(), 5L, 5L);
    }

    private void display() {
        try {
            if (this.loc.getWorld().getPlayers().isEmpty()) {
                return;
            }
            if (this.player.getLocation().distance(this.loc) <= 16.0) {
                this.particleName.display(this.data[0], this.data[1], this.data[2], this.data[3], this.amount, this.loc, this.player);
            }
        }
        catch (Exception ex) {}
    }
}

