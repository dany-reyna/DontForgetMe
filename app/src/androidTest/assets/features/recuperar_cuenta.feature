#language: es

Característica: Recuperar cuenta
  Ingresar correo electrónico, enviar código de verificación y dirigir a pantalla "Verifica tu identidad"

  Antecedentes:
    Dado que estoy en la pantalla "Recupera tu cuenta"
    Cuando escriba el correo electrónico
    Y presione el botón "Siguiente"

  Escenario: Ingresar correo en formato incorrecto
  o se excede de 100 caracteres
    Entonces debería ver un error en el campo "Correo electrónico"

  Escenario: Ingresar correo en formato correcto
    Entonces debería enviar un código de verificación y dirigirme a la pantalla "Verifica tu identidad"