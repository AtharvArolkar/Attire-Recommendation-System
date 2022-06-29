package com.example.clothme.Models;

import java.util.ArrayList;

public class ReturnClothLogicModelLists {
    ArrayList<ArrayList<String>> pairs;
    int rankSum;

    public ReturnClothLogicModelLists(ArrayList<ArrayList<String>> pairs, int rankSum) {
        this.pairs = pairs;
        this.rankSum = rankSum;
    }

    public ArrayList<ArrayList<String>> getPairs() {
        return pairs;
    }

    public int getRankSum() {
        return rankSum;
    }
}
