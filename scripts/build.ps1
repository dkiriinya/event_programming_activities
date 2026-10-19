<#
.SYNOPSIS
    Build pipeline runner for PowerShell
#>
param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$BuildArgs
)

& java -cp $PSScriptRoot AgentCLI build @BuildArgs
