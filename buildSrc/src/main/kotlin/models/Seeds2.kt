package models

import schema.Manifest2

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

    val seeds = Namespace("Seeds") {

        ComplexType("Chore") {
            Element("id", builtIn["int"])
            Element("parentId", builtIn["int"]) {
                default = 0
            }
            Element("childrenIds", builtIn["string"])
            Element("name", builtIn["string"])
            //XXX needed: Element("name", namespace.types["Schedule"]!!)
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

        ComplexType("Schedule") {
            Element("id", builtIn["int"])
            Element("choreId", builtIn["int"]) {
                default = 0
            }
            Element("workHours", builtIn["string"]) {
                minOccurs = 0
            }
            Element("completeBy", builtIn["string"]) {
                minOccurs = 0
            }
        }

        ComplexType("SeedCategory") {
            Element("id", builtIn["int"])
            Element("name", builtIn["string"])
            Element("image", builtIn["string"])
            Element("link", builtIn["string"])
        }


    }
}