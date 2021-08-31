import org.junit.Test
import schema.ManifestOld
import models.Flat
import models.Fancy
import generators.InterfaceGeneratorOld

class TestManifestOld {
    @Test
    fun test() {

        val flat = ManifestOld.Namespace(Flat::class)
        InterfaceGeneratorOld.generate(flat)

        val fancy = ManifestOld.Namespace(Fancy::class)
        InterfaceGeneratorOld.generate(fancy)
    }
}