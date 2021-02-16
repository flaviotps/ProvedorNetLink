package com.flaviotps.provedor

const val SET_CPF = "document.getElementById(\"txt_cpf\").value = \"00430577729\""

const val PRESS_LOGIN = "document.forms[\"central_login\"].submit()"

const val GET_BOLETO = "function getBoletos(){\n" +
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