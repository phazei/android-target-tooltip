package it.swabbass.android.library.xtooltip

class ClosePolicy internal constructor(private val policy: Int) {

    fun consumeInside(): Boolean = policy and CONSUME_INSIDE == CONSUME_INSIDE

    fun consumeOutside(): Boolean = policy and CONSUME_OUTSIDE == CONSUME_OUTSIDE

    fun consumeAnywhere(): Boolean = consumeInside() && consumeOutside()

    fun touchInside(): Boolean = (policy and TOUCH_INSIDE) == TOUCH_INSIDE

    fun touchOutside(): Boolean = (policy and TOUCH_OUTSIDE) == TOUCH_OUTSIDE

    fun touchAnywhere(): Boolean = touchInside() && touchOutside()


    override fun toString(): String {
        return "ClosePolicy(policy: $policy, touchInside:${touchInside()}, touchOutside: ${touchOutside()}, consumeInside: ${consumeInside()}, consumeOutside: ${consumeOutside()} )"
    }

    @Suppress("unused")
    class Builder {
        private var policy = NONE

        fun cconsume(inside: Boolean, outside: Boolean): Builder {
            policy = if (inside) policy or CONSUME_INSIDE else policy and CONSUME_INSIDE.inv()
            policy = if (outside) policy or CONSUME_OUTSIDE else policy and CONSUME_OUTSIDE.inv()
            return this
        }

        fun inside(value: Boolean, consume: Boolean): Builder {
            policy = if (value) policy or TOUCH_INSIDE else policy and TOUCH_INSIDE.inv()
            policy = if (consume) policy or CONSUME_INSIDE else policy and CONSUME_INSIDE.inv()
            return this
        }

        fun outside(value: Boolean, consume: Boolean): Builder {
            policy = if (value) policy or TOUCH_OUTSIDE else policy and TOUCH_OUTSIDE.inv()
            policy = if (consume) policy or CONSUME_OUTSIDE else policy and CONSUME_OUTSIDE.inv()
            return this
        }

        fun clear() {
            policy = NONE
        }

        fun build() = ClosePolicy(policy)
    }

    @Suppress("unused")
    companion object {
        private const val NONE = 0
        private const val TOUCH_INSIDE = 1 shl 1
        private const val TOUCH_OUTSIDE = 1 shl 2
        private const val CONSUME_INSIDE = 1 shl 3
        private const val CONSUME_OUTSIDE = 1 shl 4

        val TOUCH_NONE = ClosePolicy(NONE)
        val TOUCH_INSIDE_CONSUME = ClosePolicy(TOUCH_INSIDE or CONSUME_INSIDE)
        val TOUCH_INSIDE_NO_CONSUME = ClosePolicy(TOUCH_INSIDE)
        val TOUCH_OUTSIDE_CONSUME = ClosePolicy(TOUCH_OUTSIDE or CONSUME_OUTSIDE)
        val TOUCH_OUTSIDE_NO_CONSUME = ClosePolicy(TOUCH_OUTSIDE)
        val TOUCH_ANYWHERE_NO_CONSUME = ClosePolicy(TOUCH_INSIDE or TOUCH_OUTSIDE)
        val TOUCH_ANYWHERE_CONSUME = ClosePolicy(TOUCH_INSIDE or TOUCH_OUTSIDE or CONSUME_INSIDE or CONSUME_OUTSIDE)
    }

}