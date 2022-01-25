import java.util.Scanner

//class affineCipherCL

object affineCipherV2 {
    private val alphabet = ('a'..'z').toList()
    private val m: Int = alphabet.size

    private fun Char.encode(a: Int, b: Int) : Char = alphabet.get(Math.floorMod((a*(alphabet.indexOf(this)) + b), m))

    private fun Char.decode(mmi: Int, b: Int) : Char = alphabet.get(Math.floorMod((mmi*(alphabet.indexOf(this) - b)), m))

    private fun String.encrypt(a: Int, b: Int) : List<Char> = this.prepare().map { it.encode(a,b) }

    private fun String.decrypt(mmi: Int, b: Int) : List<Char> = this.prepare().map { it.decode(mmi,b) }

    private fun String.prepare() : List<Char> = this.lowercase().replace(Regex("""\W"""),"").toList()

    private fun Int.coPrime(n2: Int) : Boolean = if (this.greatestCommonDivisor(n2) != 1) false else true

    private fun Int.greatestCommonDivisor(n1: Int) : Int = if (n1 == 0) this else n1.greatestCommonDivisor(this % n1)

    private fun Int.getMMI(n: Int) : Int {
        val ee = this.extendedEuclid(n)[2] % this
        if (ee < 0) return ee + this else return ee
    }

    private fun List<Char>.gapEvery(chunk: Int) = this.mapIndexed { index, char ->
            when (index % chunk == 0 && index != 0) {
                true -> this.add(index, ' ')
                false -> ""
            } + char
        }

    private fun Int.extendedEuclid(n: Int) : Array<Int> {
        if (n == 0) { return arrayOf(this, 1, 0) }
        val res = n.extendedEuclid(this % n)
        return arrayOf(res[0], res[2], res[1] - this / n * res[2])
    }

    public fun start(a: Int, b: Int, text: String) : Unit {
        if (!a.coPrime(m)) throw Exception("ERROR: $a and $m are not coprime!!!")

        val crypt = text.encrypt(a,b)
        val decrypt = crypt.joinToString("").decrypt(m.getMMI(a),b)

        println(" ")
        println("The encrypted text is:")
        println(crypt.gapEvery(4))
        println(" ")

        println(" ")
        println("The decrypted text is:")
        println(decrypt.joinToString(""))
        println(" ")
    }
}

fun main() {
    val scanner  = Scanner(System.`in`)

    print("Hello V2, please give text: ")
    val text = readLine()!!

    print("Please give a key: ")
    val a = scanner.nextInt()

    print("Please give b key: ")
    val b = scanner.nextInt()

    try {
        affineCipherV2.start(a, b, text)
    }
    catch (e: Exception) {
        println(e)
    }
}

