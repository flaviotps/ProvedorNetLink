package com.flaviotps.provedor

// URLS
const val TICKET_URL = "http://186.194.46.140/central/2_via.php"
const val PAID_TICKET_URL = "http://186.194.46.140/central/faturas.php"
const val LOGIN_URL = "http://192.168.0.101:8080"

//PATHS
const val LOGIN_PATH = "central/login"
const val INDEX_PATH = "central/index"
const val OPEN_TICKETS_PATH = "central/2_via"
const val PAID_TICKETS_PATH = "central/faturas"
const val OPEN_TICKET_PATH = "/boleto/"

//QUERY
const val TICKET_QUERY = "boleto.php?titulo="

//EXTRA
const val EXTRA_KEY_LOGIN_RESPONSE = "LOGIN_RESPONSE"
const val EXTRA_KEY_HISTORIC_TICKET = "HISTORIC_TICKET"



//FRAGMENT TAGS
const val LOGIN_FRAGMENT_TAG = "LOGIN_FRAGMENT"
const val TICKET_FRAGMENT_TAG = "TICKET_FRAGMENT"
const val HISTORIC_TICKET_FRAGMENT_TAG = "HISTORIC_TICKET_FRAGMENT_TAG"