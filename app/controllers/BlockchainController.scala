package controllers

import blockchain.models.{Blockchain, Wallet}
import play.api.libs.json.Json
import play.api.mvc._

import javax.inject._

@Singleton
class BlockchainController @Inject()(
    val controllerComponents: ControllerComponents)
    extends BaseController {

  def simulation(): Action[AnyContent] = Action {
    val walletA = new Wallet()
    val walletB = new Wallet()

    walletA.send(walletB.address, 10, "First transaction")

    println(s"Balance of wallet A: ${walletA.balance}")
    println(s"Balance of wallet B: ${walletB.balance}")

    walletA.send(walletB.address, 10, "First transaction")
    walletA.send(walletB.address, 15, "First transaction")
    walletA.send(walletB.address, 25, "First transaction")

    Ok("Simulation succeded")
  }
}
