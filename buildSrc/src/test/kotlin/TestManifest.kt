import org.junit.Test
import schema.Manifest
import models.Flat
import models.Fancy
import generators.InterfaceGenerator

class TestManifest {
    @Test
    fun test() {

        val flat = Manifest.Namespace(Flat::class)
        InterfaceGenerator.generate(flat)

        val fancy = Manifest.Namespace(Fancy::class)
        InterfaceGenerator.generate(fancy)
    }
}