package applications

import com.google.inject.AbstractModule
import javax.inject.Inject
import io.ktor.routing.*
//import generated.routing.AtmRouting

class CoreApplication @Inject constructor(
    //val authorizationPin: AtmRouting.AuthorizationPin,
    //val authorizationToken: AtmRouting.AuthorizationToken,
    //val ledger: AtmRouting.Ledger
    ) {

    fun routesFrom(routing: Routing) {
        //authorizationPin.routes(routing)
        //authorizationToken.routes(routing)
        //ledger.routes(routing)
    }

    object Module : AbstractModule() {

        override fun configure() {
            //Stonesoup PR 10 removed the rest database and all MongoDB wiring.
            //bind(CoroutineDatabase::class.java).toInstance(database())
        }

    }

}
