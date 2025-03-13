package controllers

import blockchain.models.Blockchain
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._

@Singleton
class BlockController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

  def getAll(): Action[AnyContent] = Action {
      Ok(Json.toJson(Blockchain.chain))
  }

  def getByHash(blockHash: String) = Action {
    val foundBlock = Blockchain.chain.find(_.hash == blockHash)
    foundBlock match {
      case Some(block) => Ok(Json.toJson(block))
      case None        => NotFound
    }
  }

  def getLatest() = Action {
    Ok(Json.toJson(Blockchain.latestBlock))
  }
}
