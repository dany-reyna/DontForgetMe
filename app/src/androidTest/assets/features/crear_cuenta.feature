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

  Escenario: Ingresar usuario existente o dejar campo vacío
    Entonces debería ver un error en el campo "Usuario"

  Escenario: Las contraseñas no coinciden o alguno de los dos campos está vacío
    Entonces debería ver un error en el campo "Contraseña" y "Confirmar contraseña"

  Escenario: El usuario no existe y las contraseñas coinciden
    Entonces debería registrarse el usuario y dirigirme a la pantalla "Dashboard"
