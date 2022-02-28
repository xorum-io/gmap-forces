import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*
import kotlin.random.Random

private const val scale = "19.0z"

data class City(
    val name: String,
    val bottomLeft: Pair<Double, Double>,
    val topRight: Pair<Double, Double>
)

private val cities = listOf(
    City(name = "Москва", bottomLeft = Pair(55.6915, 37.4574185), topRight = Pair(55.8176146, 37.6071709))
)

fun Application.main() {
    install(Routing) {
        get {
            val city = cities.random()
            val (latitude, longitude) = with(city) {
                Pair(
                    Random.nextDouble(bottomLeft.first, topRight.first),
                    Random.nextDouble(bottomLeft.second, topRight.second)
                )
            }
            call.respondHtml {
                head {
                    meta(name = "viewport", content = "width=device-width, initial-scale=1")
                    link(rel = "stylesheet", href = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css")
                    link(rel = "stylesheet", href = "https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js")
                    script(src = "https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js") {}
                    script(src = "https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js") {}
                }
                body(classes = "container-lg") {
                    h1(classes = "text-center my-3") { +"GMaps Forces \uD83C\uDDFA\uD83C\uDDE6" }

                    p(classes = "alert alert-secondary") {
                        +"Всім привіт! Цю сторінку створено для координації зусиль з публікації коментарів "
                        +"в Google Maps для придання розголосу війни Росії проти України та її наслідків для обох держав."
                    }

                    div(classes = "card") {
                        div(classes = "card-body") {
                            h2 { +"Як це працює?" }

                            p {
                                +"Додаток містить список наступних російських міст та їх координати"
                                ul {
                                    cities.forEach {
                                        with(it) {
                                            li { +"$name: ${bottomLeft.first} ... ${topRight.first}, ${bottomLeft.second} ... ${topRight.second}" }
                                        }
                                    }
                                }
                            }

                            p {
                                +"Додаток випадковим чином обирає місто та невелику ділянку у ньому. "
                                +"Це дозволяє рівномірно розподілити потік інформації серед росіян."
                            }

                            p {
                                +"Ваша задача - залишити як можно більше відгуків у різних закладах цієї ділянки. "
                                +"Поясніть що коїться у нас, викличте емоційну реакцію у росіян."
                            }

                            p {
                                +"Після того як задача на обраній ділянці виконана, повертайтеся назад, і беріться за наступну ділянку."
                            }

                            p {
                                +"Надихатися можно наступними ресурсами:"
                                ul {
                                    li { a(href = "https://t.me/UkraineNow") { +"Ukraine NOW" } }
                                    li { a(href = "https://t.me/ukraina_novosti") { +"Україна 24/7 Новости Война Україна" } }
                                }
                            }
                        }

                        div(classes = "text-center") {
                            button(classes = "btn btn-lg btn-success col-md-6") {
                                onClick = "window.open(\"https://www.google.com/maps/@$latitude,$longitude,$scale\");" +
                                        "setTimeout(function() { window.location.reload(); }, 100);"
                                +"Поїхали"
                            }
                        }

                        p {}
                    }

                    p(classes = "p-3") {
                        a(href = "https://github.com/xorum-io/gmap-forces") {
                            +"Допомогти з розробкою та додаванням нових міст можно на GitHub"
                        }
                    }
                }
            }
        }
    }
}
