package com.plaintext.adapters.slick

import com.plaintext.domain._
import org.slf4j.LoggerFactory
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import scala.slick.session.Database
import Database.threadLocalSession
import Q.interpolation

object SlickUserRepository extends SlickSupport {

    val logger = LoggerFactory.getLogger(this.getClass)

    implicit val getUserResult = GetResult(r => User(r.nextInt, new Email(r.nextString), new Password(r.nextString)))

    db.withSession {  
        Q.updateNA(
            """create table TextUser (
                    id int not null primary key,
                    email varchar not null,
                    password varchar not null )"""
         ).execute

        Q.updateNA("""create sequence TextUserSeq""").execute
    }

    createAccount(new Email("admin@admin.com"), new Password("password"))

    db.withSession {  
        logger.debug("Pre-populated users: ")
        Q.queryNA[User]("select * from TextUser") foreach { u =>
            logger.debug(u.toString)
        }
    }

    def createAccount(email: Email, password: Password): Option[User] = 
        try {
            for (
                id <- nextUserId()
            ) yield { 
                val user = new User(id, email, password)
                insert(user)
                user
            }
        }
        catch {
            case e: Throwable => {
                logger.error("Error inserting user into database", e)
                None
            }
        }

    private def nextUserId(): Option[Int] = db.withSession {
        try {
            Q.queryNA[Int]("select TextUserSeq.nextval from dual").firstOption
        }
        catch {
            case e: Throwable => {
                logger.error("Error selecting next user id from sequence", e)
                None
            }
        }
    }

    private def insert(u: User) = db.withSession {
        (Q.u + "insert into TextUser values ("+? u.id +","+? u.email.value +","+? u.password.value +")").execute
    }

    override def destroy() {
        super.destroy()
    }
	
}
/*
package com.typesafe.slick.examples.jdbc

import scala.slick.session.Database
import Database.threadLocalSession
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation

///**
 * A simple example that uses plain SQL queries against an in-memory
 * H2 database. The example data comes from Oracle's JDBC tutorial at
 * http://download.oracle.com/javase/tutorial/jdbc/basics/tables.html.
// */
object PlainSQL extends App {

  // Case classes for our data
  case class Supplier(id: Int, name: String, street: String, city: String, state: String, zip: String)
  case class Coffee(name: String, supID: Int, price: Double, sales: Int, total: Int)

  // Result set getters
  implicit val getSupplierResult = GetResult(r => Supplier(r.nextInt, r.nextString, r.nextString,
    r.nextString, r.nextString, r.nextString))
  implicit val getCoffeeResult = GetResult(r => Coffee(r.<<, r.<<, r.<<, r.<<, r.<<))

  Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver") withSession {

    // Create the tables, including primary and foreign keys
    Q.updateNA("create table suppliers("+
      "id int not null primary key, "+
      "name varchar not null, "+
      "street varchar not null, "+
      "city varchar not null, "+
      "state varchar not null, "+
      "zip varchar not null)").execute
    Q.updateNA("create table coffees("+
      "name varchar not null, "+
      "sup_id int not null, "+
      "price double not null, "+
      "sales int not null, "+
      "total int not null, "+
      "foreign key(sup_id) references suppliers(id))").execute

    // Insert some suppliers
    (Q.u + "insert into suppliers values(101, 'Acme, Inc.', '99 Market Street', 'Groundsville', 'CA', '95199')").execute
    (Q.u + "insert into suppliers values(49, 'Superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460')").execute
    (Q.u + "insert into suppliers values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966')").execute

    def insert(c: Coffee) = (Q.u + "insert into coffees values (" +? c.name +
      "," +? c.supID + "," +? c.price + "," +? c.sales + "," +? c.total + ")").execute

    // Insert some coffees
    Seq(
      Coffee("Colombian", 101, 7.99, 0, 0),
      Coffee("French_Roast", 49, 8.99, 0, 0),
      Coffee("Espresso", 150, 9.99, 0, 0),
      Coffee("Colombian_Decaf", 101, 8.99, 0, 0),
      Coffee("French_Roast_Decaf", 49, 9.99, 0, 0)
    ).foreach(insert)

    // Iterate through all coffees and output them
    println("Coffees:")
    Q.queryNA[Coffee]("select * from coffees") foreach { c =>
      println("  " + c.name + "\t" + c.supID + "\t" + c.price + "\t" + c.sales + "\t" + c.total)
    }

    // Perform a join to retrieve coffee names and supplier names for
    // all coffees costing less than $9.00
    println("Manual join:")
    val q2 = Q.query[Double, (String, String)]("""
      select c.name, s.name
      from coffees c, suppliers s
      where c.price < ? and s.id = c.sup_id
    """)
    // This time we read the result set into a List
    val l2 = q2.list(9.0)
    for (t <- l2) println("  " + t._1 + " supplied by " + t._2)

    // Append to a StaticQuery
    val supplierById = Q[Int, Supplier] + "select * from suppliers where id = ?"
    println("Supplier #49: " + supplierById(49).first)

    def coffeeByName(name: String) = sql"select * from coffees where name = $name".as[Coffee]
    println("Coffee Colombian: " + coffeeByName("Colombian").firstOption)

    def deleteCoffee(name: String) = sqlu"delete from coffees where name = $name".first
    val rows = deleteCoffee("Colombian")
    println(s"Deleted $rows rows")
    println("Coffee Colombian: " + coffeeByName("Colombian").firstOption)
  }
}
*/