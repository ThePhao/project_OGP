import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of Hillbilly units.
 * 
 * @author 	Joris Schrauwen, Wim Schmitz
 * 
 * @invar 	Each unit can have its position as position.
 * 			| isValidPosition(this.getPosition())
 * @invar  	Each unit can have its name as name.
 * 		   	| canHaveAsName(this.getName())
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
	 * @param 	orientation.
	 * 			The orientation for this new unit.
	 * @post	The position of this new unit is the given position.
	 * 			| new.getPosition() == position
	 * @post    The name of this new unit is equal to the given name.
	 *        	| new.getName() == name
	 * @throws	OutOfBoundsException
	 * 			The given position is out of bounds.
	 * 			| ! isValidPosition(position)
	 * @throws  IllegalArgumentException
	 *         	This new unit cannot have the given name as its name.
	 *       	| ! canHaveAsName(this.getName())
	 */
	public Unit(double[] position, String name, int weight, int strength, 
			int agility, int toughness) 
					throws OutOfBoundsException, IllegalArgumentException {
						
		if (!isValidPosition(position))
			throw new OutOfBoundsException();
		
		if (!canHaveAsName(name))
			throw new IllegalArgumentException(name);
		
		this.position = position;	
		this.name = name;
		
		if (isWithinRange(strength))		
			this.setStrength(strength);
		
		if (isWithinRange(agility))
			this.setAgility(agility);
		
		if (isWithinRange(toughness))
			this.setToughness(toughness);
		
		if (isWithinRange(weight))
			this.setWeight(weight);
		
		this.orientation = (float) Math.PI/2;
		this.stamina = getMaxStamina();
		this.hitpoints = getMaxHitpoints();
	}
	
	/**
	 * Variable registering the position of this unit.
	 */
	private double[] position;
	
	/**
	 * Variable registering the name of this unit.
	 */
	private String name;
	
	/**
	 * Variable registering the weight of this unit.
	 */
	private int weight;
	
	/**
	 * Variable registering the strength of this unit.
	 */
	private int strength;
	
	/**
	 * Variable registering the agility of this unit.
	 */
	private int agility;
	
	/**
	 * Variable registering the toughness of this unit.
	 */
	private int toughness;
	
	/**
	 * Variable registering the orientation of this unit.
	 */
	private float orientation;
	
	/**
	 * Variable registering the stamina of this unit.
	 */
	private int stamina;
	
	/**
	 * Variable registering the hitpoints of this unit.
	 */
	private int hitpoints;
	
	/**
	 * Variable registering the lower bound for the x, y and z
	 * dimensions of the generated world.
	 */
	private static final int LOWER_BOUND = 0;
	
	/**
	 * Variable registering the upper bound for the x, y and z
	 * dimensions of the generated world.
	 */
	private static final int UPPER_BOUND = 50;
	
	/**
	 * Return the position of this unit.
	 */
	@Basic
	public double[] getPosition(){
		return this.position;
	}
	
	/**
	 * Check whether the given position is a valid position for a unit.
	 * @param 	position
	 * 			The position to check.
	 * @return	True if and only if all doubles of the given position
	 * 			are larger than or equal to the lower bound and smaller 
	 * 			than or equal to the upper bound.
	 * 			| result == (for (int i = 0; i < position.length;)
	 *			| 				((position[i] > LOWER_BOUND) && 
	 *			|					(position[i] < UPPER_BOUND)))
	 */
	public boolean isValidPosition(double[] position){
		for (int i = 0; i < position.length;)
			if ((position[i] < LOWER_BOUND) || (position[i] > UPPER_BOUND))
				return false;
		return true;
	}
		
	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw @Immutable
	public String getName() {
		return this.name;
	}
	
	/**
	 * Check whether this unit can have the given name as its name.
	 *  
	 * @param   name
	 *          The name to check.
	 * @return  True if and only if the name uses 2 characters or more, 
	 * 			the first letter is a capital one and all characters
	 * 			are either letters, spaces or quotes.
	 *       	| result == (name != null)
	*/
	@Raw
	public boolean canHaveAsName(String name) {
		
		if (name == null)
			return false;
		
		char[] chars = name.toCharArray();
		
		if ((chars.length < 2) || (!Character.isUpperCase(chars[0])))
			return false;
		
		for (char i : chars) 
		        if ((!Character.isLetter(i)) && (i !=' ') && 
		        		(i != '\'') && (i != '"'))
		            return false;

		return true;
	}
	
	public boolean isWithinRange(int value) {
		return ((value >= 25) && (value <= 100));
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
	
	/**
	 * Constant reflecting the lowest possible value 
	 * for an attribute of a unit.
	 * 
	 * @return 	The lowest possible value for all attributes
	 * 			of all units is 1.
	 * 			| result == 1
	 */
	public static final int MIN_ATTRIBUTE = 1;
	
	/**
	 * Constant reflecting the highest possible value 
	 * for an attribute of a unit.
	 * 
	 * @return 	The highest possible value for all attributes 
	 * 			of all units is 200.
	 * 			| result == 200
	 */
	public static final int MAX_ATTRIBUTE = 200;
	
	/**
	 * Return the weight of this unit.
	 */
	@Basic
	public int getWeight(){
		return this.weight;
	}
	
	/**
	 * Set the weight of this unit to the given weight.
	 * 
	 * @param  	weight
	 * 			The new weight for this unit.
	 * @post  	If the given weight is in range of the weight for a unit  
	 * 			and the given weight is at least the sum of the unit's
	 * 			strength and agility divided by 2, then the new weight of this unit 
	 * 			is equal to the given weight.
	 * 			| if ((weight >= MIN_ATTRIBUTE) && (weight <= MAX_ATTRIBUTE) && 
	 * 			|		(weight >= (this.getAgility() + this.getStrength()) / 2))
	 * 			|	then new.getWeight == weight
	 */
	private void setWeight(int weight) {
		if ((weight >= MIN_ATTRIBUTE) && (weight <= MAX_ATTRIBUTE) && 
				(weight >= ((this.getAgility() + this.getStrength()) / 2)))
			this.weight = weight;
	}
	
	/**
	 * Return the strength of this unit.
	 */
	@Basic
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * Set the strength of this unit to the given strength.
	 * 
	 * @param 	strength
	 * 			The new strength for this unit.
	 * @post 	If the given strength is in range of the strength for a unit
	 *			the new strength of this unit is equal to the given strength.
	 *			| if ((strength >= MIN_ATTRIBUTE) && (strength <= MAX_ATTRIBUTE)
	 *				then new.getStrength == strength
	 */
	private void setStrength(int strength) {
		if ((strength >= MIN_ATTRIBUTE) && (strength <= MAX_ATTRIBUTE))
			this.strength = strength;
	}
	
	/**
	 * Return the agility of this unit.
	 */
	@Basic
	public int getAgility(){
		return this.agility;
	}
	
	/**
	 * Set the new agility of this unit to the given agility.
	 * 
	 * @param 	agility
	 * 			The new agility for this unit.
	 * @post	If the given agility is in range of the agility for a unit
	 *			the new agility of this unit is equal to the given agility.
	 *			| if ((agility >= MIN_ATTRIBUTE) && (agility <= MAX_ATTRIBUTE)
	 *				then new.getAgility == agility
	 */
	private void setAgility(int agility){
		if ((agility >= MIN_ATTRIBUTE) && (agility <= MAX_ATTRIBUTE))
			this.agility = agility;
	}
	
	/**
	 * Return the toughness of this unit
	 */
	@Basic
	public int getToughness (){
		return this.toughness;
	}
	
	/**
	 * Set the new toughness of this unit to the given toughness.
	 * 
	 * @param 	toughness
	 * 			The new toughness for this unit.
	 * @post	If the given toughness is in range of the toughness for a unit
	 *			the new toughness of this unit is equal to the given toughness.
	 *			| if ((toughness >= MIN_ATTRIBUTE) && (toughness <= MAX_ATTRIBUTE)
	 *				then new.getToughness == toughness
	 */
	private void setToughness(int toughness){
		if( (toughness >= MIN_ATTRIBUTE) && (toughness <= MAX_ATTRIBUTE))
			this.toughness = toughness;
	}
	
	/**
	 * Inspect the current orientation of this unit.
	 */
	@Basic
	public float getOrientation (){
		return this.orientation;
	}

	/**
	 * Change the orientation of this unit to the specified angle.
	 * @param  	orientation
	 * 			The new angle of orientation for this unit.
	 * @post	If the specified angle is a double precision number between 0 and 2*PI, inclusively,
	 * 			the orientation of this unit will be changed to the specified angle.
	 */
	private void setOrientation(float angle){
		if( (angle >= 0) && (angle <= (float) 2*Math.PI))
			this.orientation = angle;
		
	}

	/**
	 * Return the current amount of hitpoints of this unit.
	 */
	public int getHitpoints() {
		return this.hitpoints;
	}
	
	/**
	 * Inspect the maximal amount of hitpoints of this unit.
	 */
	@Basic @Immutable @Raw
	public int getMaxHitpoints(){
		return (int) Math.ceil(this.getWeight()*this.getToughness()* 0.02);
	}

	public void setHitpoints(int hitpoints){
		if ((hitpoints >= 0) && (hitpoints <= this.getMaxHitpoints()))
			this.hitpoints = hitpoints;
	}
	
	/**
	 * Return the current amount of stamina of this unit.
	 */
	public int getStamina() {
		return this.stamina;
	}
	
	/**
	 * Inspect the maximal amount of stamina of this unit.
	 */
	@Basic @Immutable @Raw
	public int getMaxStamina(){
		return (int) Math.ceil(this.getWeight()*this.getToughness()* 0.02);
	}

	/**
	 * Set the current stamina of this unit to the given value.
	 * @param stamina
	 */
	public void setStamina(int stamina) {
		if ((stamina >= 0) && (stamina <= this.getMaxStamina()))
			this.stamina = stamina;		
	}
	
	/**
	 * Update the position and status of a Unit,
	 * based on that Unit's current position, attributes and a given duration ∆t in seconds of game time.
	 */
	public void advanceTime(double duration, String status) throws NotValidDurationException {
			if (!isValidDuration(duration))
				throw new NotValidDurationException(duration);
			
			if (status == "Moving")
				
				double velocity[] = this.getVelocity();
				this.position[0] = this.position[0] + duration*velocity[0];
				this.position[1] = this.position[1] + duration*velocity[1];
				this.position[2] = this.position[2] + duration*velocity[2];
			
			if (status == "Resting")
				...
				
			if (status == "Attacking")
				...
			
			if (status == 'Working')
				...
			
	}
	
	/**
	 * Constant reflecting the length of any side of a cube of the game world.
	 * 
	 * @return 	The length of all sides of all cubes of the game world is 1.
	 * 			| result == 1
	 */
	public static final int CUBE_LENGTH = 1;
	
	/**
	 * Calculate the Velocity of a Unit.
	 */
	public double[] getVelocity(double[] startPos,double[] targetPos){
		
		double basevel = 0.75*(this.getStrength()+this.getAgility())/this.getWeight();	
		double walkvel;

		if (startPos[2]-targetPos[2] > 0)
			walkvel = 1.2*basevel;
		else if (startPos[2]-targetPos[2] < 0)
			walkvel = 0.5*basevel;
		else
			walkvel = basevel;
		
		double sprintvel = 2 * walkvel;
		
		double dis = calcDistance(startPos,targetPos);
		double [] velocity = {(targetPos[0]-startPos[0])/dis,
								(targetPos[0]-startPos[0])/dis,
								(targetPos[0]-startPos[0])/dis};
		
		if (this.isSprinting())
			for (int i=0; i < velocity.length;)
				velocity[i] = velocity[i] * sprintvel;
		else
			for (int i=0; i< velocity.length;)
				velocity[i]= velocity[i] * walkvel;
		return velocity;
	}
	
	/**
	 * Calculate the distance between two points in the game world
	 * @throws	OutOfBoundsException
	 * 			The given position is out of bounds.
	 * 			| ! isValidPosition(position)
	 */
	public double calcDistance(double[] start, double[] end) throws OutOfBoundsException{
		if ((!isValidPosition(start)) || (!isValidPosition(end)))
			throw new OutOfBoundsException();
		
		return Math.sqrt(Math.pow(end[0]-start[0],2)+Math.pow(end[1]-start[1],2)+Math.pow(end[2]-start[2],2));
	}
	
	/**
	 * Check whether the given duration is a valid duration to advance the time.
	 * @param 	duration
	 * 			The duration to check.
	 * @return	True if and only if the given duration is larger than or equal to zero, and always smaller than 0.2.
	 */
	public static boolean isValidDuration(double duration){
			if ((duration < 0) || (duration >=0.2))
				return false;
		return true;
	}
	
	public void moveToAdjacent(){
		double[] speed = this.getVelocity(startPos, targetPos);
		float vy = (float) speed[1];
		float vx = (float) speed[0];
		this.setOrientation((float) Math.atan2(vy, vx));
	}
}
