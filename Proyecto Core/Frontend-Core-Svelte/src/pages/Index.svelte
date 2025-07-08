<script>
  import { onMount } from "svelte";

  let mensaje = "Bienvenido al Filtro de Usos del Seguro (Svelte)";
  let fechaInicio = "";
  let fechaFin = "";
  let resultados = [];
  let contratosCliente = [];
  let usosCliente = [];
  let filtroRealizado = false;
  let idCliente = null;

  // Obtener el idCliente desde la URL
  onMount(() => {
    const urlParams = new URLSearchParams(window.location.search);
    idCliente = urlParams.get("idCliente");

    if (!idCliente) {
      alert("No se encontró el ID del cliente. Redirigiendo...");
      window.location.href = "http://localhost:8081/login-cliente"; // Redirigir al login
    } else {
      obtenerContratosDelCliente();
    }
  });

  // Obtener contratos del cliente
  async function obtenerContratosDelCliente() {
    try {
      const response = await fetch(
        `http://localhost:8080/contrato/filtrar/cliente?idcliente=${idCliente}`
      );
      if (response.ok) {
        contratosCliente = await response.json();
        obtenerUsosDelCliente();
      } else {
        console.error("Error al obtener los contratos del cliente");
      }
    } catch (error) {
      console.error("Error al obtener contratos:", error);
    }
  }

  // Obtener usos relacionados con los contratos del cliente
  async function obtenerUsosDelCliente() {
    try {
      for (const contrato of contratosCliente) {
        const response = await fetch(
          `http://localhost:8080/uso/filtrar/contrato?idContrato=${contrato.idContrato}`
        );
        if (response.ok) {
          const usosContrato = await response.json();
          usosCliente = [...usosCliente, ...usosContrato];
        } else {
          console.error(`Error al obtener usos del contrato ${contrato.idContrato}`);
        }
      }
    } catch (error) {
      console.error("Error al obtener usos:", error);
    }
  }

  // Filtrar los usos por fecha
  function filtrarUsos() {
    if (!fechaInicio || !fechaFin) {
      alert("Por favor selecciona un rango de fechas");
      return;
    }

    resultados = usosCliente.filter(
      (uso) =>
        new Date(uso.fecha) >= new Date(fechaInicio) &&
        new Date(uso.fecha) <= new Date(fechaFin)
    );

    filtroRealizado = true;
  }
</script>

<header class="header">
  <h1>{mensaje}</h1>
  <button on:click={() => (window.location.href = "https://buenas-practicas-core-frontend.vercel.app/cliente")}>
    Regresar a Vue
  </button>
</header>

<main class="main-content">
  <h2>Filtrar Usos del Seguro</h2>
  <form on:submit|preventDefault={filtrarUsos}>
    <label for="fechaInicio">Fecha Inicio:</label>
    <input type="date" id="fechaInicio" bind:value={fechaInicio} required />

    <label for="fechaFin">Fecha Fin:</label>
    <input type="date" id="fechaFin" bind:value={fechaFin} required />

    <button type="submit">Filtrar</button>
  </form>

  <!-- Tabla General de Usos -->
  <h3>Lista General de Usos del Seguro</h3>
  {#if usosCliente.length > 0}
    <table>
      <thead>
        <tr>
          <th>ID Uso</th>
          <th>Fecha</th>
          <th>Tipo</th>
          <th>Estado</th>
          <th>Contrato ID</th>
        </tr>
      </thead>
      <tbody>
        {#each usosCliente as uso (uso.idUso)}
          <tr>
            <td>{uso.idUso}</td>
            <td>{uso.fecha}</td>
            <td>{uso.tipoUso}</td>
            <td>{uso.estadoReclamo}</td>
            <td>{uso.contrato.idContrato}</td>
          </tr>
        {/each}
      </tbody>
    </table>
  {:else}
    <p>No hay usos registrados para este cliente.</p>
  {/if}

  <!-- Resultados del Filtro -->
  {#if filtroRealizado}
    <h3>Resultados del Filtro</h3>
    {#if resultados.length > 0}
      <table>
        <thead>
          <tr>
            <th>ID Uso</th>
            <th>Fecha</th>
            <th>Tipo</th>
            <th>Estado</th>
            <th>Contrato ID</th>
          </tr>
        </thead>
        <tbody>
          {#each resultados as uso (uso.idUso)}
            <tr>
              <td>{uso.idUso}</td>
              <td>{uso.fecha}</td>
              <td>{uso.tipoUso}</td>
              <td>{uso.estadoReclamo}</td>
              <td>{uso.contrato.idContrato}</td>
            </tr>
          {/each}
        </tbody>
      </table>
    {:else}
      <p>No se encontraron resultados para las fechas seleccionadas.</p>
    {/if}
  {/if}
</main>

<style>
  .header {
    background-color: #333;
    color: white;
    padding: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .header h1 {
    margin: 0;
  }
  .main-content {
    padding: 20px;
    text-align: center;
  }
  form {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
    margin-bottom: 20px;
  }
  table {
    width: 100%;
    border-collapse: collapse;
    margin: 20px 0;
  }
  table th, table td {
    border: 1px solid #ddd;
    padding: 8px;
    text-align: center;
  }
  table th {
    background-color: #f4f4f4;
    font-weight: bold;
  }
  button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
  }
  button:hover {
    background-color: #0056b3;
  }
</style>
