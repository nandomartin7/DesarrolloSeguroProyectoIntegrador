<template>
    <h1>Empleados</h1>
    <div class="login-container">
      <h2>Iniciar Sesión</h2>
  
      <!-- login SSO -->
      <div class="login-container">
        <button class="boton-iniciarSesion" @click="$keycloak.login()">
          Iniciar Sesión
        </button>
      </div>

      <button @click="toggleRegister" class="boton-formularioregistro" > {{ showRegister ? 'Cancelar Registro' : 'Regístrate' }}</button>

      <div v-if="showRegister" class="register-container">
        <h2>Regístrate</h2>
        <form @submit.prevent="register" class="FormularioRegistro">
            <p><input v-model="newEmpleado.idEmpleado" class="input-field" placeholder="Cédula" required/></p>
            <p><input v-model="newEmpleado.nombre" class="input-field" placeholder="Nombre" required/></p>
            <p><input v-model="newEmpleado.apellido" class="input-field" placeholder="Apellido" required/></p>
            <p><input v-model="newEmpleado.direccion" class="input-field" placeholder="Direccion" required/></p>
            <p><input v-model="newEmpleado.telefono" class="input-field" placeholder="Telefono" required/></p>
            <p><input v-model="newEmpleado.correo" class="input-field" placeholder="Correo Electronico" required/></p>
            <p><input type="password" v-model="newEmpleado.password" class="input-field" placeholder="Contraseña" required/></p>
            <button type="submit" class="boton-registro">Registrarse</button>
            <p v-if="registerMessage" class="registro-message">{{ registerMessage }}</p>        
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import api from '@/axiosConfig';
//  import axios from 'axios';
  
  export default {
    name: 'LoginEmpleado',
    data() {
      return {
        newEmpleado: {
            idEmpleado: '',
            nombre: '',
            apellido: '',
            direccion: '',
            telefono: '',
            correo: '',
            password: '',
        },
        showRegister: false,
        registerMessage: '',
      };
    },

    methods: {
        toggleRegister(){
            this.showRegister = !this.showRegister;
            this.registerMessage = '';
        },

        register(){
            api.post('http://localhost:8081/empleado', this.newEmpleado)
            .then(() => {
                this.registerMessage = "Registro exitoso. Puedes iniciar sesión ahora.";
                this.newEmpleado.idEmpleado = ''; //Resetea el campo idCliente
                this.newEmpleado.nombre = ''; //Resetea el campo nombre 
                this.newEmpleado.apellido = ''; //Resetea el campo apellido
                this.newEmpleado.direccion = ''; //Resetea el campo direccion
                this.newEmpleado.telefono = ''; //Resetea el campo telefono
                this.newEmpleado.correo = ''; //Resetea el campo correo elecctronico
                this.newEmpleado.password = ''; //Resetea el campo contraseña 
            })
            .catch(error => {
                if (error.response) {
                    this.registerMessage = error.response.data.message || "Error en registro. ";
                } else {
                    this.registerMessage = "Error en la conexión";
                }
            });
        }
    }
  };
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
  
  .input-field{
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