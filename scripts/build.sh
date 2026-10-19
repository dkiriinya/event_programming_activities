#!/usr/bin/env bash
# Build pipeline runner for Git Bash / Linux / macOS
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
java -cp "$DIR" AgentCLI build "$@"
