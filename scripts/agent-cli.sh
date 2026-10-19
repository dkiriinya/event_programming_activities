#!/usr/bin/env bash
# AgentCLI executable launcher for Bash / Linux / macOS / Git Bash
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
java -cp "$DIR" AgentCLI "$@"
