package marinj.core.model

sealed interface Failure {
    data class Message(val message: String) : Failure
    object Unknown : Failure
}