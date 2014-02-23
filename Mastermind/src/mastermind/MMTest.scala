package mastermind

import org.scalatest._
import mastermind.Mastermind

class MMTest extends FunSuite {

 val mastermind1 = new Mastermind
  
 test("countColorMatches") {
 assert( mastermind1.countColorMatches(List("Yellow"), List("Yellow")) == 1 )
 assert( mastermind1.countColorMatches(List("Yellow", "Blue"), List("Yellow")) == 1 )
 assert( mastermind1.countColorMatches(List("Yellow", "Blue"), List("Yellow", "Blue")) == 2 )
 assert( mastermind1.countColorMatches(List("Yellow", "Blue", "Yellow"), List("Yellow", "Blue")) == 2 )
 assert( mastermind1.countColorMatches(List("Yellow", "Blue", "Yellow"), List("Yellow", "Blue", "Yellow")) == 3 )
 }
  
 
 test("evaluateATrial01") {
 val user = Map(1->"Blue", 2 ->"Yellow", 3 -> "Yellow", 4 -> "Orange")
 val code = Map(1->"Red", 2 ->"Yellow", 3 -> "Blue", 4 -> "Orange")
 assert( mastermind1.evaluateATrial(user, code) == (2,1))
 }
 
 test("evaluateATrial02") {
 val user = Map(1->"Red", 2 ->"Red", 3 -> "Red", 4 -> "Red")
 val code = Map(1->"Red", 2 ->"Red", 3 -> "Blue", 4 -> "Orange")
 assert( mastermind1.evaluateATrial(user, code) == (2,0))
 }
 
   test("evaluateATrial03") {
 val user = Map(1->"Blue", 2 ->"Yellow", 3 -> "Yellow", 4 -> "Blue")
 val code = Map(1->"Yellow", 2 ->"Blue", 3 -> "Blue", 4 -> "Yellow")
 assert( mastermind1.evaluateATrial(user, code) == (0,4))
 }
    test("evaluateATrial04") {
 val user = Map(1->"Red", 2 ->"Yellow", 3 -> "Red", 4 -> "Orange")
 val code = Map(1->"Red", 2 ->"Red", 3 -> "Blue", 4 -> "Blue")
 assert( mastermind1.evaluateATrial(user, code) == (1,1))
 }
 
 
 
}