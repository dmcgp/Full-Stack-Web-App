param()
# Ensure Java 17 for local run
if (-not $env:JAVA_HOME -or -not (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
  $temurin = "C:\\Program Files\\Eclipse Adoptium\\jdk-17"
  if (Test-Path $temurin) {
    $env:JAVA_HOME = $temurin
    $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
  }
}
Write-Host "JAVA_HOME=$env:JAVA_HOME"
Push-Location $PSScriptRoot
mvn -q -DskipTests=false test
mvn spring-boot:run -Dspring-boot.run.profiles=local
Pop-Location
