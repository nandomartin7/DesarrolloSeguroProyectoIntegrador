<template>
  <div id="app" >
    <h1 class="principal">Gastos de Departamentos</h1>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style scoped>
.principal{
  text-align: center;
}
</style>