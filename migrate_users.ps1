# migrate_users.ps1

# Variables
$KC_CONTAINER = 'infra-keycloak-1'
$KCADM        = '/opt/keycloak/bin/kcadm.sh'
$REALM        = 'ProyectoFinal'
$KC_URL       = 'http://localhost:8080'
$KC_ADMIN     = 'admin'
$KC_PASS      = 'admin'

# 1) Autenticar en Keycloak Master
docker exec $KC_CONTAINER `
  $KCADM config credentials `
    --server $KC_URL `
    --realm master `
    --user $KC_ADMIN `
    --password $KC_PASS

# Función para migrar una lista de usuarios
function Migrate-Users($list) {
  foreach ($u in $list) {
    $id   = $u.id
    $role = $u.role

    Write-Host "Migrando $role $id …"

    # Crear usuario
    docker exec $KC_CONTAINER `
      $KCADM create users -r $REALM `
        -s username="$id" -s enabled=true

    # Recuperar internal ID
    $json   = docker exec $KC_CONTAINER `
      $KCADM get users -r $REALM -q username="$id"
    $kcId   = ($json | ConvertFrom-Json)[0].id

    # AHORA: Resetear la contraseña a un valor común
    $newPass = 'Welcome123!'
    docker exec $KC_CONTAINER `
      $KCADM set-password -r $REALM --username $id `
        --new-password $newPass --temporary false

    # Asignar rol
    docker exec $KC_CONTAINER `
      $KCADM add-roles -r $REALM `
        --uusername $id --rolename $role
  }
}

# 3) Leer empleados de Proyecto Core
$coreEmps = docker exec proyectocore-mysql-core-1 `
  mysql -uroot -proot -N -e "SELECT id_empleado, password FROM empleado;" IngenieriaWeb `
| ForEach-Object {
    $cols = $_ -split "`t"
    [PSCustomObject]@{ id = $cols[0]; hash = $cols[1]; role = 'empleado' }
}

# 4) Leer clientes de Proyecto Core
$coreClients = docker exec proyectocore-mysql-core-1 `
  mysql -uroot -proot -N -e "SELECT id_cliente, password FROM cliente;" IngenieriaWeb `
| ForEach-Object {
    $cols = $_ -split "`t"
    [PSCustomObject]@{ id = $cols[0]; hash = $cols[1]; role = 'cliente' }
}

# 5) Leer empleados de MiniCore
$miniEmps = docker exec minicore-mysql-minicore-1 `
  mysql -uroot -proot -N -e "SELECT id_empleado, password FROM empleado;" MiniCore `
| ForEach-Object {
    $cols = $_ -split "`t"
    [PSCustomObject]@{ id = $cols[0]; hash = $cols[1]; role = 'empleado' }
}

# 6) Ejecutar migración
Migrate-Users ($coreEmps + $coreClients + $miniEmps)

Write-Host "✅ Migración completa: $(( $coreEmps + $coreClients + $miniEmps ).Count) usuarios importados."
