package streaming.events

import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.duration.DurationDouble

class Routes(implicit mat: Materializer) extends FailFastCirceSupport {

  val route: Route = cors() {
    get {
      path("streaming" / "start") {
        parameter('from ? 0) { startFrom =>
          val messages =
            Source
              .fromIterator(() => Iterator.from(startFrom))
              .throttle(1, 1.second)
              .map(i => TextMessage(i.toString))

          val flow = Flow[Message].prepend(messages)
          handleWebSocketMessages(flow)
        }
      }
    }
  }
}
