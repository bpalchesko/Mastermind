package mastermind

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._
import java.awt.{ Color, Graphics2D }
import scala.util.Random

object MastermindGuiBradPalchesko extends SimpleSwingApplication {
  var mastermindGame: Mastermind = new Mastermind()
  var pattern = mastermindGame.randomPatternGenerator
  var turnCounter = 0
  var userInputLog: List[Map[Int, String]] = List()
  var trialResultLog: List[(Int, Int)] = List()

  def top = new MainFrame { // top is a required method
    title = "Mastermind"
    size = new Dimension(400, 600)

    //Initialize array of circles to show the pattern selected by the player
    val fullGameArray = Array.ofDim[PatternDisplayBox](10,4)
    for (i <- 0 to 9) {
      for (j <- 0 to 3) {
        fullGameArray(i)(j) = new PatternDisplayBox
      }
    }

    //Initialize arrays of objects to show results of each turn
    val redResultArray = new Array[ResultBox](10)
    val whiteResultArray = new Array[ResultBox](10)
    for (i <- 0 to 9) {
      redResultArray(i) = new ResultBox(Color.RED, Color.WHITE)
      whiteResultArray(i) = new ResultBox(Color.WHITE, Color.BLACK)
    }

    val resultColumn = new GridPanel(10, 2) { //Right-hand panel for results
      preferredSize = new Dimension(70, 400)
      background = Color.black
      for (i <- 9 to 0 by -1) {
        contents += redResultArray(i)
        contents += whiteResultArray(i)
      }
    }

    val rowPatterns = new GridPanel(10, 4) { //Main panel for patterns
      preferredSize = new Dimension(300, 400)
      for (i <- 9 to 0 by -1) {
        for (j <- 0 to 3) {
          contents += fullGameArray(i)(j)
        }
      }
    }

    val topLabel = new Label {
      preferredSize = new Dimension(400, 30)
      text = "Mastermind"
      font = new Font("Ariel", java.awt.Font.ITALIC, 24)
    }

    val restart = new Button {
      preferredSize = new Dimension(90, 60)
      text = "Restart"
      foreground = Color.white
      background = Color.black
      borderPainted = true
      enabled = true
      tooltip = "Click to restart the game"
    }

    val topPanel = new BorderPanel {
      layout(topLabel) = Center
      layout(restart) = East
    }

    val mainPanel = new BorderPanel {
      layout(topPanel) = North
      layout(rowPatterns) = Center
      layout(resultColumn) = East
    }

    val winLoseText = new Label {
      background = Color.white
      preferredSize = new Dimension(400, 15)
      text = "Find the pattern!"
    }

    val resultText = new Label {
      background = Color.white
      preferredSize = new Dimension(400, 15)
      text = ""
    }

    val slot1Box = new ComboBox(mastermindGame.colorList)
    val slot2Box = new ComboBox(mastermindGame.colorList)
    val slot3Box = new ComboBox(mastermindGame.colorList)
    val slot4Box = new ComboBox(mastermindGame.colorList)
    val submitMove = new Button {
      text = "Submit"
      foreground = Color.white
      background = Color.black
      borderPainted = true
      enabled = true
      tooltip = "Click to submit your selected pattern"
    }

    val patternSelectPanel = new GridPanel(1, 5) {
      preferredSize = new Dimension(400, 55)
      contents += slot1Box
      contents += slot2Box
      contents += slot3Box
      contents += slot4Box
      contents += submitMove
    }

    val bottomPanel = new BorderPanel {
      layout(winLoseText) = North
      layout(resultText) = Center
      layout(patternSelectPanel) = South
      preferredSize = new Dimension(400, 100)
    }

    contents = new BorderPanel {
      layout(mainPanel) = North
      layout(bottomPanel) = South
    }
    size = new Dimension(400, 650)
    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Exit") {
          sys.exit(0)
        })
      }
    }

    listenTo(submitMove)
    listenTo(restart)

    reactions += {
      case ButtonClicked(component) if component == submitMove =>
        userInputLog = userInputLog :+ Map((1, slot1Box.item), (2, slot2Box.item),
          (3, slot3Box.item), (4, slot4Box.item))
        trialResultLog = trialResultLog :+ mastermindGame.evaluateATrial(userInputLog(turnCounter), pattern)
        fullGameArray(turnCounter)(0).color = mastermindGame.colorMap(slot1Box.item)
        fullGameArray(turnCounter)(1).color = mastermindGame.colorMap(slot2Box.item)
        fullGameArray(turnCounter)(2).color = mastermindGame.colorMap(slot3Box.item)
        fullGameArray(turnCounter)(3).color = mastermindGame.colorMap(slot4Box.item)
        redResultArray(turnCounter).text = trialResultLog(turnCounter)._1.toString
        whiteResultArray(turnCounter).text = trialResultLog(turnCounter)._2.toString
        turnCounter += 1
        if (trialResultLog.contains((4, 0))) {
          turnCounter = 10
          winLoseText.text = "You win! Click restart to play again!"
        } else if (turnCounter > 9 && !trialResultLog.contains((4, 0))) {
          winLoseText.text = "You lose. Click restart to try again!"
        }
        if (turnCounter == 10) {
          resultText.text = "Solution was: " +
            pattern(1) + ", " + pattern(2) + ", " + pattern(3) + ", " + pattern(4)
          deafTo(submitMove)
        }
        repaint()
      case ButtonClicked(component) if component == restart =>
        mastermindGame = new Mastermind()
        listenTo(submitMove)
        winLoseText.text = "Find the pattern!"
        resultText.text = ""
        turnCounter = 0
        pattern = mastermindGame.randomPatternGenerator
        userInputLog = List()
        trialResultLog = List()
        for (i <- 0 to 9; j <- 0 to 3) {
          fullGameArray(i)(j).color = Color.gray
        }
        for (i <- 0 to 9) {
          redResultArray(i).text = ""
          whiteResultArray(i).text = ""
        }
        repaint()
    }
  }
}
