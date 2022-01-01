package ar.edu.unahur.obj2.impostoresPaises
import ar.edu.unahur.obj2.impostoresPaises.api.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class MockTests : DescribeSpec({
  //Etapa 4
  describe("Pruebas con mockk") {
    val apiPaisesMock = mockk<RestCountriesAPI>()
    val apiCurrenciMock = mockk<CurrencyConverterAPI>()
    val observatorio = Observatorio(apiPaisesMock, apiCurrenciMock)

    every { apiCurrenciMock.convertirDolarA("ARS")} returns 0.0099
    every { apiCurrenciMock.convertirDolarA("BRL")} returns 0.18
    every { apiCurrenciMock.convertirDolarA("CLP")} returns 0.0012
    every { apiCurrenciMock.convertirDolarA("JMD")} returns 0.0064
    every { apiCurrenciMock.convertirDolarA("PYG")} returns 0.00015
    every { apiCurrenciMock.convertirDolarA("EUR")} returns 1.1
// Paises
    every { apiPaisesMock.buscarPaisesPorNombre("Argentina")} returns
            listOf(
              Country("Argentina", "ARG", "Buenos Aires", "Europa", 45376763,
                2780400.0, listOf("BRA", "CHL", "PRY"), listOf(Language("Spanish"), Language("Guaraní")),
                listOf(RegionalBloc(acronym="USAN", name="Union of South American Nations")),
                listOf(Currency("ARS"))))

    every { apiPaisesMock.buscarPaisesPorNombre("Chile")} returns
            listOf(
              Country("Chile", "CHL", "Santiago", "Americas", 19116209, 756102.0, listOf("ARG"),
                listOf(Language("Spanish")), listOf(
                  RegionalBloc(acronym="PA", name="Pacific Alliance"),
                  RegionalBloc(acronym="USAN", name="Union of South American Nations")
                ), listOf(Currency("CLP"))))

    every { apiPaisesMock.buscarPaisesPorNombre("Paraguay")} returns
            listOf(
              Country("Paraguay", "PRY", "Asunción", "Americas", 7132530, 406752.0, listOf("ARG", "BRA"),
                listOf(Language("Spanish"), Language("Guaraní")), listOf(RegionalBloc(acronym="USAN", name="Union of South American Nations")),
                listOf(Currency("PYG"))))

    every { apiPaisesMock.buscarPaisesPorNombre("Jamaica")} returns
            listOf(
              Country("Jamaica", "JAM", "Kingston", "Americas", 2961161, 10991.0, listOf(), listOf(Language("English")),
                listOf(RegionalBloc(acronym="CARICOM", name="Caribbean Community")), listOf(Currency("JMD")))            )

    every { apiPaisesMock.buscarPaisesPorNombre("Brazil")} returns
            listOf(
              Country("Brazil", "BRA", "Brasília", "Americas", 212559409, 8515767.0, listOf("ARG", "PRY"),
                listOf(Language("Portuguese")), listOf(RegionalBloc(acronym="USAN", name="Union of South American Nations")), listOf(
                  Currency("BRL"))))

    every { apiPaisesMock.buscarPaisesPorNombre("Francia")} returns
    listOf(
      Country("Francia", "FRA", "Paris", "Europa", 40567900, 8515.0, listOf("ARG", "BRA"),
        listOf(Language("Frances"),Language("Ingles")), listOf(RegionalBloc(acronym="Euro", name="Union Europea")), listOf(
                            Currency("EUR"))))
// Paises por codigo
    every { apiPaisesMock.paisConCodigo("ARG")} returns
            apiPaisesMock.buscarPaisesPorNombre("Argentina").first()

    every { apiPaisesMock.paisConCodigo("CHL")} returns
            apiPaisesMock.buscarPaisesPorNombre("Chile").first()

    every { apiPaisesMock.paisConCodigo("PRY")} returns
            apiPaisesMock.buscarPaisesPorNombre("Paraguay").first()

    every { apiPaisesMock.paisConCodigo("JAM")} returns
            apiPaisesMock.buscarPaisesPorNombre("Jamaica").first()

    every { apiPaisesMock.paisConCodigo("BRA")} returns
            apiPaisesMock.buscarPaisesPorNombre("Brazil").first()

    every { apiPaisesMock.paisConCodigo("FRA")} returns
            apiPaisesMock.buscarPaisesPorNombre("Francia").first()
// Todos los paises
    every { apiPaisesMock.todosLosPaises() } returns
            listOf(
              apiPaisesMock.buscarPaisesPorNombre("Argentina").first(),
              apiPaisesMock.buscarPaisesPorNombre("Chile").first(),
              apiPaisesMock.buscarPaisesPorNombre("Paraguay").first(),
              apiPaisesMock.buscarPaisesPorNombre("Jamaica").first(),
              apiPaisesMock.buscarPaisesPorNombre("Brazil").first(),
              apiPaisesMock.buscarPaisesPorNombre("Francia").first()
            )

    describe("Saber si") {
      it("Chile y Argentina son limitrofes") {
        observatorio.sonLimitrofes("Chile", "Argentina").shouldBeTrue()
      }
      it("Chile y Jamaica no son limitrofes") {
        observatorio.sonLimitrofes("Chile", "Jamaica").shouldBeFalse()
      }
      it("Chile y Argentina no necesitan traduccion") {
        observatorio.necesitanTraduccion("Chile", "Argentina").shouldBeFalse()
      }
      it("Chile y Jamaica necesitan traduccion") {
        observatorio.necesitanTraduccion("Chile", "Jamaica").shouldBeTrue()
      }
      it("Argentina y Paraguay son potenciales aliados") {
        observatorio.sonPotencialesAliados("Argentina", "Paraguay").shouldBeTrue()
      }
      it("Jamaica y Argentina no son potenciales aliados") {
        observatorio.sonPotencialesAliados("Jamaica", "Argentina").shouldBeFalse()
      }
      it("conviene ir de compras de Paraguay a Argentina") {
        observatorio.convieneIrDeCompras("Paraguay", "Argentina").shouldBeTrue()
      }
      it("no conviene ir de compras de Argentina a Paraguay") {
        observatorio.convieneIrDeCompras("Argentina", "Paraguay").shouldBeFalse()
      }
      it("150.00 pesos Chilenos equivalen a 1238.0 pesos Argentinos") {
        observatorio.aCuantoEquivaleEn("Chile", 150.00, "Argentina").shouldBe(1238.0)
      }
      it("Los 5 paises con mayor densidad habitacional son Brasil, Argentina, Chile, Paraguay y Jamaica") {
        observatorio.codigosISODe5PaisesMasPoblados().shouldBe(listOf("BRA", "ARG", "FRA", "CHL", "PRY"))
      }
      it("El continente con paises mas plurinacionales es el continente Americano") {
        observatorio.continenteConMasPaisesPlurinacionales().shouldBe("Europa")
      }
      it("Saber si el promedio de avitantes en todas las islas es 2961161") {
        observatorio.promedioDePoblacionEnIslas().shouldBe(2961161)
      }
    }
  }
})