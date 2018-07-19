package com.macrohard.cooklit.support;

import java.util.ArrayList;

public class Algo {
    static boolean sort(ArrayList<String> in, String keyword){ // in is the total ingredients[
                                                                // "2 Tbsp vegetable oil",
                                                                //"1 lb small red potato, sliced 1/4-inch thick]

        for(int i = 0; i < in.size(); ++i){
            String[] ary = (in.get(i)).split(" ");
            for(int ii = 0; ii < ary.length; ++ii){
                if(keyword == ary[ii]){
                    return true;
                }
            }
        }
        return false;
    }
}

