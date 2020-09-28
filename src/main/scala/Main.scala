import model.{Deactivated, User}
import java.time.LocalDateTime
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

import model.{Login, User}

object Main extends App {
  val testUser = User("vmelik", "Vadim", "Adamyan", "Rafaelovich", "vmelik@naumen.ru", Deactivated)
  val json = testUser.asJson
  val kek = Map("login" -> "vmelik", "password" -> "qwerty", "firstName" -> "Vadim", "secondName" -> "Adamyan",
    "middleName" -> "Rafaelovich", "email" -> "vmelik@naumen.ru")

  val jsonkek = kek.asJson
  println(jsonkek)
//  println(AuthService.startSignUp(jsonkek))
//  println(AuthService.users)
//  println(AuthService.signUp(AuthService.tockens.toList.head._1))
//  println(AuthService.users)

  println(AuthService2.startSignUp1(jsonkek))
  println(AuthService2.users)
}
