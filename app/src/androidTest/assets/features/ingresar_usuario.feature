#language: es

Característica: Ingresar usuario
  Escribir el nombre de usuario, verificarlo, y dirigir a la pantalla "Ingresar contraseña"
  Ir a la pantalla "Crear cuenta" si se presiona el enlace

  Escenario: Ingresar usuario no existente
  o dejar el campo vacío
  o se excede de 15 caracteres
    Dado que estoy en la pantalla "Ingresar usuario"
    Cuando escriba el usuario
    Y presione el botón "Siguiente"
    Entonces debería ver un error en el campo "Usuario"

  Escenario: Ingresar usuario existente
    Dado que estoy en la pantalla "Ingresar usuario"
    Cuando escriba el usuario
    Y presione el botón "Siguiente"
    Entonces debería dirigirme a la pantalla "Ingresar contraseña"

  Escenario: Presionar enlace
    Dado que estoy en la pantalla "Ingresar usuario"
    Cuando presione el enlace "Cree una."
    Entonces debería dirigirme a la pantalla "Crear cuenta"
