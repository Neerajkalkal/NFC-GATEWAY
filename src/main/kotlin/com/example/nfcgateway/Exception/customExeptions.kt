package com.example.nfcgateway.Exception

class DuplicateEmailException(message: String) : RuntimeException(message)
class InvalidEmailFormatException(message: String) : RuntimeException(message)
class EmailSendException(message: String) : RuntimeException(message)