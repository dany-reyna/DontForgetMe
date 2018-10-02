#language: es

Característica: Pantalla inicial
  Acceder a las pantallas "Ingresar usuario" o "Crear cuenta"

  Esquema del escenario: Se presiona un botón
    Dado que estoy en la pantalla inicial
    Cuando presione un <boton>
    Entonces debería dirigirme a la <pantalla> correspondiente

    Ejemplos:
      | boton          | pantalla         |
      | Iniciar sesion | Ingresar usuario |
      | Registrarse    | Crear cuenta     |