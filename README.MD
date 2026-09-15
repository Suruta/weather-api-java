# Weather Alert Platform

We build Weather Monitoring System. People track weather alert.

## Product
Weather Alert Platform

## Core item
Weather Alert

## Status table

| Current Status | Target Status | Allowed | Business Reason / Notes |
| :--- | :--- | :--- | :--- |
| PENDING | ACTIVE | Yes | Alert is verified and activated for region |
| ACTIVE | RESOLVED | Yes | Weather event has passed and alert is closed |
| RESOLVED | ACTIVE | No | Cannot reactivate a resolved alert; create a new alert instead |
| PENDING | RESOLVED | No | Skipped active monitoring step |

## Forbidden — why
* **RESOLVED -> ACTIVE**: Reopening an expired or resolved weather alert invalidates audit history. A new weather condition requires issuing a brand-new alert ID.
* **PENDING -> RESOLVED**: An alert cannot jump straight from pending to resolved without being activated first, as live tracking was bypassed.