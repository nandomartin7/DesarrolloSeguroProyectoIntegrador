<template>
    <div class="container">
        <button @click="logout">Cerrar Sesión</button>

        <div class="container-Empleados">
            <h2>Lista de Empleados</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="empleado in empleados" :key="empleado.idEmpleado">
                        <td>{{ empleado.idEmpleado }}</td>
                        <td>{{ empleado.nombre }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="container-Departamentos">
            <h2>Lista de Departamentos</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="departamento in departamentos" :key="departamento.idDepartamento">
                        <td>{{ departamento.idDepartamento }}</td>
                        <td>{{ departamento.nombre }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="container-Gastos">
        <h2>Lista de Gastos</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Descripcion</th>
                    <th>Monto</th>
                    <th>ID Empleado</th>
                    <th>ID Departamento</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="gasto in gastos" :key="gasto.idGasto">
                    <td>{{ gasto.idGasto }}</td>
                    <td>{{ gasto.fecha }}</td>
                    <td>{{ gasto.descripcion }}</td>
                    <td>{{ gasto.monto }}</td>
                    <td>{{ gasto.empleado.idEmpleado }}</td>
                    <td>{{ gasto.departamento.idDepartamento }}</td>
                </tr>
            </tbody>
        </table>
    </div>

    <div class="container-Filtrar">

        <div class="contenedor-central">
            <div class="fechas-wrapper">
                <p>
                    <label for="fechaInicio" class="label" >Fecha de Inicio:</label>
                    <input type="date" v-model="fechaInicio" class="input-date"/>
                </p>
                <p>
                    <label for="fechaFin" class="label">Fecha de Fin:</label>
                    <input type="date" v-model="fechaFin" class="input-date" />
                </p>
            </div>
            <p><button @click="buscarGastos" class="btnBuscar">Buscar</button></p>
        </div>

        <div v-if="departamentosFiltrados.length" class="contanier-Resultados">
            <h3>Resultados de búsqueda</h3>

            <div v-for="(departamento, index) in departamentosFiltrados" :key="index" class="acordeon-gastos">
                <div class="acordeon-header" @click="toggleGastos(index)">
                    <span>{{ departamento.departamentoKey }}</span>
                    <span>(Gastos: {{ departamento.gastos.length }} | Total: {{ departamento.totalMonto.toFixed(2) }})</span>

                </div>

                <div v-if="departamento.mostrarGastos" class="acordeon-content">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Descripcion</th>
                                <th>Monto</th>
                                <th>ID Empleado</th>
                                <th>ID Departamento</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(gasto, idx) in departamento.gastos" :key="idx">
                                <td>{{ gasto.idGasto }}</td>
                                <td>{{ gasto.fecha }}</td>
                                <td>{{ gasto.descripcion }}</td>
                                <td>{{ gasto.monto }}</td>
                                <td>{{ gasto.empleado.idEmpleado }}</td>
                                <td>{{ gasto.departamento.idDepartamento }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Header para el total general -->
            <div class="acordeon-gastos resumen-general">
                <div class="acordeon-header">
                    <span>Total General</span>
                    <span>Total Monto: {{ totalMontoGeneral.toFixed(2) }}</span>
                </div>
            </div>

        </div>
        <div v-else>
            <h3>No se encontraron Gastos en el rango de fechas seleccionado.</h3>
        </div>

    </div>

</template>

<script>
import api from '@/axiosConfig';
//import axios from 'axios';

export default{
    data(){
        return{
            empleados: [],
            departamentos: [],
            gastos: [],
            fechaInicio: '',
            fechaFin: '',
            departamentosFiltrados: []
        };
    },
    methods: {
        fetchEmpleados(){
            api.get('http://localhost:8082/empleado')
            .then(response => {
                this.empleados = response.data;
            })
            .catch(error => {
                console.error("Error fetching empleados:", error);
                
            })
        },
        fetchDepartamentos(){
            api.get('http://localhost:8082/departamento')
            .then(response => {
                this.departamentos = response.data;
            })
            .catch(error => {
                console.error("Error fetching departamentos:", error);
                
            })
        },
        fetchGastos(){
            api.get('http://localhost:8082/gasto')
            .then(response => {
                this.gastos = response.data;
            })
            .catch(error => {
                console.error("Error fetching gastos:", error);
                
            })
        },
        buscarGastos(){
            if(!this.fechaInicio || !this.fechaFin) {
                console.error('Debe seleccionar ambas fechas.');
                return;
            }

            api
                .get('http://localhost:8082/gasto/filtrar',{ 
                    params: {
                        fechaInicio: this.fechaInicio,
                        fechaFin: this.fechaFin
                    }
                })
                .then((response) => {
                    this.departamentosFiltrados = Object.keys(response.data).map((key) => {
                        const gastos = response.data[key];
                        const totalMonto = gastos.reduce((sum, gasto) => sum + gasto.monto, 0);

                        return {
                            departamentoKey: key,
                            gastos,
                            mostrarGastos: false,
                            totalMonto
                        };
                    });
                })
                .catch((error) => {
                    console.error('Error al buscar los gastos: ', error);
                    this.departamentosFiltrados = [];
                });
        },
        toggleGastos(index) {
            this.departamentosFiltrados[index].mostrarGastos =
            !this.departamentosFiltrados[index].mostrarGastos;
        },
        logout(){
            this.$keycloak.logout({ redirectUri: location.origin });
        }
    
    },
    computed: {
        totalMontoGeneral() {
            return this.departamentosFiltrados.reduce((sum, departamento) => {
                return sum + departamento.totalMonto;
            }, 0);
        }
    },

    mounted(){
        this.fetchEmpleados();
        this.fetchDepartamentos();
        this.fetchGastos();
    }
}

</script>

<style scoped>
.buscar-contratos {
  text-align: center;
  margin-top: 50px;
}

.contenedor-central {
  display: inline-block;
  max-width: 400px; /* Ancho máximo del contenedor */
  margin: auto; /* Centra el contenedor horizontalmente */
  padding: 20px; /* Espacio interior del contenedor (padding) */
  border-radius: 10px; /* Bordes redondeados */
  background-color: #00d5ff; 
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Sombra del contenedor */
  text-align: center;
}

.fechas-wrapper {
  display: flex;
  justify-content: center; /* Centrar los elementos horizontalmente */
  gap: 20px; /* Espacio entre las dos fechas */
}

.label{
  font-size: 18px; /* Tamaño de la fuente */
}

.input-date{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px;
  margin-top: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center;
}

.btnBuscar{
  width: 50%; /* Los botones ocupan el 100% del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241); 
  color: black; 
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px; 
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

.acordeon-empleado {
  width: 80%;
  margin: auto;
  margin-top: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.acordeon-header {
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
  background-color: #00d5ff; 
  display: flex;
  justify-content: space-between;
}

.acordeon-content {
  width: 80%;
  margin: auto;
  padding: 10px;
  background-color: #fff;
}

.acordeon-content table {
  width: 100%;
  border-collapse: collapse;
}

.acordeon-content table thead{
  background-color: #99dfff; 
}

.acordeon-content th, .acordeon-content td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: center;
}
</style>
