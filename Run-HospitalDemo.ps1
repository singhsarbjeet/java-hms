$ErrorActionPreference = "Stop"

Set-Location $PSScriptRoot

$jarName = "postgresql-42.7.10.jar"
$jarPath = Join-Path $PSScriptRoot $jarName

if (-not (Test-Path $jarPath)) {
    throw "Missing JDBC driver: $jarName"
}

& javac -cp ".;$jarName" HospitalDemo.java

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

& java -cp ".;$jarName" HospitalDemo

exit $LASTEXITCODE
