import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling durations that are not valid for the method advanceTime.
 * 
 * @author Joris Schrauwen, Wim Schmitz
 *
 */
public class NotValidDurationException extends Exception {
	
	/**
	 * Initialize this new exception with given duration.
	 * @param  duration
	 *         The duration value for this new exception.
	 * @post 	 The duration value of this new exception is equal to the given value.
	 * 			|  new.getDuration() == duration
	 */
	public NotValidDurationException(double[] duration) {
		this.duration = duration;
	}
	
	/**
	 * Return the position registered for this out of bounds exception. 
	 */
	@Basic @Immutable
	public double[] getDuration(){
		return this.duration;
	}
	/**
	 * Variable registering the position involved in this 
	 * out of bounds exception.
	 */
	private final double[] duration;

}
