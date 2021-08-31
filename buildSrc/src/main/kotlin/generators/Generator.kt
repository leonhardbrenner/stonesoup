package generators

import schema.ManifestOld
import schema.Manifest

interface Generator {

    fun generate(namespace: Manifest.Namespace)

    val path
        get() = "/home/lbrenner/projects/stonesoup/src" //Todo - move to config

}

@Deprecated("All Manifests and Generators with suffix Old are depricated in favor of fluent API.")
interface GeneratorOld {

    fun generate(namespace: ManifestOld.Namespace)

    val path
        get() = "/home/lbrenner/projects/stonesoup/src" //Todo - move to config

}

