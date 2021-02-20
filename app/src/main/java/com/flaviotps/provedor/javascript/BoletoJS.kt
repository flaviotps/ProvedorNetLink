package com.flaviotps.provedor.javascript

const val SET_CPF = "document.getElementById(\"txt_cpf\").value = \"%cpf%\""
const val SET_LOGIN = "document.getElementById(\"txt_login\").value = \"%login%\""
const val SET_PASSWORD = "document.getElementById(\"txt_senha\").value = \"%password%\""

const val CHECK_LOGIN_CPF = "document.getElementById(\"txt_cpf\") != null"

const val PRESS_LOGIN = "document.forms[\"central_login\"].submit()"

const val GET_TICKET = "function getBoletos(){\n" +
        " var table = document.getElementsByClassName(\"table table-striped table-bordered table-hover\")[0];\n" +
        " var result = [];\n" +
        " for (var i = 1, nr = table.rows.length; i < nr; i++) {\n" +
        "         row = table.rows[i]             \n" +
        "         var link  = row.children[0].firstElementChild.href;     \n" +
        "         var value = row.children[1].innerText; \n" +
        "         var dueDate = row.children[2].innerText;  \n" +
        "         result.push({\n" +
        "             \"link\": link,\n" +
        "             \"value\" : value,\n" +
        "             \"dueDate\" : dueDate\n" +
        "         })\n" +
        "  }\n" +
        "  return result;\n" +
        "}\n" +
        "getBoletos();"

const val GET_PAID_TICKETS = "function getPaidTickets(){\n" +
        "  var table = document.getElementsByClassName('table table-striped table-bordered table-hover')[0].getElementsByTagName('tbody')[0];\n" +
        "  var result = [];\n" +
        "  for (var i = 0, nr = table.rows.length; i < nr; i++) {\n" +
        "          row = table.rows[i]             \n" +
        "          var link  = row.children[4].firstElementChild.href;     \n" +
        "          var value = row.children[1].innerText; \n" +
        "          var dueDate = row.children[2].innerText;  \n" +
        "          if(link  === undefined){\n" +
        "            link = null\n" +
        "          }\n" +
        "          result.push({\n" +
        "              \"link\": link,\n" +
        "              \"value\" : value,\n" +
        "              \"dueDate\" : dueDate\n" +
        "          })\n" +
        "   }\n" +
        "   return result;\n" +
        " }\n" +
        " getPaidTickets();"