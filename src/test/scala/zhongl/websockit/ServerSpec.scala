package zhongl.websockit

import org.scalatest.{ BeforeAndAfterAll, FunSpec }
import org.scalatest.matchers.ShouldMatchers
import java.net.URI
import io.netty.handler.codec.http.websocketx.{ TextWebSocketFrame, CloseWebSocketFrame }
import java.util.concurrent.{ TimeUnit, SynchronousQueue }

class ServerSpec extends FunSpec with ShouldMatchers with BeforeAndAfterAll {

  describe("Server") {

    it("should open websockit console") {
      val queue = new SynchronousQueue[AnyRef]()
      Client.stub(new URI("ws://localhost:12306/console")) {
        case f: TextWebSocketFrame => queue.put(f.retain().text()); Some(new CloseWebSocketFrame())
        case f                     => queue.put(f.retain()); Some(new CloseWebSocketFrame())
      }
      queue.poll(3, TimeUnit.SECONDS) should be("WebSockit console is ready!")
    }

    it("should get stub definition") {
      val (_, content) = Http("localhost", 12306).get("/stub")
      content should be("(_ => true) => (s => s)")
    }

  }

  var s: Server = _

  override protected def beforeAll() {
    s = new Server(12306).run()
  }

  override protected def afterAll() {
    s.shutdown
  }
}

