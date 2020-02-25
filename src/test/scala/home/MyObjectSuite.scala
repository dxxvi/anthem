package home

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should._
import Matchers._

class MyObjectSuite extends AnyFunSuite {
    test("testing MyObject.reverse ...") {
        MyObject.reverse(Nil) shouldBe Nil

        MyObject.reverse(List("a")) shouldBe List("a")

        MyObject.reverse(List("a", "b", "c", "d")) shouldBe List("d", "c", "b", "a")
    }

    test("testing transform ...") {
        MyObject.transform("") shouldBe ""

        MyObject.transform("a") shouldBe "a"

        MyObject.transform("aa") shouldBe "2a"

        MyObject.transform("aBBcccD") shouldBe "a2B3cD"
    }

    test("testing reverseTransform ...") {
        MyObject.reverseTransform("") shouldBe ""

        MyObject.reverseTransform("a") shouldBe "a"

        MyObject.reverseTransform("3a") shouldBe "aaa"

        MyObject.reverseTransform("a3B2C") shouldBe "aBBBCC"
    }
}
