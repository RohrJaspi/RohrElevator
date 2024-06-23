package dev.rohrjaspi.elevator.listeners;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.player.PlotPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ElevatorListener implements Listener {
	@EventHandler
	public void onElevator(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		PlotPlayer pp = (new PlotAPI()).wrapPlayer(p.getUniqueId());
		if (pp.getCurrentPlot() != null &&
				p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.DAYLIGHT_DETECTOR) && e.getFrom().getY() != e.getTo().getY()) {
			DaylightDetector sensor = (DaylightDetector) p.getLocation().getBlock().getRelative(BlockFace.DOWN).getBlockData();
			if (sensor.isInverted()) {
				if (p.hasPermission("RohrElevator.elevator.bypass") || pp.getCurrentPlot().isOwner(p.getUniqueId()) || pp.getCurrentPlot().getTrusted().contains(p.getUniqueId()) || pp.getCurrentPlot().getMembers().contains(p.getUniqueId()))
					(new ElevatorManager()).elevatorUp(p);
			} else {
				(new ElevatorManager()).elevatorUp(p);
			}
		}
	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		PlotPlayer pp = (new PlotAPI()).wrapPlayer(p.getUniqueId());
		if (pp.getCurrentPlot() != null &&
				p.getWorld().getBlockAt(p.getLocation()).getType().equals(Material.DAYLIGHT_DETECTOR) && p.isSneaking()) {
			DaylightDetector sensor = (DaylightDetector)p.getWorld().getBlockAt(p.getLocation()).getBlockData();
			if (sensor.isInverted()) {
				if (p.hasPermission("RohrElevator.elevator.bypass") || pp.getCurrentPlot().isOwner(p.getUniqueId()) || pp.getCurrentPlot().getTrusted().contains(p.getUniqueId()) || pp.getCurrentPlot().getMembers().contains(p.getUniqueId()))
					(new ElevatorManager()).elevatorDown(p);
			} else {
				(new ElevatorManager()).elevatorDown(p);
			}
		}
	}

	@EventHandler
	public void onElevator(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		PlotPlayer pp = (new PlotAPI()).wrapPlayer(p.getUniqueId());
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK &&
				e.getClickedBlock() != null) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.DAYLIGHT_DETECTOR && pp.getCurrentPlot() != null) {
				if (!pp.getCurrentPlot().isOwner(p.getUniqueId())) {
					e.setCancelled(true);
					return;
				}
				DaylightDetector sensor = (DaylightDetector)b.getBlockData();
				if (sensor.isInverted()) {
					p.sendTitle("§8» §cÖffentlich", "§7Der Aufzug ist jetzt §cÖffentlich" , 0, 20, 0);
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				} else {
					p.sendTitle("§8» §cPrivat", "§7Der Aufzug ist jetzt §cPrivat" , 0, 20, 0);
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				}
			}
		}
	}
}