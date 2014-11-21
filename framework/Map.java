package team017.framework;

import battlecode.common.Direction;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.TerrainTile;

public class Map {

	/*
	 * Works along side the RADAR class to store battlefied information However
	 * Map focuses on Terrain and Towers
	 */

	private BasePlayer bp;
	private RobotController rc;

	/*
	 * World coordinates are given as top left Xoff, Yoff, bottom right as
	 * MapWidth + Xoff, MapHeight + Yoff We will store true for a wall by its
	 * cache coordinates a long with edge data if we discover one our Cache is
	 * double the size of the world(60 max) so we won't have toroidal shit Our
	 * Home base will always be at cache coords 64,64
	 */
	static final int CACHE_SIZE = 128;
	static final int HOME_CACHE_COORD = 64;
	int homeWorldX, homeWorldY;

	// In cache coordinates, the first off map coord
	// Zero if unknown
	int edgeXMin, edgeXMax, edgeYMin, edgeYMax;

	boolean wall[][];
	boolean sensed[][];
	private int senseRadius;
	private int[][][] optimizedScanLocations;

	public Map(BasePlayer bp) {
		this.bp = bp;
		rc = bp.rc;

		homeWorldX = bp.home.x;
		homeWorldY = bp.home.y;

		wall = new boolean[CACHE_SIZE][CACHE_SIZE];
		sensed = new boolean[CACHE_SIZE][CACHE_SIZE];

		edgeXMin = 0;
		edgeXMax = 0;
		edgeYMin = 0;
		edgeYMax = 0;

		senseRadius = (int) Math.sqrt(bp.type.sensorRadiusSquared);

		switch (bp.type) {
		case ARCHON:
			optimizedScanLocations = sensorRangeARCHON;
			break;
		case DISRUPTER:
			break;
		case SCORCHER:
			break;
		case SCOUT:
			break;
		case SOLDIER:
			optimizedScanLocations = sensorRangeSOLDIER;
			break;
		case TOWER:
			break;
		default:
			break;
		}
	}

	public boolean isWall(int worldX, int worldY) {
		return wall[worldToCacheX(worldX)][worldToCacheY(worldY)];
	}

	public boolean isSensed(int worldX, int worldY) {
		return sensed[worldToCacheX(worldX)][worldToCacheY(worldY)];
	}

	public int cacheToWorldX(int cacheX) {
		return cacheX + homeWorldX - HOME_CACHE_COORD;
	}

	public int cacheToWorldY(int cacheY) {
		return cacheY + homeWorldY - HOME_CACHE_COORD;
	}

	public int worldToCacheX(int worldX) {
		return worldX - homeWorldX + HOME_CACHE_COORD;
	}

	public int worldToCacheY(int worldY) {
		return worldY - homeWorldY + HOME_CACHE_COORD;
	}

	/*
	 * Only to be called at start by Archons, senses all tiles in range Updates
	 * sensed and wall
	 */
	public void senseAllTiles() {
		MapLocation loc = bp.loc;
		int myX = worldToCacheX(loc.x);
		int myY = worldToCacheY(loc.y);

		MapLocation scanLoc;
		int x, y;
		TerrainTile tile;
		for (int dx = -senseRadius; dx <= senseRadius; dx++) {
			for (int dy = -senseRadius; dy <= senseRadius; dy++) {
				x = myX + dx;
				y = myY + dy;
				if (sensed[x][y])
					continue;
				scanLoc = loc.add(dx, dy);
				// If we try to scan something out of range, returns null like
				// behind soldiers and corners that are out of range
				tile = rc.senseTerrainTile(scanLoc);
				if (tile != null) { //
					wall[x][y] = tile != tile.LAND;
					sensed[x][y] = true;
				}

			}
		}
	}

	public void senseTileAfterMove(Direction dir) {
		MapLocation loc = bp.loc;
		int myX = worldToCacheX(loc.x);
		int myY = worldToCacheY(loc.y);
		MapLocation scanLoc;
		int dx, dy, x, y;
		TerrainTile tile;
		int toScan[][] = optimizedScanLocations[dir.ordinal()];
		for (int i = 0; i < toScan.length; i++) {

			dx = toScan[i][0];
			dy = toScan[i][0];

			x = myX + dx;
			y = myY + dy;
			if (sensed[x][y])
				continue;
			scanLoc = loc.add(dx, dy);
			tile = rc.senseTerrainTile(scanLoc);
			if (tile != null) {
				// In range
				wall[x][y] = tile != tile.LAND;
				sensed[x][y] = true;
			}
		}
	}

	/*
	 * Checks NESW at maximum sensor range if tile is off Map If found loops
	 * till edge value is found and then updated Probably only called at start
	 * of game by archons, maybe scouts
	 */
	public void senseEdges() {

	}

	// Special Thanks to Fun Gamers for these constants
	private static final int[][][] sensorRangeARCHON = new int[][][] { // ARCHON
	{ // NORTH
			{ -6, 0 }, { -5, -3 }, { -4, -4 }, { -3, -5 }, { -2, -5 }, { -1, -5 }, { 0, -6 }, { 1, -5 }, { 2, -5 }, { 3, -5 }, { 4, -4 },
					{ 5, -3 }, { 6, 0 }, }, { // NORTH_EAST
			{ -3, -5 }, { -2, -5 }, { 0, -6 }, { 0, -5 }, { 1, -5 }, { 2, -5 }, { 3, -5 }, { 3, -4 }, { 4, -4 }, { 4, -3 }, { 5, -3 },
					{ 5, -2 }, { 5, -1 }, { 5, 0 }, { 5, 2 }, { 5, 3 }, { 6, 0 }, }, { // EAST
			{ 0, -6 }, { 0, 6 }, { 3, -5 }, { 3, 5 }, { 4, -4 }, { 4, 4 }, { 5, -3 }, { 5, -2 }, { 5, -1 }, { 5, 1 }, { 5, 2 }, { 5, 3 },
					{ 6, 0 }, }, { // SOUTH_EAST
			{ -3, 5 }, { -2, 5 }, { 0, 5 }, { 0, 6 }, { 1, 5 }, { 2, 5 }, { 3, 4 }, { 3, 5 }, { 4, 3 }, { 4, 4 }, { 5, -3 }, { 5, -2 },
					{ 5, 0 }, { 5, 1 }, { 5, 2 }, { 5, 3 }, { 6, 0 }, }, { // SOUTH
			{ -6, 0 }, { -5, 3 }, { -4, 4 }, { -3, 5 }, { -2, 5 }, { -1, 5 }, { 0, 6 }, { 1, 5 }, { 2, 5 }, { 3, 5 }, { 4, 4 }, { 5, 3 },
					{ 6, 0 }, }, { // SOUTH_WEST
			{ -6, 0 }, { -5, -3 }, { -5, -2 }, { -5, 0 }, { -5, 1 }, { -5, 2 }, { -5, 3 }, { -4, 3 }, { -4, 4 }, { -3, 4 }, { -3, 5 },
					{ -2, 5 }, { -1, 5 }, { 0, 5 }, { 0, 6 }, { 2, 5 }, { 3, 5 }, }, { // WEST
			{ -6, 0 }, { -5, -3 }, { -5, -2 }, { -5, -1 }, { -5, 1 }, { -5, 2 }, { -5, 3 }, { -4, -4 }, { -4, 4 }, { -3, -5 }, { -3, 5 },
					{ 0, -6 }, { 0, 6 }, }, { // NORTH_WEST
			{ -6, 0 }, { -5, -3 }, { -5, -2 }, { -5, -1 }, { -5, 0 }, { -5, 2 }, { -5, 3 }, { -4, -4 }, { -4, -3 }, { -3, -5 }, { -3, -4 },
					{ -2, -5 }, { -1, -5 }, { 0, -6 }, { 0, -5 }, { 2, -5 }, { 3, -5 }, }, };
	private static final int[][][] sensorRangeSOLDIER = new int[][][] { // SOLDIER
	{ // NORTH
			{ -3, -1 }, { -2, -2 }, { -1, -3 }, { 0, -3 }, { 1, -3 }, { 2, -2 }, { 3, -1 }, }, { // NORTH_EAST
			{ -1, -3 }, { 0, -3 }, { 1, -3 }, { 1, -2 }, { 2, -2 }, { 2, -1 }, { 3, -1 }, { 3, 0 }, { 3, 1 }, }, { // EAST
			{ 1, -3 }, { 1, 3 }, { 2, -2 }, { 2, 2 }, { 3, -1 }, { 3, 0 }, { 3, 1 }, }, { // SOUTH_EAST
			{ -1, 3 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 1 }, { 2, 2 }, { 3, -1 }, { 3, 0 }, { 3, 1 }, }, { // SOUTH
			{ -3, 1 }, { -2, 2 }, { -1, 3 }, { 0, 3 }, { 1, 3 }, { 2, 2 }, { 3, 1 }, }, { // SOUTH_WEST
			{ -3, -1 }, { -3, 0 }, { -3, 1 }, { -2, 1 }, { -2, 2 }, { -1, 2 }, { -1, 3 }, { 0, 3 }, { 1, 3 }, }, { // WEST
			{ -3, -1 }, { -3, 0 }, { -3, 1 }, { -2, -2 }, { -2, 2 }, { -1, -3 }, { -1, 3 }, }, { // NORTH_WEST
			{ -3, -1 }, { -3, 0 }, { -3, 1 }, { -2, -2 }, { -2, -1 }, { -1, -3 }, { -1, -2 }, { 0, -3 }, { 1, -3 }, }, };

}
