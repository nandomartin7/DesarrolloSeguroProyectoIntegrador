<template>
    <!-- Encabezado -->
  <header class="header">
    <h1>Bienvenido</h1>
    <button @click="navInicio" class="btn-nav">Inicio</button>
    <button @click="navPlanesSeguro" class="btn-nav">Planes de Seguro</button>
    <button @click="navContratos" class="btn-nav">Contratos</button>
    <button @click="navUsosSeguro" class="btn-nav">Usos del Seguro</button>
    <button @click="navFiltrarGasto" class="btn-nav">Filtrar Gastos</button>
    <button @click="cerrarSesion" class="btn-cerrar-sesion">Cerrar Sesión</button>
  </header>

  <div class="Filtrar-FechasContenedor">
    <div>
      <h2>Filtrar Gastos por Fechas</h2>
      <label>Fecha Inicio:</label>
      <input type="date" v-model="fechaInicio"/>
      <label>Fecha Fin:</label>
      <input type="date" v-model="fechaFin"/>
    </div>
    <button @click="buscarGastos">Buscar</button>


    <div v-if="departamentosFiltrados.length">
      <h3>Resultados</h3>
      <div 
        v-for="(d, idx) in departamentosFiltrados" 
        :key="idx"
        class="acordeon-gastos"
      >
        <div @click="toggle(idx)" class="acordeon-header">
          {{ d.departamentoKey }} – {{ d.gastos.length }} gastos (Total: {{ d.totalMonto.toFixed(2) }})
        </div>
        <div v-if="d.mostrar" class="acordeon-content">
          <table>
            <thead>
              <tr><th>ID</th><th>Fecha</th><th>Descripción</th><th>Monto</th></tr>
            </thead>
            <tbody>
              <tr v-for="g in d.gastos" :key="g.idGasto">
                <td>{{ g.idGasto }}</td>
                <td>{{ g.fecha }}</td>
                <td>{{ g.descripcion }}</td>
                <td>{{ g.monto }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="resumen-general">
        Total general: {{ totalGeneral.toFixed(2) }}
      </div>
    </div>
    <div v-else>
      <p>No hay gastos en ese rango.</p>
    </div>
  </div>
</template>

<script>
import api from "@/axiosConfig";

export default {
  data() {
    return {
      fechaInicio: "",
      fechaFin: "",
      departamentosFiltrados: [],
    };
  },
  computed: {
    totalGeneral() {
      return this.departamentosFiltrados.reduce((sum, d) => sum + d.totalMonto, 0);
    },
  },
  methods: {
    buscarGastos() {
      if (!this.fechaInicio || !this.fechaFin) {
        return alert("Selecciona ambas fechas");
      }
      api
        .get("http://localhost:8081/gasto/filtrar-kms", {
          params: {
            fechaInicio: this.fechaInicio,
            fechaFin: this.fechaFin,
          },
        })
        .then(({ data }) => {
          this.departamentosFiltrados = Object.entries(data).map(
            ([departamentoKey, gastos]) => {
              const totalMonto = gastos.reduce((s, g) => s + g.monto, 0);
              return { departamentoKey, gastos, mostrar: false, totalMonto };
            }
          );
        })
        .catch((err) => {
          console.error(err);
          this.departamentosFiltrados = [];
        });
    },
    toggle(i) {
      this.departamentosFiltrados[i].mostrar = !this.departamentosFiltrados[i].mostrar;
    },
    navInicio(){
      this.$router.push("/empleado")
    },
    navPlanesSeguro() {
      this.$router.push("/empleado/planes-seguro");
    },
    navContratos() {
      this.$router.push("/empleado/contratos");
    },
    navUsosSeguro() {
      this.$router.push("/empleado/usos-seguro");
    },
    navFiltrarGasto() {
      this.$router.push("/empleado/filtrar-gastos");
    },
    cerrarSesion() {
      this.$keycloak.logout({ redirectUri: location.origin });
    },
  },
};
</script>


<style scoped>

  /* General */
  body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f4f4f4;
  }

  /* Encabezado */
  .header {
    background-color: #333;
    color: #fff;
    padding: 15px 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .header h1 {
    color: #fff;
    margin: 0;
    font-size: 20px;
  }

  .btn-nav {
    background-color: #333;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }

  .btn-nav:hover {
    background-color: #615e5e;
  }

  .btn-cerrar-sesion {
    background-color: #ff1a1a;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }

  .btn-cerrar-sesion:hover {
    background-color: #ff4d4d;
  }

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


.filtrarFechas-contenedor{
    margin: 20px 0;
    padding: 15px;
    border: 1px solid #ddd;
    border-radius: 5px;
    background-color: #f9f9f9;
    text-align: center;
}

  .filtrarFechas-contenedor h2{
    margin-top: 0;
    text-align: center;
  }

  .filtrarFechas-contenedor label{
    margin-right: 10px;
    font-weight: bold;
  }

  .p {
    color: red;
    font-weight: bold;
    text-align: center;
    margin-top: 10px;
  }

  /* Botones de acción */
  button {
    padding: 8px 12px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .btnBuscar {
    background-color: #ffc107;
    color: black;
  }

  .btnBuscar:hover {
    background-color: #e0a800;
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
  background-color: #333; 
  color: #fff;
  display: flex;
  justify-content: space-between;
}

.acordeon-content {
  width: 80%;
  margin: auto;
  padding: 10px;
  background-color: #fff;
}

.acordeon-content tr td {
  width: 80%;
  margin: auto;
  padding: 10px;
}

.acordeon-content table {
  width: 100%;
  border-collapse: collapse;
}

.acordeon-content table thead{
  background-color: #333; 
  color: #fff;
}

.acordeon-content table tbody{
  color: #000; 
}

.acordeon-content th, .acordeon-content td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: center;
}
</style>
