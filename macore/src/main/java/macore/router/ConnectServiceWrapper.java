package macore.router;

/**
 * Created by wanglei on 2016/11/30.
 */

public class ConnectServiceWrapper {
    public Class<? extends LocalRouterConnectService> targetClass = null;
    public String pkgName;
    public String clsName;

    public ConnectServiceWrapper( Class<? extends LocalRouterConnectService> logicClass) {
        this.targetClass = logicClass;
    }

    public ConnectServiceWrapper(String pkgName, String clsName){
        this.pkgName = pkgName;
        this.clsName = clsName;
    }
}
