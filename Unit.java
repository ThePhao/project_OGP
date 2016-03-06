package hillbillies.model;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.OutOfBoundsException;

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
	public Unit(String name, double[] position, int weight, int agility, 
				int strength, int toughness, boolean enableDefaultBehavior) 
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
		this.stamina = getMaxHitpoints();
		this.hitpoints = getMaxHitpoints();
		this.interrupted = false;
		this.movement = "Still";
		if (enableDefaultBehavior == false)
			this.status = null;
		else
			this.status = "Default";
			this.startDefaultBehavior();
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
	 * Variable registering the status of this unit.
	 */
	private String status;
	
	/*
	 * Variable registering the unit's movement status.
	 */
	private String movement;
	
	/*
	 * Variable registering whether the unit's behavior is being interrupted.
	 */
	private boolean interrupted;
	
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
	
	private void setPosition(double[] newPos) {
		if (isValidPosition(newPos))
			this.position = newPos;
	}
		
	/**
	 * Return the name of this unit.
	 */
	@Basic @Raw @Immutable
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		if (this.canHaveAsName(newName))
			this.name = newName;
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
		int[] cubeposition = new int[3];
		for (int i = 0; i < cubeposition.length;)
		    cubeposition[i] = (int) this.position[i];
		return cubeposition;
	}
	
	/**
	 * Return the position of the cube occupied by this position.
	 */
	public int[] getCube(double[] position) {
		int[] cubeposition = new int[3];
		for (int i =0; i < cubeposition.length;)
			cubeposition[i] = (int) position[i];
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
	public void setWeight(int weight) {
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
	public void setStrength(int strength) {
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
	public void setAgility(int agility){
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
	public void setToughness(int toughness){
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
	
	public int getMinHitpoints(){
		return 0;
	}

	private void setHitpoints(int hitpoints){
		if ((hitpoints >= getMinHitpoints()) && (hitpoints <= this.getMaxHitpoints()))
			this.hitpoints = hitpoints;
		
		else if (hitpoints > this.getMaxHitpoints())
			this.hitpoints = this.getMaxHitpoints();
		
		else if (hitpoints < getMinHitpoints())
			this.hitpoints = getMinHitpoints();
	}
	
	/**
	 * Return the current amount of stamina of this unit.
	 */
	public int getStamina() {
		return this.stamina;
	}
	
	private void setStamina(int stamina){
		if ((stamina >= 0) && (stamina <= this.getMaxHitpoints()))
			this.stamina = stamina;
		
		else if (stamina > this.getMaxHitpoints())
			this.stamina = this.getMaxHitpoints();
		
		else if (stamina < getMinHitpoints())
			this.stamina = getMinHitpoints();
	}

	/**
	 * Return the current status of this unit.
	 */
	private String getStatus(){
		return this.status;
	}

	/**
	 * Set the units current status to the specified activity.
	 * 
	 * @post The units activity is changed to the given activity.
	 */
	private void setStatus(String activity){
		this.status= activity;
	}

	

	public void advanceTime(double duration, Unit defender) {
		defender.setStatus("Fighting");
		this.setStatus("Fighting");
			try {
				wait((long) (1000*duration));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}
	
	private void advanceTime(float time) throws InterruptedException {
		this.setStatus("Working");
		
		if (time < (float) 0.2)
			wait ((long) time * 1000);
			this.setStatus("Default");
			
		wait((long) (time * 1000));
	}

	public static String getRandomActivity(String[] activities) {
	    int rnd = new Random().nextInt(activities.length);
	    return activities[rnd];
	}
	
	public void attack(Unit defender){
		float attackerOr = (float)Math.atan2(defender.getPosition()[1]-this.getPosition()[1],defender.getPosition()[0]-this.getPosition()[0]);
		float defenderOr = (float)Math.atan2(this.getPosition()[1]-defender.getPosition()[1],this.getPosition()[0]-defender.getPosition()[0]);
		
		this.setOrientation(attackerOr);
		defender.setOrientation(defenderOr);
		
		for(int i=1; i<5; i++)
				this.advanceTime(0.2, defender);

		
		double dodgeProb = 0.2*defender.getAgility()/this.getAgility();
		boolean dodged = (new Random().nextDouble() <= dodgeProb);
		
		if (dodged == true){
			double[] pos = defender.getPosition();
			double[] evasion = {0,0,0};
			for(int i=0; i<2; i++){
				
				double plus = new Random().nextDouble();
				double randomValue = -1 + 2 * plus;
				evasion[i]= randomValue;
			}
			double [] newPos = new double[3];
			
			for (int i =0; i < pos.length;)
				
				newPos[i] = pos[i] + evasion[i];
			
			defender.setPosition(newPos);
		}
		
		else{
			double blockProb = 0.25*(defender.getStrength()-defender.getAgility())/(this.getStrength()-this.getAgility());
			boolean blocked = (new Random().nextDouble() <= blockProb);
			if (blocked != true){
				double curHealth = defender.getHitpoints();
				double damage = this.getStrength()/10;
				defender.setHitpoints((int) (curHealth-damage));
			}
		}
	}
	/**
	 * This method will initiate resting.
	 * 
	 * @post The units current status will be resting
	 */
	public void rest(){
		if (this.canBeInterrupted("Resting"))
			Thread.currentThread().interrupt();
			
			double initTime = 200 / this.getToughness() * 0.2;
			int nb_times = (int) (200 / this.getToughness());

			for (int i = 0; i < nb_times;)
				try {
					this.advanceTime(0.2, "InitResting");
				} catch (InterruptedException e) {
					return;
				}
			
			try {
				this.advanceTime(initTime - (0.2 * nb_times), "InitResting");
			} catch (InterruptedException e) {
				return;
			}
			
			this.restore();
			this.setStatus("Resting");
			while (this.isResting())
				for (int i = 0; i < nb_times;)
					try {
						this.advanceTime(0.2, "Resting");
					} catch (InterruptedException e) {
						return;
					}
				
				try {
					this.advanceTime(initTime - (0.2 * nb_times), "Resting");
				} catch (InterruptedException e) {
					return;
				}
				
				this.restore();
			
			this.startDefaultBehavior();
	}
	
	public void advanceTime(double duration, String status) throws InterruptedException {
		this.setStatus(status);
		try {
			wait((long) (1000*duration));
		} catch (InterruptedException e) {
			throw new InterruptedException();
		}
			
	}

	/**
	 * Restore hitpoints and stamina of a unit, when it is resting.
	 * 
	 * @post The units hitpoints will be replenished with ...
	 * 		 If the maximum hitpoints is reached, the units stamina will be replenished with ...
	 */
	public void restore() {


		if (this.getHitpoints() == this.getMaxHitpoints())
			if (this.getStamina() == this.getMaxHitpoints())
				this.setStatus("Default");
		
			else
				this.setStamina((int) (this.getStamina() + 2));
		
		else		
			this.setHitpoints((int) (this.getHitpoints() + 1));
	}
	
	/**
	 * Constant reflecting the length of any side of a cube of the game world.
	 * 
	 * @return 	The length of all sides of all cubes of the game world is 1.
	 * 			| result == 1
	 */
	public static final int CUBE_LENGTH = 1;
	
	/**
	 * Calculate the velocity of a Unit.
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
			for (int i=0; i < velocity.length;)
				velocity[i]= velocity[i] * walkvel;
		return velocity;
	}
	
	/**
	 * Calculate the distance between two points in the game world.
	 * @throws	OutOfBoundsException
	 * 			The given position is out of bounds.
	 * 			| ! isValidPosition(position)
	 */
	public double calcDistance(double[] start, double[] end) {		
		return Math.sqrt(Math.pow(end[0]-start[0],2)+Math.pow(end[1]-start[1],2)+Math.pow(end[2]-start[2],2));
	}
	
	/**
	 * Check whether the given duration is a valid duration to advance the time.
	 * 
	 * @param 	duration
	 * 			The duration to check.
	 * @return	True if and only if the given duration is larger than or equal to zero, 
	 * 			and always smaller than 0.2.
	 * 			| result == ((duration < 0) || (duration >=0.2))
	 */
	public static boolean isValidDuration(double duration){
			if ((duration < 0) || (duration >=0.2))
				return false;
		return true;
	}
	
	/**
	 * Initiate movement to a game world cube adjacent to the unit's current location.
	 * 
	 * @param 	targetPos
	 * 			The adjacent cube to which this unit has to move.
	 */
	public void moveToAdjacent(double[] targetPos) throws InterruptedException{
		if (this.canBeInterrupted("Moving")) {
			Thread.currentThread().interrupt();
			
			double[] speed = this.getVelocity(this.getPosition(), targetPos);
			float vy = (float) speed[1];
			float vx = (float) speed[0];
			this.setOrientation((float) Math.atan2(vy, vx));
			
			while (this.getPosition() != targetPos)
				try {
					this.advanceTime(0.1, speed, targetPos);
				} catch (InterruptedException e) {
					if (this.isFighting())
						throw new InterruptedException();
					else
						try {
							this.setInterruption(true);
							this.advanceTime(0.1, speed, targetPos);
							
						} catch (InterruptedException e1) {
							if (this.isFighting())
								throw new InterruptedException();
							else
								return;
						}
				}	
		}
	}

	/**
	 * Set the unit's status to moving and update it's position,
	 * based on that Unit's current position, speed, target position 
	 * and a given duration in seconds of game time.
	 * 
	 * @param 	duration
	 * 			The amount of time to advance.
	 * @param 	speed
	 * 			The speed at which the unit moves for each dimension.
	 * @param 	target
	 * 			The unit's destination.
	 * @throws 	InterruptedException
	 */
	public void advanceTime(double duration, double[] speed, double[] target) throws InterruptedException {
	
		this.setStatus("Moving");
	
		wait ((long) (duration * 1000));
		double[] oldPos = this.getPosition();				
		double[] newPos = { oldPos[0] + (duration * speed[0]),
							oldPos[1] + (duration * speed[1]),
							oldPos[2] + (duration * speed[2]) };
		if (this.destinationReached(newPos, target))
			this.setPosition(target);
		else
			this.setPosition(newPos);
		}
	
	/**
	 * Check whether the given double precision number lies between the given borders.
	 * @param 	x
	 * 			The double precision number to be checked.
	 * @param 	a
	 * 			One of the borders of the interval.
	 * @param 	b
	 * 			The other border of the interval.
	 */
	public static boolean intervalContains(double x, double a, double b) {
		if ((x < (a - (int) a)) && (x > (b - (int) b)))
			return true;
		if ((x < (b - (int) b)) && (x > (a - (int) a)))
			return true;
		return false;
	}
	
	public boolean destinationReached(double[] newPos, double[] target) {
		double[] oldPos = this.getPosition();
		if ((intervalContains(0.5, oldPos[0], newPos[0])) &&
				(intervalContains(0.5, oldPos[1], newPos[1])) &&
				(intervalContains(0.5, oldPos[2], newPos[2])) &&
				(this.getCube(target) == this.getCube(newPos)))
			
			return true;
		return false;
	}
	
	/**
	 * Initiate a more complex movement from the unit's current position to another
	 * arbitrary cube of the game world.
	 * @param 	location
	 * 			The new location to which the unit has to move.
	 */
	public void moveTo(double[] location){
		if (this.canBeInterrupted("Moving"))
			Thread.currentThread().interrupt();
			
			while ((this.getMovementStatus() == "Walking") || (this.getMovementStatus() == "Sprinting"))
				try {
					wait((long) 50);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
			this.setInterruption(false);
			
				
			double[] nextPos = new double[3];
			
			while (location != this.getPosition())

				for (int i = 0; i < nextPos.length;) {
						
					if (this.getPosition()[i] == location[i])
						nextPos[i] = 0 + this.getPosition()[i];
					else if (this.position[i] < location[i])
						nextPos[i] = 1 + this.getPosition()[i];
					else
						nextPos[i] = -1 + this.getPosition()[i];
					}
					
				try {
					this.moveToAdjacent(nextPos);
				} catch (InterruptedException e) {
					this.setStatus("Fighting");
					while(this.isFighting())
						try {
							wait(200);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					
					try {
						this.moveToAdjacent(nextPos);
					} catch (InterruptedException e1) {
						this.setStatus("Fighting");							
					}
					
					if (this.isInterrupted() == true)
						this.movement = "Still";
						return;
				}
			
			if (location == this.getPosition())
				this.setStatus("Default");
	}
	
	public boolean isInterrupted() {
		return this.interrupted;
	}
	
	public void setInterruption(boolean flag) {
		this.interrupted = flag;
	}
	
	public String getMovementStatus() {
		return this.movement;
	}
	
	public boolean isSprinting(){
		return this.getMovementStatus() == "Sprinting";
	}
	
	public void setSprinting() {
		if (this.getStamina() > 0)
			this.movement = "Sprinting";
	}
	
	public boolean isMoving() {
		return this.getStatus() == "Moving";
	}
	
	public boolean isWorking() {
		return this.getStatus() == "Working";
	}
	
	public boolean isResting() {
		return this.getStatus() == "Resting";
	}
	
	public boolean isInitResting() {
		return this.getStatus() == "Initial Resting";
	}
	
	public boolean isFighting() {
		return this.getStatus() == "Fighting";
	}
	
	/**
	 * Start default behavior for a unit. This unit will randomly choose one of three activities namely: 
	 * working, resting or moving to a random location in the game world. This unit will keep choosing and finishing activities
	 */
	public void startDefaultBehavior () {
		while (this.getStatus() == "Default") {
			int rnd = ThreadLocalRandom.current().nextInt(0, 2 + 1);
			double[] randomLoc = new double[3];
			if (rnd == 0)
				this.work();
			else if (rnd == 1)
				this.rest();
			else if (rnd == 2)
	
				for (int i = 0; i < 3;)
					randomLoc[i] = (ThreadLocalRandom.current().nextInt(0, 49 + 1) + 0.5);
				this.moveTo(randomLoc);
		}
		return;
	}
	
	public void stopDefaultBehavior () {
		this.setStatus(null);
	}
	
	public void work() {
		if (this.canBeInterrupted("Working"))
			Thread.currentThread().interrupt();
		
			float time = (float) (500 / this.getStrength());
			float nbtimes = time * 5;
						
			for (int i = 0; i < (int) nbtimes;)
				try {
					this.advanceTime((float) 0.2);
				} catch (InterruptedException e) {
					return;
				}
			
			try {
				this.advanceTime((float) (time - (0.2 * nbtimes)));
			} catch (InterruptedException e) {
				return;
			}
	}
	
	/**
	 * Check whether the unit's current activity can be interrupted by the given interruptor.
	 * 
	 * @param 	interruptor
	 * 			The interruptor
	 */
	public boolean canBeInterrupted(String interruptor) {
		if ((this.isWorking()) && (interruptor != "Working"))
			return true;
		
		if ((this.isResting()) && (interruptor != "Resting"))
			return true;
		
		if ((this.isInitResting()) && (interruptor == "Defending"))
			return true;
		
		if ((this.isMoving()) && (interruptor != "Working"))
			return true;
		
		if (this.getStatus() == null)
			return true;
		

		return false;
	}
}
