package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.api.*
import ar.edu.unahur.obj2.impostoresPaises.cli.Consola
import ar.edu.unahur.obj2.impostoresPaises.cli.Programa
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

class ProgramaTest: DescribeSpec ({
  describe("Consola"){
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
    every { api.buscarPaisesPorNombre("Brazil") } returns listOf(
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

    it("Busca pero no encuentra el pais"){
      every { consola.leerLinea() } returns "1" andThen "arute"
      every { api.buscarPaisesPorNombre("arute") } returns listOf()
      programa.iniciar()
      verify {
        consola.escribirLinea("No existe un pais con el nombre arute")
      }
    }
    it("opcion 1: Son limitrofes"){
      every { consola.leerLinea() } returnsMany listOf("1", "Argentina",  "Brazil")
      programa.iniciar()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del primer pais")
        consola.escribirLinea("Por favor, escribí el nombre del segundo pais")
        consola.escribirLinea("son limítrofes.")
      }
    }
    it("opcion 2: nesecitan traduccion."){
      every { consola.leerLinea() } returnsMany listOf("2", "Argentina",  "Brazil")
      programa.iniciar()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del primer pais")
        consola.escribirLinea("Por favor, escribí el nombre del segundo pais")
        consola.escribirLinea("nesecitan traduccion.")
      }
    }
    it("Opcion 2: pero se ingreso mal un pais"){
      every { consola.leerLinea() } returns "2" andThen "argola" andThen "Argentina"
      every { api.buscarPaisesPorNombre("argola") } returns  listOf()
      programa.iniciar()
      verify {
        consola.escribirLinea("No existe un pais con el nombre argola")
      }
    }
    it("opcion 3: son potenciales aliados."){
      every { consola.leerLinea() } returnsMany listOf("3", "Argentina",  "Brazil")
      programa.iniciar()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del primer pais")
        consola.escribirLinea("Por favor, escribí el nombre del segundo pais")
        consola.escribirLinea("No son potenciales aliados.")
      }
    }
    it("opcion 4: conviene ir de compras."){
      every { consola.leerLinea() } returnsMany listOf("4", "Argentina",  "Brazil")
      programa.iniciar()
      verify {
        consola.escribirLinea("Por favor, escribí el nombre del primer pais")
        consola.escribirLinea("Por favor, escribí el nombre del segundo pais")
        consola.escribirLinea("No conviene ir de compras.")
      }
    }
    it("opcion 5: convertirMoneda de un pais a otro"){
      every { consola.leerLinea() } returnsMany listOf("5", "Argentina", "Brazil", "1000" )
      programa.iniciar()
      verify {
        consola.escribirLinea("ese monto en la moneda de Brazil es: 29.0")
      }
    }
    it("opcion 6: paises De Mayor Densidad"){
      every { consola.leerLinea() } returns "6"
      programa.iniciar()
      verify {
        consola.escribirLinea("Los códigos ISO de los cinco paises más poblados son: [BRA, ARG, CUB]")
      }
    }
    it("opcion 7: Continente Con Mas Paises Plurinacionales"){
      every { consola.leerLinea() } returns "7"
      programa.iniciar()
      verify {
        consola.escribirLinea("El continente con más paises plurinacionales es: Americas")
      }
    }
    it("opcion 8: promedio Poblacion en Islas"){
      every { consola.leerLinea() } returns "8"
      programa.iniciar()
      verify {
        consola.escribirLinea("El promedio de poblacion en islas es de: 11326616 de personas")
      }
    }
  }
})