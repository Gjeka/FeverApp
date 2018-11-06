package com.fever.feverapp.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ranksAndAcheivements {

    public static Map<Integer, ArrayList<String>> ranks = new HashMap<>();

    public static boolean addtoRanks(int rank, String title){
        if(ranks.containsKey(rank)){
            ArrayList<String> titles = ranks.get(rank);
            titles.add(title);
            return true;
        }
        return false;
    }
    // TODO: might use a linked list instead of arraylist, Just cause i dont want to add titles for avery class rather a range
}
