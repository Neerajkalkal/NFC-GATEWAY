package com.example.nfcgateway.Global_Exception

class DuplicateEmailException(message: String) : RuntimeException(message)
class InvalidEmailFormatException(message: String) : RuntimeException(message)
class EmailSendException(message: String) : RuntimeException(message)