package events.monitor.ui.r4s

import com.github.ahnfelt.react4s._

case class MainComponent() extends Component[NoEmit] {
  override def render(get: Get): ElementOrComponent = {
    E.div(
      Text("Hello World")
    )
  }
}
