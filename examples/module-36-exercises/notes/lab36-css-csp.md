# Lab 36 — XSS and CSP Notes

## Step 1 — Danger

If a malicious name contains `<script>...` and you use `dangerouslySetInnerHTML`, XSS can steal tokens.

## Step 2 — Rule

Prefer text children / React escaping; avoid HTML injection APIs.

## Step 3 — CSP

CSP can reduce inline script risk (lab may only document).

## Step 4 — Test idea

Paper test string: `Amina <b>Khan</b>` should show angle brackets as text.

## Scope
Pre-lab only — do not finish the full graded lab in this exercise.
