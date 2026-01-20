package arithmetic.operations

/** This Generic Interface has abstract functions that are to be overridden by subclasses.
 *  @param T a type parameter representing the specific number type.
 */

interface Operations<T : Number> {
    fun parse(token: String): T

    fun add(
        a: T,
        b: T,
    ): T

    fun subtract(
        a: T,
        b: T,
    ): T

    fun multiply(
        a: T,
        b: T,
    ): T

    fun divide(
        a: T,
        b: T,
    ): T
}
