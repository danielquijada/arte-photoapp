package com.arte.photoapp.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.arte.photoapp.model.Photo;

import org.json.JSONException;
import org.json.JSONObject;

public class GetPhotoRequest {

    private static final String PHOTO_LIST_URL_BASE = "http://jsonplaceholder.typicode.com/photos/";
    private String mId;

    public interface Callbacks {
        void onGetPhotoSuccess(Photo photo);

        void onGetPhotoError();
    }

    private Context mContext;
    private Callbacks mCallbacks;

    public GetPhotoRequest(Context context, Callbacks callbacks, String id) {
        mContext = context;
        mCallbacks = callbacks;
        mId = id;
    }

    public void execute() {
        // TODO do JSONObject volley request
        // TODO transform JSONObject to Photo
        // TODO call mCallbacks methods
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getGetPhotoUrl(mId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Photo photo = new Photo();
                try {
                    photo.setId("" + response.getInt("id"));
                    photo.setTitle(response.getString("title"));
                    photo.setThumbnailUrl(response.getString("url"));
                    photo.setThumbnailUrl(response.getString("thumbnailUrl"));
                } catch (JSONException e) {
                    Log.e(GetPhotoRequest.class.getSimpleName(), "Error deserializando JSON", e);
                    mCallbacks.onGetPhotoError();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mCallbacks.onGetPhotoError();
            }
        });

        RequestQueueManager.getInstance(mContext).addToRequestQueue(request);
    }

    private String getGetPhotoUrl (String id) {
        return PHOTO_LIST_URL_BASE + id;
    }
}
