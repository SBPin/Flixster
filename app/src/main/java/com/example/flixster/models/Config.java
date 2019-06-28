package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {

    //  base url for image loading
    String imageBaseUrl;
    //  poster size for fetching images, part of the url
    String posterSize;
    //  BACKDROP size to use when fetching images
    String backdropSize;

    public Config(JSONObject object) throws JSONException {

        JSONObject images = object.getJSONObject("images");
        //  get image base url
        imageBaseUrl = images.getString("secure_base_url");
        //  get the poster size - comes as an array as option
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        //  use option at index 3 or w342 as a fallback
        posterSize = posterSizeOptions.optString(3, "w342");
        // parse the backdrop sizes and use the option at index 1 (w780 as fallback)
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }

    //  helper method for creating urls
    public String getImageUrl(String size, String path){
        return String.format("%s%s%s", imageBaseUrl, size, path); //    concatenate all three
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() { return backdropSize; }
}
