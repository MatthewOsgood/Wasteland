package com.mygdx.game.Model.Items;

import com.mygdx.game.enums.Items;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory implements Inventory {

    /**
     * the items in the inventory and their amount
     */
    private final Map<Items, Integer> items;
    /**
     * the number of slots in this inventory
     */
    private final int maxSize;


    public PlayerInventory(Map<Items, Integer> items, int maxSize) {
        if (items.size() > maxSize) {
            throw new IllegalArgumentException(
                    "number of items in inventory (" + items.size() + ") is more than maxSize "  + maxSize);
        }
        this.items = items;
        this.maxSize = maxSize;
    }

    public PlayerInventory(int maxSize) {
        this(new HashMap<Items, Integer>(maxSize), maxSize);
    }


    /**
     * @param item the item we want to check if we can add
     * @return if there is room for the item to be added
     */
    @Override
    public boolean canAdd(Items item) {
        return this.contains(item) || (this.items.size() < this.maxSize);
    }

    /**
     * @param item the item we want to check if its in the inventory
     * @return if the item is in the inventory
     */
    @Override
    public boolean contains(Items item) {
        return this.items.containsKey(item);
    }

    /**
     * @param item   the items to be added
     * @param amount the number of the given item to be added
     */
    @Override
    public void addItem(Items item, int amount) {
        boolean isInInventory = this.contains(item);
        int prev = isInInventory ? this.items.get(item) : 0;
        this.items.put(item, amount + prev);
    }

    /**
     * must check if the given item can be removed with {@link #contains(Items)}
     *
     * @param item   the items to be removed from the inventory
     * @param amount the number of that item to remove from the inventory
     */
    @Override
    public void removeItem(Items item, int amount) {
        this.addItem(item, -amount);
        if (this.items.get(item) <= 0) {
            this.items.remove(item);
        }
    }

    /**
     * removes the entire stack
     *
     * @param items the item that will be removed
     */
    @Override
    public void removeItem(Items items) {
        this.items.remove(items);
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    /**
     * @param item the item to get
     * @return the number of that item in this inventory
     */
    @Override
    public int count(Items item) {
        return this.items.get(item);
    }
}
