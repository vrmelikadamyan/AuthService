package model

object AuthError {

  final case class UserCreated(token: String)

  final case class SendActivation(token: String)

  final case class RuntimeError(e: Throwable)

  sealed trait UserCreationError

  case object UserAlreadyExists extends UserCreationError

  final case class SignedUp()

  sealed trait SignedUpError

  case object TokenExpired
    extends SignedUpError

  case object UserNotFound
    extends SignedUpError

  case object TokenNotFound
    extends SignedUpError

  case object InvalidToken
    extends SignedUpError

  case object MethodNotImplemented
    extends SignedUpError with UserCreationError

}
