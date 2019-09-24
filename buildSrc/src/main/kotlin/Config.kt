interface Config {
    val urls: Urls
    val applicationId: String
}

interface Urls {
    val baseUrl: Pair<String, String>
}

object Prod: Config {
    override val applicationId = "dev.moustaches.quefaireaparis"
    override val urls = object: Urls {
        override val baseUrl = "API_BASE_URL" to "\"https://opendata.paris.fr/api/records/1.0/\""
    }
}

object PreProd: Config {
    override val applicationId = "dev.moustaches.preprod.quefaireaparis"
    override val urls = object: Urls {
        override val baseUrl = "API_BASE_URL" to "\"https://opendata.paris.fr/api/records/1.0/\""
    }
}