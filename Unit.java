import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class of Hillbilly units.
 * 
 * @author Joris Schrauwen, Wim Schmitz
 *
 */
public class Unit {
	
	/**
	 * Initialize this new unit with given position, name, weight, 
	 * strength, agility and toughness.
	 * 
	 * @param	position
	 * 			The default position for this new unit.
	 * @param	name
	 * 			The name for this new unit.
	 * @param	weight
	 * 			The weight for this new unit.
	 * @param	strength
	 * 			The strength for this new unit.
	 * @param 	agility
	 * 			The agility for this new unit.
	 * @param	toughness
	 * 			The toughness for this new unit.
	 * @post	The position of this new unit is the given position.
	 * 			| new.getPosition() == position
	 * @throws	OutOfBoundsException
	 * 			The given position is out of bounds.
	 * 			| ! isValidPosition(position)
	 */
	public Unit(double[] position, String name, int weight, int strength, 
			int agility, int toughness) 
					throws OutOfBoundsException {
		if (!isValidPosition(position))
			throw new OutOfBoundsException(position);
		
		this.position = position;		
	}
	
	/**
	 * Variable registering the position of this unit.
	 */
	private double[] position;
	
	/**
	 * Variable registering the lower bound for the x, y and z
	 * dimensions of the generated world.
	 */
	public static int lowerbound = 0;
	
	/**
	 * Variable registering the upper bound for the x, y and z
	 * dimensions of the generated world.
	 */
	public static int upperbound = 50;
	
	/**
	 * Check whether the given position is a valid position for a unit.
	 * @param 	position
	 * 			The position to check.
	 * @return	True if and only if all doubles of the given position
	 * 			are larger than or equal to the lowerbound and smaller 
	 * 			than or equal to the upperbound.
	 * 			result == (
	 */
	public static boolean isValidPosition(double[] position){
		for (int i = 0; i < position.length;)
			if ((position[i] < lowerbound) || (position[i] > upperbound))
				return false;
		return true;
	}
	
	/**
	 * Return the position of this unit.
	 */
	@Basic
	public double[] getPosition(){
		return this.position;
	}
	
	/**
	 * Return the position of the cube occupied by this unit.
	 */
	public int[] getCube(){
		int[] cubeposition = new int[this.position.length];
		for (int i = 0; i < cubeposition.length; ++i)
		    cubeposition[i] = (int) this.position[i];
		return cubeposition;
	}

}

