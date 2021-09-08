package generators

import schema.Manifest

interface Generator {

    fun generate(namespace: Manifest.Namespace)

    val path
        get() = "/home/lbrenner/projects/stonesoup/src" //Todo - move to config

}
