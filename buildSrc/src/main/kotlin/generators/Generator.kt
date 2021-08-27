package generators

import schema.Manifest
import schema.Manifest2

interface Generator {

    fun generate(namespace: Manifest.Namespace)

    val path
        get() = "/home/lbrenner/projects/stonesoup/src"

}

interface Generator2 {

    fun generate(namespace: Manifest2.Namespace)

    val path
        get() = "/home/lbrenner/projects/stonesoup/src"

}

