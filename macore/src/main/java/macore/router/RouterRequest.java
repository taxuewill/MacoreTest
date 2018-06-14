package macore.router;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wanglei on 2016/12/27.
 */

public class RouterRequest<T> implements Parcelable {
    static AtomicInteger sIndex = new AtomicInteger(0);

    String from;
    String domain;
    String provider;
    String action;
    HashMap<String, String> data;
    private static final int PARCELABLE_SINGLE = 0;//单个parcelable
    private static final int PARCELABLE_LIST = 1;//parcelable集合
    int parcelType = PARCELABLE_SINGLE; // 0: <? extends parcelable>  1: list<? extends parcelable>
    T requestObject;

    public AtomicBoolean isIdle = new AtomicBoolean(true);

    public RouterRequest() {
        this.from = RouterRequestUtil.DEFAULT_PROCESS;
        this.domain = RouterRequestUtil.DEFAULT_PROCESS;
        this.provider = "";
        this.action = "";
        this.data = new HashMap<>();
    }


    RouterRequest(Context context) {
        this.from = RouterRequestUtil.getProcess(context);
        this.domain = RouterRequestUtil.getProcess(context);
        this.provider = "";
        this.action = "";
        this.data = new HashMap<>();
    }

    protected RouterRequest(Parcel in) {
        from = in.readString();
        domain = in.readString();
        provider = in.readString();
        action = in.readString();
        parcelType = in.readInt();
        if (parcelType == PARCELABLE_SINGLE) {
            requestObject = (T) in.readParcelable(this.getClass().getClassLoader());
        } else if (parcelType == PARCELABLE_LIST) {
            requestObject = (T) Arrays.asList(in.readParcelableArray(this.getClass().getClassLoader()));
        }
        int mapSize = in.readInt();
        if (mapSize > 0) {
            data = new HashMap<>();
        }
        for (int i = 0; i < mapSize; i++) {
            String key = in.readString();
            String value = in.readString();
            data.put(key, value);
        }
    }

    public T getRequestObject() {
        return requestObject;
    }

    public static final Creator<RouterRequest> CREATOR = new Creator<RouterRequest>() {
        @Override
        public RouterRequest createFromParcel(Parcel in) {
            return new RouterRequest(in);
        }

        @Override
        public RouterRequest[] newArray(int size) {
            return new RouterRequest[size];
        }
    };

    public String getFrom() {
        return from;
    }

    public String getDomain() {
        return domain;
    }

    public String getProvider() {
        return provider;
    }

    public String getAction() {
        return action;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public String toJsonString() {
        //Here remove Gson to save about 10ms.
        //String result = new Gson().toJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("from", from);
            jsonObject.put("domain", domain);
            jsonObject.put("provider", provider);
            jsonObject.put("action", action);
            jsonObject.put("requestObj", requestObject);

            try {
                if (data != null) {
                    JSONObject jsonData = new JSONObject();
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        jsonData.put(entry.getKey(), entry.getValue());
                    }
                    jsonObject.put("data", jsonData);
                } else {
                    jsonObject.put("data", "{}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                jsonObject.put("data", "{}");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    public RouterRequest domain(String domain) {
        this.domain = domain;
        return this;
    }


    public RouterRequest provider(String provider) {
        this.provider = provider;
        return this;
    }


    public RouterRequest action(String action) {
        this.action = action;
        return this;
    }

    public RouterRequest reqeustObject(T t) {
        if (t != null && t instanceof List) {
            parcelType = PARCELABLE_LIST;
        }
        this.requestObject = t;
        return this;
    }

    public RouterRequest data(String key, String data) {
        this.data.put(key, data);
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(domain);
        dest.writeString(provider);
        dest.writeString(action);
        dest.writeInt(parcelType);
        if (parcelType == PARCELABLE_SINGLE) {
            dest.writeParcelable((Parcelable) requestObject, flags);
        } else if (parcelType == PARCELABLE_LIST) {
            dest.writeParcelableArray(((List<Parcelable>) requestObject).toArray(new Parcelable[]{}), flags);
        }
        if(data !=null){
            dest.writeInt(data.size());
            for (Map.Entry<String, String> entry : data.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }else{
            dest.writeInt(0);
        }
    }
}
