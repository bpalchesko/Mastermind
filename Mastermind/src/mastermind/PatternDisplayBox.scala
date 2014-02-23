package mastermind

import scala.swing._
import java.awt.{ Color, Graphics2D }

case class PatternDisplayBox extends Panel {
  var color: java.awt.Color = Color.gray
  override def paintComponent(g: Graphics2D) {
    g.clearRect(0, 0, size.width, size.height)
    g.setColor(color)
    g.fillOval(size.width / 10, size.height / 10, size.width * 4 / 5, size.height * 4 / 5)
  }
}