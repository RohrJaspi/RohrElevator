package dev.rohrjaspi.elevator.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ElevatorManager {
  private boolean isPrivateElevator(Block b) {
    if (b.getType().equals(Material.LEGACY_DAYLIGHT_DETECTOR_INVERTED))
      return true; 
    return false;
  }
  
  private int calculateFloor(Player p) {
    Location to = p.getLocation();
    to.setY(-63.0D);
    int count = -1;
    for (int i = -63; i < p.getLocation().getY(); i++) {
      if (to.getBlock().getType() == Material.DAYLIGHT_DETECTOR)
        count++; 
      to.add(0.0D, 1.0D, 0.0D);
    } 
    return count;
  }
  
  private Location getElevatorAbove(Player p) {
    Location to = p.getLocation();
    for (int i = to.getBlockY(); i < to.getWorld().getMaxHeight(); i++) {
      if (to.getBlock().getType() == Material.DAYLIGHT_DETECTOR)
        return new Location(p.getWorld(), p.getLocation().getX(), (to.getBlockY() + 1), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()); 
      to.add(0.0D, 1.0D, 0.0D);
    } 
    return p.getLocation();
  }
  
  private Location getElevatorBelow(Player p) {
    Location to = p.getWorld().getBlockAt(p.getLocation()).getLocation();
    to.setY((to.getBlockY() - 1));
    for (int i = to.getBlockY(); i > -63; i--) {
      if (to.getBlock().getType() == Material.DAYLIGHT_DETECTOR)
        return new Location(p.getWorld(), p.getLocation().getX(), (to.getBlockY() + 1), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()); 
      to.setY(i);
    } 
    return p.getLocation();
  }
  
  public void elevatorUp(Player p) {
    if (p.getLocation().equals(getElevatorAbove(p)))
      return;
    p.teleport(getElevatorAbove(p).subtract(0.0D, 0.5D, 0.0D));
    p.sendTitle("§a▲", (calculateFloor(p) == 0) ? "§7Erdgeschoss" : ("Etage §a"+ calculateFloor(p)), 5, 5, 5);
    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
  }
  
  public void elevatorDown(Player p) {
    if (p.getLocation().equals(getElevatorBelow(p)))
      return; 
    p.teleport(getElevatorBelow(p).subtract(0.0D, 0.5D, 0.0D));
    p.sendTitle("§c▼", (calculateFloor(p) == 0) ? "§7Erdgeschoss" : ("Etage §a"+ calculateFloor(p)), 5, 5, 5);
    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 0.5F);
  }
}
