package com.sat_preparation_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(MockitoExtension.class)
//public class AnalyticsServiceTest {
//
//    @Mock
//    private TestRepository testRepository;
//
//    @InjectMocks
//    private AnalyticsService analyticsService;
//
//    @Test
//    void shouldCalculateAverageScore() {
//
//        User user = new User();
//
//        List<TestAttempt> attempts = List.of(
//                new TestAttempt(1300),
//                new TestAttempt(1400)
//        );
//
//        when(testRepository.findByUser(user)).thenReturn(attempts);
//
//        AnalyticsResponse response = analyticsService.getAnalytics(user);
//
//        assertEquals(1350, response.getAverageScore());
//    }
//
//    @Test
//    void shouldReturnEmptyAnalytics_whenNoAttempts() {
//
//        User user = new User();
//
//        when(testRepository.findByUser(user)).thenReturn(List.of());
//
//        AnalyticsResponse response = analyticsService.getAnalytics(user);
//
//        assertTrue(response.isEmpty());
//    }
//}
