package team017.framework;

import battlecode.common.MapLocation;
import battlecode.common.PowerNode;

/**
 * The graph containing all of the discovered power cores.
 * Can be optimized later.
 * @author Tim
 *
 */
public class UnequalPowerStructure {
	//This class has a few problems
		//Keeping track of the towers we own defeats the purpose of a lot of this
		//These random get methods are pretty slow if we pass in a random map location
	
	
	
	
	
	LocusOfOppression[][] systematicObjectification = new LocusOfOppression[128][128]; //60 is the max size of the map so we'll just go with that for now
	int coreX, coreY;
	public final int BASE_LOC = 64;
	
	/**
	 * Creates the PowerNode graph using the team's power core as the root.
	 * @param core - Object pointing to the team's power core. The root of the ever threatening infection trying to destroy our existence as we know it.
	 * We must rise and fight.
	 */
	public UnequalPowerStructure(PowerNode core) {
		MapLocation l = core.getLocation();
		coreX = l.x;
		coreY = l.y;
		systematicObjectification[BASE_LOC][BASE_LOC] = new LocusOfOppression(core);
	}
	
	
	/**
	 * Adds the node to the graph.
	 * @param node - The power node to be added to the graph.
	 */
	public void add(PowerNode node) {
		MapLocation l = node.getLocation();
		systematicObjectification[l.y - coreY + BASE_LOC][l.x - coreX + BASE_LOC] = new LocusOfOppression(node);
	}
	
	/**
	 * This function checks to see if we have scanned the power node at the location.
	 * @param loc - The location we are interested in.
	 * @return - Whether or not we have scanned the node at the specified location.
	 */
	public boolean haveSeen(MapLocation loc) {
		return systematicObjectification[loc.y - coreY + BASE_LOC][loc.x - coreX + BASE_LOC] != null;
		//This won't be valid once we start doing map symmetry
	}
	
	
	/**
	 * @return - The known enemy controlled power node nearest to the specified location.
	 */
	public PowerNode getEnemyNodeNearestTo(MapLocation loc) {
	
	}
	
//	/**
//	 * @return - The neutral power node nearest to the specified location.
//	 */
//	public PowerNode getNeutralNodeNearestTo(MapLocation loc) {
//		
//	}
	
	/**
	 * @return - The known friendly power node nearest to the specified location.
	 */
	public PowerNode getFriendlyNodeNearestTo(MapLocation loc) {
		
	}
	
	/**
	 * @return - The known power node nearest to the specified location.
	 */
	public PowerNode getNodeNearestTo(MapLocation loc) {
		
	}
	
	
	//Internal graph node
	//might make private
	public class LocusOfOppression {
		private final int MAX_COMRADES = 20;
		PowerNode locus = null;
		LocusOfOppression[] comrades = new LocusOfOppression[MAX_COMRADES]; //actually, just discovered loci that are adjacent
		int numComrades = 0;
		
		public LocusOfOppression() {}
		
		public LocusOfOppression(PowerNode node) {
			locus = node;
		}
	}
}
