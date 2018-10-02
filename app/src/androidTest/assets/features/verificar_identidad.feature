#language: es

Característica: Verificar identidad
  Ingresar código de verificación, confirmar y dirigir a pantalla "Cambia tu contraseña"

  Antecedentes:
    Dado que estoy en la pantalla "Verifica tu identidad"
    Cuando escriba el código de verificación
    Y presione el botón "Siguiente"

  Escenario: Ingresar código incorrecto
    Entonces debería ver un error en el campo "Código"

  Escenario: Ingresar código correcto
    Entonces debería dirigirme a la pantalla "Cambia tu contraseña"