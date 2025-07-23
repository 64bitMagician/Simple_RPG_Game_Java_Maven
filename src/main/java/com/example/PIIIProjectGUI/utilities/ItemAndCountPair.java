package com.example.PIIIProjectGUI.utilities;

import com.example.PIIIProjectGUI.implementation.interfaces.Item;

public class ItemAndCountPair implements Comparable<ItemAndCountPair>{

    public Item key;
    public Integer value;

    public ItemAndCountPair(Item key, Integer value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(ItemAndCountPair other) {
        if(this.value > other.value){
            return 1;
        }
        else if(this.value < other.value){
            return -1;
        }

        return 0;
    }
}
