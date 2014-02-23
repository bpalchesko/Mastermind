package mastermind

import scala.util.Random
import java.awt.{ Color, Graphics2D }

class Mastermind {

  val colorList = List("Red", "Orange", "Yellow", "Green", "Blue", "Purple")
  val darkOrange = new Color (250,120,10)
  val javaColorList: List[java.awt.Color] = List(Color.red, darkOrange, Color.yellow,
      Color.green, Color.blue, Color.magenta)
  val colorMap = colorList.zip(javaColorList).toMap

  
  /**
   * Generates a random pattern with each game for the player to solve.
   * @randomPattern is a map of Int -> String, where Int refers to the position and String refers to the color.
   * the default type (immutable) of Map is used.
   */
  def randomPatternGenerator: Map[Int, String] = {
    var randomPattern = Map[Int, String]()
    for (i <- 1 to 4) {
      val randomIndex = Random.nextInt(6)
      randomPattern += (i -> colorList(randomIndex))
    }
    randomPattern
  }

  /**
   *  Determines the result (number of reds, number of whites) of each trial the player submits.
   *  the result of @evaluateATrial is a tuple whose 1st element is the number of reds,
   *  2nd element is the number of whites for the given trial.
   */
  def evaluateATrial(input: Map[Int, String], code: Map[Int, String]): (Int, Int) = {
    var reds = 0
    var inputColors: List[String] = List()
    var codeColors: List[String] = List()
    for (i <- input) {
      //convert the user input and code into two list of colors
      inputColors = inputColors :+ input(i._1)
      codeColors = codeColors :+ code(i._1)
      //if the user input and code have the same color at the same position, count of reds +1
      if (input(i._1) == code(i._1)) {
        reds += 1
      }
    }
    var colorMatches = countColorMatches(inputColors, codeColors)
    (reds, (colorMatches - reds))
  }

  /**
   * Counts the number of matching colors in each pattern. This is the total
   * number of reds and whites. This method is utilized as the 2nd step of evaluating a trial.
   */
  def countColorMatches(pattern1: List[String], pattern2: List[String]): Int = {
    var colorMatches = 0
    for (j <- colorList) {
      
      //for each possible color prescribed by the colorList, count its occurrence in each list
      //take the less of the 2 numbers as the number of matches for this color
      if (pattern1.count(_ == j) >= pattern2.count(_ == j)) {
        colorMatches += pattern2.count(_ == j)
      } else {
        colorMatches += pattern1.count(_ == j)
      }
    }
    colorMatches
  }
}
