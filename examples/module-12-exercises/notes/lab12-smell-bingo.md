# Lab 12 — Smell Bingo

## Step 1 — Smell list
long method, magic strings for ACTIVE/PROSPECT, == on Strings, mixed I/O in domain, unclear names.

## Step 2 — Fixture tie-in
Long method: May hide logic errors that incorrectly process CUS-1001 or CUS-1002.
Magic strings: A typo such as "ACTVE" could leave CUS-1001 or CUS-1002 with the wrong status.
== on Strings: Status checks may fail even when the text matches, causing incorrect handling of CUS-1001 or CUS-1002.
Mixed I/O in domain logic: Console or file operations can interfere with testing the expected behavior for CUS-1001 and CUS-1002.
Unclear names: Increases the chance of updating or validating the wrong customer fixture.

## Step 3 — Priority
* == on Strings
* Magic strings
* 
## Scope
Pre-lab only — do not finish the full graded lab in this exercise.