package controllers

import blockchain.models.Blockchain
import play.api.libs.json.Json
import javax.inject._
import play.api.mvc._

@Singleton
class TransactionController @Inject()(
    val controllerComponents: ControllerComponents)
    extends BaseController {

  def getAll(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val transactions = Blockchain.transactions
      Ok(Json.toJson(transactions))
  }

  def getByHash(txHash: String) = Action {
    val foundTx = Blockchain.transactions.find(_.hash == txHash)
    foundTx match {
      case Some(tx) => Ok(Json.toJson(tx))
      case None     => NotFound
    }
  }
}
