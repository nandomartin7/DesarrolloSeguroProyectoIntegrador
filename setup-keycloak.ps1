# setup-keycloak.ps1
# Ejecutar en PowerShell (no Git Bash)

param(
  [string]$KeycloakUrl = 'http://localhost:8080',
  [string]$AdminUser   = 'admin',
  [string]$AdminPass   = 'admin',
  [string]$Realm       = 'ProyectoFinal'
)

Write-Host "🔐 Autenticando contra $KeycloakUrl…" -ForegroundColor Cyan
$tokenResp = Invoke-RestMethod `
  -Uri "$KeycloakUrl/realms/master/protocol/openid-connect/token" `
  -Method Post `
  -ContentType 'application/x-www-form-urlencoded' `
  -Body @{ 
    client_id   = 'admin-cli'
    grant_type  = 'password'
    username    = $AdminUser
    password    = $AdminPass
  }
$token = $tokenResp.access_token

# Función auxiliar para POST JSON
function PostJson($url, $body) {
  Invoke-RestMethod `
    -Uri $url `
    -Method Post `
    -Headers @{ Authorization = "Bearer $token" } `
    -ContentType 'application/json' `
    -Body ($body | ConvertTo-Json -Depth 5)
}

# 1) Crear el realm
Write-Host "🌐 Creando realm '$Realm'…" -ForegroundColor Cyan
PostJson "$KeycloakUrl/admin/realms" @{ realm = $Realm; enabled = $true }

# 2) Crear roles
foreach ($r in 'admin','empleado','cliente') {
  Write-Host "🔑 Creando rol '$r'…" -ForegroundColor Cyan
  PostJson "$KeycloakUrl/admin/realms/$Realm/roles" @{ name = $r }
}

# 3) Crear clients de back (confidential)
foreach ($c in 'core-back','minicore-back') {
  Write-Host "🤖 Cliente backconfidential '$c'…" -ForegroundColor Cyan
  PostJson "$KeycloakUrl/admin/realms/$Realm/clients" @{
    clientId                    = $c
    enabled                     = $true
    publicClient                = $false
    protocol                    = 'openid-connect'
    directAccessGrantsEnabled   = $true
    serviceAccountsEnabled      = $true
  }
}

# 4) Crear clients de front (public)
Write-Host "🌐 Cliente core-front (public)…" -ForegroundColor Cyan
PostJson "$KeycloakUrl/admin/realms/$Realm/clients" @{
  clientId     = 'core-front'
  enabled      = $true
  publicClient = $true
  protocol     = 'openid-connect'
  redirectUris = @('http://localhost:5173/*')
  webOrigins   = @('*')
}

Write-Host "🌐 Cliente minicore-front (public)…" -ForegroundColor Cyan
PostJson "$KeycloakUrl/admin/realms/$Realm/clients" @{
  clientId     = 'minicore-front'
  enabled      = $true
  publicClient = $true
  protocol     = 'openid-connect'
  redirectUris = @('http://localhost:5174/*')
  webOrigins   = @('*')
}

# 5) Habilitar TOTP
Write-Host "🔒 Habilitando 2FA (TOTP) para realm…" -ForegroundColor Cyan
$realmConfig = @{
  realm                    = $Realm
  enabled                  = $true
  accessTokenLifespan      = 300
  otpPolicyType            = 'totp'
  otpPolicyAlgorithm       = 'HmacSHA1'
  otpPolicyDigits          = 6
  otpPolicyInitialCounter  = 0
  otpPolicyLookAheadWindow = 1
  otpPolicyPeriod          = 30
  requiredActions          = @('CONFIGURE_TOTP')
}
Invoke-RestMethod `
  -Uri "$KeycloakUrl/admin/realms/$Realm" `
  -Method Put `
  -Headers @{ Authorization = "Bearer $token" } `
  -ContentType 'application/json' `
  -Body ($realmConfig | ConvertTo-Json -Depth 5)

Write-Host "✅ Keycloak configurado." -ForegroundColor Green
