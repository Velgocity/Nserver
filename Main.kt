import net.velgo.Nserver.Server;

fun main() {
    val httpaS: Server = Server(8000, "localhost");
    httpaS.ResponseFILE("index.html", "a")
    httpaS.run()
}
