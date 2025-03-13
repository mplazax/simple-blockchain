package controllers

import blockchain.models.Blockchain
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._

@Singleton
class WalletController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  def create(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val transactions = Blockchain.transactions
      Ok(Json.toJson(transactions))
  }
}
