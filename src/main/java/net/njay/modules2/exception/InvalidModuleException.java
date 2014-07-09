package net.njay.modules2.exception;

public class InvalidModuleException extends Exception{

    /**
     * Thrown when a Module does not meet all of the requirements
     */
    public InvalidModuleException(){ super(); }

    /**
     * Thrown when a Module does not meet all of the requirements
     *
     * @param msg Exception message
     */
	public InvalidModuleException(String msg){
		super(msg);
	}
}
