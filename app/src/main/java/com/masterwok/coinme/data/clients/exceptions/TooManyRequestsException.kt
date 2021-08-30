package com.masterwok.coinme.data.clients.exceptions

import java.io.IOException

internal class TooManyRequestsException : IOException("API request allowance exceeded.")
