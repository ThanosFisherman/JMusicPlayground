@file:JvmName("Main")

package fractaloid

import jm.JMC
import jm.music.data.Note
import jm.util.Play

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Play.midi(Note(JMC.c5, JMC.HALF_NOTE))
    }
}

