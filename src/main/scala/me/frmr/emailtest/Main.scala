package me.frmr.emailtest

import net.liftweb.common._
import net.liftweb.util._
  import Mailer._

import scala.io._

import javax.mail._

object PasswordAuthenticator extends Authenticator {
  override def getPasswordAuthentication =
    new PasswordAuthentication(Props.get("username").openOr(""), Props.get("password").openOr(""))
}

object Main extends App {
  println("Lift Framework email test.")
  print("Enter an email address for me to hit: ")
  val email = readLine()

  val from = From(Props.get("mail.from").openOr("bacon@bacon.com"))

  Mailer.authenticator = Full(PasswordAuthenticator)

  // Construct an email with both embedded images and PDF attachments.
  val emailNodeSeq = <html>
    <h1>This is a test email.</h1>
    <img src="cid:bacon.png" /><br /><br />
    There is also an embedded pdf. Enjoy.
  </html>

  val baconDotPng = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("bacon.png"), "ISO-8859-1").map(_.toByte).toArray
  val baconDotPdf = scala.io.Source.fromInputStream(getClass.getClassLoader.getResourceAsStream("bacon.pdf"), "ISO-8859-1").map(_.toByte).toArray

  val pngAttachment = PlusImageHolder("bacon.png", "image/png", baconDotPng, false)
  val pdfAttachment = PlusImageHolder("bacon.pdf", "application/pdf", baconDotPdf, true)

  val emailPart = XHTMLPlusImages(emailNodeSeq, pngAttachment, pdfAttachment)

  blockingSendMail(from, Subject("test email from lift"), To(email, Full("Test user")), emailPart)
}
