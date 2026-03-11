package seedu.duke;


import java.util.ArrayList;

/**
 * Manages the list of tasks, providing operations to add, delete, mark, and retrieve tasks.
 */
public class ItemList {
    private final ArrayList<Item> items;

    /**
     * Creates an empty Item list.
     */
    public ItemList() {
        this.items = new ArrayList<>();
    }

    /**
     * Creates a task list pre-populated with the given tasks.
     *
     * @param items An existing list of tasks to manage.
     */
    public ItemList(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Adds a item to the list.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Zero-based index of the task to delete.
     * @return The removed task.
     */
    public Item deleteItem(int index) {
        return items.remove(index);
    }


    /**
     * Returns the item at the given index.
     *
     * @param index Zero-based index of the item.
     * @return The task at that item.
     */
    public Item getItem(int index) {
        return items.get(index);
    }

    /**
     * Returns the number of items in the list.
     *
     * @return The item count.
     */
    public int size() {
        return items.size();
    }

    /**
     * Returns true if there are no tasks in the list.
     *
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * *Returns the underlying ArrayList of tasks.
     *
     * @return The task list.
     */
    public ArrayList<Item> getTasks() {
        return items;
    }
}
