package macore.router;
import macore.router.MaActionResult;
import macore.router.RouterRequest;

interface ILocalRouterAIDL {
    boolean checkResponseAsync(in RouterRequest routerRequset);
    MaActionResult route(in RouterRequest routerRequest);
    boolean stopWideRouter();
    void connectWideRouter();
}
