package mastermind

import scala.swing._
import java.awt.{ Color, Graphics2D }

case class ResultBox(circleColor: Color, textColor: Color) extends Panel {
  var text = ""
  override def paintComponent(g: Graphics2D) {
    g.setColor(Color.black)
    g.fillRect(0, 0, size.width, size.height)
    g.setColor(circleColor)
    g.fillOval(size.width / 6, size.height / 6, size.width * 2 / 3, size.height * 2 / 3)
    g.setColor(textColor)
    g.drawString(text, size.width / 2 - 4, size.height / 2 + 3)
  }
}