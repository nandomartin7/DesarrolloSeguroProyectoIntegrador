<template>
  <div class="login-container">
    <h2>Iniciar sesión (Admin)</h2>
    <button class="boton-iniciarSesion" @click="$keycloak.login()">
      Iniciar Sesión
    </button>
  </div>
</template>

<script>
export default { name: 'LoginPage' };
</script>



<style scoped>
body {
  background-color: #b3e5fc; /* Fondo celeste */
}

/* Contenedor principal para el inicio de sesión */
.login-container {
  max-width: 400px; /* Ancho máximo del contenedor */
  margin: auto; /* Centra el contenedor horizontalmente */
  padding: 30px; /* Espacio interior del contenedor (padding) */
  border-radius: 10px; /* Bordes redondeados */
  background-color: #333; 
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Sombra del contenedor */
  text-align: center; 
}

/* Título principal (Iniciar sesión / Registrarse) */
h2 {
  text-align: center;  
  color: white; 
  font-family: 'Arial', sans-serif; /* Fuente del texto */
  margin-bottom: 20px; 
}

.input-fieldInicioN{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s;  
  text-align: center;  
}

.input-fieldInicioP{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}


.boton-iniciarSesion{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

.boton-formularioregistro{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

/* Mensaje de error en rojo */
.error-message {
  color: white;
  text-align: center;
  font-size: 25px;
  font-weight: bold; 
}

/* Contenedor de registro */
.register-container {
  margin-top: 20px; /* Espacio superior entre la sección de registro e inicio de sesión */
  padding-top: 20px; /* Espacio interior en la parte superior */
  border-top: 1px solid #eeeeee; /* Línea superior que separa la sección de registro */
  text-align: center; /* Centra el contenido del registro */
}

/* Título de la sección de registro */
.register-container h2 {
  margin-bottom: 20px; /* Espacio inferior del título de registro */
}

.input-fieldRegistroN{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}

.input-fieldRegistroP{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}

.boton-registro{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}


.registro-message{
  color: white; 
  text-align: center;
  font-size: 25px;
  font-weight: bold; 
}

/* Estilos para pantallas pequeñas (responsive) */
@media (max-width: 500px) {
  .login-container,
  .register-container {
    padding: 20px; /* Reduce el padding en pantallas más pequeñas */
  }
}
</style>