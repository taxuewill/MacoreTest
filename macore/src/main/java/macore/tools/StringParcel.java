package macore.tools;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chentao on 2017/11/6.
 */

public class StringParcel implements Parcelable {

    private String[] val;

    public StringParcel(String val) {
        this.val = new String[1];
        this.val[0] = val;
    }

    public StringParcel(String[] val) {
        this.val = val;
    }

    public String[] getVal() {
        return this.val;
    }

    protected StringParcel(Parcel in) {
        int len = in.readInt();
        val = new String[len];
        for (int i = 0; i < len; i++) {
            val[i] = in.readString();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (val != null) {
            dest.writeInt(val.length);
            for (String s : val) {
                dest.writeString(s);
            }
        } else {
            dest.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StringParcel> CREATOR = new Creator<StringParcel>() {
        @Override
        public StringParcel createFromParcel(Parcel in) {
            return new StringParcel(in);
        }

        @Override
        public StringParcel[] newArray(int size) {
            return new StringParcel[size];
        }
    };

    @Override
    public String toString() {
        if (val != null) {
            StringBuffer sb = new StringBuffer();
            for (String tmp : val) {
                sb.append(tmp).append(", ");
            }
            return sb.toString();
        }
        return null;
    }
}
