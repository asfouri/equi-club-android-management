# Security Policy

This Android project includes local data, user flows, and role-based screens. Security and privacy should remain part of the quality bar even for a portfolio application.

## Reporting Issues

Please report sensitive vulnerabilities privately.

Include:

- Affected screen or module
- Steps to reproduce
- Possible impact
- Suggested fix, if available

## Security Priorities

- No hardcoded sensitive credentials
- Safe local data handling
- Clear demo account boundaries
- Input validation
- Secure release configuration
- Dependency review

## Production Hardening

Before production release:

- Enable release minification where appropriate
- Review local database access rules
- Add automated tests for role-sensitive flows
- Add dependency scanning
- Review exported APK signing configuration
