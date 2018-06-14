package macore;

import android.content.Context;

import macore.router.MaActionResult;
import macore.router.RouterRequest;


/**
 * Created by wanglei on 2016/11/29.
 */

public interface MaAction<T> {
    boolean isAsync(Context context, RouterRequest<T> routerRequest);

    MaActionResult invoke(Context context, RouterRequest<T> routerRequest);

    String getName();
}
