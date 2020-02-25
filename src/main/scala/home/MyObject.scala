package home

object MyObject {
    def reverse[T](list: List[T]): List[T] = {
        @annotation.tailrec
        def f(input: List[T], output: List[T]): List[T] = input match {
            case Nil => output
            case head :: tail => f(tail, head +: output)
          }

        f(list, Nil)
    }

    def transform(s: String): String = {
        @annotation.tailrec
        def f(input: String, temp: String): String = {
            if (input == "") {
                temp
            }
            else {
                val c = input(0)
                val s = input.dropWhile(_ == c)
                val cLength = input.length - s.length
                f(s, temp + (if (cLength == 1) "" else cLength.toString) + c)
            }
        }

        f(s, "")
    }

    def reverseTransform(s: String): String = {
        @annotation.tailrec
        def f(input: String, temp: String): String = {
            if (input == "") {
                temp
            }
            else {
                val numberPart = input.takeWhile(c => c >= '0' && c <= '9')
                val numberPartLength = numberPart.length
                val number = if (numberPart == "") 1 else numberPart.toInt
                val c = input(numberPartLength)
                f(input.substring(numberPartLength + 1), temp + c.toString * number)
            }
        }

        f(s, "")
    }
}
