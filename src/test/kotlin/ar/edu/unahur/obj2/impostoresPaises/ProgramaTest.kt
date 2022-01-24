package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.api.*
import ar.edu.unahur.obj2.impostoresPaises.cli.Consola
import ar.edu.unahur.obj2.impostoresPaises.cli.Programa
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.mockk.*

class ProgramaTest: DescribeSpec ({
  describe("En ejecucion del programa"){
    val consola = mockk<Consola>()
    val api = mockk<RestCountriesAPI>()
    val apiCurrency = mockk<CurrencyConverterAPI>()
    val programa = Programa(apiPaises=api,entradaSalida= consola)

    every { consola.escribirLinea(any()) } just Runs // atrapamos el ecribir linea.
    // creamos paises
    every { api.paisConCodigo("ARG") } returns
            Country("Argentina", "ARG", "Buenos Aires", "Americas", 45376763,
              2780400.0, listOf("BRA"), listOf(Language("Spanish"), Language("Guaraní")),
              listOf(RegionalBloc("USAN", "Union of South American Nations")),
              listOf(Currency("ARS")))
    every { api.buscarPaisesPorNombre("Argentina") } returns listOf(
      api.paisConCodigo("ARG")
    )

    every { api.paisConCodigo("BRA") } returns
            Country("Brazil", "BRA", "Brasília", "Americas", 212559409, 8515767.0,
              listOf("ARG"),
              listOf(Language("Portuguese")), listOf(RegionalBloc("USAN", "Union of South American Nations")),
              listOf(Currency("BRL")))
    every { api.buscarPaisesPorNombre("Brazil" ) } returns listOf(
      api.paisConCodigo("BRA")
    )

    every { api.paisConCodigo("CUB") } returns
            Country("Cuba", "CUB", "Havana", "Americas", 11326616,
              109884.0, listOf(), listOf(Language("Spanish")),
              listOf(),
              listOf(Currency("CUC")))
    every { api.buscarPaisesPorNombre("Cuba") } returns listOf(
      api.paisConCodigo("CUB")
    )

    every { api.todosLosPaises() } returns listOf(
      api.paisConCodigo("CUB"),
      api.paisConCodigo("BRA"),
      api.paisConCodigo("ARG"),
    )
    // declaramoslos tipos de cambio.
    every { apiCurrency.convertirDolarA("ARS") } returns 170.0
    every { apiCurrency.convertirDolarA("BRL") } returns 5.0
    every { apiCurrency.convertirDolarA("CUC") } returns 300.0

    it("No existe el pais Argola"){
      every { api.buscarPaisesPorNombre("Argola") } returns  emptyList()
      Observatorio().existe("Argola").shouldBeFalse()
    }
    it("opcion 1: Son limitrofes"){
      every { consola.leerLinea() } returnsMany listOf("1", "Argentina",  "Brazil")
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del pais 1")
        consola.escribirLinea("Por favor, escribí el nombre del pais 2")
        consola.escribirLinea("son limítrofes.\n")
      }
    }
    it("opcion 2: nesecitan traduccion."){
      every { consola.leerLinea() } returnsMany listOf("2", "Argentina",  "Brazil")
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del pais 1")
        consola.escribirLinea("Por favor, escribí el nombre del pais 2")
        consola.escribirLinea("nesecitan traduccion.\n")
      }
    }
    it("opcion 3: son potenciales aliados."){
      every { consola.leerLinea() } returnsMany listOf("3", "Argentina",  "Brazil")
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del pais 1")
        consola.escribirLinea("Por favor, escribí el nombre del pais 2")
        consola.escribirLinea("No son potenciales aliados.\n")
      }
    }
    it("opcion 4: conviene ir de compras."){
      every { consola.leerLinea() } returnsMany listOf("4", "Argentina",  "Brazil")
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del pais 1")
        consola.escribirLinea("Por favor, escribí el nombre del pais 2")
        consola.escribirLinea("No conviene ir de compras.\n")
      }
    }
    it("opcion 5: convertirMoneda de un pais a otro"){
      every { consola.leerLinea() } returnsMany listOf("5", "Argentina", "Brazil", "1000" )
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("ese monto en la moneda de Brazil es: 1000.0")
      }
    }
    it("opcion 6: paises De Mayor Densidad"){
      every { consola.leerLinea() } returns "6"
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("Los códigos ISO de los cinco paises más poblados son: [BRA, ARG, CUB] \n")
      }
    }
    it("opcion 7: Continente Con Mas Paises Plurinacionales"){
      every { consola.leerLinea() } returns "7"
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea( "El continente con más paises plurinacionales es: Americas " + "\n")
      }
    }
    it("opcion 8: promedio Poblacion en Islas"){
      every { consola.leerLinea() } returns "8"
      programa.procesoPedidoYRespuesta()
      verify {
        consola.escribirLinea("El promedio de poblacion en islas es de: 11326616 personas \n")
      }
    }
  }
})