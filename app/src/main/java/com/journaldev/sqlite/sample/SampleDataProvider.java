package com.journaldev.sqlite.sample;

import com.journaldev.sqlite.model.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<DataItem> dataItemList;
    public static Map<String, DataItem> dataItemMap;

    static {
        dataItemList = new ArrayList<>();
        dataItemMap = new HashMap<>();

    //FROM DataItem.java ******   public DataItem(String itemId, String itemName, String category, String description, int sortPosition, double price, String image) {
        addItem(new DataItem(null, "Chef's Salad", "Salads",
                "The chef’s salad has cucumber, tomatoes, red onions, mushrooms, dressing.",
                2, 9, "chef_salad.jpg"));


    }

    private static void addItem(DataItem item) {
        dataItemList.add(item);
        dataItemMap.put(item.getItemId(), item);
    }

}
