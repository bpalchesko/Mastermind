package mastermind

object TextMastermind {

  def main(args: Array[String]): Unit = {
    var playGame = true

    while (playGame) {
      val mastermindText = new Mastermind
      val pattern = mastermindText.randomPatternGenerator
      print("the code for this game is "); print(pattern)

      var solved = false
      //Initiate the following two lists,
      //which are used to keep track of the player input and result of each trial.
      var userInputLog: List[Map[Int, String]] = List()
      var eachTrialResultLog: List[(Int, Int)] = List()

      for (i <- 0 until 10 if !solved) {

        //at the beginning of each trial, all the @trialInput method to invite the player to type in color selection.
        val thisTrialUserInput = trialInput
        println(thisTrialUserInput)

        //this trial's user input is stored in @userInputLog
        userInputLog = userInputLog :+ thisTrialUserInput
        println(userInputLog)

        val thisTrialResult = mastermindText.evaluateATrial(thisTrialUserInput, pattern)
        println("red: " + thisTrialResult._1 + "; whites: " + thisTrialResult._2)
        //this trial's result is stored in @eachTrialResultLog
        eachTrialResultLog = eachTrialResultLog :+ thisTrialResult

        if (thisTrialResult == (4, 0)) {
          println("You win!")
          solved = true
        }
      }

      if (!solved) println("Sorry, you loose. Feel free to play again.")
      if (readLine("Do you want to play again (Y/N)?") == "N_*") playGame = false
    }
  }

  def trialInput: Map[Int, String] = {
    var trialInputMap = Map[Int, String]()
    val position01 = readLine("What color to fill the 1st position? Red/Orange/Yellow/Green/Blue/Purple")
    trialInputMap += (1 -> position01)
    val position02 = readLine("What color to fill the 2nd position? Red/Orange/Yellow/Green/Blue/Purple")
    trialInputMap += (2 -> position02)
    val position03 = readLine("What color to fill the 3rd position? Red/Orange/Yellow/Green/Blue/Purple")
    trialInputMap += (3 -> position03)
    val position04 = readLine("What color to fill the 4th position? Red/Orange/Yellow/Green/Blue/Purple")
    trialInputMap += (4 -> position04)
    trialInputMap
  }

}