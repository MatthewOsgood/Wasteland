package com.mygdx.game.Model.Items;

import com.mygdx.game.enums.Items;

import java.util.Objects;

/**
 * represents an item
 */
public class ItemStack {

    private final Items item;
    private int quantity;
    public ItemStack(Items item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Items getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * merges the given item stack into this stack`
     *
     * @param that the stack to be merged into this stack
     */
    public void merge(ItemStack that) {
        this.quantity += that.quantity;
        that.quantity = 0;
    }

    /**
     * the item quantity will always be between 0 and the item stack size
     *
     * @param amount the amount for the item quantity to be changed by
     */
    public void changeQuantity(int amount) {
        this.quantity += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStack stack = (ItemStack) o;
        return this.quantity == stack.quantity && this.item == stack.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, quantity);
    }
}
