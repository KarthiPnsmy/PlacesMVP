package com.titut.placesmvp.utils;

import com.titut.placesmvp.model.Place;

import java.util.HashMap;

/**
 * Created by karthi.ponnusamy on 1/6/17.
 */

public class PlacesCache extends HashMap<String, Object> {

    private static PlacesCache cache;
    private HashMap<String, Place> placeDetailCache = new HashMap<>();

    public PlacesCache() {

    }

    public static PlacesCache getInstance() {
        if (cache == null) {
            cache = new PlacesCache();
        }

        return cache;
    }

    public HashMap<String, Place> getPlaceDetailCache(){
        return placeDetailCache;
    }

    public void addPlaceToCache(String placeId, Place place){
        placeDetailCache.put(placeId, place);
    }
}
