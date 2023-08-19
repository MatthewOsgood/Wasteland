package com.mygdx.game.Model.ai.pfa;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.SmoothableGraphPath;
import com.badlogic.gdx.math.Vector2;

public class SmoothablePath extends DefaultGraphPath<MapNode> implements SmoothableGraphPath<MapNode, Vector2>{

    /**
     * Returns the position of the node at the given index.
     *
     * @param index the index of the node you want to know the position
     */
    @Override
    public Vector2 getNodePosition(int index) {
        return this.nodes.get(index).getWorldPosition();
    }

    /**
     * Swaps the specified nodes of this path.
     *
     * @param index1 index of the first node to swap
     * @param index2 index of the second node to swap
     */
    @Override
    public void swapNodes(int index1, int index2) {
        this.nodes.swap(index1, index2);
    }

    /**
     * Reduces the size of this path to the specified length (number of nodes). If the path is already smaller than the specified
     * length, no action is taken.
     *
     * @param newLength the new length
     */
    @Override
    public void truncatePath(int newLength) {
        this.nodes.truncate(newLength);
    }
}
