package com.mygdx.game.Model.Items;

import com.mygdx.game.enums.Items;

public interface Inventory {

    /**
     * @param item the item we want to check if we can add
     * @return if the item can be added
     */
    boolean canAdd(Items item);

    /**
     * must check if the given item can be removed with {@link #addItem(Items, int)}
     *
     * @param item the item we want to check if its in the inventory
     * @return if the item is in the inventory
     */
    boolean contains(Items item);

    /**
     * @param item the items to be added
     * @param amount the number of the given item to be added
     */
    void addItem(Items item, int amount);

    /**
     * must check if the given item can be removed with {@link #contains(Items)}
     *
     * @param item the items to be removed from the inventory
     * @param amount the number of that item to remove from the inventory
     */
    void removeItem(Items item, int amount);

    /**
     * removes the entire stack
     *
     * @param items the item that will be removed
     */
    void removeAll(Items items);

    /**
     * @return the max number of item stacks that can be in this inventory
     */
    int getMaxSize();

    /**
     * @param item the item to get
     * @return the number of that item in this inventory
     */
    int get(Items item);

}
