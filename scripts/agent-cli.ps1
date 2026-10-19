<#
.SYNOPSIS
    AgentCLI PowerShell wrapper
#>
param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$CliArgs
)

& java -cp $PSScriptRoot AgentCLI @CliArgs
