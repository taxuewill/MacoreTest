// IRouterAIDL.aidl
package macore.router;
import macore.router.MaActionResult;
import macore.router.RouterRequest;
// Declare any non-default types here with import statements

interface IWideRouterAIDL {
    boolean checkResponseAsync(String domain,in RouterRequest routerRequset);
    MaActionResult route(String domain,in RouterRequest routerRequest);
    boolean stopRouter(String domain);
}
