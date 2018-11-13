#language: es

Característica: Cambiar contraseña
  Escribir la contraseña, y repetir la contraseña
  Verificar y dirigir a la pantalla "Dashboard"

  Antecedentes:
    Dado que estoy en la pantalla "Cambia tu contraseña"
    Cuando escriba la contraseña
    Y repita la contraseña
    Y presione el botón "Cambiar"

  Escenario: El campo "Contraseña" está vacío
  o el campo contraseña tiene menos de 6 caracteres
  o el campo contraseña se excede de 100 caracteres
    Entonces debería ver un error en el campo "Contraseña"

  Escenario: El campo "Confirmar contraseña" está vacío
  o las contraseñas no coinciden
    Entonces debería ver un error en el campo "Confirmar contraseña"

  Escenario: Las contraseñas coinciden
    Entonces debería actualizarse la contraseña y dirigirme a la pantalla "Dashboard"
