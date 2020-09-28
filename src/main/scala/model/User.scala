package model

sealed trait UserState
case object Deleted extends UserState
case object Created extends UserState
case object Activated extends UserState
case object Deactivated extends UserState

case class User(login: String,
                firstName: String,
                secondName: String,
                middleName: String,
                email: String,
                state: UserState
               )
