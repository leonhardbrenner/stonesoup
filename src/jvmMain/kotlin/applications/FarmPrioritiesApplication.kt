package applications

import com.google.inject.AbstractModule
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import models.Chore
import org.litote.kmongo.eq
import javax.inject.Inject
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.ChoreUpdate
import org.litote.kmongo.addToSet
import org.litote.kmongo.pullByFilter
import org.litote.kmongo.setValue

class FarmPrioritiesApplication @Inject constructor(val service: Service) {

    fun routesFrom(routing: Routing) = routing.apply {
        route(Chore.path) {

            get {
                call.respond(service.get())
            }

            post {
                val item = call.receive<Chore>()
                service.add(item)
                call.respond(HttpStatusCode.OK)
            }

            put("/{id}") {
                val item = call.receive<ChoreUpdate>()
                service.update(item)
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
                service.delete(id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    object Module : AbstractModule() {

        override fun configure() {
            bind(CoroutineDatabase::class.java).toInstance(database())
        }

        fun database(): CoroutineDatabase {
            val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
                ConnectionString("$it?retryWrites=false")
            }
            val client = connectionString?.let {
                KMongo.createClient(connectionString).coroutine
            } ?: KMongo.createClient().coroutine
            return client.getDatabase(connectionString?.database ?: "test")
        }

    }

    class Service @Inject constructor(val database: CoroutineDatabase) {
        val collection
            get() = database.getCollection<Chore>()

        suspend fun get(): List<Chore> {
            val chores = collection.find().toList()
            //val view = TreeView(0, chores.map {it.id!! to it }.toMap())
            return chores
        }

        suspend fun add(item: Chore) {
            //collection.insertOne(Chore("<root>", id = 0)) //XXX - This need to be made upon creation of collection
            collection.updateOne(
                Chore::id eq 0, //XXX
                addToSet(Chore::childrenIds, item.id!!)
            )
            collection.insertOne(item)
        }

        /**
         * Todo
         * Tomorrow you should do the following:
         *     item.path should be used to lookup our node
         *     the node id should then be used for our update
         *     let's start with move then do link
         *     display on the front end more like a graph
         *     create a link routine something as simple as little x(es) that connect
         *     make a routine do delete as well
         *     make a priority widget something like a +/-
         *     make a box for real description
         *     we need a field for time estimates
         *     move to tornadoFx
         */
        suspend fun update(item: ChoreUpdate) {
            val formerParent = collection.findOne(Chore::id eq item.id)!!.parentId
            collection.updateOne(
                Chore::id eq formerParent,
                pullByFilter(Chore::childrenIds eq item.id!!)
            )
            collection.updateOne(
                Chore::id eq item.moveTo,
                addToSet(Chore::childrenIds, item.id!!)
            )
            collection.updateOne(
                Chore::id eq item.id,
                setValue(Chore::parentId, item.moveTo!!)
            )
            /**
             * Find our path by looking for our name then comparing the paths.
             *     build a name to nodes map
             *     build path in MongoDb
             *     nodes[name].mapIndexed { (i, node) -> if (node.path) }
             */

        }
        suspend fun delete(id: Int) = collection.deleteOne(Chore::id eq id)
    }
}