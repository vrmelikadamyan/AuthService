import java.time.LocalDateTime

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import model.{Activated, Deactivated, Login, User, UserState}

import scala.collection.mutable
import scala.util.Random

// Mock отправки оповещения
object Notification {
  def sendNotification(email: String, text: String) = {}
}

object AuthService {

  val tockens = mutable.HashMap.empty[String, LocalDateTime]
  val users = mutable.HashMap.empty[String, User]
  val logins = mutable.HashMap.empty[String, Login]
  val tokenLifeTimeInHours = 3

  // сервисы
  def notificationService = Notification

  //
  // 1. Создать пользователя, отправить пользователю код подтверждения
  // - Надо проверить существует пользователь или нет
  // - Проверить что email указан и это email
  // - Проверить что остальные данные заполенны
  // - Создать пользователя, пользователь должен создаваться не активным
  // - Создать запись о регистрации логин/пароль
  // - Отправить токен активации, если уже был отправлен послать новый
  //
  // Возвращаем подтверждение или текст ошибки
  // { "error": [...] }
  // { "success": "user [...] created" }
  def startSignUp(signUpDTO: Json) = {
    signUpDTO.as[Map[String, String]] match {
      case Left(_) => Map("error" -> "invalid JSON").asJson
      case Right(data) =>
        if (validateData(data)) {
          if (isUserRegistred(data("login"))) Map("error" -> "user is already registered").asJson else {
            val token = createToken
            createUser(data, token)
            createRegistrationRecord(data("login"), data("password"))
            sendToken(data("email"), token)
            Map("success" -> s"user ${data("login")} created").asJson
          }
        } else Map("error" -> "invalid data").asJson
    }
  }

  def startSignUp1(signUpDTO: Json) = {
    for {
      i <- unpackJson(signUpDTO)
    } yield i
  }

  def unpackJson(json: Json) =
    json.as[Map[String, String]].map(Right(_)).getOrElse(Left(Map("error" -> "invalid data").asJson))

  def validateData(value: Map[String, String]) = {
    if (hasEmptyFields(value)) false else {
      validateEmail(value("email"))
    }
  }

  def hasEmptyFields(value: Map[String, String]) = {
    value.exists(field => field._2 == "")
  }

  def validateEmail(email: String) = {
    email.matches("(\\w+[.-]?\\w+@\\w+\\.\\w+)")
  }

  def isUserRegistred(login: String) = logins.contains(login)

  def createUser(data: Map[String, String], token: String): Unit =
    users += token -> User(data("login"), data("firstName"), data("secondName"),
      data("middleName"), data("email"), Deactivated)

  def createRegistrationRecord(login: String, password: String): Unit =
    logins += login -> Login(login, password)

  def sendToken(email: String, token: String): Unit = {
    notificationService.sendNotification(email, token)
  }

  def createToken: String = {
    val token = Random.alphanumeric.take(30).mkString
    if (tockens.contains(token)) createToken else {
      tockens += token -> LocalDateTime.now()
      token
    }
  }

  // 2. Активировать пользователя, завершив процедуру регистрации
  // - Надо проверить существует token или нет
  // - Надо проверить, что token действует и является последним
  // - Надо проверить, что пользователь создан
  // - Надо проверить, что пользователь находиться в состоянии не активирован
  // - Активироваь пользователя
  // - Отправлем пользователю уведомление, о том что пользователь активирован и приглашение
  //
  // Возвращаем подтверждение или текст ошибки
  // { "error": [...] }
  // { "success": "user [...] registred" }
  def signUp(token: String) = {
    if (validateToken(token)) {
      if (users.contains(token)) {
        if (users(token).state == Deactivated) {
          users(token) = activateUser(users(token))
          notificationService.sendNotification(users(token).email, "user activated!")
          Map("sucess" -> "user activated").asJson
        } else Map("error" -> "user is already activated").asJson
      } else Map("error" -> "user does not exist").asJson
    } else Map("error" -> "token does not exist").asJson
  }

  def validateToken(token: String) = {
    if (tockens.contains(token)) validateTokenLifeTime(token) else false
  }

  def validateTokenLifeTime(token: String, currentDate: LocalDateTime = LocalDateTime.now()) = {
    if (tockens(token).plusHours(tokenLifeTimeInHours).compareTo(currentDate) > 0) true else false
  }

  def activateUser(user: User) = user.copy(state = Activated)

  // 3. Входим в систему
  // - Передаем пару логин/пароль на выходе получаем идентификатор сессии
  // Возвращаем идентификатор сессии или ошибку
  // { "error": [...] }
  // { "success": [sid] }
  def signIn(user: String, password: String) = ???

}