package models

import schema.*

val manifest = Manifest2 {

    val builtIn = Namespace("BuiltIn") {
        //TODO: This is a fine place for type alias to be generated we could do stuff like:
        //    SimpleType("Id", Int::class)
        //    SimpleType("PhoneNumber", String::class)
        // which could generate:
        //    typealias Id = Int
        //    typealias PhoneNumber = String::class
        // we could flag this with isAlias.
        // another output option is:
        //    value class PhoneNumber(private val s: String)
        //    val mobile = PhoneNumber("1-718-247-9234")
        SimpleType("int", Int::class)
        SimpleType("string", String::class)
        SimpleType("long", Long::class)
        SimpleType("double", Double::class)
    }
    builtIn {
        SimpleType("float", Float::class)
    }

    val seeds = Namespace("Seeds")
    seeds {

        //Todo - support forward references so Schedule can be referenced after Chore. This means we should fail during
        //  validation. I would be nice to capture the line number where DSL occurred but we can handle this with
        //  something like: LinkException Chore.schedule referrenced type Seeds.Schedule which is never defined.
        ComplexType("Schedule") {
            Element("id", builtIn["int"])
            Element("choreId", builtIn["int"]) {
                default = 0 //Todo - add support in InterfaceGenerator
            }
            Element("workHours", builtIn["string"]) {
                minOccurs = 0
            }
            Element("completeBy", builtIn["string"]) {
                minOccurs = 0
            }
        }

        ComplexType("Chore", isTable = true) { //Todo - once isTable flag is implemented replicate on other types
            Element("id", builtIn["int"])
            Element("parentId", builtIn["int"]) {
                default = 0
            }
            Element("childrenIds", builtIn["string"]) //Todo - make this a list of Ids
            Element("name", builtIn["string"])
            Link("schedule", seeds["Schedule"], JoinType.LEFT) { //Todo => ).left {
                //Todo - id == Schedule.choreId
            }
        }

        ComplexType("DetailedSeed") {
            Element("id", builtIn["int"])
            Element("name", builtIn["string"])
            Element("maturity", builtIn["string"]) {
                minOccurs = 0
            }
            Element("secondary_name", builtIn["string"]) {
                minOccurs = 0
            }
            Element("description", builtIn["string"]) {
                minOccurs = 0
            }
            Element("image", builtIn["string"]) {
                minOccurs = 0
            }
            Element("link", builtIn["string"]) {
                minOccurs = 0
            }
        }

        ComplexType("MySeeds") {
            Element("id", builtIn["int"])
            Element("seed_label", builtIn["string"])
            Element("description", builtIn["string"])
            Element("germination_test", builtIn["string"])
        }

        ComplexType("SeedCategory") {
            Element("id", builtIn["int"])
            Element("name", builtIn["string"])
            Element("image", builtIn["string"])
            Element("link", builtIn["string"])
        }

    }
    seeds {
        //Todo - Implement generator for this.
        //Todo - Create a CRUD template it should be enough to say Service("chore")
        Resource(seeds["Chore"]) {
        //    post {
        //        Parameter("parentId", builtIn["int"]) {
        //            default = 0
        //        }
        //        Parameter("childrenIds", builtIn["string"])
        //        Parameter("name", builtIn["string"])
        //        ReturnType(builtIn["int"])
        //    }
        //    get {
        //        Parameter("id", builtIn["int"])
        //        ReturnType(seeds["Chore"])
        //    }
        //    put {
        //        Parameter("id", builtIn["int"])
        //        Parameter("parentId", builtIn["int"]) {
        //            default = 0
        //        }
        //        Parameter("childrenIds", builtIn["string"])
        //        Parameter("name", builtIn["string"])
        //        //TODO - perhaps this could included lastUpdate: OffsetDateTime
        //    }
        //    delete {
        //        Parameter("id", builtIn["int"])
        //    }
        }

    }
}