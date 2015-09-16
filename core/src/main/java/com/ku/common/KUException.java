package com.ku.common;

public class KUException extends Exception {
	private static final long serialVersionUID = 1L;
	
		/**
		 * Default Constructor.
		 */
		public KUException() {
			super();
		}

		/**
		 * <p>
		 * Constructor with one Argument.
		 * </p>
		 * 
		 * @param msg - a String Value - Exception message
		 */
		public KUException(final String msg) {
			super(msg);
		}

		/**
		 * Constructor with two Argument.
		 * 
		 * @param msg - a String Value - Exception message
		 * @param cause -Throwable object
		 */
		public KUException(final String msg, final Throwable cause) {
			super(msg, cause);
		}

		/**
		 * Constructor with one Argument.
		 * 
		 * @param cause - Throwable object
		 */
		public KUException(final Throwable cause) {
			super(cause);
		}

}
