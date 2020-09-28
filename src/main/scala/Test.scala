//import scala.util.Try
//
//object Test extends App {
//  // *****************************************
//  case class Car(model: String,
//                 owner: Option[Person],
//                 registrationPlate: Option[String])
//
//  case class Person(name: String,
//                    age: Int,
//                    drivingLicense: Option[String])
//
//  def ownerDrivingLicense1(optCar: Option[Car]): Option[String] =
//    optCar.flatMap(car =>
//      car.owner.flatMap(p =>
//        p.drivingLicense))
//
//  def ownerDrivingLicense2(optCar: Option[Car]): List[String] =
//    for {
////      item <- List("1.0", "2.0", "3.0")
//      y <- List(1, 2, 3)
//      car <- optCar
//      person <- car.owner
//      drivingLicense <- person.drivingLicense
//      x <- List(1, 2, 3)
//    } yield drivingLicense
//
//
//  def ownerDrivingLicense3(optCar: Option[Car], ownerName: String): Option[String] =
//    optCar.flatMap(car => {
//      car.owner.flatMap(p => {
//        if (p.name == ownerName) p.drivingLicense.map(_.toUpperCase)
//        else None
//      })
//    })
//
//  def ownerDrivingLicense4(optCar: Option[Car], ownerName: String): Option[String] =
//    for {
//      car <- optCar
//      owner <- car.owner
//      if owner.name == ownerName
//      license <- owner.drivingLicense
//    } yield license.toUpperCase
//  // *****************************************
//
//  // *****************************************
//  def parseDouble(s: String): Either[String, Double] =
//      Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not double!"))
//
//  def divide(a: Double, b: Double): Either[String, Double] =
//      Either.cond(b != 0, a / b, "b can't be zero")
//
//
//  def divisionProgram(a: String, b: String): Either[String, Double] =
//        for {
//          item <- List("1.0", "2.0", "3.0")
//          inA <- parseDouble(a)
//          inB <- parseDouble(b)
//          tmp <- Right(5.0)
//          result <- divide(inA, inB)
//          x <- Right(List(1.0, 2.0, 3.0))
//        } yield result + tmp
//
//    println(divisionProgram("4", "2"))
//  // *****************************************
//
//  // *****************************************
////    val myList = 1 :: 2 :: 3 :: Nil
////    val mySeq = Seq(1, 2, 3)
////    val mySet = Set(1, 2)
////
////    for {
////      x <- myList
////      y <- mySeq
////      z <- mySet
////    } println(s"x = $x, y = $y, z = $z")
//  // *****************************************
//
//}
