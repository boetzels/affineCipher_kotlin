import java.util.Scanner

//class affineCipherCL

object affineCipher {
    private val alphabet = ('a'..'z').toList()
    private val m: Int = alphabet.size

    private fun encryptLetter(a: Int, b: Int, letter: Char) : Char = alphabet.get(Math.floorMod((a*(alphabet.indexOf(letter)) + b), m))

    private fun decryptLetter(mmi: Int, b: Int, letter: Char) : Char = alphabet.get(Math.floorMod((mmi*(alphabet.indexOf(letter) - b)), m))

    fun encrypt(a: Int, b: Int, text: String) : String {
        try {
            this.checkCoPrimes(a)
            var letters: String = this.prepareString(text)

            var result: String = ""

            var i = 0
            for (l in letters) {
                if (i % 4 == 0 && i != 0) result += " "
                i++
                result += this.encryptLetter(a,b,l)
            }

            return result
        }
        catch (e: Exception) {
            println(e)
        }
        return ""
    }

    fun decrypt(a: Int, b: Int, text: String) : String {
        try {
            this.checkCoPrimes(a)
            var letters: String = this.prepareString(text)
            val mmi = this.getMMI(m,a)

            var result: String = ""

            for (l in letters) {
                result += this.decryptLetter(mmi,b,l)
            }

            return result
        }
        catch (e: Exception) {
            println(e)
        }
        return ""
    }

    private fun prepareString(text: String) : String {
        return text.lowercase().replace(Regex("""\W"""),"")
    }

    private fun checkCoPrimes(a: Int) : Unit {
        val gcd = this.greatestCommonDivisor(a,m)
        if (gcd != 1) throw Exception("ERROR: $a and $m are not coprime (gcd: $gcd)!!!")
    }

    private fun greatestCommonDivisor(n1: Int, n2: Int) : Int {
        if (n1 == 0) return n2
        if (n2 == 0) return n1
        var rest: Int = 0
        var lastN1: Int = n1
        var lastN2: Int = n2
        do {
            rest = lastN1 % lastN2
            lastN1 = lastN2
            lastN2 = rest
        } while (rest != 0)
        return Math.abs(lastN1)
    }

    private fun getMMI(n1: Int, n2: Int) : Int {
        val ee = extendedEuclid(n1, n2)[1] % n1
        if (ee < 0) return ee + n1 else return ee
    }

    private fun extendedEuclid(n1: Int, n2: Int) : Array<Int> {
        var rest: Int = 0
        var lastN1: Int = n1
        var lastN2: Int = n2
        var s = mutableListOf<Int>(1,0)
        var t = mutableListOf<Int>(0,1)

        do {
            rest = lastN1 % lastN2
            val sSave = s[1]
            val tSave = t[1]

            if (rest != 0) {
                s[1] = s[0] - (lastN1 - rest) / lastN2 * s[1]
                s[0] = sSave
                t[1] = t[0] - (lastN1 - rest) / lastN2 * t[1]
                t[0] = tSave
            }

            lastN1 = lastN2
            lastN2 = rest
        } while (rest != 0)

        return arrayOf(s[1], t[1])
    }
}

fun mainv1() {
    val scanner  = Scanner(System.`in`)

    print("Hello, please give text: ")
    val text = readLine()!!

    print("Please give a key: ")
    val a = scanner.nextInt()

    print("Please give b key: ")
    val b = scanner.nextInt()

    val crypt = affineCipher.encrypt(a,b,text)
    val decrypt = affineCipher.decrypt(a,b,crypt)

    println(" ")
    println("The encrypted text is:")
    println(crypt)
    println(" ")

    println(" ")
    println("The decrypted text is:")
    println(decrypt)
    println(" ")
}

