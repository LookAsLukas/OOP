package ru.nsu.nmashkin.task241.service;

/**
 * .
 *
 * @param buildOk .
 * @param docsOk .
 * @param styleOk .
 * @param tests .
 * @param error .
 */
public record TaskVerificationResult(
        boolean buildOk, boolean docsOk, boolean styleOk,
        TestService.TestResult tests, String error) {}
