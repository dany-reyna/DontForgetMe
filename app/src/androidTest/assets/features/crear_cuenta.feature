#language: es

Característica: Crear cuenta
  Escribir el usuario, la contraseña, y repetir la contraseña
  Verificar y dirigir a la pantalla "Dashboard"

  Antecedentes:
    Dado que estoy en la pantalla "Crear cuenta"
    Cuando escriba el usuario
    Y escriba la contraseña
    Y repita la contraseña
    Y presione el botón "Registrarse"

  Escenario: Ingresar usuario existente
  o dejar campo vacío
  o se excede de 15 caracteres
    Entonces debería ver un error en el campo "Usuario"

  Escenario: El campo "Contraseña" está vacío
  o el campo contraseña tiene menos de 6 caracteres
  o el campo contraseña se excede de 100 caracteres
    Entonces debería ver un error en el campo "Contraseña"

  Escenario: El campo "Confirmar contraseña" está vacío
  o las contraseñas no coinciden
    Entonces debería ver un error en el campo "Confirmar contraseña"

  Escenario: El usuario no existe, las contraseñas son correctas y coinciden
    Entonces debería registrarse el usuario y dirigirme a la pantalla "Dashboard"