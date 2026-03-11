package seedu.duke;

/**
 * Represents a task with a description and completion status.
 */
public class Item {
	protected String description;
	protected int quantity;
	
	/**
	 * Creates a task with the given description, marked as not done.
	 *
	 * @param description The task description.
	 */
	public Item(String description, int quantity) {
		this.description = description;
		this.quantity = quantity;
	}
	
//	/** Marks this task as done. */
//	public void markDone() {
//		quantity = true;
//	}
	
	/** set this item quantity. */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Returns the status icon for display.
	 *
	 * @return "X" if done, " " if not done.
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Returns the task in save file format.
	 *
	 * @return A string formatted for writing to the save file.
	 */
	public String toSaveFormat() {
		return (quantity) + " | " + description;
	}
	
	/**
	 * Returns the task description.
	 *
	 * @return The description string.
	 */
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "[" + getQuantity() + "] " + description;
	}
}


