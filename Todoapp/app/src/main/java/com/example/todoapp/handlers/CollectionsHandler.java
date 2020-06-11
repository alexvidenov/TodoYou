package com.example.todoapp.handlers;

public class CollectionsHandler {

    // unfortunately, this can't be done any other way
    public static boolean[] convertBooleanArrayToPrimitive(Boolean[] wrapperArray) {
        boolean[] primitiveArray = new boolean[wrapperArray.length];

        for(int idx = 0; idx < primitiveArray.length; idx++) {
            primitiveArray[idx] = wrapperArray[idx].booleanValue();
        }

        return primitiveArray;
    }
}
