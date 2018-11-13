#language: es

Característica: Ingresar contraseña
  Escribir la contraseña, verificarla, y dirigir a la pantalla "Dashboard"
  Ir a la pantalla "Recupera tu cuenta" si se presiona el enlace

  Escenario: Ingresar contraseña incorrecta
  o dejar el campo vacío
    Dado que estoy en la pantalla "Ingresar contraseña"
    Cuando escriba la contraseña
    Y presione el botón "Iniciar sesión"
    Entonces debería ver un error en el campo "Contraseña"

  Escenario: Ingresar contraseña correcta
    Dado que estoy en la pantalla "Ingresar contraseña"
    Cuando escriba la contraseña
    Y presione el botón "Iniciar sesión"
    Entonces debería dirigirme a la pantalla "Dashboard"

  Escenario: Presionar enlace
    Dado que estoy en la pantalla "Ingresar contraseña"
    Cuando presione el enlace "He olvidado mi contraseña"
    Entonces debería dirigirme a la pantalla "Recupera tu cuenta"
