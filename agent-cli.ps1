<#
.SYNOPSIS
    Root AgentCLI entry point for PowerShell
#>
param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$CliArgs
)

& java -cp (Join-Path $PSScriptRoot "scripts") AgentCLI @CliArgs
