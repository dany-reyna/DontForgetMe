#language: es

Característica: Cambiar contraseña
  Escribir la contraseña, y repetir la contraseña
  Verificar y dirigir a la pantalla "Dashboard"

  Antecedentes:
    Dado que estoy en la pantalla "Cambia tu contraseña"
    Cuando escriba la contraseña
    Y repita la contraseña
    Y presione el botón "Cambiar"

  Escenario: Las contraseñas no coinciden o alguno de los dos campos está vacío
    Entonces debería ver un error en el campo "Contraseña" y "Confirmar contraseña"

  Escenario: Las contraseñas coinciden
    Entonces debería actualizarse la contraseña y dirigirme a la pantalla "Dashboard"
