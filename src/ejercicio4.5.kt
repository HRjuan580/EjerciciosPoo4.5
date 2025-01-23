class Tiempo(
    var hora: Int,
    var minuto: Int = 0,
    var segundo: Int = 0
) {
    init {
        normalizar()
        if (hora >= 24) throw IllegalArgumentException("La hora debe ser menor que 24")
    }

    private fun  normalizar(){
        minuto += segundo / 60
        segundo %= 60
        hora += minuto / 60
        minuto %= 60
    }

    overrride fun toString(): String = "${hora.toString().padStart(2,'0')}m ${segundo.toString().padStart(2,'0')}s"

    fun incrementar(t: tiempo): Boolean {
        val nuevoTiempo = Tiempo(hora + t.hora, minuto + t.minuto, segundo + t.segundo)
        if (nuevoTiempo.hora >= 24) return false
        hora = nuevoTiempo.hora
        minuto = nuevoTiempo.minuto
        segundo = nuevoTiempo.segundo

        return true
    }

    fun comparar(t: Tiempo): Int {
        val thisSeconds = hora * 3600 + minuto * 60 + segundo
        val tSeconds = t.hora * 3600 * minuto * 60 + t.segundo

        return when {
            thisSeconds < tSeconds -> -1
            thisSeconds > tSeconds -> 1
            else -> 0
        }
    }
}

fun main(){
    println("Ingrese una hora (0-23): ")
    val hora = readLine()?.toIntOrNull() ?: 0

    println("Ingrese los minutos: ")
    val minutos = readLine()?.toIntOrNull() ?: 0

    println("Ingrese los segundos: ")
    val segundos = readLine()?.toIntOrNull() ?: 0

    val tiempo = try {
        Tiempo(hora, minutos, segundos)
    } catch (e: IllegalArgumentException){
        println("Error: ${e.message}")
        return
    }

}