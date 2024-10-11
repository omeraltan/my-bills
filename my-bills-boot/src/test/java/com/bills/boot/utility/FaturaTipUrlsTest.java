package com.bills.boot.utility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FaturaTipUrlsTest {

    @Test
    void testFaturaTipUrls() {
        assertThat(FaturaTipUrls.DEVELOPER).isEqualTo("/dev");
        assertThat(FaturaTipUrls.VERSIONS).isEqualTo("/v1");
        assertThat(FaturaTipUrls.FATURA_TIP_PROFILE).isEqualTo("/dev/v1/faturatip");
        assertThat(FaturaTipUrls.GET_ALL_FATURA_TIP).isEqualTo("/faturalar");
        assertThat(FaturaTipUrls.ID).isEqualTo("/{id}");
        assertThat(FaturaTipUrls.ACTIVE_STATUS).isEqualTo("/{id}/active-status");
    }
}
